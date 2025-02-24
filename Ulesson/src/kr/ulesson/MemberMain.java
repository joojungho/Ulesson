package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MemberMain {
	private BufferedReader br;
	private MemberDAO dao;
	private PointDAO pot;
	private String mem_id; // 로그인한 아이디 저장
	private boolean isLoggedIn; // 로그인 여부

	private boolean isPointInfo = false;
	private boolean isMyPage;
	private CategoryService categoryService;
	private LessonService lessonService;
	private NoticeMain noticeMain;
	private MyLessonDAO myLessonDAO = new MyLessonDAO();

	public MemberMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new MemberDAO();
			pot = new PointDAO();
			categoryService = new CategoryService(br);
			lessonService = new LessonService(br);
			callMenu();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			} //try-catch
		} //finally

	} // MemberMain

	private void callMenu() throws IOException {
		while (true) {
			System.out.print("\n 1. 로그인 | 2. 회원가입 | 3. 종료 >> ");
			try {
				int choice = Integer.parseInt(br.readLine());
				if (choice == 1) {
					login();
					if(isLoggedIn) break;
				} else if (choice == 2) {
					register();
				} else if (choice == 3) {
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("잘못 입력했습니다. 다시 선택하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			} //try-catch

		} //while-callMenu

		// 로그인 후 추가 기능 메뉴
		while (isLoggedIn) {
			System.out.print("\n 1. 마이페이지 | 2. 강의 둘러보기 | 3. 공지사항 | 4. 종료 >> ");
			try {
				int choice = Integer.parseInt(br.readLine());

				if (choice == 1) {
					isMyPage = true;
				} else if (choice == 2) {
					// 강의 카테고리 선택
					Item result = categoryService.viewCategory(null);
					ArrayList<Item> list = lessonService.viewLesson(result.getName());

					// 강의 선택
					System.out.print("강의를 선택하세요: ");
					int num = Integer.parseInt(br.readLine());
					int lesNum = list.get(num - 1).getNumber();

					// 강의 상세 정보 출력
					lessonService.viewLessonDetail(lesNum);

					
					System.out.print("이 강의를 구매하시겠습니까? (Y/N): ");
					String buyChoice = br.readLine().trim().toUpperCase();
					if (buyChoice.equals("Y")) {
						
						PointDAO pointDAO = new PointDAO();
						pointDAO.minusPointsForLesson(mem_id, lesNum);

						
						MyLessonDAO myLessonDAO = new MyLessonDAO();
						myLessonDAO.addLesson(mem_id, lesNum);

						System.out.println("구매가 완료되었습니다! 내 학습에서 확인하세요.");
					} else {
						System.out.println("구매를 취소하셨습니다.");
					}

				} else if (choice == 3) {
					noticeMain = new NoticeMain(null, false);
				} else if (choice == 4) {
					System.out.println("프로그램을 종료합니다.");
					break;
				} else {
					System.out.println("잘못 입력했습니다. 다시 선택하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
			//마이페이지
			while (isMyPage) {
				System.out.print("\n 1.회원 정보 | 2. 포인트 관련 | 3.장바구니 | 4.내 학습 | 5.뒤로가기 >> ");
				System.out.println(); //개행

				try {
					int choice = Integer.parseInt(br.readLine());

					switch(choice) {
					case 1:
						dao.getMemberInfo(mem_id);
						System.out.println(); // 개행
						System.out.print("1. 회원 정보 수정 | 2. 뒤로가기 >> ");
						int subChoice = Integer.parseInt(br.readLine()); // 하위 메뉴 입력 받기
						if (subChoice == 1) {
							updateMemberInfo(); 
						} else if (subChoice == 2) {
							System.out.println("이전 메뉴로 돌아갑니다.");
						}
						break;

					case 2:
						isPointInfo = true;  // 포인트 관련
						break;

					case 3:
						dao.wishlist(mem_id); // 장바구니
						break;

					case 4:
						myLessonDAO.myLesson(mem_id);
						break;

					case 5:
						System.out.println("이전 메뉴로 돌아갑니다.");
						isMyPage = false;
						break;

					default:
						System.out.println("올바른 번호를 입력하세요.");
						break;
					}

				} catch (NumberFormatException e) {
					System.out.println("[숫자만 입력 가능]");
				}
				
				//마이포인트
				while (isPointInfo) {
					System.out.print("\n 1.포인트 조회 | 2.포인트 충전 | 3.뒤로가기 >>");
					try {
						int pChoice = Integer.parseInt(br.readLine());
						if (pChoice == 1) {
							pot.pointInfo(mem_id);
						} else if (pChoice == 2) {
							addPoint();
						} else if (pChoice == 3){
							System.out.println("이전 메뉴로 돌아갑니다.");
							isPointInfo = false;  // 이전 메뉴
							break;
						} else {
							System.out.println("잘못 입력했습니다. 다시 선택하세요.");
						}
					} catch (NumberFormatException e) {
						System.out.println("[숫자만 입력 가능]");
					}
				} //while-isPointInfo
			} //while-isLoggedIn
		}
	}







	// 로그인 메서드
	private void login() throws IOException {
		System.out.print("아이디(취소: 0) >> ");
		mem_id = br.readLine();
		if (mem_id.equals("0")) return;

		System.out.print("비밀번호 >> ");
		String mem_pw = br.readLine();

		if (dao.loginCheck(mem_id, mem_pw)) {
			System.out.println(mem_id + "님, 로그인 되었습니다.");
			isLoggedIn = true;
		} else {
			System.out.println("아이디 또는 비밀번호가 불일치합니다.");

		} //if-else

	} //login

	// 회원가입 메서드
	private void register() throws IOException {
		System.out.println("\n[회원가입]");
		String mem_id;

		// 아이디 중복 체크
		while (true) {
			System.out.print("아이디(취소: 0) >> ");
			mem_id = br.readLine();
			if (mem_id.equals("0")) return;

			if (dao.isIdExists(mem_id)) {
				System.out.println("이미 사용 중인 아이디입니다. 다시 입력하세요.");
			} else {
				break;
			}
		} //while

		System.out.print("비밀번호 >> ");
		String mem_pw = br.readLine();
		System.out.print("이름 >> ");
		String mem_name = br.readLine();
		System.out.print("휴대폰 번호 >> ");
		String mem_cell = br.readLine();
		System.out.print("이메일 >> ");
		String mem_email = br.readLine();

		// 기본값 설정
		int mem_auth = 1; // 일반 사용자
		int mem_point = 0; // 첫 포인트

		boolean success = dao.insertMember(mem_id, mem_pw, mem_auth, mem_name, mem_cell, mem_email, mem_point);
		if (success) {
			System.out.println("회원가입이 완료되었습니다!");
		} else {
			System.out.println("회원가입에 실패했습니다. 다시 시도해주세요.");
		}
	}

	//회원 정보 수정
	private void updateMemberInfo() throws IOException {
		System.out.println("\n[회원 정보 수정]");
		System.out.print("새로운 비밀번호");
		String new_pw = br.readLine();
		System.out.print("새로운 이름");
		String new_name = br.readLine();
		System.out.print("새로운 전화번호");
		String new_cell = br.readLine();
		System.out.print("새로운 이메일");
		String new_email = br.readLine();

		boolean success = dao.updateMember(mem_id, new_pw, new_name, new_cell, new_email);

		if (success) {
			System.out.println("회원 정보가 성공적으로 수정되었습니다.");
		} else {
			System.out.println("회원 정보를 다시 입력해주세요");
		} //if-else

	} //updateMemberInfo

	private void addPoint() throws IOException {
		System.out.println(); //개행

	    int pt_value = 0;

	    while (true) {
	        try {
	            System.out.print("포인트 숫자 입력: ");
	            pt_value = Integer.parseInt(br.readLine());

	            if (pt_value < 0) {
	                System.out.println("[오류] 0 이하의 금액은 충전할 수 없습니다. 다시 입력하세요.");
	            } else {
	                break; // 정상적인 입력이면 반복문 종료
	            }
	        } catch (NumberFormatException e) {
	            System.out.println("[숫자만 입력 가능] 올바른 금액을 입력하세요.");
	        }
	    }

	    boolean add = pot.addPoint(mem_id, pt_value);

	    if (add) {
	        System.out.println("★포인트 충전 완료★");
	    } else {
	        System.out.println("포인트 충전에 실패했습니다. 다시 시도하세요.");
	    }
	}





	public static void main(String[] args) {
		new MemberMain();
	} //main


} //class
