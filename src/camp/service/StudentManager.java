package camp.service;

import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



public class StudentManager {
    // 필드(생성자로 주입)
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




    // 수강생 등록 메서드
    public void createStudent(String studentName, List<String>  selectedSubjectNames) {
        Student student = new Student(idGenerator.generateStudentId(), studentName,  selectedSubjectNames);
        studentStore.add(student);
    } // 필드에 생성자로 초기화된 idGenerator, studentStore 를 사용해 id,studentName 를 주입하고  selectSubjectNamesList를 통해 반환된 선택한과목 이름들 목록 주입후 Student 객체 생성후  studentStore 배열에 객체 넣어줌


    // 수강생의 과목 선택 메서드
    public List<String> selectSubjectsName(String subjectType,int minSubjects) {
        List<String> selectedSubjectNames = new ArrayList<>();

        List<Subject> availableSubjects = subjectStore.stream()
                .filter(subject -> subject.getSubjectType().equals(subjectType))
                .toList(); // 생성자로 초기화한 subjectStore에서 일치하는 과목 유형(MANDATORY 또는 CHOICE)을 필터링하여 리스트로 변환

        consoleIO.print(subjectType.equals(SUBJECT_TYPE_MANDATORY) ? "필수 과목 선택:" : "선택 과목 선택:");
        for (int i = 0; i < availableSubjects.size(); i++) {
            consoleIO.print((i + 1) + ". " + availableSubjects.get(i).getSubjectName());
        } // 필터링에따른  과목 목록 출력

        // 과목 선택 프로세스
        boolean done = false;
        while (!done) {
            int choice = consoleIO.getIntInput("선택하고 싶은 과목 번호를 입력하세요 (선택을 마치려면 0을 입력):");
            if (choice == 0) {
                if (selectedSubjectNames.size() >= minSubjects) {
                    done = true; // 최소 선택 조건을 만족하면 루프 종료
                } else {
                    consoleIO.print("최소 " + minSubjects + "개의 과목을 선택해야 합니다. 계속 선택하세요.");
                }
            } else if (choice > 0 && choice <= availableSubjects.size()) {
                Subject selectedSubject = availableSubjects.get(choice - 1);
                if (!selectedSubjectNames.contains(selectedSubject.getSubjectName())) {
                    selectedSubjectNames.add(selectedSubject.getSubjectName());
                    consoleIO.print(selectedSubject.getSubjectName() + " 과목이 선택되었습니다.");
                } else {
                    consoleIO.print("이미 선택된 과목입니다.");
                }
            } else {
                consoleIO.print("잘못된 입력입니다. 다시 시도해 주세요.");
            }
        }
        return selectedSubjectNames;
    }

    //   selectSubjectsName을 통해 선택된 과목을 통합하여 리스트로 반환시키는 메서드
    public List<String> selectSubjectNamesList() {
        List<String> selectedSubjectNames = new ArrayList<>();
        selectedSubjectNames.addAll(selectSubjectsName(SUBJECT_TYPE_MANDATORY,3));
        selectedSubjectNames.addAll(selectSubjectsName(SUBJECT_TYPE_CHOICE,2));
        return selectedSubjectNames;
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

    //  수강생의 객체중 입력(매개변수)한 id와 같은 id를 가진 객체를 찾아서 반환
    public Optional<Student> getStudentById(String studentId) {
        return studentStore.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    } // Optional객체를 사용해서 NUll값 반환 에러 방지

    // 수강생의 이름을 업데이트하는 메서드
    public void updateStudentName(Student student, String newName) {
        student.setStudentName(newName);
        consoleIO.print("수강생 이름이 성공적으로 업데이트되었습니다.");
    }

    // 수강생의 상태를 업데이트하는 메서드
    public void updateStudentStatus(Student student, String newStatus) {
        student.setStudentStatus(newStatus);
        consoleIO.print("수강생 이름이 성공적으로 업데이트되었습니다.");
    }

    // 수강생을 삭제하는 메서드
    public void deleteStudent(Student student) {
        if (studentStore.remove(student)) {
            consoleIO.print("수강생 정보가 성공적으로 삭제되었습니다.");
        } else {
            consoleIO.print("수강생 정보 삭제에 실패했습니다.");
        }
    }
}
