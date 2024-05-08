package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ScoreManager {
    private final List<Score> scoreStore;
    private final List<Subject> subjectStore;
    private final List<Student> studentStore;
    private final ConsoleIO consoleIO;

    public ScoreManager(List<Score> scoreStore, List<Subject> subjectStore, List<Student> studentStore, ConsoleIO consoleIO) {
        this.scoreStore = scoreStore;
        this.subjectStore = subjectStore;
        this.studentStore = studentStore;
        this.consoleIO = consoleIO;
    }

    Scanner sc = new Scanner(System.in);

    private String getStudentId() {
        System.out.println("관리할 수강생의 번호를 입력하시오...");
        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");

        // 기능 구현

        //수강생 확인
        Student selectedStudent = null;
        for (Student student : studentStore) {
            if (getStudentId().equals(studentId)) {
                selectedStudent = student;
                break;
            }
        }
        if (selectedStudent == null) {
            System.out.println("존재하지 않는 수강생입니다");
            return;
        }
        System.out.println("등록할 과목을 선택하세요:");
        for (int i = 0; i < subjectStore.size(); i++) {
            Subject subject = subjectStore.get(i);
            System.out.println(i + 1 + ". " + (subject.getSubjectName()));
        }
        System.out.print("과목 번호를 입력하세요: ");
        int selectedIndex = sc.nextInt();
        if (selectedIndex < 0 || selectedIndex > subjectStore.size()) {
            System.out.println("잘못된 과목 번호입니다.");
            return;
        }
        //시험 회차 선택
        Subject selectedSubject = subjectStore.get(selectedIndex);

        System.out.print("등록할 시험 회차를 입력하세요 (1회차부터 10회차까지 가능): ");
        int scoreId = sc.nextInt();
        if (scoreId < 1 || scoreId > 10) {
            System.out.println("잘못된 회차입니다.");
            return;
        }
        // 이미 회차에 점수가 등록되어 있는지 확인
        boolean scoreIdExit = false;
        for (Score testscore : scoreStore) {
            if (testscore.getStudentId().equals(studentId) && testscore.getSubjectId().equals(selectedSubject.getSubjectId()) && testscore.getscoreId() == scoreId) {
                scoreIdExit = true;
                break;
            }
        }
        if (scoreIdExit) {
            System.out.println("이미 해당 회차에 점수가 등록되어 있습니다.");
        }

    }
}






