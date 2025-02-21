package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryService {

	private BufferedReader br;
	private CategoryDAO dao;
	private static final int MAX_DEPTH = 2;

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

	public Item viewCategory(String name) throws NumberFormatException, IOException {
		ArrayList<Item> list = null;
		int depth = dao.getDepth(name);
		int cnt = 0;
		int num = 0;
		do {
			list = dao.selectCategory(name);
			System.out.print("카테고리 선택: ");
			num = Integer.parseInt(br.readLine());
			name = list.get(num - 1).getName();
			cnt++;
		}while(cnt < MAX_DEPTH - depth);
		return list.get(num - 1);
	}

	public void deleteCategory() throws NumberFormatException, IOException {
		System.out.print("삭제할 카테고리를 선택하시오");
		Item item = viewCategory(null);
		dao.deleteCategory(item.getName());
		
		if(dao.selectCategory(item.getName()).isEmpty()) {
			dao.updateDepth(item.getNumber());
		}
	}

}
