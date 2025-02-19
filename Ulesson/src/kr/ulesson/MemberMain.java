package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MemberMain {
    private BufferedReader br;
    private MemberDAO dao;
    private String mem_id; // 로그인한 아이디 저장
    private boolean isLoggedIn; // 로그인 여부

    public MemberMain() {
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            dao = new MemberDAO();
            
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
            
        } //while

        // 로그인 후 추가 기능 메뉴
        while (isLoggedIn) {
            System.out.print("\n 1. 회원 정보 조회 | 2.회원 정보 수정 | 3. 로그아웃 | 4. 종료 >> ");
            try {
                int choice = Integer.parseInt(br.readLine());
                if (choice == 1) {
                    dao.getMemberInfo(mem_id); // 회원 정보 출력
                } else if (choice == 2) {
                	updateMemberInfo();
                } else if (choice == 3) {
						logout();
                } else if (choice == 4) {
                    System.out.println("프로그램을 종료합니다.");
                    break;
                } else {
                    System.out.println("잘못 입력했습니다. 다시 선택하세요.");
                }
            } catch (NumberFormatException e) {
                System.out.println("[숫자만 입력 가능]");
            }
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

    // 로그아웃 메서드
    private void logout() {
        isLoggedIn = false;
        mem_id = null;
        System.out.println("로그아웃되었습니다.");
    } //logout

    public static void main(String[] args) {
        new MemberMain();
    } //main
    
    
} //class
