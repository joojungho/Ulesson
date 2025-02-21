package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;

public class CategoryService {

	private BufferedReader br;
	private CategoryDAO dao;
	
	public CategoryService(BufferedReader br) {
		this.br = br;
		dao = new CategoryDAO();
	}
	
	public void addCategory() throws IOException {
		System.out.println("카테고리 추가를 시작합니다.");
		System.out.print("카테고리명: ");
		String name = br.readLine();
		System.out.println("상위카테고리명(최상위 카테고리는 미입력):");
		String parent = br.readLine();
		
		if(parent.equals("")) {
			parent = null;
		}
		dao.insertCategory(name, parent);
	}
	
	public void viewCategory(String name) {
		dao.selectCategory(name);
	}

}
