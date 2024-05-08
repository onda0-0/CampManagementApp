package camp.model;

import java.util.List;

public class Student {
    private final String studentId;
    private String studentName;
    private String studentStatus;
    private final List<String> subjectNames;

    public Student(String studentId, String studentName, List<String> subjectNames) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentStatus = "Unknown";  // 기본 상태
        this.subjectNames = subjectNames;
    }


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
