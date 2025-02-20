package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class sectionDAO {
	// 섹션 추가
	public boolean insertSection(String name, int lesNum, String link, int time) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int cnt = 0;
		
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO section (sec_num,les_num,sec_link,sec_time) VALUES (sec_seq.nextval,?,?,?)";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setInt(2, lesNum);
			pstmt.setString(3, link);
			pstmt.setInt(4, time);
	
			//4단계
			int result = pstmt.executeUpdate();
			if(result != 0) {
				flag = true;
				System.out.println( name + " 섹션이 추가되었습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
	
	// 섹션 수정
	public boolean updateSection(String oldName, String newName, String link, int time) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int cnt = 0;
		
		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "UPDATE section SET sec_name=?,sec_link=?,sec_time=?,sec_date=sysdate WHERE "
					+ "sec_num=(SELECT sec_num FROM section WHERE sec_name=?)";
	
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(++cnt, newName);
			pstmt.setString(++cnt, link);
			pstmt.setInt(++cnt, time);
			pstmt.setString(++cnt, oldName);
		
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if(count > 0)
			{
				flag = true;
				System.out.println(oldName + " 섹션을 수정했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
	
	// 섹션 조회(해당 강의의)
	public void selectSection(int lesNum) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT sec_num,sec_name,sec_link,sec_time FROM section WHERE les_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, lesNum);

			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
				
			System.out.println("----------------------------------");
			
			if(rs.next()) {
				System.out.println("제목\t\t링크\t\t시간");
				do {
					System.out.print(rs.getString("sec_name"));
					System.out.print("\t" + rs.getString("sec_link"));
					System.out.println("\t" + rs.getInt("sec_time") + "분");
				} while (rs.next());
			} else {
				System.out.println("표시할 데이터가 없습니다.");
			}
			
			System.out.println("----------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(rs, pstmt, conn);
		}
	}
	
	public boolean deleteSection(int num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM section WHERE sec_num=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setInt(1, num);
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if (count > 0) {
				flag = true;
				System.out.println("섹션을 삭제했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
}
