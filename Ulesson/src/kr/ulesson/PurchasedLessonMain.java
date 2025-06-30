package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class PurchasedLessonMain {
	private UlessonMainYB mainMenu;
	private boolean isLoggedIn;
	private String mem_id;

	public PurchasedLessonMain(String id, boolean isLoggedIn) throws ClassNotFoundException {
		this.isLoggedIn = isLoggedIn;
		this.mem_id = id;
		
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
			scanner.nextLine();
			
			if (option == 1) { // 구매내역 조회
				while(true) {
					List<PurchasedLesson> purchasedLessons = dao.getAllPurchasedLessons(mem_id);
					if (purchasedLessons.isEmpty()) {
						System.out.println("구매된 강의가 없습니다.");
						break;
					} else {
						System.out.println("구매된 강의 목록:");
						for (PurchasedLesson lesson : purchasedLessons) {
							System.out.println(lesson);
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
							
							dao.requestRefund(mem_id,pchStatusToUpdate);
							break;
						} else if (subOption == 2){
							break;
						} else if (subOption == 3) {
							System.exit(0);
						} else {
							System.out.println("잘못된 입력입니다. 다시 입력하세요");
						}
					}
					
					
				} // while
				
			} else if (option == 2){ // 뒤로가기
				return;
			} else if (option == 3) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}		
	}
	
	public void refundAdmin() throws NumberFormatException, IOException, ClassNotFoundException {
		PurchasedLessonDAO dao = new PurchasedLessonDAO();
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {
			System.out.println("1. 환불 신청 열람");
			System.out.println("2. 뒤로가기");
			System.out.println("3. 종료");
			System.out.print("옵션을 선택하세요: ");
			
			int option = Integer.parseInt(br.readLine());
			
			if (option == 1) { // 구매내역 조회
				while(true) {
					List<PurchasedLesson> purchasedLessons = dao.getAllneedRefund();
					if (purchasedLessons.isEmpty()) {
						System.out.println("환불 신청 내역이 없습니다.");
					} else {
						System.out.println("환불 신청 목록:");
						int i = 0;
						for (PurchasedLesson lesson : purchasedLessons) {
							System.out.println(++i + ". " + lesson);
						}
					}
					
					System.out.println("1. 환불처리");
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = Integer.parseInt(br.readLine());
           
					if (subOption == 1) {
						System.out.print("환불할 번호를 입력하세요: ");
						int pchStatusToUpdate = Integer.parseInt(br.readLine());;
						dao.updatePchStatus(purchasedLessons.get(pchStatusToUpdate - 1).getMemId(), 
								purchasedLessons.get(pchStatusToUpdate - 1).getLesNum());
						System.out.println("환불이 완료되었습니다.");
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
				return;
			} else if (option == 3) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}		
	}
	
} // class
