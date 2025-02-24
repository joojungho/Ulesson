package kr.ulesson;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.util.DBUtil;

public class PointDAO {

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
				System.out.println(); //개행
				
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


	public void minusPointsForLesson(String memId, int lesNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false); // 트랜잭션 시작

			// 강의 가격 조회
			String sql = "SELECT les_price FROM lesson WHERE les_num = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, lesNum);
			rs = pstmt.executeQuery();

			if (!rs.next()) {
				System.out.println("해당 강의를 찾을 수 없습니다.");
				return;
			}
			int lesPrice = rs.getInt("les_price");

			// 차감될 강의 가격
			int pointToDeduct = lesPrice;

			// 현재 내 포인트 확인
			String sql2 = "SELECT mem_point FROM member WHERE mem_id = ?";
			pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setString(1, memId);
			rs2 = pstmt2.executeQuery();

			if (!rs2.next()) {
				System.out.println("회원 정보를 찾을 수 없습니다.");
				return;
			}
			int currentPoint = rs2.getInt("mem_point");

			if (currentPoint < pointToDeduct) {
				System.out.println("포인트가 부족하여 차감할 수 없습니다.");
				conn.rollback();
				return;
			}

			// 포인트 차감
			String sql3 = "INSERT INTO point (pt_num, mem_id, pt_value) " +
					"VALUES (point_seq.NEXTVAL, ?, ?)";
			pstmt3 = conn.prepareStatement(sql3);
			pstmt3.setString(1, memId);
			pstmt3.setInt(2, -pointToDeduct); // 차감이므로 음수 값
			pstmt3.executeUpdate();

			// member point update
			String sql4 = "UPDATE member SET mem_point = ( SELECT mem_point - ? FROM member WHERE mem_id = ?) WHERE mem_id = ?";
			pstmt4 = conn.prepareStatement(sql4);
			pstmt4.setInt(1, pointToDeduct);
			pstmt4.setString(2, memId);
			pstmt4.setString(3, memId);
			pstmt4.executeUpdate();

			conn.commit(); // 트랜잭션 커밋
			System.out.println(memId + "님의 포인트가 " + pointToDeduct + " 차감되었습니다. (남은 포인트: " + (currentPoint - pointToDeduct) + ")");

		} catch (Exception e) {
			try {
				if (conn != null) conn.rollback(); // 오류 발생 시 롤백
			} catch (Exception se) {
				se.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, null);
			DBUtil.executeClose(rs2, pstmt2, null);
			DBUtil.executeClose(null, pstmt3, null);
			DBUtil.executeClose(null, pstmt4, conn);
		}
	}
} //class
