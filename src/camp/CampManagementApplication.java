package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.service.IdSequenceGenerator;
import camp.service.ScoreManager;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;
import camp.service.DataInitializer;
import camp.view.MainView;
import camp.view.ScoreView;
import camp.view.StudentView;

import java.util.List;


public class CampManagementApplication {
    public static void main(String[] args) {

        ConsoleIO consoleIO = new ConsoleIO();//입력관련 간단하게 함수로 구현
        IdSequenceGenerator idGenerator = new IdSequenceGenerator();//id 증가 관련 함수

        //수강생,과목,점수 저장소 리스트

        List<Student> studentStore = DataInitializer.initializeStudents();
        List<Subject> subjectStore = DataInitializer.initializeSubjects(idGenerator);
        List<Score> scoreStore = DataInitializer.initializeScores();


        //수강생관리,점수관리
        StudentManager studentManager = new StudentManager(studentStore, subjectStore, consoleIO, idGenerator);
        ScoreManager scoreManager = new ScoreManager(scoreStore, subjectStore, studentStore, consoleIO);

        //화면출력

        StudentView studentView = new StudentView(studentManager, consoleIO);
        ScoreView scoreView = new ScoreView(scoreManager, consoleIO);
        MainView mainView = new MainView(studentView, scoreView, consoleIO);

        mainView.displayMainView();
    }
}

//import camp.model.Score;
//import camp.model.Student;
//import camp.model.Subject;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
///**
// * Notification
// * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
// * main 메서드를 실행하면 프로그램이 실행됩니다.
// * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
// * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
// * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
// */
//public class CampManagementApplication {
//    // 데이터 저장소
//    private static List<Student> studentStore;
//    private static List<Subject> subjectStore;
//    private static List<Score> scoreStore;
//
//    // 과목 타입
//    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
//    private static String SUBJECT_TYPE_CHOICE = "CHOICE";
//
//    // index 관리 필드
//    private static int studentIndex;
//    private static final String INDEX_TYPE_STUDENT = "ST";
//    private static int subjectIndex;
//    private static final String INDEX_TYPE_SUBJECT = "SU";
//    private static int scoreIndex;
//    private static final String INDEX_TYPE_SCORE = "SC";
//
//    // 스캐너
//    private static Scanner sc = new Scanner(System.in);
//
//    public static void main(String[] args) {
//        setInitData();
//        try {
//            displayMainView();
//        } catch (Exception e) {
//            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
//        }
//    }
//
//    // 초기 데이터 생성
//    private static void setInitData() {
//        studentStore = new ArrayList<>();
//        subjectStore = List.of(
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "Java",
//                        SUBJECT_TYPE_MANDATORY
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "객체지향",
//                        SUBJECT_TYPE_MANDATORY
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "Spring",
//                        SUBJECT_TYPE_MANDATORY
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "JPA",
//                        SUBJECT_TYPE_MANDATORY
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "MySQL",
//                        SUBJECT_TYPE_MANDATORY
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "디자인 패턴",
//                        SUBJECT_TYPE_CHOICE
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "Spring Security",
//                        SUBJECT_TYPE_CHOICE
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "Redis",
//                        SUBJECT_TYPE_CHOICE
//                ),
//                new Subject(
//                        sequence(INDEX_TYPE_SUBJECT),
//                        "MongoDB",
//                        SUBJECT_TYPE_CHOICE
//                )
//        );
//        scoreStore = new ArrayList<>();
//    }
//
//    // index 자동 증가
//    private static String sequence(String type) {
//        switch (type) {
//            case INDEX_TYPE_STUDENT -> {
//                studentIndex++;
//                return INDEX_TYPE_STUDENT + studentIndex;
//            }
//            case INDEX_TYPE_SUBJECT -> {
//                subjectIndex++;
//                return INDEX_TYPE_SUBJECT + subjectIndex;
//            }
//            default -> {
//                scoreIndex++;
//                return INDEX_TYPE_SCORE + scoreIndex;
//            }
//        }
//    }
//
//    private static void displayMainView() throws InterruptedException {
//        boolean flag = true;
//        while (flag) {
//            System.out.println("\n==================================");
//            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
//            System.out.println("1. 수강생 관리");
//            System.out.println("2. 점수 관리");
//            System.out.println("3. 프로그램 종료");
//            System.out.print("관리 항목을 선택하세요...");
//            int input = sc.nextInt();
//
//            switch (input) {
//                case 1 -> displayStudentView(); // 수강생 관리
//                case 2 -> displayScoreView(); // 점수 관리
//                case 3 -> flag = false; // 프로그램 종료
//                default -> {
//                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
//                    Thread.sleep(2000);
//                }
//            }
//        }
//        System.out.println("프로그램을 종료합니다.");
//    }
//
//    private static void displayStudentView() {
//        boolean flag = true;
//        while (flag) {
//            System.out.println("==================================");
//            System.out.println("수강생 관리 실행 중...");
//            System.out.println("1. 수강생 등록");
//            System.out.println("2. 수강생 목록 조회");
//            System.out.println("3. 메인 화면 이동");
//            System.out.print("관리 항목을 선택하세요...");
//            int input = sc.nextInt();
//
//            switch (input) {
//                case 1 -> createStudent(); // 수강생 등록
//                case 2 -> inquireStudent(); // 수강생 목록 조회
//                case 3 -> flag = false; // 메인 화면 이동
//                default -> {
//                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
//                    flag = false;
//                }
//            }
//        }
//    }
//
//    // 수강생 등록
//    private static void createStudent() {
//        System.out.println("\n수강생을 등록합니다...");
//        System.out.print("수강생 이름 입력: ");
//        String studentName = sc.next();
//        // 기능 구현 (필수 과목, 선택 과목)
//
//        Student student = new Student(sequence(INDEX_TYPE_STUDENT), studentName); // 수강생 인스턴스 생성 예시 코드
//        // 기능 구현
//        System.out.println("수강생 등록 성공!\n");
//    }
//
//    // 수강생 목록 조회
//    private static void inquireStudent() {
//        System.out.println("\n수강생 목록을 조회합니다...");
//        // 기능 구현
//        System.out.println("\n수강생 목록 조회 성공!");
//    }
//
//    private static void displayScoreView() {
//        boolean flag = true;
//        while (flag) {
//            System.out.println("==================================");
//            System.out.println("점수 관리 실행 중...");
//            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
//            System.out.println("2. 수강생의 과목별 회차 점수 수정");
//            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
//            System.out.println("4. 수강생의 과목별 평균 등급 조회");
//            System.out.println("5. 특정 상태 수강생들의 필수 과목 평균 등급 조회");
//            System.out.println("6. 메인 화면 이동");
//            System.out.print("관리 항목을 선택하세요...");
//            int input = sc.nextInt();
//
//            switch (input) {
//                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
//                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
//                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
//                case 4 -> inquireAvgRateBySubject(); // 수강생의 과목별 평균 등급 조회
//                case 5 -> inquireStatusAvgBySubject(); // 특정 상태 수강생들의 필수 과목 평균 등급 조회
//                case 6 -> flag = false; // 메인 화면 이동
//                default -> {
//                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
//                    flag = false;
//                }
//            }
//        }
//    }
//
//    private static String getStudentId() {
//        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
//        return sc.next();
//    }
//
//    // 수강생의 과목별 시험 회차 및 점수 등록
//    private static void createScore() {
//        String studentId = getStudentId(); // 관리할 수강생 고유 번호
//        System.out.println("시험 점수를 등록합니다...");
//        // 기능 구현
//        System.out.println("\n점수 등록 성공!");
//    }
//
//    // 수강생의 과목별 회차 점수 수정
//    private static void updateRoundScoreBySubject() {
//        String studentId = getStudentId(); // 관리할 수강생 고유 번호
//        // 기능 구현 (수정할 과목 및 회차, 점수)
//        System.out.println("시험 점수를 수정합니다...");
//        // 기능 구현
//        System.out.println("\n점수 수정 성공!");
//    }
//    // 조회할 과목 ID 입력
//    private static String getSubjectId() {
//        System.out.print("\n조회하고 싶은 과목 번호을 입력하시오...");
//        return sc.next();
//    }
//
//    // 수강생의 특정 과목 회차별 등급 조회
//    private static void inquireRoundGradeBySubject() {
//        String studentId = getStudentId(); // 관리할 수강생 고유 번호
//        String subjectId = getSubjectId(); // 조회할 과목 고유 번호
//
//        List<Score> filterScore = scoreStore.stream()
//                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId))
//                .toList();
//        System.out.println("회차별 등급을 조회합니다...");
//        // 기능 구현
//        System.out.println("\n등급 조회 성공!");
//        if(filterScore.isEmpty()){
//            System.out.println("조회할 정보가 없습니다.");
//        } else {
//            for(Score score : filterScore){
//                System.out.println(score.getScoreId() + "회차" + "등급: " + score.getTestRate());
//            }
//            System.out.println("\n등급 조회 성공!");
//        }
//    }
//    // 수강생의 과목별 평균 등급 조회
//    private static void inquireAvgRateBySubject() {
//        String studentId = getStudentId(); // 관리할 수강생 고유 번호
//        String subjectId = getSubjectId(); // 조회할 과목 번호 입력
//        // 수강생의 과목별 평균 등급 조회
//        System.out.println("과목별 평균 등급을 조회합니다...");
//        double avgScore = scoreStore.stream().filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId))
//                .mapToDouble(Score::getTestScore)
//                .average().getAsDouble();
//        // 과목 이름
//        subjectStore.stream().filter(name -> name.getSubjectId().equals(subjectId))
//                .forEach(f -> System.out.println(f.getSubjectName()));
//
//        // 과목 타입
//        for(Subject subject : subjectStore){
//            if(subject.getSubjectId().equals(subjectId)){
//                String subjectType = subject.getSubjectType();
//                if(subjectType.equals("MANDATORY")){
//                    if(avgScore>=95){
//                        System.out.println('A');
//                    }else if(avgScore>=90){
//                        System.out.println('B');
//                    }else if(avgScore>=80){
//                        System.out.println('C');
//                    }else if(avgScore>=70){
//                        System.out.println('D');
//                    }else if(avgScore>=60){
//                        System.out.println('F');
//                    }else if(avgScore<60){
//                        System.out.println('N');
//                    }
//                }
//                if(subjectType.equals("CHOICE")) {
//                    if (avgScore >= 90) {
//                        System.out.println('A');
//                    } else if (avgScore >= 80) {
//                        System.out.println('B');
//                    } else if (avgScore >= 70) {
//                        System.out.println('C');
//                    } else if (avgScore >= 60) {
//                        System.out.println('D');
//                    } else if (avgScore >= 50) {
//                        System.out.println('F');
//                    } else if (avgScore < 50) {
//                        System.out.println('N');
//                    }
//                }
//            }
//        }
//        System.out.println("\n평균 등급 조회 성공!");
//
//    }
//    // 특정 상태 수강생들의 필수 과목 평균 등급을 조회
//    private static void inquireStatusAvgBySubject() {
//
//    }
//
//}
