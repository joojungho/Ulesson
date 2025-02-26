package kr.ulesson;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



import kr.util.DBUtil;

public class BoardCommentDAO {

   // 댓글 추가
   public void insertBoardComment(String memId, String cmtContent, int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = "INSERT INTO board_comment (cmt_num, bd_num, mem_id, cmt_content, cmt_date) "
            + "VALUES (bd_cmt_seq.NEXTVAL, ?, ?, ?, SYSDATE)";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum);      
         pstmt.setString(2, memId);   
         pstmt.setString(3, cmtContent); 

         int count = pstmt.executeUpdate();
         System.out.println(count + "개의 댓글이 작성되었습니다.");
      } catch (Exception e) {      
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn);
      }
   }

   // 댓글 삭제
   public void deleteBoardComment(int cmtNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = "DELETE FROM board_comment WHERE cmt_num = ?";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cmtNum);     

         int count = pstmt.executeUpdate();
         if (count > 0) {
            System.out.println("댓글이 삭제되었습니다.");
         } else {
            System.out.println("댓글 번호가 존재하지 않습니다.");
         }
      }  catch (Exception e) {         
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(null, pstmt, conn); 
      }
   }

   // 게시글 번호로 댓글 조회
   public static void selectCommentsByBoard(int bdNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT cmt_num, mem_id, cmt_content, cmt_date FROM board_comment WHERE bd_num = ? ORDER BY cmt_num";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, bdNum); 
         rs = pstmt.executeQuery();

         System.out.println("------------------ 댓글목록 ----------------");
         while (rs.next()) {
            int cmtNum = rs.getInt("cmt_num");
            String memId = rs.getString("mem_id");
            String cmtContent = rs.getString("cmt_content");
            Date cmtDate = rs.getDate("cmt_date");
            System.out.println("------------------------------------------"); 
            System.out.println("댓글 번호 : " + cmtNum);
            System.out.println("댓글 내용 : " + cmtContent);
            System.out.println("작성자 : " + memId);
            System.out.println("작성일 : " + cmtDate);
            
               

         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
   }

   // 내 댓글 조회
   public void AllmyComments(String memId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT cmt_num, mem_id, cmt_content FROM board_comment WHERE mem_id = ? ORDER BY cmt_num";

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, memId);
         rs = pstmt.executeQuery();
         if (!rs.next()) {
            System.out.println("작성한 댓글이 없습니다.");      
         } else {
            System.out.println("------------------------------------------");
            System.out.println("내 댓글 목록");
            do {
               int cmtNum = rs.getInt("cmt_num");
               String mem_Id = rs.getString("mem_id");
               String cmtContent = rs.getString("cmt_content");
               System.out.println("------------------------------------------");
               System.out.println("댓글 번호 : " + cmtNum);
               System.out.println("작성자 : " + mem_Id);
               System.out.println("댓글 내용 : " + cmtContent);

            } while (rs.next());
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
   }

   // 댓글 작성자 확인
   public boolean isCmtWriter(int cmtNum, String memId) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = null;

      try {
         conn = DBUtil.getConnection();

         sql = "SELECT mem_id FROM board_comment WHERE cmt_num = ?"; // 작성자 확인
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cmtNum);
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

   // 댓글 번호 존재 여부 확인
   public boolean isCmtNumberExist(int cmtNum) {
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      String sql = "SELECT COUNT(*) FROM board_comment WHERE cmt_num = ?"; 

      try {
         conn = DBUtil.getConnection();
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, cmtNum);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true;  // 존재하는 댓글 번호
         }
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         DBUtil.executeClose(rs, pstmt, conn);
      }
      return false;  // 존재하지 않는 댓글 번호
   }
   
   //게시물에 댓글이 있는지 확인
   public boolean existCmtByBoard(int BoardNum) {

      Connection conn = null;
      PreparedStatement pstmt = null;
      String sql = null;
      ResultSet rs = null;
      try {
         conn = DBUtil.getConnection();
         sql = "SELECT COUNT(*) FROM board_comment WHERE bd_num = ?";
         pstmt = conn.prepareStatement(sql);
         pstmt.setInt(1, BoardNum);
         rs = pstmt.executeQuery();

         if (rs.next() && rs.getInt(1) > 0) {
            return true;         
         }      
      } catch (Exception e) {
         e.printStackTrace();
      }      
      return false;   
   }
   
   // 내 댓글 수정
   public void updateBoardCmt(int cmtNum, String newCmtContent) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    String sql = null;

	    try {
	        conn = DBUtil.getConnection();
	        sql = "UPDATE board_comment SET cmt_content = ?, cmt_mdate = SYSDATE WHERE cmt_num = ?";
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, newCmtContent);	        	   
	        pstmt.setInt(2, cmtNum);
	       	       
	        int count = pstmt.executeUpdate();
	        if (count > 0) {	            
	        } else {
	            System.out.println("수정할 댓글이 존재하지 않습니다.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.executeClose(null, pstmt, conn);
	    }
	}


}//class














