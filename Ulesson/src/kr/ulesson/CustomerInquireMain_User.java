package kr.ulesson;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CustomerInquireMain_User {
	private UlessonMainYB mainMenu;
	private boolean isLoggedIn;
	private String mem_id;

	public CustomerInquireMain_User(UlessonMainYB mainMenu, boolean isLoggedIn) {
		this.mainMenu = mainMenu;
		this.isLoggedIn = isLoggedIn;
		this.mem_id = mainMenu.getMemId();
	}

	public static void main(String[] args) {

	} // main  

	// 고객센터(사용자)
	public void showCustomerInquire_User() throws ClassNotFoundException, SQLException {
		CustomerInquireDAO dao = new CustomerInquireDAO();
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1. 모든 문의글 보기");
			System.out.println("2. 내 문의내역 보기");
			System.out.println("3. 문의글 작성");
			System.out.println("4. 문의글 수정");
			System.out.println("5. 답변 조회");
			System.out.println("6. 뒤로가기");
			System.out.println("7. 종료");
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
			} 
			else if (option == 2) { // 내 문의내역 보기
				while(true) {
					System.out.println("1. 내 문의내역 보기");
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if(subOption == 1) {
						List<CustomerInquire> inquiries = dao.getMyInquires(mem_id);
						if (inquiries.isEmpty()) {
							System.out.println("조회된 문의글이 없습니다.");
						} else {
							for (CustomerInquire inquiry : inquiries) {
								System.out.println(inquiry);							
							}
							break;
						}
					}	else if(subOption == 2) {
						break;
					} else if(subOption == 3) {
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			}
			else if (option == 3) {  // 문의글 작성
				while(true) {					 
					System.out.println("1. 문의글 작성");
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) {
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
					} else if (subOption == 2){
						break;
					} else if (subOption == 3){
						return;
					} else{
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			} 
			else if (option == 4) {  // 문의글 수정 및 삭제
				while(true) {
					System.out.println("1. 문의글 수정");
					System.out.println("2. 문의글 삭제");
					System.out.println("3. 뒤로가기");
					System.out.println("4. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();

					if (subOption == 1) { // 문의글 수정
						System.out.print("수정할 문의글 번호를 입력하세요: ");
						int iqNumToUpdate = scanner.nextInt();
						scanner.nextLine();

						System.out.print("수정할 문의글 항목을 입력하세요: ");
						String updatedIqCate = scanner.nextLine();

						System.out.print("수정할 문의글 내용을 입력하세요: ");
						String updatedIqContent = scanner.nextLine();

						CustomerInquire updatedInquiry = new CustomerInquire(iqNumToUpdate, updatedIqCate, updatedIqContent,
								null, null, null, null, null);
						boolean isUpdated = dao.updateInquiry(updatedInquiry);
						if (isUpdated) {
							System.out.println("문의글이 수정되었습니다.");
						} else {
							System.out.println("문의글 수정에 실패했습니다.");
						}
					} else if(subOption == 2) { // 문의글 삭제						 
						System.out.print("삭제할 문의글 번호를 입력하세요: ");
						int iqNumToDelete = scanner.nextInt();
						scanner.nextLine();  // 버퍼 비우기

						boolean isDeleted = dao.deleteInquiry(iqNumToDelete);
						if (isDeleted) {
							System.out.println("문의글이 삭제되었습니다.");
						} else {
							System.out.println("문의글 삭제에 실패했습니다.");
						}
					} else if(subOption == 3) { // 뒤로가기
						break;
					} else if(subOption == 4) { // 종료
						return;
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");						
					}
				}
			} // else if 4
			else if(option == 5) {
				while(true) {
					// 옵션 선택
					System.out.println("1. 답변 조회");					
					System.out.println("2. 뒤로가기");
					System.out.println("3. 종료");
					System.out.print("옵션을 선택하세요: ");
					int subOption = scanner.nextInt();
					scanner.nextLine();
					if(subOption == 1) {
						dao = new CustomerInquireDAO();		        
						try {
							// 로그인한 mem_id를 사용하여 답변을 조회
							List<String> answers = dao.getAnswers(mem_id);

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
					} else if (subOption == 2) {
						break; // 뒤로가기
					} else if (subOption == 3) {
						return; // 종료
					} else {
						System.out.println("잘못된 입력입니다. 다시 입력하세요");
					}
				}
			}
			else if (option == 6) {  // 뒤로가기				
				mainMenu.showMemberMenu();
				break;
			} else if(option == 7) { // 종료
				System.out.println("프로그램을 종료합니다.");
				System.exit(0);
			} else {  // 잘못 입력
				System.out.println("잘못된 입력입니다. 다시 입력하세요.");
			}
		}
	}
} // class
