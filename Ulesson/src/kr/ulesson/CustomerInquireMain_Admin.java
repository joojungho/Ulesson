package kr.ulesson;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerInquireMain_Admin {
	private UlessonMainYB mainMenu;
    private boolean isLoggedIn;
    private String mem_id;
    
    public CustomerInquireMain_Admin(UlessonMainYB mainMenu, boolean isLoggedIn) {
        this.mainMenu = mainMenu;
        this.isLoggedIn = isLoggedIn;
        this.mem_id = "admin";
        
        try {
			showCustomerInquire_Admin();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    // 고객센터(관리자)        
	public void showCustomerInquire_Admin() throws ClassNotFoundException, SQLException {
		CustomerInquireDAO dao = new CustomerInquireDAO();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1. 모든 문의글 보기");			
			System.out.println("2. 답변 조회");
			System.out.println("3. 답변 작성");
			System.out.println("4. 뒤로가기");
			System.out.println("5. 종료");
			System.out.print("옵션을 선택하세요: ");

			int option = scanner.nextInt();
			scanner.nextLine();  // 버퍼 비우기

			if (option == 1) {  // 모든 문의글 보기
				while(true) {
					List<CustomerInquire> inquiries = dao.getInquires();
					if (inquiries.isEmpty()) {
						System.out.println("조회된 문의글이 없습니다.");
					} else {
						for (CustomerInquire inquiry : inquiries) {
							System.out.println(inquiry);
						}
					}

					// 뒤로가기
					System.out.println("1. 뒤로가기");
					System.out.println("2. 종료");
					System.out.print("옵션을 선택하세요");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
						break;
					} else if (subOption == 2){
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			} else if (option == 2) {  // 답변 조회
				while(true) {
					dao = new CustomerInquireDAO();		        
					try {
						// 로그인한 mem_id를 사용하여 답변을 조회
						List<String> answers = dao.getAnswersAdmin();

						// 등록된 답변이 없을 경우 처리
						if (answers.isEmpty()) {
							System.out.println("등록된 답변이 없습니다.");
						} else {
							// 등록된 답변이 있을 경우 모두 출력
							System.out.println("문의에 등록된 모든 답변:");
							for (String answer : answers) {
								System.out.println(answer);
							}
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}						
					// 옵션 선택
					// System.out.println("1. 답변 조회");					
					System.out.println("1. 뒤로가기");
					System.out.println("2. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt()+1;
					scanner.nextLine();
					if(subOption == 1) {
						
					} else if (subOption == 2) {
						break; // 뒤로가기
					} else if (subOption == 3) {
						return; // 종료
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}

			} else if (option == 3) {  // 답변 작성
				while(true) {
					// 옵션 선택
					System.out.println("1. 답변작성");					
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
						System.out.print("답변을 작성할 문의글 번호를 입력하세요: ");
						int iqNumToAnswer = scanner.nextInt();
						scanner.nextLine();

						// 답변 작성
						System.out.print("답변 내용을 입력하세요: ");
						String rsContent = scanner.nextLine();		
						
						dao.addAnswer(iqNumToAnswer, rsContent);
					} else if (subOption == 2){
						break;
					} else if (subOption == 3){
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			} else if (option == 4) {  // 뒤로가기				
				return;
			} else if(option == 5) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {  // 잘못 입력
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}
	}
} // class
