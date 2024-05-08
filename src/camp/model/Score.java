package camp.model;

import java.util.ArrayList;

public class Score {
    //과목 고유 번호
    private String subjectId;
    //수강생 고유 번호
    private String studentId;

    //점수 ( 0 ~ 100 )
    private int testScore;
    //점수 고유 번호 = 인덱스 = 회차 1~10
    private int scoreId;
    //등급 (A,B,C,D,E,F,N)
    private char testRate;


    public Score(String studentId, String subjectId, int testScore, int scoreId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.testScore = testScore;
        this.scoreId = scoreId;
    }

    // Getter
    public int getScoreId(int inputA) {
        return inputA;
    }

    public void modifiScore(int testScore) {
        this.testScore = testScore;
    }

    public String returnFindingStudentId(){
        return this.studentId;
    }
    public String returnFindingSubjectId(){
        return this.subjectId;
    }
    public int returnFindingTestScore(){
        return this.testScore;
    }
    public int returnFindingScoreId(){
        return this.scoreId;
    }


}
