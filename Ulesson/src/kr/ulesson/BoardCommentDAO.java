package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import kr.util.DBUtil;

public class BoardCommentDAO {

    // 댓글 작성
    public void insertBoardComment(String memId, String commentContent, int bdNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO board_comment (cmt_num, bd_num, mem_id, cmt_content, cmt_date) "
                + "VALUES (bd_cmt_seq.NEXTVAL, ?, ?, ?, SYSDATE)";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bdNum);
            pstmt.setString(2, memId);  // 로그인한 사용자의 ID
            pstmt.setString(3, commentContent);

            int count = pstmt.executeUpdate();
            System.out.println(count + "개의 댓글이 작성되었습니다.");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 댓글 수정
    public void updateComment(int cmtNum, String newContent) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "UPDATE board_comment SET cmt_content = ?, cmt_mdate = SYSDATE WHERE cmt_num = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newContent);
            pstmt.setInt(2, cmtNum);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println(count + "개의 댓글이 수정되었습니다.");
            } else {
                System.out.println("댓글 수정 실패.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 댓글 삭제
    public void deleteComment(int cmtNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "DELETE FROM board_comment WHERE cmt_num = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cmtNum);

            int count = pstmt.executeUpdate();
            if (count > 0) {
                System.out.println(count + "개의 댓글이 삭제되었습니다.");
            } else {
                System.out.println("삭제할 댓글이 없습니다.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(null, pstmt, conn);
        }
    }

    // 댓글 존재 여부 확인
    public boolean isCommentExist(int cmtNum) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT COUNT(*) FROM board_comment WHERE cmt_num = ?";

        try {
            conn = DBUtil.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cmtNum);
            rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.executeClose(rs, pstmt, conn);
        }
        return false;
    }
}
