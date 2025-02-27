package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardMain_Admin {

	private static BufferedReader br;
	private static BoardDAO_User boardDAO;
	private BoardCommentDAO boardCommentDAO;
	private String id = "admin";
	private BoardService boardService;

	public BoardMain_Admin() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			boardDAO = new BoardDAO_User();
			boardCommentDAO = new BoardCommentDAO();
			boardService = new BoardService();
			// 메뉴 호출
			callMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	// 메뉴
	private void callMenu() throws IOException {
		while (true) {
			System.out.println("------------------------------------------");
			System.out.print("1.게시글 작성, 2.게시글 보기, 3.관리자 수정, 4.게시글 삭제, 5.댓글 삭제, 6.종료> ");

			try {
				int no = Integer.parseInt(br.readLine());

				if (no == 1) {
					boardService.SelectCategory();
				} else if (no == 2) {
					// 상세글보기           
					BoardCategoryDAO.selectAllBoardCategories();

					int bdNum = 0;
					int bdctNum = 0;
					boardService.SelectBoardDetail(bdNum, bdctNum);
					
					while (true) {
						System.out.println("------------------------------------------");
						System.out.println("1. 댓글 보기");
						System.out.println("2. 게시글 추천하기");
						System.out.println("3. 메뉴로 돌아가기");
						System.out.println("------------------------------------------");
						System.out.print("숫자를 입력하시오 : ");

						int choice = 0;
						try {
							choice = Integer.parseInt(br.readLine());
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
							continue;
						}
						if (choice == 1) {
							// 댓글 보기
							BoardCommentDAO.selectCommentsByBoard(bdNum);
							callMenu();
							return;
						} else if (choice == 2) {

							// 게시글 추천하기                     
							boardDAO.recommendBoard(bdNum); 
							callMenu();
							return;
						} else if (choice == 3) {
							// 메뉴로 돌아가기                      
							callMenu();
							return;   

						}   else {
							System.out.println("올바른 번호를 입력해주세요.");
						}
					}        

				} else if (no == 3) {          
					BoardCategoryDAO.selectAllBoardCategories();
					// 관리자 수정

					int bdctNum = 0;
					int bdNum = 0;

					boardService.SelectBoardDetail(bdNum, bdctNum);

					// 게시글 수정 메뉴
					while (true) {
						System.out.println("------------------수정목록-----------------");
						System.out.println("1. 카테고리 수정");
						System.out.println("2. 제목 수정");
						System.out.println("3. 내용 수정");
						System.out.println("4. 수정 종료");
						System.out.println("------------------------------------------");
						System.out.print("수정할 항목을 선택하세요: ");

						int choice = 0;
						try {
							choice = Integer.parseInt(br.readLine());
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
							continue;
						}

						if (choice == 1) {
							// 카테고리 수정
							BoardCategoryDAO.selectAllBoardCategories();
							int bdct_Num = 0;
							while (true) {
								try {
									System.out.println("---------------------------------------------");
									System.out.print("수정할 카테고리 번호 : ");
									bdct_Num = Integer.parseInt(br.readLine());

									if (!boardDAO.isBDCT_numExist(bdct_Num)) {
										System.out.println("유효하지 않은 카테고리 번호입니다. 다시 입력해주세요.");
									} else {
										boardDAO.updateBoardCategory(bdNum, bdct_Num);
										callMenu();
										return;
									}
								} catch (Exception e) {
									System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
								}
							}
						} else if (choice == 2) {
							// 제목 수정
							System.out.print("수정할 제목 입력 : ");
							String newTitle = br.readLine();
							boardDAO.updateBoardTitle(bdNum, newTitle); 
							callMenu();
							return;

						} else if (choice == 3) {
							// 내용 수정
							System.out.print("수정할 게시글 내용 입력: ");
							String newContent = br.readLine();
							boardDAO.updateBoardContent(bdNum, newContent);           	        
							callMenu();
							return;

						} else if (choice == 4) {
							// 수정 종료
							System.out.println("수정이 종료되었습니다.");
							callMenu();
							return;
						} else {
							System.out.println("올바른 번호를 입력해주세요.");
						}
					}



				}
			else if (no == 4) {
					// 게시글 삭제
					BoardCategoryDAO.selectAllBoardCategories();

					int bdNum = 0;
					int bdctNum = 0;
					boardService.SelectBoardDetail(bdNum, bdctNum);      
					boardDAO.deleteBoard(bdNum);

				} else if (no == 5) {
					//댓글 삭제
	                BoardCategoryDAO.selectAllBoardCategories();

	                int bdNum = 0;
	                int bdctNum = 0;
	                boardService.SelectBoardDetail(bdNum, bdctNum);
	                int cmtNum = 0;
	        	    while(true) {
	        	        try {
	        	            System.out.println("------------------------------------------");
	        	            System.out.print("삭제할 댓글 번호 : ");
	        	            cmtNum = Integer.parseInt(br.readLine());	            
	        	           
	        	            if (!boardCommentDAO.isCmtNumberExist(cmtNum)) {
	        	                System.out.println("존재하지 않는 댓글 번호입니다. 다시 입력해주세요.");                  
	        	            } else {
	        	            	boardCommentDAO.deleteBoardComment(cmtNum);  // 댓글 삭제	                    
        	                    break;        	             
	        	            }
	        	        } catch (NumberFormatException e) {
	        	            System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
	        	        }
	        	    }
				} else if (no==6){
					// 종료
					System.out.println("------------------------------------------");
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("------------------------------------------");
					System.out.println("잘못 입력했습니다. 다시 입력해주세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("------------------------------------------");
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
			}
		}
	}
	
	public static void main(String[] args) {
		new BoardMain_Admin();
	}

}


