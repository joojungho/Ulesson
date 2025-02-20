package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;

public class LessonManager {
	
	private BufferedReader br;
	private LessonService lessonService;
	private CategoryManager categoryManager;

	public LessonManager(BufferedReader br) {
		this.br = br;
		lessonService = new LessonService(br);
		categoryManager = new CategoryManager(br);
	}
	
	public void lessonManage() throws NumberFormatException, IOException {
		System.out.println("1.카테고리 관리 2.강의추가 3.강의수정 4.강의삭제");
		int no = Integer.parseInt(br.readLine());
		switch (no) {
		case 1:{
			categoryManager.categoryManage();
			break;
		}
		case 2: {
			categoryManager.viewCategory(null);
			lessonService.addLesson();
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + no);
		}
	}
}
