package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

public class SectionService {
	
	private BufferedReader br;
	private sectionDAO dao;
	
	public SectionService(BufferedReader br) {
		this.br = br;
		this.dao = new sectionDAO();
	}
	
	public ArrayList<Item> viewSection(int lesNum) throws NumberFormatException, IOException {
		System.out.println("섹션 목록: ");
		return dao.selectSection(lesNum);
	}

	public void updateSection(int lesNum) throws NumberFormatException, IOException {
		System.out.println("수정을 원하시는 섹션을 선택하시오.");
		ArrayList<Item> list = viewSection(lesNum);
		int num = Integer.parseInt(br.readLine());
		int secNum = list.get(num - 1).getNumber();
		
		System.out.println("섹션명: ");
		String name = br.readLine();
		System.out.println("링크: ");
		String link = br.readLine();
		System.out.println("시간: ");
		int time = Integer.parseInt(br.readLine());
		
		dao.updateSection(secNum, name, link, time);
		
	}

	public void insertSection(int lesNum) throws IOException {
		
		System.out.println("섹션명: ");
		String name = br.readLine();
		System.out.println("링크: ");
		String link = br.readLine();
		System.out.println("시간: ");
		int time = Integer.parseInt(br.readLine());
		
		dao.insertSection(name, lesNum, link, time);
	}
	
	public void deleteSection(int lesNum) throws NumberFormatException, IOException {
		System.out.println("삭제를 원하시는 섹션을 선택하시오.");
		ArrayList<Item> list = viewSection(lesNum);
		int num = Integer.parseInt(br.readLine());
		int secNum = list.get(num - 1).getNumber();
		
		dao.deleteSection(secNum);
		
	}
}
