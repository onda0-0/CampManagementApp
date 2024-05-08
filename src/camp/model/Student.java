package camp.model;

import java.util.List;

public class Student {
    // 필드
    private final String studentId; // 수강생 id
    private String studentName; // 수강상 이름
    private String studentStatus; // 수강생 상태
    private final List<String> subjectNames; // 수강생이 선태한 과목들

    // 생성자
    public Student(String studentId, String studentName, List<String> subjectNames) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentStatus = "Unknown";  // 기본 상태(추후 세터를 사용하여 수정)
        this.subjectNames = subjectNames;
    }

    // Getter
    public String getStudentId() {
        return studentId;
    }
    public Object getStudentName() {return  studentName;}
    public String getStudentStatus() {return studentStatus;}


    // toString() 메서드는 객체의 상태를 문자열로 표현하는 것과 직접적으로 관련된 메서드이기 때문에, 객체의 내부 상태를 가장 잘 알고 있는 모델 클래스에 위치
    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                ", studentStatus='" + studentStatus + '\'' +
                ", subjectNames=" + subjectNames +
                '}';
    }


}
