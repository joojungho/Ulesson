package kr.ulesson;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BoardCommentMain {

    private BufferedReader br;
    private BoardCommentDAO boardCommentDAO;
    private BoardDAO_User boardDAO;
    private String mem_id;

    public BoardCommentMain(String memId) {
        try {
            this.mem_id = memId;  // 로그인된 사용자 ID 설정
            br = new BufferedReader(new InputStreamReader(System.in));
            boardCommentDAO = new BoardCommentDAO();
            boardDAO = new BoardDAO_User();  
            callMenu();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 메뉴 호출
    private void callMenu() {
        while (true) {
            System.out.println("------------------------------------------");
            System.out.print("1.댓글 작성, 2.댓글 수정, 3.댓글 삭제, 4.종료 > ");
            
            try {
                int no = Integer.parseInt(br.readLine());
                
                if (no == 1) {
                    // 댓글 작성
                    int bdNum = 0;
                    while (true) {
                        try {
                            boardDAO.selectAllBoards();  // boardDAO 초기화된 객체 사용
                            System.out.println("------------------------------------------");
                            System.out.print("댓글을 작성할 글의 번호 : ");
                            bdNum = Integer.parseInt(br.readLine());
                            // 게시글 존재 확인
                            if (!boardDAO.isBoardNumberExist(bdNum)) {
                                System.out.println("존재하지 않는 게시글 번호입니다. 다시 입력해주세요.");
                            } else {
                                break;  // 유효한 게시글 번호면 루프 종료
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                        }
                    }
                    
                    System.out.print("댓글 내용 : ");
                    String commentContent = br.readLine();
                    
                    // 댓글 작성 메서드 호출
                    boardCommentDAO.insertBoardComment(mem_id, commentContent, bdNum);

                } else if (no == 2) {
                    // 댓글 수정
                    System.out.print("수정할 댓글 번호를 입력하세요: ");
                    int cmtNum = Integer.parseInt(br.readLine());

                    System.out.print("수정할 댓글 내용을 입력하세요: ");
                    String newContent = br.readLine();

                    // 댓글 수정 메서드 호출
                    boardCommentDAO.updateComment(cmtNum, newContent);

                } else if (no == 3) {
                    // 댓글 삭제
                    System.out.print("삭제할 댓글 번호를 입력하세요: ");
                    int cmtNum = Integer.parseInt(br.readLine());

                    // 댓글 삭제 메서드 호출
                    boardCommentDAO.deleteComment(cmtNum);

                } else if (no == 4) {
                    System.out.println("프로그램을 종료합니다.");
                    break;

                } else {
                    System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String loggedInMemId = "user1";  // 예시로 user1을 로그인된 사용자로 설정
        new BoardCommentMain(loggedInMemId);  // 로그인된 사용자 아이디를 전달
    }
}
