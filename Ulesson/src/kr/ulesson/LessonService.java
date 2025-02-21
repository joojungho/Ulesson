package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;

public class LessonService {
	
	private LessonDAO dao = new LessonDAO();
	private BufferedReader br;
	
	public LessonService(BufferedReader br) {
		this.br = br;
	}
	
	public void addLesson() throws IOException {
		System.out.println("강의 추가를 시작합니다.");
		System.out.println("강의명: ");
		String name = br.readLine();
		System.out.println("강사명: ");
		String teacher = br.readLine();
		System.out.println("가격: ");
		int price = Integer.parseInt(br.readLine());
		System.out.println("개요: ");
		String detail = br.readLine();
		System.out.println("카테고리: ");
		String ctName = br.readLine();
		
		dao.insertLesson(name, teacher, price, detail, ctName);
	}
}
