package kr.ulesson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.util.DBUtil;

public class PointDAO {

	private Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@211.238.142.200:1521:xe"; 
		String user = "jteam03"; // 오라클 계정
		String password = "1234"; // 비밀번호
		return DriverManager.getConnection(url, user, password);
	} //getConnection

	//포인트 충전
	public boolean addPoint(String mem_id, int mem_point) {
		boolean add = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		// point 테이블에 포인트 충전
		String sql = "INSERT INTO point (pt_num, mem_id, pt_chng_date, pt_value) VALUES (point_seq.NEXTVAL, ?, SYSDATE, ?)";
		// 충전된 포인트 member 업데이트
		String sql2 = "UPDATE member SET mem_point = mem_point + ? WHERE mem_id = ?";

		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			pstmt.setInt(2, mem_point);
			int result = pstmt.executeUpdate();
			
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, mem_point);
			pstmt2.setString(2, mem_id);
			int result2 = pstmt2.executeUpdate();

			if (result > 0 && result2 > 0) {
				conn.commit();
				add = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return add;
	} //addPoint
	
	//충전된 포인트 확인
	public void pointInfo(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sql = "SELECT * FROM point WHERE mem_id = ?";
		String sql2 = "SELECT mem_id,mem_point FROM member WHERE mem_id =?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			boolean data = true;
			while (rs.next()) {
				System.out.println("아이디" + rs.getString("mem_id"));
				System.out.println("충전일자" + rs.getDate("pt_chng_date"));
				System.out.println("충전금액" + rs.getInt("pt_value"));
				System.out.println(); //개행
			} if(!data) {
				System.out.println("충전 정보를 더 이상 찾을 수 없습니다.");
			}
			
			// 합계 포인트 조회
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, mem_id);
			rs2 = pstmt2.executeQuery();
			if (rs2.next()) {
				System.out.println(rs2.getString("mem_id") + "님의 포인트는 " + rs2.getInt("mem_point") + "점 입니다.");
			} else {
				System.out.println("충전된 정보 없음");
			}


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, null);
			DBUtil.executeClose(rs2, pstmt2, conn);
		}
		
	} //pointInfo


} //class
