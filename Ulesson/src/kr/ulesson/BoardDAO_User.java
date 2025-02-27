package kr.ulesson;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class BoardDAO_User {

   // 글 추천
   public void recommendBoard(int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = "UPDATE board SET bd_recommend=bd_recommend+1 WHERE bd_num = ? ";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);

         int count = pstmt.executeUpdate();
         if (count>0) {
            System.out.println("게시글 " + bdNum +"번이 추천되었습니다.");
         } else {
            System.out.println("추천할 게시글이 존재하지 않습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         DBUtil.executeClose(null, pstmt, conn);
      }

   }


   // 내 게시물 목록
   public void MyBoard(String memId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT b.bd_num, b.bd_title, b.bd_content, bc.bdct_name " +
            "FROM board b JOIN board_category bc ON b.bd_category = bc.bdct_num " + 
            "WHERE b.mem_id = ? ORDER BY b.bd_num ASC";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, memId);
         rs = pstmt.executeQuery();

         if (!rs.next()) {
            System.out.println("작성한 게시물이 없습니다.");
         } else {
            System.out.println("------------------------------------------");
            System.out.println("내 게시물 목록");
            do {
               int bdNum = rs.getInt("bd_num");
               String bdContent = rs.getString("bd_content");
               String bdTitle = rs.getString("bd_title");
               String bdCategory = rs.getString("bdct_name");
               System.out.println("------------------------------------------");
               System.out.println("게시글 번호 : " + bdNum);
               System.out.println("게시글 카테고리 : " + bdCategory);
               System.out.println("게시글 제목 : " + bdTitle);
               System.out.println("게시글 내용 : " + bdContent);

            } while (rs.next());
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
   }


   // 게시글 작성자 확인

   public boolean isBoardWriter(int bdNum, String memId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();

         sql = "SELECT mem_id FROM board WHERE bd_num = ?"; // 작성자 확인
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);
         rs = pstmt.executeQuery();

         if (rs.next()) {
            String writerId = rs.getString("mem_id");

            return writerId.equals(memId);
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return false;  
   }


   // 게시판(글) 번호 존재 여부 확인
   public boolean isBoardNumberExist(int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT COUNT(*) FROM board WHERE bd_num = ?"; 

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true; 
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return false;  // 존재하지 않는 게시판(글) 번호
   }

   // 회원 아이디 존재 여부 확인
   public boolean isMemberExist(String memId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT COUNT(*) FROM member WHERE mem_id = ?"; 

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, memId);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true;  
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return false;  
   }

   // 카테고리 존재 여부 확인
   public boolean isBDCT_numExist(int bdCategory) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT COUNT(*) FROM board_category WHERE bdct_num = ?"; 

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdCategory);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true;  
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return false;  
   }

   // 글쓰기
   public void insertBoard(String memId, String bdContent, int bdCategory, String bdTitle) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      try {
         conn = DBUtil.getConnection();


         sql = "INSERT INTO board (bd_num,bd_title,mem_id, bd_content, bd_category, bd_date) "
               + "VALUES (board_seq.NEXTVAL,?, ?, ?, ?, SYSDATE)";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1,bdTitle);
         pstmt.setString(2,memId );
         pstmt.setString(3, bdContent);
         pstmt.setInt(4,bdCategory);

         int count = pstmt.executeUpdate();

         System.out.println(count + "개의 글을 추가했습니다.");

      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

   // 목록보기
   public void selectAllBoards() {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();
         sql = "SELECT * FROM board ORDER BY bd_num DESC";
         pstmt = conn.prepareStatement(sql);
         rs = pstmt.executeQuery();

         System.out.println("--------------- 게시글 목록 ---------------");
         while (rs.next()) {
            System.out.println("------------------------------------------");
            System.out.println("게시글 번호 : " + rs.getInt("bd_num"));            
            System.out.println("게시글 제목 : " + rs.getString("bd_title"));
            System.out.println("작성자 : " + rs.getString("mem_id"));


         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
   }

   // 상세글보기
   public void selectBoardDetail(int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();

         sql = "SELECT b.bd_title, b.bd_num, b.mem_id, b.bd_content, b.bd_category, b.bd_date,b.bd_mdate, b.bd_recommend, bc.bdct_name " +
               "FROM board b " +
               "JOIN Board_Category bc ON b.bd_category = bc.bdct_num " +
               "WHERE b.bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);
         rs = pstmt.executeQuery();
         System.out.println("------------------ 상세글 ------------------");
         if (rs.next()) {
            System.out.println("------------------------------------------");
            System.out.println("글번호 : " + rs.getInt("bd_num"));
            System.out.println("카테고리 : " + rs.getString("bdct_name"));            
            System.out.println("제목 : " + rs.getString("bd_title"));
            System.out.println("내용 : " + rs.getString("bd_content"));
            System.out.println("작성자 : " + rs.getString("mem_id"));                                              
            System.out.println("작성일 : " + rs.getDate("bd_date"));
            Date mDate = rs.getDate("bd_mdate");
            if (mDate != null) {
               System.out.println("수정일 : " + mDate);
            } else {
               System.out.println("수정일 : 수정되지 않음");
            }

            System.out.println("추천수 :" + rs.getInt("bd_recommend"));

         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
   }
   // 게시글이 특정 카테고리에 속하는지 확인
   
   public boolean isBoardExistInCategory(int bdctNum, int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      ResultSet rs = null;
      
      try {
         conn = DBUtil.getConnection();
         sql = "SELECT COUNT(*) FROM board b " +
                    "JOIN board_category bc ON b.bd_category = bc.BDCT_NUM " +
                    "WHERE bc.BDCT_NUM = ? AND b.bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdctNum);
         pstmt.setInt(2, bdNum);
         rs = pstmt.executeQuery();
         
         if (rs.next() && rs.getInt(1) > 0) {
            return true;         
         }      
      } catch (Exception e) {
         e.printStackTrace();
      }   
      return false;
   }

   // 카테고리에 게시물 있는지 확인
   public boolean existBoardByCategory(int categoryNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      ResultSet rs = null;

      try {
         conn = DBUtil.getConnection();
         sql = "SELECT COUNT(*) FROM board WHERE bd_category = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, categoryNum);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true;         
         }      
      } catch (Exception e) {
         e.printStackTrace();
      }
      return false;
   }

   // 선택한 카테고리에 맞는 게시물 출력
   public void selectBoardByCategory(int categoryNum){
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      ResultSet rs = null;

      try {
         conn = DBUtil.getConnection();
         sql = "SELECT * FROM board WHERE bd_category =? ";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, categoryNum);
         rs = pstmt.executeQuery();

         boolean existBoardByCategory = false; // 게시글이 있는지 여부 확인

         while (rs.next()) {
            existBoardByCategory = true;

            System.out.println("------------------------------------------");
            System.out.println("게시글 번호 : " + rs.getInt("bd_num"));            
            System.out.println("게시글 제목 : " + rs.getString("bd_title"));
            System.out.println("작성자 : " + rs.getString("mem_id"));      
         }
         if (!existBoardByCategory) {
            System.out.println("이 카테고리에는 게시물이 없습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      }

   }

   // 게시글 내용 수정
   public void updateBoardContent(int bdNum, String newContent) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();
         sql = "UPDATE board SET bd_content = ?, bd_mdate = SYSDATE WHERE bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, newContent);
         pstmt.setInt(2, bdNum);

         int count = pstmt.executeUpdate();
         if (count > 0) {
            System.out.println("게시글 " + bdNum + "번의 내용이 수정되었습니다.");
         } else {
            System.out.println("수정할 게시글이 존재하지 않습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

   // 글 삭제
   public void deleteBoard(int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;

      try {
    	  conn = DBUtil.getConnection();
         sql = "DELETE FROM board WHERE bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);
         int count = pstmt.executeUpdate();
         
         if (count>0) {
            System.out.println("게시글 " + bdNum + "번이 삭제되었습니다.");               
         }else {
            System.out.println("삭제할 게시글이 존재하지 않습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

   //게시글 제목 수정
   public void updateBoardTitle(int bdNum, String newTitle) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();
         sql = "UPDATE board SET bd_title = ?, bd_mdate = SYSDATE WHERE bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, newTitle);
         pstmt.setInt(2, bdNum);

         int count = pstmt.executeUpdate();
         if (count > 0) {
            System.out.println("게시글 " + bdNum + "번의 제목이 수정되었습니다.");
         } else {
            System.out.println("수정할 게시글이 존재하지 않습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

   //게시글 카테고리 수정
   public void updateBoardCategory(int bdNum, int newCategory) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();
         sql = "UPDATE board SET bd_category = ?, bd_mdate = SYSDATE WHERE bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, newCategory);
         pstmt.setInt(2, bdNum);

         int count = pstmt.executeUpdate();
         if (count > 0) {
            System.out.println("게시글 " + bdNum + "번의 카테고리가 수정되었습니다.");
         } else {
            System.out.println("수정할 게시글이 존재하지 않습니다.");
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

}