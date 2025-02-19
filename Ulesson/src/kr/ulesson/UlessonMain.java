package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UlessonMain {
	private BufferedReader br;
	private String me_id; // 로그인한 아이디 저장
	private boolean flag; // 로그인 여부
	
	public UlessonMain() {
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
	private void callMenu() throws IOException{
		//로그인 체크 영역
		while(true) {
			System.out.print("1.로그인 2.회원가입 3.종료");
			try {
				int no = Integer.parseInt(br.readLine());
				if (no == 1) {
					
				} else if (no == 2){
					
				} else if (no == 3) {
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
			System.out.println("1.상품목록 2.MY구매상품리스트 3.종료");
			try {
				int no = Integer.parseInt(br.readLine());
				if(no == 1) {
				} else if (no == 2) {
					
				} else if (no == 3) {
					break;
				} else {
					System.out.println("잘못 입력했습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("[숫자만 입력 가능]");
			}
		}
	}
	public static void main(String[] args) {
		new UlessonMain();
	}

}
