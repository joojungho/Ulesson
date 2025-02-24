package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class BoardCommentDAO {
	
	// 댓글 작성
	public void insertBoardComment(String memId, String BoardComment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		try {
			conn = DBUtil.getConnection();
			
			sql = "INSERT INTO board_comment(cmt_num,mem_id,cmt_content,cmt_date)"
					+"VALUES(?,?,?,	SYSDATE)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,memId);
			pstmt.setString(2, BoardComment);
			
			int count = pstmt.executeUpdate();
			
			System.out.println(count + "개의 댓글을 추가했습니다.");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//상세글보기-댓글쓰기전
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
	            System.out.println("------------------------------------------");
	            System.out.println("글번호: " + rs.getInt("bd_num"));
	            System.out.println("작성자: " + rs.getString("mem_id"));
	            System.out.println("내용: " + rs.getString("bd_content"));
	            System.out.println("카테고리: " + rs.getString("bdct_name"));
	            System.out.println("작성일: " + rs.getDate("bd_date"));
	            System.out.println("추천수:" + rs.getInt("bd_recommend"));
	            
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         DBUtil.executeClose(rs, pstmt, conn);
	      }
	   }

}//class
