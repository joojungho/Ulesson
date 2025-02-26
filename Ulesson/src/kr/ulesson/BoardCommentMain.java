package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardCommentMain {

   private BufferedReader br;
   private BoardCommentDAO boardCommentDAO;
   private BoardDAO_User boardDAO_User;
   
   private String id = "admin";

   public BoardCommentMain(String id) {
      try {
    	 this.id = id;
         br = new BufferedReader(new InputStreamReader(System.in));
         boardCommentDAO = new BoardCommentDAO();
         boardDAO_User = new BoardDAO_User();
         // 메뉴 호출
      } catch (Exception e) {
         e.printStackTrace();
      } 
   }

   // 메뉴
   private void callMenu() throws IOException {
      while (true) {
         System.out.println("------------------------------------------");
         System.out.print("1.댓글 추가, 2.댓글 삭제, 3.댓글 목록 조회, 4.종료 > ");
         try {
            int num = Integer.parseInt(br.readLine());

            if (num == 1) {
               // 댓글 추가
               BoardCategoryDAO.selectAllBoardCategories();

               int bdNum = 0;
               int bdctNum = 0;
               while (true) {
                  try {
                     System.out.println("------------------------------------------");
                     System.out.print("선택할 카테고리의 번호 : ");
                     bdctNum = Integer.parseInt(br.readLine()); 

                     // 카테고리 확인
                     if (!boardDAO_User.isBDCT_numExist(bdctNum)) {                        
                        System.out.println("존재하지 않는 카테고리입니다. 다시 입력해주세요.");
                     } else {
                        // 카테고리에 게시물이 있는지 확인
                        boolean existBoardByCategory = boardDAO_User.existBoardByCategory(bdctNum); // 카테고리에 게시물이 있는지 확인하는 메서드 호출

                        if (!existBoardByCategory) {
                           System.out.println("이 카테고리에는 게시물이 없습니다. 다른 카테고리를 선택해주세요.");
                        } else {
                           // 선택된 카테고리에 속한 게시글만 출력
                           boardDAO_User.selectBoardByCategory(bdctNum);
                           


                           while (true) {
                              try {
                                 System.out.println("------------------------------------------");
                                 System.out.print("게시글 번호를 선택하세요: ");
                                 bdNum = Integer.parseInt(br.readLine()); // 게시글 번호 입력

                                 // 선택한 게시글이 해당 카테고리에 속하는지 확인
                                 if (boardDAO_User.isBoardExistInCategory(bdctNum, bdNum)) {
                                    // 게시글이 선택된 카테고리에 속하면 게시글을 출력
                                    boardDAO_User.selectBoardDetail(bdNum);
                                    break; // 게시글이 유효하면 루프 종료
                                 } else {
                                    System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
                                 }

                              } catch (NumberFormatException e) {
                                 System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                              }
                           }

                           break; // 카테고리가 유효하고 게시글을 선택한 경우 루프 종료
                        }
                     }   

                  } catch (Exception e) {
                     System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                  }
               }
               boardDAO_User.selectBoardDetail(bdNum);

               // 댓글 내용 입력
               System.out.println("------------------------------------------");
               System.out.print("댓글 내용을 입력하세요: ");
               String cmtContent = br.readLine();

               // 댓글 추가
               boardCommentDAO.insertBoardComment(id, cmtContent, bdNum);

            } else if (num == 2) {
               // 댓글 삭제
               String memId = id;
               boardCommentDAO.AllmyComments(id);

               int cmtNum = 0;
               while(true) {
                  try {
                     System.out.println("------------------------------------------");
                     System.out.print("삭제할 댓글 번호 : ");
                     cmtNum = Integer.parseInt(br.readLine());
                     //댓글 존재 확인

                     if (!boardCommentDAO.isCmtNumberExist(cmtNum)) {
                        System.out.println("존재하지 않는 댓글 번호입니다. 다시 입력해주세요.");                  
                     }else {
                        if (!boardCommentDAO.isCmtWriter(cmtNum, memId)) {
                           System.out.println("본인이 작성한 댓글만 삭제할 수 있습니다.");
                        } else {
                           break;
                        }
                     }
                  } catch (Exception e) {
                     System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                  }
               } boardCommentDAO.deleteBoardComment(cmtNum);

            } else if (num == 3) {
               // 댓글 목록 조회
               BoardCategoryDAO.selectAllBoardCategories();

               int bdNum = 0;
               int bdctNum = 0;
               while (true) {
                  try {
                     System.out.println("------------------------------------------");
                     System.out.print("선택할 카테고리의 번호 : ");
                     bdctNum = Integer.parseInt(br.readLine()); 

                     // 카테고리 확인
                     if (!boardDAO_User.isBDCT_numExist(bdctNum)) {                        
                        System.out.println("존재하지 않는 카테고리입니다. 다시 입력해주세요.");
                     } else {
                        // 카테고리에 게시물이 있는지 확인
                        boolean existBoardByCategory = boardDAO_User.existBoardByCategory(bdctNum); // 카테고리에 게시물이 있는지 확인하는 메서드 호출

                        if (!existBoardByCategory) {
                           System.out.println("이 카테고리에는 게시물이 없습니다. 다른 카테고리를 선택해주세요.");
                        } else {
                           // 선택된 카테고리에 속한 게시글만 출력
                           boardDAO_User.selectBoardByCategory(bdctNum);
                           


                           while (true) {
                              try {
                                 System.out.println("------------------------------------------");
                                 System.out.print("게시글 번호를 선택하세요: ");
                                 bdNum = Integer.parseInt(br.readLine()); // 게시글 번호 입력

                                 // 선택한 게시글이 해당 카테고리에 속하는지 확인
                                 if (boardDAO_User.isBoardExistInCategory(bdctNum, bdNum)) {
                                    // 게시글이 선택된 카테고리에 속하면 게시글을 출력
                                    BoardCommentDAO.selectCommentsByBoard(bdNum);
                                    break; // 게시글이 유효하면 루프 종료
                                 } else {
                                    System.out.println("선택한 게시글은 해당 카테고리에 없습니다. 다시 선택해주세요.");
                                 }

                              } catch (NumberFormatException e) {
                                 System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                              }
                           }

                           break; // 카테고리가 유효하고 게시글을 선택한 경우 루프 종료
                        }
                     }   

                  } catch (Exception e) {
                     System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                  }
               }

            } else if (num == 4) {
               // 종료
               System.out.println("프로그램을 종료합니다.");
               break;
            } else {
               System.out.println("잘못 입력했습니다.");
            }

         } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
         }
      }
   }
   
   public void deleteComment(String memId) throws IOException {
       int cmtNum = 0;
    
       while(true) {
           try {
               System.out.println("------------------------------------------");
               System.out.print("삭제할 댓글 번호 : ");
               cmtNum = Integer.parseInt(br.readLine());               
              
               if (!boardCommentDAO.isCmtNumberExist(cmtNum)) {
                   System.out.println("존재하지 않는 댓글 번호입니다. 다시 입력해주세요.");                  
               } else {
                   
                   if (!boardCommentDAO.isCmtWriter(cmtNum, memId)) {
                       System.out.println("본인이 작성한 댓글만 삭제할 수 있습니다.");
                   } else {
                      
                       boardCommentDAO.deleteBoardComment(cmtNum);  // 댓글 삭제                       
                       break;  
                   }
               }
           } catch (Exception e) {
               System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
           }
       }
   }

//   public static void main(String[] args) {
//      new BoardCommentMain();
//   }
}
