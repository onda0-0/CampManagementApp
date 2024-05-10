package camp.model;

public class Score {
    private final String studentId;
    private final String subjectId;

    private final int iteration; //회차
    private int score; //점수
    private String grade; //등급  //하은-char로 변경



    public Score(String studentId, String subjectId, int iteration, int score, String grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.iteration = iteration;
        this.score = score;
        this.grade = grade;
    }

    // Getter

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIteration() {
        return iteration;
    }

    public int getScore() {
        return score;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getStudentId() {
        return studentId;
    }

    // Setter


    public String getGrade() {
        return grade;
    }
}
