package camp.model;

public class Score {
    private String studentId;
    private String subjectId;

    private int iteration; //회차
    private int score; //점수

    public int getIteration() {
        return iteration;
    }

    private String grade; //등급


    public Score(String studentId, String subjectId, int iteration, int score, String grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.iteration = iteration;
        this.score = score;
        this.grade = grade;
    }


    // Getter

    public String getSubjectId() {
        return subjectId;
    }

    public int getScore() {
        return score;
    }

    public String getStudentId() {
        return studentId;
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


}
