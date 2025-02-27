package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardMain_Admin {

	private static BufferedReader br;
	private static BoardDAO_User boardDAO;
	private BoardCommentDAO boardCommentDAO;
	private String id = "admin";

	public BoardMain_Admin() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			boardDAO = new BoardDAO_User();
			boardCommentDAO = new BoardCommentDAO();
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

				} else if (no == 2) {
					// 상세글보기           
					BoardCategoryDAO.selectAllBoardCategories();

					int bdNum = 0;
					int bdctNum = 0;
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
												break; // 게시글이 유효하면 종료
											} else {
												System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
											}

										} catch (NumberFormatException e) {
											System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
										}
									}
									break; // 카테고리가 유효하고 게시글을 선택한 경우 종료
								}
							}   
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
						}
					}

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

									// 게시글 번호 선택
									while (true) {
										try {
											System.out.println("------------------------------------------");
											System.out.print("게시글 번호를 선택하세요: ");
											bdNum = Integer.parseInt(br.readLine());

											// 선택한 게시글이 해당 카테고리에 속하는지 확인
											if (boardDAO.isBoardExistInCategory(bdctNum, bdNum)) {
												break; // 게시글이 존재하면 루프 종료
											} else {
												System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
											}

										} catch (NumberFormatException e) {
											System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
										}
									}
									break; // 유효한 게시글 번호를 선택한 경우 루프 종료
								}
							}
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
						}
					}

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
											bdNum = Integer.parseInt(br.readLine()); 


											if (boardDAO.isBoardExistInCategory(bdctNum, bdNum)) {
												// 게시글이 선택된 카테고리에 속하면 게시글을 출력                                   
												break; 

											} else {
												System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
											}

										} catch (NumberFormatException e) {
											System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
										}
									}
									break; 
								}
							}   
						} catch (Exception e) {
							System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
						}
					}      
					boardDAO.deleteBoard(bdNum);

				} else if (no == 5) {
					//댓글 삭제
	                BoardCategoryDAO.selectAllBoardCategories();

	                int bdNum = 0;
	                int bdctNum = 0;
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
	                         boolean existBoardByCategory = boardDAO.existBoardByCategory(bdctNum); // 카테고리에 게시물이 있는지 확인하는 메서드 호출

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
	                                     // 게시글이 선택된 카테고리에 속하면 게시글을 출력
	                                     BoardCommentDAO.selectCommentsByBoard(bdNum);
	                                     break; 
	                                  } else {
	                                     System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
	                                  }

	                               } catch (NumberFormatException e) {
	                                  System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
	                               }
	                            }

	                            break; 
	                         }
	                      }   

	                   } catch (Exception e) {
	                      System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
	                   }
	                }
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


