package kr.ulesson;

import java.util.Date;

public class Notice {
    private int ntNum;      // 공지/이벤트 번호
    private String ntContent;  // 내용
    private int ntType;        // 타입 (0: 공지, 1: 이벤트)
    private Date ntDate;       // 날짜

    // 생성자
    public Notice(int ntNum, String ntContent, int ntType, Date ntDate) {
        this.ntNum = ntNum;
        this.ntContent = ntContent;
        this.ntType = ntType;
        this.ntDate = ntDate;
    }

    // Getter와 Setter
    public int getNtNum() {
        return ntNum;
    }

    public void setNtNum(int ntNum) {
        this.ntNum = ntNum;
    }

    public String getNtContent() {
        return ntContent;
    }

    public void setNtContent(String ntContent) {
        this.ntContent = ntContent;
    }

    public int getNtType() {
        return ntType;
    }

    public void setNtType(int ntType) {
        this.ntType = ntType;
    }

    public Date getNtDate() {
        return ntDate;
    }

    public void setNtDate(Date ntDate) {
        this.ntDate = ntDate;
    }

    @Override
    public String toString() {
        String type = (ntType == 0) ? "공지" : "이벤트";
        return "[번호=" + ntNum + ", 내용=" + ntContent + ", 타입=" + type + ", 날짜=" + ntDate + "]";
    }
}
