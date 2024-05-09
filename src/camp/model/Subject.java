package camp.model;

public class Subject {
    private final String subjectId;
    private final String subjectName;
    private final String subjectType;

    public Subject(String subjectId , String subjectName, String subjectType) {
        this.subjectId = subjectId ;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public String getSubjectName() {
        return subjectName;
    }
}
