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
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT p.pch_num, p.les_num, p.mem_id, p.pch_date, p.pch_status, l.les_name " +
                     "FROM purchased_lesson p " +
                     "JOIN lesson l ON p.les_num = l.les_num";
        
        try {
            conn = DBUtil.getConnection();  // DB 연결
            pstmt = conn.prepareStatement(sql);  // 쿼리 실행 준비
            rs = pstmt.executeQuery();  // 실행
            
            // 결과 처리
            while (rs.next()) {
                int pchNum = rs.getInt("pch_num");
                int lesNum = rs.getInt("les_num");
                String memId = rs.getString("mem_id");
                String pchDate = rs.getString("pch_date");
                int pchStatus = rs.getInt("pch_status");
                String lesName = rs.getString("les_name");  // les_name 가져오기       
                 
                String pchStatusStr = (pchStatus == 0) ? "보유 중" : "환불처리";
                
                // PurchasedLesson 객체에 결과를 저장
                PurchasedLesson purchasedLesson = new PurchasedLesson(pchNum, lesNum, lesName, memId, pchDate, pchStatusStr);
                purchasedLessons.add(purchasedLesson);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);  // 자원 정리
        }
        
        return purchasedLessons;  // 결과 반환
    }
}
