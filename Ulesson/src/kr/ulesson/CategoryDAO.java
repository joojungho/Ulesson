package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.util.DBUtil;

public class CategoryDAO {
	// 카테고리 추가
	public boolean insertCategory(String name, String parentName ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		int depth = 0;
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO category (ct_num,ct_name,parent_ct_num,ct_depth) VALUES (ct_seq.nextval,?,"
					+ "(SELECT ct_num FROM category WHERE ct_name=?),?)";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, parentName);
			
			if(parentName != null) {
				depth = 1;
			}
			
			pstmt.setInt(3, depth);
			
			//4단계
			int result = pstmt.executeUpdate();
			if(result != 0) flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
	
	//카테고리 삭제
	public boolean deleteCategory(String ctName) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			//JDBC 수행 1,2단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "DELETE FROM category WHERE ct_name=?";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			//?에 데이터 바인딩
			pstmt.setString(1, ctName);
			
			//JDBC 수행 4단계
			int count = pstmt.executeUpdate();
			if (count > 0) {
				flag = true;
				System.out.println(ctName + " 카테고리를 삭제했습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//자원정리
			DBUtil.executeClose(null, pstmt, conn);
		}
		return flag;
	}
	
	// 카테고리 나열
	public void selectCategory(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		String subCategory = "=(SELECT ct_num FROM category WHERE ct_name=?)";
		int cnt = 0;
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "SELECT * FROM category WHERE parent_ct_num";
					
			if (name != null) {
				sql += subCategory;
				//JDBC 수행 3단계
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, name );
			} else {
				sql += " is null";
				//JDBC 수행 3단계
				pstmt = conn.prepareStatement(sql);
			}
			
				
			//JDBC 수행 4단계
			rs = pstmt.executeQuery();
				
			System.out.println("----------------------------------");
			
			if(rs.next()) {
				do {
					System.out.print(++cnt + ". " + rs.getString("ct_name"));
					System.out.print("\t\t");
				} while (rs.next());
				System.out.println();
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
}
