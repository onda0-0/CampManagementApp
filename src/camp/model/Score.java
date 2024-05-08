package camp.model;

public class Score {
    private String studentId;
    private String subjectId;
    private int iteration;
    private int score;
    private String grade;

    public Score(String studentId, String subjectId, int iteration, int score, String grade) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.iteration = iteration;
        this.score = score;
        this.grade = grade;
    }


}
