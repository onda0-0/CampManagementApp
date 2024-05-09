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
    public void createStudent(String studentName, List<String>  selectedSubjectNames,String studentStatus) {
        Student student = new Student(idGenerator.generateStudentId(), studentName,  selectedSubjectNames, studentStatus);
        studentStore.add(student);
    } // 필드에 생성자로 초기화된 idGenerator, studentStore 를 사용해 id,studentName 를 주입하고  selectSubjectNamesList를 통해 반환된 선택한과목 이름들 목록 주입후 Student 객체 생성후  studentStore 배열에 객체 넣어줌


    /*// studentStore에 저장된 수강생의 객체중 입력(매개변수)한 id와 같은 id를 가진 객체를 찾아서 반환
    public Optional<Student> getStudentById(String studentId) {
        return studentStore.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst();
    } // Optional객체를 사용해서 NUll값 반환 에러 방지->optional은 결과가 null이 발생할 가능성이 매우 높을때 사용하는게 좋다고 해서,*/

    // 수강생의 과목 선택 메서드
    public List<String> selectSubjectsName(String subjectType, int minSubjects) {
        List<String> selectedSubjectNames = new ArrayList<>();

        List<Subject> availableSubjects = subjectStore.stream()
                .filter(subject -> subject.getSubjectType().equals(subjectType))
                .toList(); // 생성자로 초기화한 subjectStore에서 일치하는 과목 유형(MANDATORY 또는 CHOICE)을 필터링하여 리스트로 변환

        consoleIO.print(subjectType.equals(SUBJECT_TYPE_MANDATORY) ? "필수 과목 선택:" : "선택 과목 선택:");
        for (int i = 0; i < availableSubjects.size(); i++) {
            consoleIO.print((i + 1) + ". " + availableSubjects.get(i).getSubjectName());
        } // 필터링에따른  과목 목록 출력

        //사용자가 과목목록을 입력했을때 유효성검사

        return selectedSubjectNames;
    }

    //   selectSubjectsName을 통해 선택된 과목을 통합하여 리스트로 반환시키는 메서드
    public List<String> selectSubjectNamesList() {
        List<String> selectedSubjectNames = new ArrayList<>();
        selectedSubjectNames.addAll(selectSubjectsName(SUBJECT_TYPE_MANDATORY, 3));
        selectedSubjectNames.addAll(selectSubjectsName(SUBJECT_TYPE_CHOICE, 2));
        return selectedSubjectNames;
    }

    //수강생 상태입력 메서드
    public String getStatusInput(){
        String status="";
        while(true) {
            status = consoleIO.getStringInput("수강생 상태 입력 (green, yellow, red): ");
            if (status.equalsIgnoreCase("green") || status.equalsIgnoreCase("yellow") || status.equalsIgnoreCase("red")) {
                break;
            }
            System.out.println("잘못된 상태입니다. 다시 입력하세요.");
        }
        status = status.toUpperCase();
        return status;
    }

    //중복 확인 메서드-리스트요소의 중복을 제거했을때 처음과 크기가 다르면 중복이 있다는 의미. 즉, 참이면 중복이 있다는 뜻이다.
    public static boolean hasDuplicates(List<?> list) {
        return list.stream().distinct().count() != list.size();
    }

    //유효범위 초과 여부 확인 메서드-true가 리턴되면 유효하지않은 값이 들어있다는 의미가 된다.
    public static boolean hasInvalidValues(List<String> stringList, int threshold) {
        List<Integer> intList = stringList.stream()
                .map(Integer::parseInt) // 문자열을 정수로 변환
                .collect(Collectors.toList());

        // 0보다 작은 값 또는 threshold보다 큰 값이 있는지 확인
        return intList.stream().anyMatch(value -> value < 0 || value > threshold);
    }



}
