package kr.ulesson;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import kr.util.DBUtil;

public class BoardCategoryDAO {
	
	// 게시판 카테고리 중복 검사
	public boolean bdctNumExist(int bdct_num) {
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT COUNT(*) FROM board_category WHERE bdct_num = ?";

	    try {
	    	
	        conn = DBUtil.getConnection();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, bdct_num);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;  
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.executeClose(rs, pstmt, conn);
	    }

	    return false; 
	}
	
	
	//카테고리 추가
	public void insertBoardCategory(int bdct_num, String bdct_name) {
		Connection conn = null;
		PreparedStatement pstmt =  null;
		String sql = null;
		
		try {
			  
			conn = DBUtil.getConnection();			
			sql = "INSERT INTO board_category(bdct_num,bdct_name) VALUES(?,?)";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bdct_num);
			pstmt.setString(2, bdct_name);
			
			int count = pstmt.executeUpdate();
			System.out.println(count + "개의 카테고리를 추가했습니다.");
					
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
	}
	//카테고리 목록
	public static void selectAllBoardCategories() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = null;
        
        
      try {
          conn = DBUtil.getConnection();
          sql = "SELECT * FROM board_category ORDER BY bdct_num DESC";
          pstmt = conn.prepareStatement(sql);
          rs = pstmt.executeQuery();
    	  
    	  System.out.println("--------------- 카테고리 목록 ---------------");
          while(rs.next())
          	System.out.println("게시판 카테고리 : " + rs.getInt("bdct_num") +"."+ rs.getString("bdct_name"));
		
	} catch (Exception e) {
		e.printStackTrace();
	}finally {
		DBUtil.executeClose(rs, pstmt, conn);
	}        
        
	}
	// 카테고리 삭제
	public void deleteBoardCategory(int bdct_num) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = null;
		
		try {
			conn = DBUtil.getConnection();
			sql = "DELETE FROM board_category WHERE bdct_num = ?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, bdct_num);
			int count = pstmt.executeUpdate();
			if (count>0) {
				System.out.println(count + "개의 게시판 카테고리를 삭제했습니다.");
				System.out.println("------------------------------------------");
			}else {
				System.out.println("해당 카테고리가 존재하지 않습니다.");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBUtil.executeClose(null, pstmt, conn);
		}
		
	}

}
