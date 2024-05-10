package camp.service;

import camp.model.Student;
import camp.model.Subject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentManager {
    private final List<Student> studentStore;
    private final List<Subject> subjectStore;
    private final IdSequenceGenerator idGenerator;



    public StudentManager(List<Student> studentStore, List<Subject> subjectStore, IdSequenceGenerator idGenerator) {
        this.studentStore = studentStore;
        this.subjectStore = subjectStore;
        this.idGenerator = idGenerator;
    }

    public void createStudent(String studentName, List<String> selectedSubjectNames) {
        Student student = new Student(idGenerator.generateStudentId(), studentName, selectedSubjectNames);
        studentStore.add(student);
    }


    public List<Student> getAllStudents() {
        return new ArrayList<>(studentStore);
    }

    public List<Student> getStudentsByStatus(String status) {
        return studentStore.stream()
                .filter(student -> student.getStudentStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }
    public List<Subject> getSubjectsByType(String subjectType) {
        return subjectStore.stream()
                .filter(subject -> subject.getSubjectType().equals(subjectType))
                .collect(Collectors.toList());
    }
    public Optional<Student> getStudentById(String studentId) {
        return studentStore.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    }

    public void updateStudentName(Student student, String newName) {
        student.setStudentName(newName);
    }

    public void updateStudentStatus(Student student, String newStatus) {
        student.setStudentStatus(newStatus);
    }

    public void deleteStudent(Student student) {
        studentStore.remove(student);

    }
}
