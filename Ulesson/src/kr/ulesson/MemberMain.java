package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberMain {
	private BufferedReader br;
	private MemberDAO dao;
	private PointDAO pot;
	private String mem_id; // 로그인한 아이디 저장
	private boolean isLoggedIn; // 로그인 여부
	private boolean isPointInfo = false;
	private boolean isMyPage;

	public MemberMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new MemberDAO();
			pot = new PointDAO();

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
			System.out.print("\n 1. 마이페이지 | 2. 마이포인트 | 3.장바구니 | 4.내학습 | 5. 종료 >> ");
			try {
				int choice = Integer.parseInt(br.readLine());
				if (choice == 1) {
					isMyPage = true;
				} else if (choice == 2) {
					isPointInfo = true;
				} else if (choice == 3) {
					dao.wishlist(mem_id);
				} else if (choice == 4) {
					dao.myLesson(mem_id);
				} else if (choice == 5) {
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
				System.out.print("\n 1.회원 정보 조회 | 2.회원 정보 수정 | 3.뒤로가기 >> ");
				try {
					int choice = Integer.parseInt(br.readLine());
					if (choice == 1) {
						dao.getMemberInfo(mem_id);
					} else if (choice == 2) {
						updateMemberInfo();
					} else if (choice == 3){
						System.out.println("이전 메뉴로 돌아갑니다.");
						isMyPage = false;
						break;
					}
				} catch (NumberFormatException e) {
					System.out.println("[숫자만 입력 가능]");
				}
			}
			
			//마이포인트
			while (isPointInfo) {
				System.out.print("\n 1.포인트 조회 | 2.포인트 충전 | 3.뒤로가기 >>");
				try {
					int choice = Integer.parseInt(br.readLine());
					if (choice == 1) {
						pot.pointInfo(mem_id);
					} else if (choice == 2) {
						addPoint();
					} else if (choice == 3){
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

	} //callMenu




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
		System.out.println("[포인트 충전]");
		System.out.print("포인트 숫자 입력");
		int pt_value = Integer.parseInt(br.readLine());

		boolean add = pot.addPoint(mem_id, pt_value);

		if(add) {
			System.out.println("포인트 충전 완료");
		} else {
			System.out.println("포인트를 다시 입력하세요.");
		} //if-else

	} //addPoint
	




	public static void main(String[] args) {
		new MemberMain();
	} //main


} //class
