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
                    System.out.print("회원 ID: ");
                    String memId = br.readLine();

                    System.out.print("게시글 내용: ");
                    String bdContent = br.readLine();

                    System.out.print("카테고리 번호: ");
                    int bdCategory = Integer.parseInt(br.readLine());

                    // BoardDAO의 insertBoard 메서드를 호출해서 입력받은 데이터 전달
                    boardDAO.insertBoard(memId, bdContent, bdCategory);

                } else if (no == 2) {
                    // 목록보기
                    boardDAO.selectAllBoards();
  
                } else if (no == 3) {
                    // 상세글보기
                    boardDAO.selectAllBoards();

                    System.out.print("선택한 글의 번호: ");
                    int bdNum = Integer.parseInt(br.readLine());

                    System.out.println("------------------");

                    boardDAO.selectBoardDetail(bdNum);

                } else if (no == 4) {
                    // 글수정
                    

                    System.out.print("수정할 글의 번호: ");
                    int bdNum = Integer.parseInt(br.readLine());
                    System.out.print("게시글 내용: ");
                    String bdContent = br.readLine();
                  

                    
                    boardDAO.updateBoard(bdNum, bdContent);

                } else if (no == 5) {
                    // 글삭제
                    boardDAO.selectAllBoards();

                    System.out.print("삭제할 글의 번호: ");
                    int bdNum = Integer.parseInt(br.readLine());

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
