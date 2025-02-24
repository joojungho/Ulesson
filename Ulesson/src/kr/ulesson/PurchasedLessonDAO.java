package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil; 

public class PurchasedLessonDAO {

	// 데이터 조회
	public List<PurchasedLesson> getAllPurchasedLessons() {
		List<PurchasedLesson> purchasedLessons = new ArrayList<>();        

		String sql = "SELECT p.pch_num, p.les_num, p.mem_id, p.pch_date, p.pch_status, l.les_name " +
				"FROM purchased_lesson p " +
				"JOIN lesson l ON p.les_num = l.les_num";

		try (Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){


			// 결과 처리
			while (rs.next()) {
				int pchNum = rs.getInt("pch_num");
				int lesNum = rs.getInt("les_num");
				String memId = rs.getString("mem_id");
				String pchDate = rs.getString("pch_date");
				int pchStatus = rs.getInt("pch_status");
				String lesName = rs.getString("les_name");  // les_name 가져오기       

				String pchStatusStr = (pchStatus == 0) ? "보유 중" : "환불 접수";

				// PurchasedLesson 객체에 결과를 저장
				PurchasedLesson purchasedLesson = new PurchasedLesson(pchNum, lesNum, lesName, memId, pchDate, pchStatusStr);
				purchasedLessons.add(purchasedLesson);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}              
		return purchasedLessons;  // 결과 반환
	}

	// 환불
	public boolean updatePchStatus() throws ClassNotFoundException {
		String sql = "UPDATE PURCHASED_LESSON SET PCH_STATUS = 1 WHERE PCH_STATUS = 0";
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {
			
			int rowsAffected = pstmt.executeUpdate();			
			return rowsAffected > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;  // 실패 시 false 반환
	}
	
} // class
