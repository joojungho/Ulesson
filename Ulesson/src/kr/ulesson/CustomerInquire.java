package kr.ulesson;

import java.sql.Date;
                      
public class CustomerInquire {
    private int iqNum;      // 문의글 번호
    private String iqCate;  // 문의글 항목
    private String iqContent; // 문의글 내용
    private String memId;   // 작성자 ID
    private Date iqDate;    // 작성일
    private Date iqMdate;   // 수정일
    private String rsContent; // 답변 내용
    private Date rsDate;    // 답변일

    // 생성자
    public CustomerInquire(int iqNum, String iqCate, String iqContent, String memId, Date iqDate, Date iqMdate, String rsContent, Date rsDate) {
        this.iqNum = iqNum;
        this.iqCate = iqCate;     
        this.iqContent = iqContent;
        this.memId = memId;
        this.iqDate = iqDate;
        this.iqMdate = iqMdate;
        this.rsContent = rsContent;
        this.rsDate = rsDate;
    }

    // Getter, Setter
    public int getIqNum() {
        return iqNum;
    }

    public void setIqNum(int iqNum) {
        this.iqNum = iqNum;
    }

    public String getIqCate() {
        return iqCate;
    }

    public void setIqCate(String iqCate) {
        this.iqCate = iqCate;
    }

    public String getIqContent() {
        return iqContent;
    }

    public void setIqContent(String iqContent) {
        this.iqContent = iqContent;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public Date getIqDate() {
        return iqDate;
    }

    public void setIqDate(Date iqDate) {
        this.iqDate = iqDate;
    }

    public Date getIqMdate() {
        return iqMdate;
    }

    public void setIqMdate(Date iqMdate) {
        this.iqMdate = iqMdate;
    }

    public String getRsContent() {
        return rsContent;
    }

    public void setRsContent(String rsContent) {
        this.rsContent = rsContent;
    }

    public Date getRsDate() {
        return rsDate;
    } 

    public void setRsDate(Date rsDate) {
        this.rsDate = rsDate;
    }

    @Override
    public String toString() {
        return "[문의번호 : " + iqNum + " / 사용자 : " + memId + " / 문의항목 : " + iqCate + "\n 문의내용 : " + iqContent + 
               " \n 문의작성일 : " + iqDate + " / 문의수정일 : " + iqMdate + "\n "
               		+ "답변내용 : " + (rsContent==null ? "없음" : rsContent) + "/ 답변작성일 : " + (rsDate==null ? " " : rsDate) + "]";
    }
}
