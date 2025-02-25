package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import kr.util.DBUtil;

public class BoardCommentDAO {

    // 댓글 추가
    public void insertBoardComment(String memId, String cmtContent, int bdNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO board_comment (cmt_num, bd_num, mem_id, cmt_content, cmt_date) "
                + "VALUES (bd_cmt_seq.NEXTVAL, ?, ?, ?, SYSDATE)";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bdNum);      // 게시글 번호 설정
            pstmt.setString(2, memId);   // 회원 아이디 설정
            pstmt.setString(3, cmtContent);  // 댓글 내용 설정

            int count = pstmt.executeUpdate();
            System.out.println(count + "개의 댓글이 작성되었습니다.");
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn); // DB 연결 및 PreparedStatement 종료
        }
    }

    // 댓글 삭제
    public void deleteBoardComment(int cmtNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM board_comment WHERE cmt_num = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cmtNum);      // 댓글 번호 설정

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println("댓글이 삭제되었습니다.");
            } else {
                System.out.println("댓글 번호가 존재하지 않습니다.");
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn); // DB 연결 및 PreparedStatement 종료
        }
    }

    // 게시글 번호로 댓글 조회
    public void selectCommentsByBoard(int bdNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT cmt_num, mem_id, cmt_content, cmt_date FROM board_comment WHERE bd_num = ? ORDER BY cmt_num";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bdNum); // 게시글 번호 설정

            rs = pstmt.executeQuery();

            while (rs.next()) {
                int cmtNum = rs.getInt("cmt_num");
                String memId = rs.getString("mem_id");
                String cmtContent = rs.getString("cmt_content");
                Timestamp cmtDate = rs.getTimestamp("cmt_date");

                System.out.println("댓글 번호: " + cmtNum);
                System.out.println("회원 ID: " + memId);
                System.out.println("댓글 내용: " + cmtContent);
                System.out.println("작성일: " + cmtDate);
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("SQL 오류: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("기타 오류: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn); // DB 연결 및 PreparedStatement 종료
        }
    }
}
