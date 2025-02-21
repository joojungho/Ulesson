package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LessonService {
	
	private LessonDAO dao = new LessonDAO();
	private BufferedReader br;
	private CategoryService categoryService;
	
	public LessonService(BufferedReader br) {
		this.br = br;
		this.categoryService = new CategoryService(br);
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
	
	public void updateLesson(int lecNum) throws IOException {
		System.out.println("강의 수정을 시작합니다.");
		System.out.println("강의명: ");
		String name = br.readLine();
		System.out.println("강사명: ");
		String teacher = br.readLine();
		System.out.println("가격: ");
		int price = Integer.parseInt(br.readLine());
		System.out.println("개요: ");
		String detail = br.readLine();
		System.out.println("시간: ");
		int time = Integer.parseInt(br.readLine());
		System.out.println("카테고리: ");
		String ctName = br.readLine();
		
		dao.updateLesson(name, teacher, price, detail, time, ctName, lecNum);
	}
	
	public void deleteLesson() throws NumberFormatException, IOException {
		Item item = categoryService.viewCategory(null);
		ArrayList<Item> list = viewLesson(item.getName());
		System.out.print("삭제할 강의를 선택하세요: ");
		int num = Integer.parseInt(br.readLine());
		System.out.println("강의를 삭제합니다.");
		
		dao.deleteLesson(list.get(num-1).getName());
	}
	
	public ArrayList<Item> viewLesson(String ctName) {
		return dao.selectLessonByCategory(ctName);	
	}
	
	public void viewLessonDetail(int lesNum) {
		dao.selectLessonDetail(lesNum);
	}
}
