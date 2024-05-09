package camp.model;

public class Score {
    private String studentId;
    private String subjectId;

    private int iteration; //회차
    private int score; //점수
    private char grade; //등급  //하은-char로 변경


    public Score(String studentId, String subjectId, int iteration, int score, char grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.iteration = iteration;
        this.score = score;
        this.grade = grade;
    }

    // Getter
    public int getScoreId(int inputA) {
        return inputA;
    }
    public int getIteration() {
        return iteration;
    }

    // Setter
    public void modifiScore(int testScore) {
        this.score = testScore;
    }

    public String returnFindingStudentId(){
        return this.studentId;
    }
    public String returnFindingSubjectId(){
        return this.subjectId;
    }
    public int returnFindingTestScore(){
        return this.score;
    }
    public int returnFindingScoreId(){
        return this.iteration;
    }

    public char getTestRate() {
        return grade;
    }


//    private String scoreId;
//    //과목 고유 번호
//    private String subjectId;
//    //수강생 고유 번호
//    private String studentId;
//    //점수 ( 0 ~ 100 )
//    private int testScore;
//    //등급 (A,B,C,D,E,F,N)
//    private char testRate;
//
//    public Score(String studentId, String subjectId, int testScore, String scoreId, char testRate) {
//        this.studentId = studentId;
//        this.subjectId = subjectId;
//        this.testScore = testScore;
//        this.scoreId = scoreId;
//        this.testRate = testRate;
//    }
//    // Getter
//    public String getScoreId() {
//        return scoreId;
//    }
//    public char getTestRate() {
//        return testRate;
//    }
//    public String getStudentId() {
//        return studentId;
//    }
//    public String getSubjectId() {
//        return subjectId;
//    }
//    public int getTestScore(){
//        return testScore;
//    }

}
