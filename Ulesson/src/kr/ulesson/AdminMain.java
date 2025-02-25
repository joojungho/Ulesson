package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class AdminMain {
	private BufferedReader br;
	private MemberDAO dao;
	private boolean isAdminLoggedIn;
	private LessonManager lessonManager;
	private NoticeMain noticeMain;

	public AdminMain() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			dao = new MemberDAO();
			lessonManager = new LessonManager(br);
			adminMenu();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) br.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

	private void adminMenu() throws IOException {
		System.out.println("=== 관리자 로그인 ===");
		System.out.print("관리자 ID >> ");
		String adminId = br.readLine();
		System.out.print("비밀번호 >> ");
		String adminPw = br.readLine();

		if (dao.adminLogin(adminId, adminPw)) {
			isAdminLoggedIn = true;
			System.out.println("관리자 로그인 성공!");
		} else {
			System.out.println("관리자 로그인 실패. 프로그램 종료.");
			return;
		}

		while (isAdminLoggedIn) {
			System.out.println("\n=== 관리자 메뉴 ===");
			System.out.println("1. 회원 목록 조회");
			System.out.println("2. 회원 삭제");
			System.out.println("3. 회원 권한 변경");
			System.out.println("4. 강의 관리");
			System.out.println("5. 공지사항 관리");
			System.out.println("6. 문의사항 관리");			
			System.out.println("7. 로그아웃");
			System.out.print("선택 >> ");

			try {
				int choice = Integer.parseInt(br.readLine());
				switch (choice) {
				case 1:
					listAllMembers();
					break;
				case 2:
					listAllMembers(); //삭제 할 회원 먼저 보여주기
					deleteMember(); //삭제 할 회원 정하기
					break;
				case 3:
					listAllMembers(); //변경할 회원 먼저 보여주기
					changeMemberAuth();
					break;
				case 4:
					lessonManager.lessonManage();
					break;
				case 5:
					noticeMain = new NoticeMain(null, isAdminLoggedIn);
					break;
				case 6:
					new CustomerInquireMain_Admin(null, true);
					break;
				case 7:
					logout();
					break;
				default:
					System.out.println("잘못 입력했습니다. 다시 선택하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}

	// 회원 목록 출력
	private void listAllMembers() {
		System.out.println("\n=== 회원 목록 ===");
		List<String> members = dao.getAllMembers();
		for (String member : members) {
			System.out.println(member);
		}
	}

	// 회원 삭제
	private void deleteMember() throws IOException {
		while (true) {
			try {
				String mem_id;
				System.out.print("\n삭제할 회원 ID 입력 (취소: 0) >> ");
				mem_id = br.readLine();
				if (mem_id.equals("0")) return;

				if (dao.deleteMember(mem_id)) {
					System.out.println(mem_id + " 회원이 삭제되었습니다.");
				} else {
					System.out.println("회원 삭제 실패. 다시 시도하세요.");
				}
				

			} catch (IOException e) {
				System.out.println("입력 오류 다시 입력해주세요");
			}
		}
	}



	// 회원 권한 변경
	private void changeMemberAuth() throws IOException {
		while (true) { // 잘못된 입력 시 반복
			try {
				String mem_id;
				while (true) { // 유효한 회원 ID 입력할 때까지 반복
					System.out.print("\n권한을 변경할 회원 ID 입력 (취소: 0) >> ");
					mem_id = br.readLine();
					if (mem_id.equals("0")) return; // 취소 입력 시 종료

					if (dao.isUpdateMember(mem_id)) { // DB에서 ID가 있는지 확인
						break; 
					} else {
						System.out.println("존재하지 않는 회원 ID입니다. 다시 입력해주세요.");
					}
				}

				int newAuth;
				while (true) { // 올바른 권한 값 입력할 때까지 반복
					System.out.print("변경할 권한 (1: 일반회원, 9: 관리자) >> ");
					try {
						newAuth = Integer.parseInt(br.readLine());
						if (newAuth == 1 || newAuth == 9) {
							break; // 정상적인 값이면 반복 종료
						} else {
							System.out.println("잘못된 권한 값입니다. 1(일반회원) 또는 9(관리자)만 입력하세요.");
						}
					} catch (NumberFormatException e) {
						System.out.println("숫자 형식으로 입력해주세요. (1: 일반회원, 9: 관리자)");
					}
				}

				if (dao.updateMemberAuth(mem_id, newAuth)) {
					System.out.println(mem_id + " 회원의 권한이 변경되었습니다.");
				} else {
					System.out.println("권한 변경 실패. 다시 시도하세요.");
				}
				return; // 정상 처리 후 함수 종료

			} catch (IOException e) {
				System.out.println("입력 오류가 발생했습니다. 다시 시도해주세요.");
			} catch (Exception e) {
				System.out.println("알 수 없는 오류 발생: " + e.getMessage());
			}
		}
	}




	// 로그아웃
	private void logout() {
		isAdminLoggedIn = false;
		System.out.println("관리자 로그아웃되었습니다.");
	}

//	public static void main(String[] args) {
//		new AdminMain();
//	}
}
