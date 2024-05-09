package camp.service;


// id 증가 생성 서비스
public class IdSequenceGenerator {
    private int studentIdCounter = 0;
    private int subjectIdCounter = 0;

    public String generateStudentId() {
        studentIdCounter++;
        return "ST" + studentIdCounter;
    }

    public String generateSubjectId() {
        subjectIdCounter++;
        return "SU" + subjectIdCounter;
    }
}
