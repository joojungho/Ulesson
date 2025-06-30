package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardCategoryMain {

   private BufferedReader br;
   private BoardCategoryDAO BoardCategoryDAO;

   public BoardCategoryMain() {
      try {
         br=new BufferedReader(new InputStreamReader(System.in));
         BoardCategoryDAO = new BoardCategoryDAO();
         // 메뉴 호출
         callMenu();
      } catch (Exception e) {
         e.printStackTrace();
      }finally {
         //자원 정리
         if (br!=null) {
            try {
               br.close();
            } catch (Exception e) {

            } 
         }
      }
   }

   //메뉴
   private void callMenu() throws IOException {
      while (true) {
    	 System.out.println("------------------------------------------");
         System.out.print("1.게시판 카테고리 추가, 2. 게시판 카테고리 삭제 3. 게시판 카테고리 목록 4.종료>");
         try {
            int num = Integer.parseInt(br.readLine());

            if (num ==1) {
               int bdct_num = 0;
               // 게시판 카테고리 추가
               while(true) {
                  System.out.println("------------------------------------------");
                  System.out.print("추가할 게시판 카테고리 번호를 입력하시오>");
                  bdct_num = Integer.parseInt(br.readLine());
                  if (BoardCategoryDAO.bdctNumExist(bdct_num)) {
                     System.out.println("이미 존재하는 카테고리 번호입니다. 다른 번호를 입력하세요.");

                  } else {
                     break;
                  }
               }
               System.out.print("게시판 카테고리 이름 입력 : ");
               String bdct_name = br.readLine();

               BoardCategoryDAO.insertBoardCategory(bdct_num, bdct_name);


            }else if (num == 2) {
               // 게시판 카테고리 삭제
               kr.ulesson.BoardCategoryDAO.selectAllBoardCategories();

               System.out.print("삭제할 게시판 카테고리 번호 : ");
               int bdct_num = Integer.parseInt(br.readLine());

               BoardCategoryDAO.deleteBoardCategory(bdct_num);

            }else if (num == 3) {
               // 게시판 카테고리 목록
               kr.ulesson.BoardCategoryDAO.selectAllBoardCategories();
               System.out.println("------------------------------------------");

            } else if(num== 4) {
               //종료
               System.out.println("프로그램을 종료합니다.");
               break;
            }else {
               System.out.println("잘못 입력했습니다.");

            }

         } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
         }
      }

   }
   public static void main(String[] args) {
      new BoardCategoryMain();
   }

}


