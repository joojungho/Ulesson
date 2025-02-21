package kr.ulesson;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import kr.util.DBUtil;

public class MemberDAO {


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

	//회원가입
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

	//사용자 정보 조회
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
	} //getMemberInfo

	//사용자 정보 수정
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
	} //updateMember

	//장바구니
	public void wishlist (String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT w.wish_num, w.mem_id, l.les_num, l.les_name, l.les_price " +
				"FROM wishlist w " +
				"JOIN lesson l ON w.les_num = l.les_num " +
				"WHERE w.mem_id = ?";

		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();

			System.out.println("==== " + memId + "님의 위시리스트 ====");
			while (rs.next()) {
				int wishNum = rs.getInt("wish_num");
				int lesNum = rs.getInt("les_num");
				String lesName = rs.getString("les_name");
				int lesPrice = rs.getInt("les_price");

				System.out.println("위시번호: " + wishNum +
						", 강의번호: " + lesNum +
						", 강의명: " + lesName +
						", 가격: " + lesPrice + "원");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	//내학습
	public void myLesson(String memId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT m.ml_num, m.les_num, l.les_name, m.ml_progress " +
                "FROM mylesson m " +
                "JOIN lesson l ON m.les_num = l.les_num " +
                "WHERE m.mem_id = ?";
		
		try {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memId);
			rs = pstmt.executeQuery();
			
			System.out.println(memId + "님의 학습 진행도");
			while (rs.next()) {
				
				int lesNum = rs.getInt("les_num");
				String lesname = rs.getString("les_name");
				int mlPg = rs.getInt("ml_progress");
				System.out.println("-".repeat(50));
				System.out.println( 
						"강의번호\t" + lesNum +"\n"+ 
						"강의명\t" + lesname +"\n"+ 
						"진행도\t" + mlPg+"/100");
				System.out.println("-".repeat(50));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
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
	} //getAllMembers

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
	} //deleteMember

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
	} //updateMemberAuth

	// 업데이트 할 ID 유효성 검사 메서드
	public boolean isUpdateMember(String mem_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String query = "SELECT COUNT(*) FROM member WHERE mem_id = ?";
		try  {
			conn = DBUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, mem_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0; // 1 이상이면 존재하는 ID
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}





} //class




