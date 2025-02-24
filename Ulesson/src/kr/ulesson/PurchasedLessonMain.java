package kr.ulesson;

import java.util.List;
import java.util.Scanner;

public class PurchasedLessonMain {
	private UlessonMainYB mainMenu;
	private boolean isLoggedIn;
	private String mem_id;

	public PurchasedLessonMain(UlessonMainYB mainMenu, boolean isLoggedIn) {
		this.mainMenu = mainMenu;
		this.isLoggedIn = isLoggedIn;
		this.mem_id = mainMenu.getMemId();
	}

	public static void main(String[] args) {		

	}


	public void showPurchasedLessons() throws ClassNotFoundException {
		PurchasedLessonDAO dao = new PurchasedLessonDAO();
		Scanner scanner = new Scanner(System.in);
		
		while (true) {
			System.out.println("1. 구매내역 조회");
			System.out.println("2. 뒤로가기");
			System.out.println("3. 종료");
			System.out.print("옵션을 선택하세요: ");
			
			int option = scanner.nextInt();
			scanner.nextLine();  // 버퍼 비우기
			
			if (option == 1) { // 구매내역 조회
				while(true) {
					List<PurchasedLesson> purchasedLessons = dao.getAllPurchasedLessons();
					if (purchasedLessons.isEmpty()) {
						System.out.println("구매된 강의가 없습니다.");
					} else {
						System.out.println("구매된 강의 목록:");
						for (PurchasedLesson lesson : purchasedLessons) {
							System.out.println(lesson);
						}
					}
					
					System.out.println("1. 환불신청");
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
						System.out.print("환불할 강의 번호를 입력하세요: ");
						int pchStatusToUpdate = scanner.nextInt();
						scanner.nextLine();
						
						PurchasedLessonDAO dao2 = new PurchasedLessonDAO();
						boolean refund = dao.updatePchStatus();
						
						if (refund) {
							System.out.println("환불 신청에 성공했습니다");
						} else {
							System.out.println("환불 신청에 실패했습니다");
						}
						break;
					} else if (subOption == 2){
						break;
					} else if (subOption == 3) {
						System.exit(0);
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
					
				} // while
				
			} else if (option == 2){ // 뒤로가기
				mainMenu.showMemberMenu();
				break;
			} else if (option == 3) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}		
	}
	
} // class
