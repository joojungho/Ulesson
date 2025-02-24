package kr.ulesson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BoardCommentMain {
	
	private BufferedReader br;
	private BoardCommentDAO BoardCommentDAO;
	private BoardDAO boardDAO;
	
	public BoardCommentMain(){
		try {
			br=new BufferedReader(new InputStreamReader(System.in));
			BoardCommentDAO = new BoardCommentDAO();
			// 메뉴 호출
			callMenu();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (br!=null) {
				try {
					br.close();
				} catch (Exception e) {
					
				}
			}
		}
	}
	
	private void callMenu() {
		
		
		while (true) {
			System.out.println("------------------------------------------");
	        System.out.print("1.댓글 작성, 2.댓글 수정, 3.댓글 삭제, 4.종료 > ");
	        
	        try {
	        	
				int no = Integer.parseInt(br.readLine());
				//댓글 작성
				
				if (no==1) {
					 boardDAO.selectAllBoards();
               
		               int bdNum = 0;
		               while (true) {
		                  try {                  
		                     System.out.println("------------------------------------------");
		                     System.out.print("선택할 게시글의 번호 : ");
		                     bdNum = Integer.parseInt(br.readLine());   

		                     // 게시글 존재 확인
		                     if (!boardDAO.isBoardNumberExist(bdNum)) {                        
		                        System.out.println("존재하지 않는 글 번호입니다. 다시 입력해주세요.");                  
		                     }else {
		                        break;
		                     }         
		                  } catch (NumberFormatException e) {                  
		                     System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");         
		                  }
		               }
		               boardDAO.selectBoardDetail(bdNum);
					
				

				}
			} catch (Exception e) {
				
			}

			
		}//while
		
	}
	public static void main(String[] args) {
		
	}//main

}//class
