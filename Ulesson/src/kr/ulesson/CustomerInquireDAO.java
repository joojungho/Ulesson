package kr.ulesson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;

public class CustomerInquireDAO {

    // 모든 문의글 조회
    public List<CustomerInquire> getInquires() throws ClassNotFoundException {
        List<CustomerInquire> inquiries = new ArrayList<>();
        String sql = "SELECT IQ_NUM, IQ_CATE, IQ_CONTENT, MEM_ID, IQ_DATE, IQ_MDATE, RS_CONTENT, RS_DATE FROM CUSTOMER_INQUIRE";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int iqNum = rs.getInt("IQ_NUM");
                String iqCate = rs.getString("IQ_CATE");
                String iqContent = rs.getString("IQ_CONTENT");
                String memId = rs.getString("MEM_ID");
                Date iqDate = rs.getDate("IQ_DATE");
                Date iqMdate = rs.getDate("IQ_MDATE");
                String rsContent = rs.getString("RS_CONTENT");
                Date rsDate = rs.getDate("RS_DATE");

                CustomerInquire inquiry = new CustomerInquire(iqNum, iqCate, iqContent, memId, iqDate, iqMdate, rsContent, rsDate);
                inquiries.add(inquiry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inquiries;
    }

    // 문의글 작성
    public boolean addInquiry(CustomerInquire inquire) throws ClassNotFoundException {
        String sql = "INSERT INTO CUSTOMER_INQUIRE (IQ_NUM, IQ_CATE, IQ_CONTENT, MEM_ID) VALUES (IQ_SEQ.NEXTVAL, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inquire.getIqCate());
            pstmt.setString(2, inquire.getIqContent());
            pstmt.setString(3, inquire.getMemId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 문의글 수정
    public boolean updateInquiry(CustomerInquire inquire) throws ClassNotFoundException {
        String sql = "UPDATE CUSTOMER_INQUIRE SET IQ_CATE = ?, IQ_CONTENT = ?, IQ_MDATE = SYSDATE WHERE IQ_NUM = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inquire.getIqCate());
            pstmt.setString(2, inquire.getIqContent());
            pstmt.setInt(3, inquire.getIqNum());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 답변 조회
    public String getAnswer(int iqNum) throws ClassNotFoundException {
        String sql = "SELECT RS_CONTENT FROM CUSTOMER_INQUIRE WHERE IQ_NUM = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, iqNum);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("RS_CONTENT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

 // 답변 작성 (관리자만 가능)
 // 답변 작성 (관리자만 가능)
    public boolean addAnswer(int iqNum, String rsContent) throws ClassNotFoundException {
        // rsContent가 null이거나 빈 값이라면 null 설정
        if (rsContent == null || rsContent.trim().isEmpty()) {
            rsContent = null;  // null 값 설정
        }

        // 답변을 작성하고 RS_DATE에 SYSDATE를 삽입
        String sql = "UPDATE CUSTOMER_INQUIRE SET RS_CONTENT = ?, RS_DATE = SYSDATE WHERE IQ_NUM = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, rsContent, Types.VARCHAR);  // 답변 내용 (null 허용)
            pstmt.setInt(2, iqNum);  // 문의글 번호

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // 업데이트가 성공했으면 true 반환

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


}
