package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardService {
	private BoardDAO_User boardDAO = new BoardDAO_User();
	private String id = "admin";
	private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	// 게시물 확인 메서드
	public void BoardByCategory(int bdNum,int bdctNum) throws IOException{
		while (true) {
			try {
				System.out.println("------------------------------------------");
				System.out.print("게시글 번호를 선택하세요: ");
				bdNum = Integer.parseInt(br.readLine()); // 게시글 번호 입력

				// 선택한 게시글이 해당 카테고리에 속하는지 확인
				if (boardDAO.isBoardExistInCategory(bdctNum, bdNum)) {
					// 게시글이 선택된 카테고리에 속하면 게시글 출력
					boardDAO.selectBoardDetail(bdNum);
					break; // 게시글이 유효하면 종료
				} else {
					System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
				}

			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.222");
			}
		}
		
	}
	//카테고리 선택 메서드
	public void SelectCategory() throws IOException {

		// 글쓰기

		BoardCategoryDAO.selectAllBoardCategories();

		int bdCategory =0;

		while(true) {
			System.out.println("------------------------------------------");
			System.out.print("카테고리 번호 선택 : ");                              
			try {
				bdCategory = Integer.parseInt(br.readLine());
				if (!boardDAO.isBDCT_numExist(bdCategory)) {
					System.out.println("존재하지 않는 카테고리 번호입니다. 다시 입력해주세요.");                             
				}else {
					break;
				}

			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
			}
		}
		System.out.print("게시글 제목 : ");
		String bdTitle = br.readLine();
		System.out.print("게시글 내용 : ");
		String bdContent = br.readLine();

		boardDAO.insertBoard(id, bdContent, bdCategory, bdTitle);

	
	}
	//댓글 추가 메서드
	public void insertComment(int bdNum ) throws IOException {
		
		// 댓글 추가						
		System.out.println("------------------------------------------");
		System.out.print("댓글을 작성하시겠습니까?(y/n) >");
		String answer = br.readLine();

		if (answer.matches("[Yy]")) {
			// 댓글 내용 입력
			System.out.println("------------------------------------------");
			System.out.print("댓글 내용을 입력하세요: ");
			String cmtContent = br.readLine();

			// 댓글 추가
			new BoardCommentDAO().insertBoardComment(id, cmtContent, bdNum);
		}
	}

	
	//상세글 보기 메서드
	public int SelectBoardDetail(int bdNum, int bdctNum) {
		while (true) {
			try {
				System.out.println("------------------------------------------");
				System.out.print("선택할 카테고리의 번호 : ");
				bdctNum = Integer.parseInt(br.readLine()); 

				// 카테고리 확인
				if (!boardDAO.isBDCT_numExist(bdctNum)) {                        
					System.out.println("존재하지 않는 카테고리입니다. 다시 입력해주세요.");
				} else {
					// 카테고리에 게시물이 있는지 확인
					boolean existBoardByCategory = boardDAO.existBoardByCategory(bdctNum);

					if (!existBoardByCategory) {
						System.out.println("이 카테고리에는 게시물이 없습니다. 다른 카테고리를 선택해주세요.");
					} else {
						// 선택된 카테고리에 속한 게시글만 출력
						boardDAO.selectBoardByCategory(bdctNum);

						while (true) {
							try {
								System.out.println("------------------------------------------");
								System.out.print("게시글 번호를 선택하세요: ");
								bdNum = Integer.parseInt(br.readLine()); // 게시글 번호 입력

								// 선택한 게시글이 해당 카테고리에 속하는지 확인
								if (boardDAO.isBoardExistInCategory(bdctNum, bdNum)) {
									// 게시글이 선택된 카테고리에 속하면 게시글 출력
									boardDAO.selectBoardDetail(bdNum);									 
									return bdNum;
								} else {
									System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
								}

							} catch (NumberFormatException e) {
								System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
							}
						}
						//break; // 카테고리가 유효하고 게시글을 선택한 경우 종료
					}
				}   
			} catch (Exception e) {
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
			}
		
			
		}
		

		
	}
}
