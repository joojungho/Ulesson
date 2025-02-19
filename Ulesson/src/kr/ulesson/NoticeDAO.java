package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.sql.Types;

import kr.util.DBUtil;  // DB 연결을 위한 util 클래스

public class NoticeDAO {
    
    // 모든 공지 및 이벤트 목록을 조회
    public List<Notice> getAllNotices() {
        List<Notice> notices = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT NT_NUM, NT_CONTENT, NT_TYPE, NT_DATE FROM NOTICE";
        
        try {
            conn = DBUtil.getConnection();  // DB 연결
            pstmt = conn.prepareStatement(sql);  // 쿼리 실행 준비
            rs = pstmt.executeQuery();  // 실행
            
            // 결과 처리
            while (rs.next()) {
                int ntNum = rs.getInt("NT_NUM");
                String ntContent = rs.getString("NT_CONTENT");
                int ntType = rs.getInt("NT_TYPE");
                Date ntDate = rs.getDate("NT_DATE");
                
                // Notice 객체에 결과 저장
                Notice notice = new Notice(ntNum, ntContent, ntType, ntDate);
                notices.add(notice);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);  // 자원 정리
        }
        
        return notices;  // 결과 반환
    }

    // 특정 번호의 공지 또는 이벤트 조회
    public Notice getNoticeById(int ntNum) {
        Notice notice = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT NT_NUM, NT_CONTENT, NT_TYPE, NT_DATE FROM NOTICE WHERE NT_NUM = ?";
        
        try {
            conn = DBUtil.getConnection();  // DB 연결
            pstmt = conn.prepareStatement(sql);  // 쿼리 실행 준비
            pstmt.setInt(1, ntNum);  // ntNum 바인딩
            rs = pstmt.executeQuery();  // 실행
            
            // 결과 처리
            if (rs.next()) {
                String ntContent = rs.getString("NT_CONTENT");
                int ntType = rs.getInt("NT_TYPE");
                Date ntDate = rs.getDate("NT_DATE");
                
                // Notice 객체에 결과 저장
                notice = new Notice(ntNum, ntContent, ntType, ntDate);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);  // 자원 정리
        }
        
        return notice;  // 결과 반환
    }

    // 새로운 공지나 이벤트 추가
    public boolean addNotice(Notice notice) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        // NT_NUM은 시퀀스를 통해 자동으로 증가하므로, 이를 포함할 필요 없음
        String sql = "INSERT INTO NOTICE (NT_NUM, NT_CONTENT, NT_TYPE) VALUES (NT_SEQ.NEXTVAL, ?, ?)";

        try {
            conn = DBUtil.getConnection();  // DB 연결
            pstmt = conn.prepareStatement(sql);  // 쿼리 실행 준비
            pstmt.setString(1, notice.getNtContent());  // 내용 바인딩
            pstmt.setInt(2, notice.getNtType());  // 타입 바인딩
            int rowsAffected = pstmt.executeUpdate();  // 실행

            return rowsAffected > 0;  // 삽입 성공 여부 반환
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);  // 자원 정리
        }

        return false;  // 삽입 실패
    }


}
