package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LessonService {
	
	private String id;
	private LessonDAO dao = new LessonDAO();
	private BufferedReader br;
	private CategoryService categoryService;
	private ReviewService reviewService;
	
	public LessonService(BufferedReader br) {
		this.br = br;
		this.categoryService = new CategoryService(br);
		this.reviewService = new ReviewService(br);
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public void viewLessonDetail(int lesNum) throws NumberFormatException, IOException {
		dao.selectLessonDetail(lesNum);
		System.out.print("1.리뷰 열람 2.강의 구매 3.뒤로가기 >> ");
		int num = Integer.parseInt(br.readLine());
		switch (num) {
			case 1:
				reviewService.viewReviewLec(lesNum);
				reviewService.insertReview(lesNum, id);
				break;
			case 2:
				break;
			case 3:
				return;
			default:
				break;
		}
	}
	
	public ArrayList<Item> searchLesson() throws IOException{
		System.out.print("\n 검색할 내용을 입력하세요: ");
		return dao.selectLessonSearch(br.readLine());
	}
}
