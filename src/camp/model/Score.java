package camp.model;

public class Score {
    private String scoreId;
    //과목 고유 번호
    private String subjectId;
    //수강생 고유 번호
    private String studentId;
    //점수 ( 0 ~ 100 )
    private int testScore;
    //등급 (A,B,C,D,E,F,N)
    private char testRate;

    public Score(String studentId, String subjectId, int testScore, String scoreId, char testRate) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.testScore = testScore;
        this.scoreId = scoreId;
        this.testRate = testRate;
    }
    // Getter
    public String getScoreId() {
        return scoreId;
    }
    public char getTestRate() {
        return testRate;
    }
    public String getStudentId() {
        return studentId;
    }
    public String getSubjectId() {
        return subjectId;
    }

}
