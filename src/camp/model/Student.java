package camp.model;

import java.util.List;

public class Student {
    private final String studentId;
    private String studentName;
    private String studentStatus;
    private final List<String> subjectNames;


    public Student(String studentId, String studentName, List<String> subjectNames,String studentStatus) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentStatus = studentStatus;
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
    public String getStudentId(){
        return studentId;
    }
    public String getStudentStatus() {
        return studentStatus;
    }

    public List<String> getSubjectNames() {
        return subjectNames;
    }


    //Setter

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

}
