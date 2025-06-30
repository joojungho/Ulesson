package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardService {
    private static BoardDAO_User boardDAO;
    private static BufferedReader br;
    
    public BoardService(BoardDAO_User boardDAO) {
        this.boardDAO = boardDAO;
        this.br = new BufferedReader(new InputStreamReader(System.in));


       
    }
    
	
    //게시글 출력메서드(카테고리 검사)
	public static void selectBoardInCategory(int bdctNum) {
        while (true) {
            try {
                System.out.println("------------------------------------------");
                System.out.print("게시글 번호를 선택하세요: ");
                int bdNum = Integer.parseInt(br.readLine()); // 게시글 번호 입력

                // 선택한 게시글이 해당 카테고리에 속하는지 확인
                if (boardDAO.isBoardExistInCategory(bdctNum, bdNum)) {
                    // 게시글이 선택된 카테고리에 속하면 게시글 출력
                    boardDAO.selectBoardDetail(bdNum);
                    break; // 게시글이 유효하면 종료
                } else {
                    System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
            } catch (IOException e) {
                System.out.println("입력 오류가 발생했습니다. 다시 시도해주세요.");
            }
        }
    }
	//카테고리 선택 메서드
	public void SelectBoard(int bdCategory) throws IOException {
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
	}
	//상세글 확인 메서드
	public void SelectBoardDetail(int bdNum) throws NumberFormatException, IOException {
		
		int bdctNum = Integer.parseInt(br.readLine());
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
						BoardService.selectBoardInCategory(bdctNum);
						break; // 카테고리가 유효하고 게시글을 선택한 경우 종료
					}
				}   
			} catch (Exception e) {
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
			}
		}
		
	}
	
	public void UpdateCategory(int bdct_Num) {
		BoardMain_User boardMain = new BoardMain_User();
		int bdNum = 0;
		bdct_Num = 0;
		while (true) {
			try {
				System.out.println("---------------------------------------------");
				System.out.print("수정할 카테고리 번호 : ");
				bdct_Num = Integer.parseInt(br.readLine());

				if (!boardDAO.isBDCT_numExist(bdct_Num)) {
					System.out.println("유효하지 않은 카테고리 번호입니다. 다시 입력해주세요.");
				} else {
					boardDAO.updateBoardCategory(bdNum, bdct_Num);
					System.out.println("카테고리가 수정되었습니다.");
					 
			        boardMain.callMenu();
					
					return;
				}
			} catch (Exception e) {
				System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
			}
		}

	}

	
	

}//class
