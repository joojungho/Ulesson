package kr.ulesson;

import java.io.BufferedReader;
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
}
