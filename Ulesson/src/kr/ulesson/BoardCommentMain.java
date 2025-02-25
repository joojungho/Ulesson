package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BoardCommentMain {

    private BufferedReader br;
    private BoardCommentDAO boardCommentDAO;
    private String id = "admin";

    public BoardCommentMain() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            boardCommentDAO = new BoardCommentDAO();
            // 메뉴 호출
            callMenu();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 자원 정리
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 메뉴
    private void callMenu() throws IOException {
        while (true) {
            System.out.println("------------------------------------------");
            System.out.print("1. 댓글 추가, 2. 댓글 삭제, 3. 댓글 목록 조회, 4. 종료 > ");
            try {
                int num = Integer.parseInt(br.readLine());

                if (num == 1) {
                    // 댓글 추가
                    int bdNum = 0;
                  
                    // 게시글 번호 입력
                    while (true) {
                        System.out.println("------------------------------------------");
                        System.out.print("댓글을 추가할 게시글 번호를 입력하세요: ");
                        bdNum = Integer.parseInt(br.readLine());

                        if (bdNum <= 0) {
                            System.out.println("유효하지 않은 게시글 번호입니다.");
                        } else {
                            break;
                        }
                    }

                    System.out.print("댓글 내용을 입력하세요: ");
                    String cmtContent = br.readLine();

                    boardCommentDAO.insertBoardComment(id,cmtContent, bdNum);

                } else if (num == 2) {
                    // 댓글 삭제
                    System.out.println("------------------------------------------");
                    System.out.print("삭제할 댓글 번호를 입력하세요: ");
                    int cmtNum = Integer.parseInt(br.readLine());

                    boardCommentDAO.deleteBoardComment(cmtNum);

                } else if (num == 3) {
                    // 댓글 목록 조회
                    System.out.println("------------------------------------------");
                    System.out.print("조회할 게시글 번호를 입력하세요: ");
                    int bdNum = Integer.parseInt(br.readLine());

                    boardCommentDAO.selectCommentsByBoard(bdNum);

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

    public static void main(String[] args) {
        new BoardCommentMain();
    }
}
