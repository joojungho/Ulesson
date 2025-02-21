package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;

public class CategoryManager {

	private BufferedReader br;
	private CategoryService categoryService;

	public CategoryManager(BufferedReader br) {
		this.br = br;
		categoryService = new CategoryService(br);
	}
	
	public void categoryManage() throws NumberFormatException, IOException {
		System.out.println("1.카테고리 추가 2.카테고리 삭제");
		int num = Integer.parseInt(br.readLine());
		switch (num) {
		case 1: 
			categoryService.addCategory();
			break;
		case 2:
			categoryService.deleteCategory();
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + num);
		}
	}
	
	public Item viewCategory(String name) throws NumberFormatException, IOException {
		return categoryService.viewCategory(name);
	}
}
