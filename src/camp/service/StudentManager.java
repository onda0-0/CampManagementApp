package camp.service;

import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;
import java.util.List;

public class StudentManager {
    private final List<Student> studentStore;// 수강생 저장 공간
    private final List<Subject> subjectStore;// 과목 저장 공간
    private final ConsoleIO consoleIO;// 입출력 유틸리티 클래스
    private final IdSequenceGenerator idGenerator;// id증가 클래스

    // 상수 필드(고정값)
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";// 필수 과목
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";//선택과목

    //생성자
    public StudentManager(List<Student> studentStore, List<Subject> subjectStore, ConsoleIO consoleIO, IdSequenceGenerator idGenerator) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
        this.consoleIO = consoleIO;
        this.idGenerator = idGenerator;
    }



}
