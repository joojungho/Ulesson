package kr.ulesson;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;

public class MemberDAO {


	// DB 연결 메서드
	private Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@211.238.142.200:1521:xe"; // 오라클 DB 주소 (환경에 맞게 수정)
		String user = "jteam03"; // 오라클 계정
		String password = "1234"; // 비밀번호
		return DriverManager.getConnection(url, user, password);
	}

	// 아이디 중복 체크
	public boolean isIdExists(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean exists = false;
		String sql = "SELECT COUNT(*) FROM member WHERE mem_id = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				exists = true; // 아이디 중복
			}
		} catch (Exception e) {
			System.out.println("중복되는 아이디입니다.");
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return exists;
	}


	public boolean insertMember(String mem_id, String mem_pw, int mem_auth, String mem_name, 
			String mem_cell, String mem_email, int mem_point) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		String sql = "INSERT INTO member (mem_id, mem_pw, mem_auth, mem_name, mem_cell, mem_email, mem_point, mem_mdate) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, NULL)";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem_id);
			pstmt.setString(2, mem_pw);
			pstmt.setInt(3, mem_auth);
			pstmt.setString(4, mem_name);
			pstmt.setString(5, mem_cell);
			pstmt.setString(6, mem_email);
			pstmt.setInt(7, mem_point);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return success;
	}

	// 로그인 체크
	public boolean loginCheck(String mem_id, String mem_pw) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean flag = false;
		String sql = "SELECT COUNT(*) FROM member WHERE mem_id = ? AND mem_pw = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem_id);
			pstmt.setString(2, mem_pw);
			rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				flag = true; // 로그인 성공
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return flag;
	}

	public void getMemberInfo(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM member WHERE mem_id = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				System.out.println("아이디: " + rs.getString("mem_id"));
				System.out.println("이름: " + rs.getString("mem_name"));
				System.out.println("휴대폰: " + rs.getString("mem_cell"));
				System.out.println("이메일: " + rs.getString("mem_email"));
				System.out.println("가입일: " + rs.getDate("mem_date"));
				System.out.println("마지막 수정일: " + rs.getDate("mem_mdate"));
				System.out.println("포인트: " + rs.getInt("mem_point"));
			} else {
				System.out.println("회원 정보를 찾을 수 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}

	public boolean updateMember(String mem_id, String new_pw,String new_name,String new_cell,String new_email) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE member SET mem_pw = ?, mem_name = ?, mem_cell = ?, mem_email = ?, mem_mdate = SYSDATE WHERE mem_id = ?";
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, new_pw);
			pstmt.setString(2, new_name);
			pstmt.setString(3, new_cell);
			pstmt.setString(4, new_email);
			pstmt.setString(5, mem_id);

			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return success;
	}

	//------------------------------------------------------------------------------
	//관리자 메서드
	
	//관리자 로그인
	public boolean adminLogin(String mem_id, String mem_pw) {
		boolean isAdmin = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT COUNT(*) FROM member WHERE mem_id = ? AND mem_pw = ? AND mem_auth = 9";
		
		try  {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem_id);
			pstmt.setString(2, mem_pw);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next() && rs.getInt(1) > 0) {
				isAdmin = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return isAdmin;
	}

	// 모든 회원 목록 조회
	public List<String> getAllMembers(){
		
		List<String> members = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT mem_id, mem_name, mem_auth FROM member ORDER BY mem_auth DESC, mem_id ASC";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				String info = "ID: " + rs.getString("mem_id") + 
						", 이름: " + rs.getString("mem_name") + 
						", 권한: " + (rs.getInt("mem_auth") == 9 ? "관리자" : "일반회원");
				members.add(info);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
		return members;
	}
	
	// 부적절한 회원 삭제
	public boolean deleteMember(String mem_id) {
		
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM member WHERE mem_id = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem_id);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return success;
	}
	
	// 회원 권한 변경 (일반 회원 <-> 관리자)
	public boolean updateMemberAuth(String mem_id, int newAuth) {
		boolean success = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "UPDATE member SET mem_auth = ? WHERE mem_id = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, newAuth);
			pstmt.setString(2, mem_id);
			int result = pstmt.executeUpdate();
			if (result > 0) {
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return success;
	}


}




