package kr.ulesson;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
                                  
public class NoticeMain {
    private UlessonMainYB mainMenu;
    private boolean isAdmin = false;
    private String[] option = {"모든 공지 및 이벤트 보기","공지 및 이벤트 작성","공지 및 이벤트 수정",
    		"뒤로 가기","종료"};

    public NoticeMain(UlessonMainYB mainMenu, boolean isAdmin) {
        this.mainMenu = mainMenu;
        this.isAdmin = isAdmin;
        try {
			showNotice();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public static void main(String[] args) {
        new NoticeMain(null, false);
    }   

	// 공지 목록을 조회하고 새로운 공지를 작성하는 메소드
	public void showNotice() throws ClassNotFoundException {
		NoticeDAO dao = new NoticeDAO();
		Scanner scanner = new Scanner(System.in);
		int cnt = 0;

		while(true) {
//			System.out.println("1. 모든 공지 및 이벤트 보기");
//			System.out.println("2. 공지 및 이벤트 작성");
//			System.out.println("3. 공지 및 이벤트 수정");
//			System.out.println("4. 뒤로 가기");
//			System.out.println("5. 종료");
			
			for(int i = 0, k = 0; i < option.length; i++) {
				if (!isAdmin && i < 3 && i > 0) {
					cnt++;
					continue;
				} else {
					k = i - cnt;
				}
				System.out.println( (k+1) + ". " + option[i]);
			}
			System.out.print("옵션을 선택하세요: ");

			int option = scanner.nextInt();
			scanner.nextLine();  // 버퍼 비우기

			if(option==1){// 모든 공지 및 이벤트 조회
				while(true) {
					List<Notice> notices = dao.getAllNotices();

					if (notices.isEmpty()) {
						System.out.println("현재 공지나 이벤트가 없습니다.");
					} else {
						System.out.println("현재 공지 및 이벤트 목록:");
						for (Notice notice : notices) {
							System.out.println(notice);
						}
					}
					// 뒤로가기
					System.out.println("1. 특정 번호 공지글 보기");
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
						System.out.print("조회할 공지 번호를 입력하세요: ");
						String input = scanner.nextLine();

						if (!input.trim().isEmpty()) {
							int ntNum = Integer.parseInt(input);
							Notice specificNotice = dao.getNoticeById(ntNum);
							if (specificNotice != null) {
								System.out.println("조회된 공지: " + specificNotice);
							} else {
								System.out.println("해당 번호의 공지가 없습니다.");
							}
						}			
					} else if (subOption == 2){
						break;
					} else if (subOption == 3) {
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			}
			else if (option == 2-cnt) { // 새로운 공지 작성
				while (true) {
					// 관리자 ID입력
//					System.out.print("관리자 ID를 입력하세요: ");
//					String adminId = scanner.nextLine();

					// 관리자 ID 일치하는지 확인
					if (isAdmin) {
						System.out.println("새로운 글을 작성하려면 내용을 입력하세요.");

						System.out.print("공지나 이벤트 내용: ");
						String content = scanner.nextLine();

						System.out.print("공지 또는 이벤트 타입을 선택하세요 (0 - 공지, 1 - 이벤트): ");
						int type = scanner.nextInt();
						scanner.nextLine(); // 버퍼 비우기
						// Notice 객체 생성
						Notice newNotice = new Notice(0, content, type, null);

						// NoticeDAO 객체를 통해 새로운 공지 추가
						boolean isAdded = dao.addNotice(newNotice);
						
						// 유효성 검사: 타입은 0 또는 1
						if (type != 0 && type != 1) {
							System.out.println("잘못된 입력입니다. 0 또는 1을 입력하세요.");
							continue;
						}
						
						// 결과 출력
						if (isAdded) {
							System.out.println("새로운 공지가 추가되었습니다.");
						} else {
							System.out.println("공지 추가 실패.");
						}
					} else {
						System.out.println("권한이 없습니다."); // 관리자 ID 불일치
					}

					// 뒤로가기 또는 종료
					System.out.println("1. 뒤로가기");
					System.out.println("2. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
						break;
					} else if (subOption == 2) {
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요.");
					}
				}
			} else if (option == 3-cnt) { // 공지글 수정 및 삭제
				while(true) {
					System.out.println("1. 공지글 수정");
					System.out.println("2. 공지글 삭제");
					System.out.println("3. 뒤로가기");
					System.out.println("4. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) { // 공지글 수정
//						// 관리자 ID입력
//						System.out.print("관리자 ID를 입력하세요: ");
//						String adminId = scanner.nextLine();

						// 관리자 ID 일치하는지 확인
						if (isAdmin) {				            
							//System.out.println("권한 확인되었습니다.");
							System.out.print("수정할 공지글 번호를 입력하세요: ");
							int ntNumToUpdate = scanner.nextInt();
							scanner.nextLine();

							System.out.print("수정할 공지글 타입을 입력하세요 (0 - 공지, 1 - 이벤트): ");
							int updatedNtType = scanner.nextInt();
							scanner.nextLine();

							System.out.print("수정할 공지글 내용을 입력하세요: ");
							String updatedNtContent = scanner.nextLine();

							Notice updatedNotice = new Notice(ntNumToUpdate, updatedNtContent, updatedNtType, null);
							boolean isUpdated = dao.updateNotice(updatedNotice);
							if (isUpdated) {
								System.out.println("공지글이 수정되었습니다.");
							} else {
								System.out.println("공지글 수정에 실패했습니다.");
							}

						} else {
							System.out.println("권한이 없습니다."); // 관리자 ID 불일치
							break;
						}

					} else if(subOption == 2) { // 공지글 삭제
						// 관리자 ID입력
//						System.out.print("관리자 ID를 입력하세요: ");
//						String adminId = scanner.nextLine();

						// 관리자 ID 일치하는지 확인
						if (isAdmin) {
							System.out.print("삭제할 공지글 번호를 입력하세요: ");
							int ntNumToDelete = scanner.nextInt();
							scanner.nextLine();

							boolean isDeleted = dao.deleteInquiry(ntNumToDelete);
							if (isDeleted) {
								System.out.println("공지글이 삭제되었습니다.");
							} else {
								System.out.println("공지글 삭제에 실패했습니다.");
							}
						} else {
							System.out.println("권한이 없습니다.");
							break;
						}
					}else if(subOption == 3) { // 뒤로가기
						break;
					} else if(subOption == 4) { // 종료
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");						
					}
				}
			} else if (option == 4-cnt) { // 뒤로가기
				return;
			} else if (option == 5-cnt) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력하세요");
			}
			cnt = 0;
		} // while
	}
}