package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;


	public class MyLessonDAO {

	    // 강의를 '내 학습'에 추가하는 메서드
	    public void addLesson(String memId, int lesNum) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            conn = DBUtil.getConnection();
	            conn.setAutoCommit(false); // 트랜잭션 시작

	            // 1️⃣ '내 학습'에 추가 (mylesson 테이블)
	            String sql = "INSERT INTO mylesson (ml_num, les_num, mem_id, ml_progress) " +
	                         "VALUES (ml_seq.NEXTVAL, ?, ?, 0)";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, lesNum);
	            pstmt.setString(2, memId);
	            pstmt.executeUpdate();

	            conn.commit();
	            System.out.println(memId + "님의 강의가 '내 학습'에 추가되었습니다.");

	        } catch (Exception e) {
	            try {
	                if (conn != null) conn.rollback();
	            } catch (Exception se) {
	                se.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            DBUtil.executeClose(null, pstmt, conn);
	        }
	    }
	    
	    // 강의를 '내 학습'에서 삭제하는 메서드
	    public void deleteMyLesson(String memId, int lesNum) {
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            conn = DBUtil.getConnection();
	            conn.setAutoCommit(false); // 트랜잭션 시작

	            // 1️⃣ '내 학습'에 추가 (mylesson 테이블)
	            String sql = "DELETE FROM mylesson WHERE les_num=? AND mem_id=?;";
	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, lesNum);
	            pstmt.setString(2, memId);
	            pstmt.executeUpdate();

	            conn.commit();
	            System.out.println(memId + "님의 강의가 삭제되었습니다.");

	        } catch (Exception e) {
	            try {
	                if (conn != null) conn.rollback();
	            } catch (Exception se) {
	                se.printStackTrace();
	            }
	            e.printStackTrace();
	        } finally {
	            DBUtil.executeClose(null, pstmt, conn);
	        }
	    }
	    
	    //내 학습 진행도
	    public void myLesson(String memId) {
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT m.ml_num, m.les_num, l.les_name, m.ml_progress " +
	                "FROM mylesson m " +
	                "JOIN lesson l ON m.les_num = l.les_num " +
	                "WHERE m.mem_id = ?";
			
			try {
				conn = DBUtil.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, memId);
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					do {
					int lesNum = rs.getInt("les_num");
					String lesname = rs.getString("les_name");
					System.out.println("-".repeat(50));
					System.out.println( 
							"강의번호\t" + lesNum +"\n"+ 
							"강의명\t" + lesname );
					System.out.println("-".repeat(50));
					}while(rs.next());
				} else {
					System.out.println("구매한 강의가 없습니다.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
		}
	
	

} //class
