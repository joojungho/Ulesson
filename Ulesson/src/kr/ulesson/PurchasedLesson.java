package kr.ulesson;

public class PurchasedLesson {
    private int pchNum;      // 구매번호
    private int lesNum;      // 강의번호
    private String lesName;  // 강의명
    private String memId;    // 멤버 ID
    private String pchDate;  // 구매 날짜
    private String pchStatus;   // 구매 상태 (구매 상태 또는 환불 상태로 처리)
         
    // 생성자
    public PurchasedLesson(int pchNum, int lesNum, String lesName, String memId, String pchDate, String pchStatus) {
        this.pchNum = pchNum;
        this.lesNum = lesNum;
        this.lesName = lesName;
        this.memId = memId;
        this.pchDate = pchDate;
        this.pchStatus = pchStatus;
    }

    // Getter Setter
    public int getPchNum() {
        return pchNum;
    }

    public void setPchNum(int pchNum) {
        this.pchNum = pchNum;
    }

    public int getLesNum() {
        return lesNum;
    }

    public void setLesNum(int lesNum) {
        this.lesNum = lesNum;
    }

    public String getLesName() {
        return lesName;
    }

    public void setLesName(String lesName) {
        this.lesName = lesName;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getPchDate() {
        return pchDate;
    }

    public void setPchDate(String pchDate) {
        this.pchDate = pchDate;
    }

    public String getPchStatus() {
        return pchStatus;
    }

    public void setPchStatus(String pchStatus) {
        this.pchStatus = pchStatus;
    }

    @Override
    public String toString() {
        return "[구매번호 : " + pchNum + ", 강의번호 : " + lesNum + ", 강의명 : " + lesName + ", 사용자ID : " + memId + ", 구매날짜 : " + pchDate + ", 구매상태 : " + pchStatus + "]";
    }
}
