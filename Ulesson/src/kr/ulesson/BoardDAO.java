package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kr.util.DBUtil;

public class BoardDAO {
	
	// 게시글 작성자 확인
	
	public boolean isWriter(int bdNum, String memId) {
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
                return true;  // 존재하는 게시판(글) 번호
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
                return true;  // 존재하는 회원 ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return false;  // 존재하지 않는 회원 ID
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
                return true;  // 존재하는 카테고리 넘버
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return false;  // 존재하지 않는 카테고리 넘버
    }

    // 글쓰기
    public void insertBoard(String memId, String bdContent, int bdCategory) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;
        try {
            conn = DBUtil.getConnection();
            
            
            sql = "INSERT INTO board (bd_num,mem_id, bd_content, bd_category, bd_date) "
                + "VALUES (board_seq.NEXTVAL,?, ?, ?, SYSDATE)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memId);
            pstmt.setString(2, bdContent);
            pstmt.setInt(3, bdCategory);

            int count = pstmt.executeUpdate();
           
            	System.out.println(count + "개의 글을 추가했습니다.");
            	System.out.println("------------------------------------------");
		
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
                System.out.println("글번호: " + rs.getInt("bd_num"));
                

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
            sql = "SELECT b.bd_num, b.mem_id, b.bd_content, b.bd_category, b.bd_date, b.bd_recommend, bc.bdct_name " +
                    "FROM board b " +
                    "JOIN Board_Category bc ON b.bd_category = bc.bdct_num " +
                    "WHERE b.bd_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bdNum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("글번호: " + rs.getInt("bd_num"));
                System.out.println("작성자: " + rs.getString("mem_id"));
                System.out.println("내용: " + rs.getString("bd_content"));
                System.out.println("카테고리: " + rs.getString("bdct_name"));
                System.out.println("작성일: " + rs.getDate("bd_date"));
                System.out.println("추천수:" + rs.getInt("bd_recommend"));
            } else {
                System.out.println("해당 번호의 게시글이 존재하지 않습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
    }

    // 글 수정
    public void updateBoard(int bdNum, String newContent) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = null;

        try {
            // 데이터베이스 연결
            conn = DBUtil.getConnection();
            // 게시글 내용 수정
            sql = "UPDATE board SET bd_content = ? WHERE bd_num = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContent);  // 수정된 내용
            pstmt.setInt(2, bdNum);  // 수정할 게시글 번호

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("게시글 " + bdNum + "번이 수정되었습니다.");
            } else {
                System.out.println("해당 게시글이 존재하지 않습니다.");
            }
         
        }catch (Exception e) {
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
				 System.out.println("해당 게시글이 존재하지 않습니다.");
			}
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }
    
    

}