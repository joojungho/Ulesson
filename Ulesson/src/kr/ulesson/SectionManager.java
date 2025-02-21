package kr.ulesson;

import java.io.BufferedReader;
import java.io.IOException;

public class SectionManager {
	
	private BufferedReader br;
	private SectionService sectionService;
	
	public SectionManager(BufferedReader br) {
		this.br = br;
		this.sectionService = new SectionService(br);
	}
	
	public void sectionManage(int lesNum) throws NumberFormatException, IOException {
		sectionService.viewSection(lesNum);
		System.out.println("1.섹션 수정 2.섹션 추가 3.섹션 삭제");
		int num = Integer.parseInt(br.readLine());
		switch (num) {
		case 1:
			sectionService.updateSection(lesNum);
			break;
		case 2:
			sectionService.insertSection(lesNum);
			break;
		case 3:
			sectionService.deleteSection(lesNum);
		default:
			break;
		}
	}
}
