package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class UlessonMainYB {
	private BufferedReader br;
	private String mem_id; // 로그인한 아이디 저장
	private boolean flag; // 로그인 여부
	public boolean isLoggedIn;

	public String getMemId() {
		return mem_id;
	}

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
	public void callMenu() throws IOException, ClassNotFoundException, SQLException {
		while (true) {
			System.out.print("1.로그인 2.회원가입 3.종료");
			try {
				int no = Integer.parseInt(br.readLine());
				if (no == 1) {
					// 로그인
					login();
					if (isLoggedIn) {
						// 로그인 성공 시, 회원제 서비스 메뉴     
						while (isLoggedIn) {
							showMemberMenu();
							try {
								int no2 = Integer.parseInt(br.readLine());  // no2를 사용해야 합니다.
								if (no2 == 1) {
									// 강의로 이동
									showlesson();
								} else if (no2 == 2) {
									// 게시판으로 이동
									showboard();
								} else if (no2 == 3) {
									// 내 강의로 이동
									showmylesson();
								} else if (no2 == 4) {
									// 구매내역으로 이동
									PurchasedLessonMain purchasedLesson = new PurchasedLessonMain(this, isLoggedIn);
									purchasedLesson.showPurchasedLessons();
								} else if (no2 == 5) {
									// 위시리스트로 이동
									showwishlist();
								} else if (no2 == 6) {
									// 리뷰로 이동
									showreview();
								} else if (no2 == 7) {
									// 포인트로 이동
									showpoint();
								} else if (no2 == 8) {                        
									// 고객센터로 이동
									if(mem_id.equals("admin")) {
										CustomerInquireMain_Admin customerInquire = new CustomerInquireMain_Admin(this, isLoggedIn);
										customerInquire.showCustomerInquire_Admin();
									} else {
										//CustomerInquireMain_User customerInquire = new CustomerInquireMain_User(this, isLoggedIn);
										//customerInquire.showCustomerInquire_User();
									}
								} else if (no2 == 9) {
									// 공지게시판으로 이동
									NoticeMain noticeMain = new NoticeMain(this, isLoggedIn);
									noticeMain.showNotice();
								} else if (no2 == 0) {
									// 종료
									System.exit(0);
								} else {
									System.out.println("잘못 입력했습니다.");
								}       
							} catch (NumberFormatException e) {
								System.out.println("[숫자만 입력 가능]");
							}
						}
					}
				} else if (no == 2) {
					// 회원가입 처리
					register();
				} else if (no == 3) {
					// 프로그램 종료
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

	private void showpoint() {
		// TODO Auto-generated method stub

	}

	private void showreview() {
		// TODO Auto-generated method stub

	}

	private void showwishlist() {
		// TODO Auto-generated method stub

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

	public void showMemberMenu() {				
		System.out.println("\n1. 강의");
		System.out.println("2. 게시판");
		System.out.println("3. 내 강의");
		System.out.println("4. 구매내역");
		System.out.println("5. 위시리스트");
		System.out.println("6. 리뷰");
		System.out.println("7. 포인트");
		System.out.println("8. 고객센터");
		System.out.println("9. 공지게시판");
		System.out.println("0. 종료");
		System.out.print("메뉴를 선택하세요: ");
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
			isLoggedIn = true; // 로그인 상태 저장
		} else {
			System.out.println("아이디 또는 비밀번호가 잘못되었습니다.");
		}
	}	




	public static void main(String[] args) {
		new UlessonMainYB();
	}
}
