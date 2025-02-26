package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import kr.util.DBUtil;

public class ReviewDAO {
	
	// 리뷰 조회(해당 강의의)
	public ArrayList<Item> selectReview(int lesNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<Item> result = new  ArrayList<Item>();
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT r.rv_num,m.mem_name,r.rv_content,r.rv_score FROM review r, member m WHERE r.mem_id=m.mem_id AND r.les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, lesNum);

			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
				
			System.out.println("----------------------------------");
			
			if(rs.next()) {
				do {
					System.out.println("작성자: " + rs.getString("mem_name"));
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println(rs.getString("rv_content"));
					System.out.println("\t\t" + rs.getInt("rv_score") + "점");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println();
					result.add(new Item(rs.getInt("rv_num"), rs.getString("mem_name")));
				} while (rs.next());
			} else {
				System.out.println("표시할 데이터가 없습니다.");
			}
			
			System.out.println("----------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return result;
	}
	
	// 리뷰 조회(해당 사용자의)
		public ArrayList<Item> selectReview(String id) {
			ArrayList<Item> list = new ArrayList<Item>();
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = null;
			
			try {
				//JDBC 수행 1,2 단계
				conn = DBUtil.getConnection();
				//SQL문 작성
				sql = "SELECT r.rv_num,m.mem_name,r.rv_content,r.rv_score FROM review r, member m WHERE r.mem_id=m.mem_id AND r.mem_id=?";
				//JDBC 수행 3단계
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);

				//JDBC 수행 4단계
				rs = pstmt.executeQuery();
					
				System.out.println("----------------------------------");
				
				if(rs.next()) {
					do {
						System.out.println("작성자: " + rs.getString("mem_name"));
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println(rs.getString("rv_content"));
						System.out.println("\t\t" + rs.getInt("rv_score") + "점");
						System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
						System.out.println();
						list.add(new Item(rs.getInt("rv_num"), rs.getString("mem_name")));
					} while (rs.next());
				} else {
					System.out.println("표시할 데이터가 없습니다.");
				}
				
				System.out.println("----------------------------------");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.executeClose(rs, pstmt, conn);
			}
			return list;
		}
	
	//리뷰 작성
	public boolean insertReview(int lesNum, String id, String content, int score) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int cnt = 0;

		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO review VALUES (rv_seq.nextval,?,?,?,?)";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(++cnt, lesNum);
			pstmt.setString(++cnt, id);
			pstmt.setString(++cnt, content);
			pstmt.setInt(++cnt, score);

			//4단계
			int result = pstmt.executeUpdate();
			if(result != 0) flag = true;
			updateLessonScore(lesNum);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
		return flag;
	}
	
	//리뷰 삭제
	public boolean deleteReview(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		String sql = null;
		String sql2 = null;
		boolean flag = false;
		int lessonNum = -1;
		try {
			 // JDBC 수행 1,2단계
		    conn = DBUtil.getConnection();
		    conn.setAutoCommit(false);  // 🔥 트랜잭션 시작

		    // ✅ 삭제할 리뷰의 강의 번호(les_num) 먼저 가져오기
		    sql2 = "SELECT les_num FROM review WHERE rv_num=?";
		    pstmt2 = conn.prepareStatement(sql2);
		    pstmt2.setInt(1, num);
		    rs = pstmt2.executeQuery();
		    
		    if (rs.next()) {
		        lessonNum = rs.getInt("les_num");  //삭제 전에 강의 번호 저장
		    } else {
		        System.out.println("해당 리뷰가 존재하지 않습니다.");
		        return flag;
		    }

		    // ✅ 리뷰 삭제 실행
		    sql = "DELETE FROM review WHERE rv_num = ?";
		    pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, num);
		    
		    int count = pstmt.executeUpdate();
		    if (count > 0) {
		        flag = true;
		        System.out.println("리뷰를 삭제했습니다.");
		        conn.commit();
		        updateLessonScore(lessonNum);
		    }

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//자원정리
			DBUtil.executeClose(rs, pstmt2, null);
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
	
	// 리뷰 작성 시 강의 별점 갱신
	public void updateLessonScore(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE lesson SET les_score=(SELECT NVL(AVG(rv_score),0) FROM review WHERE les_num=?) WHERE les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);

			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if (count > 0) {
				flag = true;
				System.out.println("강의 별점 갱신");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
}
