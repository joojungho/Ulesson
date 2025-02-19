package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UlessonMainJH {
	private BufferedReader br;
	private String me_id; // 로그인한 아이디 저장
	private boolean flag; // 로그인 여부
	LessonDAO dao;
	CategoryDAO ctdao;
	public UlessonMainJH() {
		try {
			br = new BufferedReader(new InputStreamReader(System.in));
			//메뉴 호출
			dao = new LessonDAO();
			ctdao = new CategoryDAO();
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
					//dao.updateLesson("수정 테스트", "주정호", 123456 , "강의 수정 테스트용 디테일내용", 120, "테스트",0);
					//dao.selectLessonByCategory("테스트");
//					dao.selectLessonSearch("수정");
//					dao.selectLessonSearch("주정");
//					dao.selectLessonSearch("정");
					ctdao.insertCategory("웹 개발", null);
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
		new UlessonMainJH();
	}

}
