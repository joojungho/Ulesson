package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReviewManager {
	
	private BufferedReader br;
	private ReviewService reviewService;
	
	public ReviewManager(BufferedReader br) {
		this.br = br;
		this.reviewService = new ReviewService(br);
	}
	
	public void reviewManage(int lesNum) throws NumberFormatException, IOException {
		ArrayList<Item> list = reviewService.viewReviewLec(lesNum);
		System.out.print("삭제할 리뷰 선택: ");
		int num = Integer.parseInt(br.readLine());
		reviewService.deleteReview(list.get(num - 1).getNumber());
	}
	
	
}
