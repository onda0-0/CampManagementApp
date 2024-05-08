package camp.service;

import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StudentManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;
    private final ConsoleIO consoleIO;
    private final IdSequenceGenerator idGenerator;

    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";

    public StudentManager(List<Student> studentStore, List<Subject> subjectStore, ConsoleIO consoleIO, IdSequenceGenerator idGenerator) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
        this.consoleIO = consoleIO;
        this.idGenerator = idGenerator;
    }

    // 전체 수강생 목록 반환 메서드
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentStore);
    }

    // 상태별 수강생 목록 반환 메서드
    public List<Student> getStudentsByStatus(String status) {
        return studentStore.stream()
                .filter(student -> student.getStudentStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
}
