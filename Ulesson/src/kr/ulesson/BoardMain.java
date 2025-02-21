package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardMain {

	private BufferedReader br;
	private BoardDAO boardDAO;

	public BoardMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			boardDAO = new BoardDAO();
			// 메뉴 호출
			callMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 자원 정리
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
				}
		}
	}

	// 메뉴
	private void callMenu() throws IOException {
		while (true) {

			System.out.print("1.글쓰기, 2.목록보기, 3.상세글보기, 4.글수정, 5.글삭제, 6.종료>");
			try {
				int no = Integer.parseInt(br.readLine());

				if (no == 1) {
					// 글쓰기
					String memId = null;

					while (true) {
						System.out.print("회원 ID: ");
						memId = br.readLine();

						if (!boardDAO.isMemberExist(memId)) {
							System.out.println("------------------------------------------");
							System.out.println("잘못된 회원 ID입니다. 다시 입력해주세요.");
						} else {
							break; 
						}
					}

					BoardCategoryDAO.selectAllBoardCategories();

					int bdCategory =0;

					while(true) {
						System.out.println("------------------------------------------");
						System.out.print("카테고리 번호를 선택하시오>");										
						try {
							bdCategory = Integer.parseInt(br.readLine());
							if (!boardDAO.isBDCT_numExist(bdCategory)) {
								System.out.println("존재하지 않는 카테고리 번호입니다. 다시 입력해주세요.");		                       
							}else {
								break;
							}

						} catch (NumberFormatException e) {
							System.out.println("숫자만 입력 가능. 다시 입력해주세요.");
						}

					}
					System.out.println("------------------------------------------");
					System.out.print("게시글 내용: ");
					String bdContent = br.readLine();

					boardDAO.insertBoard(memId, bdContent, bdCategory);

				} else if (no == 2) {
					// 목록보기
					boardDAO.selectAllBoards();
					System.out.println("------------------------------------------");

				} else if (no == 3) {
					// 상세글보기
					boardDAO.selectAllBoards();


					System.out.println("------------------------------------------");
					int bdNum = 0;
					while (true) {
						try {
							System.out.print("선택할 게시글의 번호: ");
							bdNum = Integer.parseInt(br.readLine());
							System.out.println("------------------------------------------");
							// 게시글 존재 확인
							if (!boardDAO.isBoardNumberExist(bdNum)) {
								System.out.println("존재하지 않는 글 번호입니다. 다시 입력해주세요.");							
							}else {
								break;
							}			
						} catch (NumberFormatException e) {
							System.out.println("숫자만 입력 가능합니다. 다시 시도해주세요.");
						}
					}
					boardDAO.selectBoardDetail(bdNum);

				} else if (no == 4) {

					// 글수정

					while (true) {
						System.out.print("회원 ID: ");
						String memId = br.readLine();

						if (!boardDAO.isMemberExist(memId)) {
							System.out.println("잘못된 회원 ID입니다. 다시 입력해주세요.");
						} else {
							break;
						}
					}
					int bdNum = 0;
					while(true) {
						try {
							System.out.print("수정할 글의 번호: ");
							bdNum = Integer.parseInt(br.readLine());
							// 게시글 존재 확인
							if (!boardDAO.isBoardNumberExist(bdNum)) {
								System.out.println("존재하지 않는 글 번호입니다. 다시 입력해주세요.");						
							}else {

								break;
							}
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 시도해주세요.");
						}
					}
					System.out.print("게시글 내용: ");

					String bdContent = br.readLine();
					boardDAO.updateBoard(bdNum, bdContent);					

				} else if (no == 5) {
					// 글삭제

					while (true) {
						System.out.print("회원 ID: ");
						String memId = br.readLine();

						if (!boardDAO.isMemberExist(memId)) {
							System.out.println("잘못된 회원 ID입니다. 다시 입력해주세요.");
						} else {
							break; 
						}
					}

					boardDAO.selectAllBoards();

					int bdNum=0;
					while(true) {
						try {
							System.out.print("삭제할 글의 번호: ");
							bdNum = Integer.parseInt(br.readLine());
							//게시글 존재 확인
							if (!boardDAO.isBoardNumberExist(bdNum)) {
								System.out.println("존재하지 않는 글 번호입니다. 다시 입력해주세요.");						
							}else {
								break;
							}
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 시도해주세요.");
						}
					}
					boardDAO.deleteBoard(bdNum);			

				} else if (no == 6) {
					// 종료
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("잘못 입력했습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}

	public static void main(String[] args) {
		new BoardMain();
	}
}
