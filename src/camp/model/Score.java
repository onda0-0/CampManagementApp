package camp.model;

public class Score {
    //과목 고유 번호
    private String subjectId;
    //수강생 고유 번호
    private String studentId;

    //점수 ( 0 ~ 100 )
    private int testScore;
    //인덱스 = 회차 1~10
    private int scoreId;

    //등급 (A,B,C,D,E,F,N)
    private char testRate;

    public char scoreTestRate(){
        if (subjectId.equals("Java") || subjectId.equals("객체지향") || subjectId.equals("Spring") || subjectId.equals("JPA") || subjectId.equals("MySQL")) {
            if (testScore >= 95)
                testRate = 'A';
            else if (testScore >= 90)
                testRate = 'B';
            else if (testScore >= 80)
                testRate = 'C';
            else if (testScore >= 70)
                testRate = 'D';
            else if (testScore >= 60)
                testRate = 'F';
            else
                testRate = 'N';
        } else {
            if (testScore >= 90)
                testRate = 'A';
            else if (testScore >= 80)
                testRate = 'B';
            else if (testScore >= 70)
                testRate = 'C';
            else if (testScore >= 60)
                testRate = 'E';
            else if (testScore >= 50)
                testRate = 'F';
            else
                testRate = 'N';
        }
        return testRate;
    }


    public Score( String studentId, String subjectId, int scoreId, int testsScore) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.scoreId = scoreId;
        this.testScore = testsScore;
    }



    // Getter
    public int getScoreId() {
        return scoreId;
    }

    public Object getStudentId() {
        return studentId;}

    public int getscoreId() {
        return scoreId;}

    public Object getSubjectId() {
        return subjectId;}
}
