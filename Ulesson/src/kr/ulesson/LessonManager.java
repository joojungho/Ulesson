package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class LessonManager {

	private BufferedReader br;
	private LessonService lessonService;
	private CategoryManager categoryManager;
	private CategoryService categoryService;
	private SectionManager sectionManager;
	private ReviewManager reviewManager;

	public LessonManager(BufferedReader br) {
		this.br = br;
		lessonService = new LessonService(br);
		categoryManager = new CategoryManager(br);
		categoryService = new CategoryService(br);
		sectionManager = new SectionManager(br);
		reviewManager = new ReviewManager(br);
	}

	public void lessonManage() throws NumberFormatException, IOException {
		System.out.println("1.카테고리 관리 2.강의추가 3.강의수정 4.강의삭제");
		int no = Integer.parseInt(br.readLine());
		switch (no) {
		case 1:
			categoryManager.categoryManage();
			break;
		case 2: 
			lessonService.addLesson();
			break;
		case 3:
			Item category = categoryService.viewCategory(null);
			ArrayList<Item> list = lessonService.viewLesson(category.getName());

			if (list.isEmpty())
				break;

			System.out.print("수정할 강의를 선택하세요: ");
			int lesNum = list.get(Integer.parseInt(br.readLine())-1).getNumber();
			System.out.println("1. 강의 정보 수정 2. 섹션 관리 3. 리뷰 관리");
			no = Integer.parseInt(br.readLine());
			switch (no) {
			case 1:
				lessonService.updateLesson(lesNum);
				break;
			case 2:
				sectionManager.sectionManage(lesNum);
				break;
			case 3:
				reviewManager.reviewManage(lesNum);
				break;
			default:
				throw new IllegalArgumentException("Unexpected value: " + no);
			}
			break;
		case 4:
			lessonService.deleteLesson();
			break;
		}

	}
}
