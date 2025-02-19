package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.util.DBUtil;

public class UlessonDAO {

    // 로그인 체크 메서드 (아이디, 비밀번호로 로그인 확인)
    public boolean loginCheck(String mem_id, String mem_pw) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        boolean loginSuccess = false;

        try {
            // DB 연결
            conn = DBUtil.getConnection();

            // 로그인 체크 쿼리
            String sql = "SELECT mem_id FROM member WHERE mem_id = ? AND mem_pw = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mem_id);    // 아이디
            pstmt.setString(2, mem_pw); // 비밀번호

            // 쿼리 실행
            rs = pstmt.executeQuery();

            // 결과 처리 (로그인 성공 여부)
            if (rs.next()) {
                loginSuccess = true;  // 로그인 성공
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 자원 정리
            DBUtil.executeClose(rs, pstmt, conn);
        }

        return loginSuccess;  // 로그인 성공 여부 반환
    }

    // 회원가입 메서드 (아이디, 비밀번호, 이메일, 전화번호 저장)
    public boolean register(String mem_name,String mem_id, String mem_pw, String mem_email, String mem_cell,String mem_auth) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        boolean registerSuccess = false;

        try {
            // DB 연결
            conn = DBUtil.getConnection();

            // 중복 아이디 확인 쿼리
            String checkSql = "SELECT mem_id FROM member WHERE mem_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setString(1, mem_id);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // 이미 존재하는 아이디가 있으면 false 반환
                return false;
            }

            // 회원가입 쿼리
            String sql = "INSERT INTO member (mem_name, mem_id, mem_pw, mem_email, mem_cell, mem_auth) VALUES (?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, mem_name); //이름
            pstmt.setString(2, mem_id);    // 아이디
            pstmt.setString(3, mem_pw); // 비밀번호
            pstmt.setString(4, mem_email);  // 이메일
            pstmt.setString(5, mem_cell);   // 전화번호
            pstmt.setString(6, mem_auth); // 회원번호

            // 실행
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                registerSuccess = true;  // 회원가입 성공
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            // 자원 정리
            DBUtil.executeClose(null, pstmt, conn);
        }

        return registerSuccess;  // 회원가입 성공 여부 반환
    }
}
