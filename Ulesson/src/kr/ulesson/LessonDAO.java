package kr.ulesson;
//test 1
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.util.DBUtil;

public class LessonDAO {
	// 강의 추가(관리자)
	public boolean insertLesson(String name, String teacher, int price, String detail, String ctName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int cnt = 0;

		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO lesson (les_num,les_name,les_teacher,les_price,les_detail,ct_num) VALUES (les_seq.nextval,?,?,?,?,"
					+ "(SELECT ct_num FROM category WHERE ct_name=?))";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(++cnt, name);
			pstmt.setString(++cnt, teacher);
			pstmt.setInt(++cnt, price);
			pstmt.setString(++cnt, detail);
			pstmt.setString(++cnt, ctName);

			//4단계
			int result = pstmt.executeUpdate();
			if(result != 0) flag = true;
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		}
		finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}

	//강의 세부사항 조회
	public void selectLessonDetail(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT l.*,c.ct_name FROM lesson l JOIN category c ON l.ct_num=c.ct_num WHERE l.les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();


			System.out.println("----------------------------------");

			if(rs.next()) {
				do {
					System.out.println("번호: " + rs.getInt("les_num"));
					System.out.println("제목: " + rs.getString("les_name"));
					System.out.println("강사: " + rs.getString("les_teacher"));
					System.out.println("가격: " + rs.getInt("les_price"));
					System.out.println("개요: " + rs.getString("les_detail"));
					System.out.println("완강시간: " + rs.getInt("les_time"));
					System.out.println("카테고리: " + rs.getString("ct_name"));
					System.out.println("별점: " + rs.getDouble("les_score"));
				} while (rs.next());
			} else {
				System.out.println("표시할 데이터가 없습니다.");
			}

			System.out.println("----------------------------------");
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	//강의 수정(관리자)
	public boolean updateLesson(String name, String teacher, int price, String detail, int time, String ctName, int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int cnt = 0;

		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE lesson SET les_name=?,les_teacher=?,les_mdate=SYSDATE,les_price=?,les_detail=?,les_time=?,ct_num=("
					+ "SELECT ct_num FROM category WHERE ct_name=?"
					+ ") WHERE les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, name);
			pstmt.setString(++cnt, teacher);
			pstmt.setInt(++cnt, price);
			pstmt.setString(++cnt, detail);
			pstmt.setInt(++cnt, time);
			pstmt.setString(++cnt, ctName);
			pstmt.setInt(++cnt, num);

			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if(count > 0)
			{
				flag = true;
				System.out.println(num + "번 강의를 수정했습니다.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}

	//강의 별점 수정
	public boolean updateLesson(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE lesson SET les_score=(SELECT avg(rv_score) FROM review GROUP BY les_num HAVING les_num=?) WHERE les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			pstmt.setInt(2, num);

			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if(count > 0)
			{
				flag = true;
				System.out.println("별점을 갱신했습니다.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}

	// 카테고리로 강의 조회
	public ArrayList<Item> selectLessonByCategory(String ctname) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		int cnt = 0;
		ArrayList<Item> result = new ArrayList<Item>();
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT les_num,les_name,les_teacher,les_score,les_price FROM lesson WHERE ct_num="
					+ "(SELECT ct_num FROM category WHERE ct_name=?)";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, ctname);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();

			System.out.println("----------------------------------");

			if(rs.next()) {
				System.out.println("제목\t\t강사\t평점\t가격");
				do {
					System.out.print(++cnt + ". " + rs.getString("les_name"));
					System.out.print("\t" + rs.getString("les_teacher"));
					System.out.print("\t" + rs.getDouble("les_score"));
					System.out.println("\t" + rs.getInt("les_price") + "원");
					result.add(new Item(rs.getInt("les_num"),rs.getString("les_name")));
				} while (rs.next());
			} else {
				System.out.println("표시할 데이터가 없습니다.");
			}

			System.out.println("----------------------------------");
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return result;
	}

	// 해당 단어를 가진 강의 조회
	public ArrayList<Item> selectLessonSearch(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		ArrayList<Item> result = new ArrayList<Item>();
		int cnt = 0;

		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT les_num,les_name,les_teacher,les_score,les_price FROM lesson "
					+ "WHERE les_name LIKE ? OR les_teacher LIKE ?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);

			String searchKeyword = "%" + keyword + "%";
			pstmt.setString(1, searchKeyword );
			pstmt.setString(2, searchKeyword);
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();

			System.out.println("----------------------------------");

			if(rs.next()) {
				System.out.println("제목\t\t강사\t평점\t가격");
				do {
					System.out.print(++cnt + ". " + rs.getString("les_name"));
					System.out.print("\t" + rs.getString("les_teacher"));
					System.out.print("\t" + rs.getInt("les_score"));
					System.out.println("\t" + rs.getInt("les_price") + "원");
					result.add(new Item(rs.getInt("les_num"),rs.getString("les_name")));
				} while (rs.next());
			} else {
				System.out.println("표시할 데이터가 없습니다.");
			}

			System.out.println("----------------------------------");
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return result;
	}

	// 강의 삭제(관리자)
	public boolean deleteLesson(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;

		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM lesson WHERE les_num=(SELECT les_num FROM lesson WHERE les_name=?)";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, name);

			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if (count > 0) {
				flag = true;
				System.out.println(name + " 강의를 삭제했습니다.");
			}
		} catch (ClassNotFoundException e) {
			System.out.println("[데이터베이스 연결 오류]");
		} catch (SQLException e) {
			System.out.println("[잘못된 처리]");
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
}
