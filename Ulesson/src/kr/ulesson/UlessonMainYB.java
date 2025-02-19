package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class UlessonMainYB {
	private BufferedReader br;
	private String mem_id; // 로그인한 아이디 저장
	private boolean flag; // 로그인 여부

	public UlessonMainYB() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			//메뉴 호출
			callMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br!=null) try {br.close();} catch (IOException e){}
		}
	}

	//메뉴 호출
	private void callMenu() throws IOException, ClassNotFoundException{
		//로그인 체크 영역
		while(true) {
			System.out.print("1.로그인 2.회원가입 3.종료");
			try {
				int no = Integer.parseInt(br.readLine());
				if (no == 1) {
					// 로그인
					login();
				} else if (no == 2){
					// 회원가입
					register();
				} else if (no == 3) {
					// 종료
					break;
				} else {
					System.out.println("잘못 입력했습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
		//로그인 성공 후 회원제 서비스 영역
		while(flag) {			
			showMemberMenu();
			try {
				int no = Integer.parseInt(br.readLine());
				if(no == 1) {
					// 강의로 이동
					showlesson();
					break;
				} else if (no == 2) {
					// 게시판으로 이동
					showboard();
					break;
				} else if (no == 3) {
					// 내 강의로 이동
					showmylesson();
					break;
				} else if (no == 4) {
					// 구매내역으로 이동
					showPurchasedLessons();
					break;
				} else if (no == 5) {
					// 위시리스트로 이동
					showwishlist();
					break;
				} else if (no == 6) {
					// 리뷰로 이동
					showreview();
					break;
				} else if (no == 7) {
					// 포인트로 이동
					showpoint();
					break;
				} else if (no == 8) {
					// 고객센터로 이동
					showCustomerInquire();
					break; 
				} else if (no == 9) {
					// 공지게시판으로 이동
					showNotice();
					break;
				} else {

					System.out.println("잘못 입력했습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}


	// 권용범 작성 공지사항
	// 공지 목록을 조회하고 새로운 공지를 작성하는 메소드
	private void showNotice() {
		NoticeDAO dao = new NoticeDAO();
		Scanner scanner = new Scanner(System.in);

		// 모든 공지 및 이벤트 조회
		List<Notice> notices = dao.getAllNotices();

		if (notices.isEmpty()) {
			System.out.println("현재 공지나 이벤트가 없습니다.");
		} else {
			System.out.println("현재 공지 및 이벤트 목록:");
			for (Notice notice : notices) {
				System.out.println(notice);
			}
		}

		// 특정 번호의 공지 조회
		System.out.print("조회할 공지 번호를 입력하세요 (번호 입력 안 하면 건너뜁니다): ");
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

		// 새로운 공지 작성
		System.out.println("새로운 공지를 작성하려면 내용을 입력하세요.");

		System.out.print("공지나 이벤트 내용: ");
		String content = scanner.nextLine();

		System.out.print("공지 또는 이벤트 타입을 선택하세요 (0 - 공지, 1 - 이벤트): ");
		int type = scanner.nextInt();

		// 유효성 검사: 타입은 0 또는 1이어야 함
		if (type != 0 && type != 1) {
			System.out.println("잘못된 입력입니다. 0 또는 1을 입력하세요.");
			return;
		}

		// Notice 객체 생성
		Notice newNotice = new Notice(0, content, type, null);

		// NoticeDAO 객체를 통해 새로운 공지 추가
		boolean isAdded = dao.addNotice(newNotice);

		// 결과 출력
		if (isAdded) {
			System.out.println("새로운 공지가 추가되었습니다.");
		} else {
			System.out.println("공지 추가 실패.");
		}
	}

	// 메뉴 실행
	public void callNoticeMenu() {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("메뉴:");
			System.out.println("1. 공지 목록 조회 및 새로운 공지 작성");
			System.out.println("2. 종료");
			System.out.print("선택: ");
			int choice = scanner.nextInt();
			scanner.nextLine();  // 버퍼 비우기

			switch (choice) {
			case 1:
				showNotice();  // 공지 조회 및 작성
				break;
			case 2:
				System.out.println("프로그램을 종료합니다.");
				return;
			default:
				System.out.println("잘못된 선택입니다.");
				break;
			}
		}
	}

	// 프로그램 시작
	public static void notice(String[] args) throws IOException {
		UlessonMainYB main = new UlessonMainYB();
		main.callNoticeMenu();  // 메뉴 실행
	}

	// 권용범 작성 고객문의사항(고객센터)
	

    public void showCustomerInquire() throws ClassNotFoundException {
    	CustomerInquireDAO dao = new CustomerInquireDAO();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. 모든 문의글 조회");
            System.out.println("2. 문의글 작성");
            System.out.println("3. 문의글 수정");
            System.out.println("4. 답변 조회");
            System.out.println("5. 답변 작성");
            System.out.println("6. 종료");
            System.out.print("옵션을 선택하세요: ");

            int option = scanner.nextInt();
            scanner.nextLine();  // 버퍼 비우기 (nextInt 후 nextLine() 호출 필수)

            switch (option) {
                case 1:  // 모든 문의글 조회
                    List<CustomerInquire> inquiries = dao.getInquires();
                    if (inquiries.isEmpty()) {
                        System.out.println("조회된 문의글이 없습니다.");
                    } else {
                        for (CustomerInquire inquiry : inquiries) {
                            System.out.println(inquiry);
                        }
                    }
                    break;

                case 2:  // 문의글 작성
                    System.out.print("문의글 항목을 입력하세요: ");
                    String iqCate = scanner.nextLine();

                    System.out.print("문의글 내용을 입력하세요: ");
                    String iqContent = scanner.nextLine();

                    System.out.print("작성자 ID를 입력하세요: ");
                    String memId = scanner.nextLine();

                    CustomerInquire newInquiry = new CustomerInquire(0, iqCate, iqContent, memId, null, null, null, null);
                    boolean isAdded = dao.addInquiry(newInquiry);
                    if (isAdded) {
                        System.out.println("새로운 문의글이 작성되었습니다.");
                    } else {
                        System.out.println("문의글 작성에 실패했습니다.");
                    }
                    break;

                case 3:  // 문의글 수정
                    System.out.print("수정할 문의글 번호를 입력하세요: ");
                    int iqNumToUpdate = scanner.nextInt();
                    scanner.nextLine();  // 버퍼 비우기

                    System.out.print("수정할 문의글 항목을 입력하세요: ");
                    String updatedIqCate = scanner.nextLine();

                    System.out.print("수정할 문의글 내용을 입력하세요: ");
                    String updatedIqContent = scanner.nextLine();

                    CustomerInquire updatedInquiry = new CustomerInquire(iqNumToUpdate, updatedIqCate, updatedIqContent, null, null, null, null, null);
                    boolean isUpdated = dao.updateInquiry(updatedInquiry);
                    if (isUpdated) {
                        System.out.println("문의글이 수정되었습니다.");
                    } else {
                        System.out.println("문의글 수정에 실패했습니다.");
                    }
                    break;

                case 4:  // 답변 조회
                    System.out.print("답변을 조회할 문의글 번호를 입력하세요: ");
                    int iqNumToViewAnswer = scanner.nextInt();
                    scanner.nextLine();  // 버퍼 비우기

                    String answer = dao.getAnswer(iqNumToViewAnswer);
                    if (answer != null) {
                        System.out.println("답변 내용: " + answer);
                    } else {
                        System.out.println("답변이 없습니다.");
                    }
                    break;

                case 5:  // 답변 작성
                    System.out.print("답변을 작성할 문의글 번호를 입력하세요: ");
                    int iqNumToAnswer = scanner.nextInt();
                    scanner.nextLine();  // 버퍼 비우기

                    System.out.print("답변 내용을 입력하세요: ");
                    String rsContent = scanner.nextLine();

                    System.out.print("관리자 ID를 입력하세요: ");
                    String adminId = scanner.nextLine();

                    // 관리자인지 확인 (예시: admin만 가능)
                    if ("admin".equals(adminId)) {
                        boolean isAnswered = dao.addAnswer(iqNumToAnswer, rsContent);
                        if (isAnswered) {
                            System.out.println("답변이 작성되었습니다.");
                        } else {
                            System.out.println("답변 작성에 실패했습니다.");
                        }
                    } else {
                        System.out.println("관리자만 답변을 작성할 수 있습니다.");
                    }
                    break;

                case 6:  // 종료
                    System.out.println("프로그램을 종료합니다.");
                    return;

                default:
                    System.out.println("잘못된 선택입니다. 다시 시도해주세요.");
            }
            break;
        }
    }

	private void showpoint() {
		// TODO Auto-generated method stub

	}

	private void showreview() {
		// TODO Auto-generated method stub

	}

	private void showwishlist() {
		// TODO Auto-generated method stub

	}

	// 권용범 작성 구매내역조회
	private void showPurchasedLessons() {
		// PurchasedLessonDAO 인스턴스 생성
		PurchasedLessonDAO dao = new PurchasedLessonDAO();

		// 모든 purchased_lesson 데이터 가져오기
		List<PurchasedLesson> purchasedLessons = dao.getAllPurchasedLessons();

		// 조회된 purchasedLessons 목록 출력
		if (purchasedLessons.isEmpty()) {
			System.out.println("구매된 강의가 없습니다.");
		} else {
			System.out.println("구매된 강의 목록:");
			for (PurchasedLesson lesson : purchasedLessons) {
				System.out.println(lesson);  // toString 메서드가 자동 호출되어 강의 정보를 출력
			}
		}
	}




	private void showmylesson() {
		// TODO Auto-generated method stub

	}

	private void showboard() {
		// TODO Auto-generated method stub

	}

	private void showlesson() {
		// TODO Auto-generated method stub

	}

	private void showMemberMenu() {
		System.out.println("\n메뉴를 선택하세요:");
		System.out.println("1. 강의");
		System.out.println("2. 게시판");
		System.out.println("3. 내 강의");
		System.out.println("4. 구매내역");
		System.out.println("5. 위시리스트");
		System.out.println("6. 리뷰");
		System.out.println("7. 포인트");
		System.out.println("8. 고객센터");
		System.out.println("9. 공지게시판");
	}

	private void register() throws IOException { // 회원가입 메서드
		System.out.print("이름: ");
		String name = br.readLine();
		System.out.print("아이디: ");
		String id = br.readLine();
		System.out.print("비밀번호: ");
		String pw = br.readLine();
		System.out.print("이메일: ");
		String email = br.readLine();
		System.out.print("전화번호: ");
		String cell = br.readLine();
		System.out.println("회원번호: ");
		String auth = br.readLine();

		// UlessonDAO를 이용해 회원가입 처리
		UlessonDAO memberDAO = new UlessonDAO();
		boolean registerSuccess = memberDAO.register(name, id, pw, email, cell, auth);

		if (registerSuccess) {
			System.out.println("회원가입 성공!");
		} else {
			System.out.println("회원가입 실패, 이미 존재하는 아이디일 수 있습니다.");
		}

	}

	private void login() throws IOException { // 로그인 메서드
		System.out.print("아이디: ");
		String id = br.readLine();
		System.out.print("비밀번호: ");
		String pw = br.readLine();

		// MemberDAO를 이용해 로그인 체크
		UlessonDAO memberDAO = new UlessonDAO();
		boolean loginSuccess = memberDAO.loginCheck(id, pw);

		if (loginSuccess) {
			System.out.println("로그인 성공!");
			mem_id = id;  // 로그인한 사용자 아이디 저장
			flag = true;   // 로그인 성공 후 flag 설정
		} else {
			System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
		}
	}	

	


//	public static void main(String[] args) {
//		new UlessonMainYB();
//	}


}
