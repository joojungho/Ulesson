package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.util.DBUtil;

public class CategoryDAO {
	// 카테고리 추가
	public boolean insertCategory(String name, String parentName ) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		boolean flag = false;
		
		try {
			//JDBC 수행 1,2 단계
			conn = DBUtil.getConnection();
			//SQL문 작성
			sql = "INSERT INTO category (ct_num,ct_name,parent_ct_num,ct_depth) VALUES (ct_seq.nextval,?,"
					+ "(SELECT ct_num FROM category WHERE ct_name=?))";
			//JDBC 수행 3단계
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, parentName);
			
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
}
