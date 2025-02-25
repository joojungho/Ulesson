package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReviewService {
	
	private BufferedReader br;
	private ReviewDAO dao;
	
	public ReviewService(BufferedReader br) {
		this.br = br;
		this.dao = new ReviewDAO();
	}
	
	public ArrayList<Item> viewReviewLec(int lecNum) {
		return dao.selectReview(lecNum);
	}
	
	public void deleteReview(int num) {
		dao.deleteReview(num);
	}
	
	public void insertReview(int lecNum, String id) throws IOException {
		System.out.print("리뷰를 작성하시겠습니까?(y/n): ");
		if(br.readLine().matches("[Yy]")) {
			System.out.print("리뷰 내용을 작성하시오: ");
			String content = br.readLine();
			System.out.print("별점을 입력하시오(0~5): ");
			int score = Integer.parseInt(br.readLine());
			dao.insertReview(lecNum, id, content, score);
		}
	}
	
	public void deleteMyReview(String id) throws NumberFormatException, IOException {
		ArrayList<Item> list = dao.selectReview(id);
		System.out.println("삭제할 리뷰를 선택하세요: ");
		int choice = Integer.parseInt(br.readLine());
		dao.deleteReview(list.get(choice - 1).getNumber());
	}
}
