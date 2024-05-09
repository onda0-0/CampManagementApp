package camp.view;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.service.DataInitializer;
import camp.service.ScoreManager;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;

import java.util.List;
import java.util.Scanner;

public class ScoreView {
    private ScoreManager scoreManager;
    private ConsoleIO consoleIO;
    Scanner sc = new Scanner(System.in);

    public ScoreView(ScoreManager scoreManager, ConsoleIO consoleIO) {
        this.scoreManager = scoreManager;
        this.consoleIO = consoleIO;

    }


    //점수 관리 메뉴 핸들링 메서드
    public void handleScoreMenu() {
        boolean flag = true;
        while (flag) {
            consoleIO.print("\n==================================\n");
            consoleIO.print("점수 관리 실행 중...");
            consoleIO.print("1. 수강생의 과목별 시험 회차 및 점수 등록");
            consoleIO.print("2. 수강생의 과목별 회차 점수 수정");
            consoleIO.print("3. 수강생의 특정 과목 회차별 등급 조회");
            consoleIO.print("4. 수강생의 과목별 시험점수 삭제");
            consoleIO.print("5. 메인 화면 이동");
            int input = consoleIO.getIntInput("관리 항목을 선택하세요...");

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> scoreManager.updateRoundScoreBySubject(consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ")
                        , consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ")); // 수강생의 과목별 회차 점수 수정

                //case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> scoreManager.removeRoundScoreBySubject(consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ")
                        , consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ")); //수강생의 과목별 시험점수 삭제
                case 5 -> flag = false; // 메인 화면 이동
                default -> {
                    consoleIO.print("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }


    public String getStudentId() {
       consoleIO.print("관리할 수강생의 번호를 입력하시오...");
        return sc.next();

    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        consoleIO.print("시험 점수를 등록합니다...");
        // 기능 구현
        Student selectedStudent = null;
        for (Student student : scoreManager.getStudentStore() ) {
            if (getStudentId().equals(studentId)) {
                selectedStudent = student;
                break;
            }
        }
        if (selectedStudent == null) {
            System.out.println("다시 확인해주세요");
            return;
        }


        consoleIO.print("등록할 과목을 선택하세요:");
        for (int i = 0; i < scoreManager.getSubjectStore().size(); i++) {
            Subject subject = scoreManager.getSubjectStore().get(i);
            System.out.println(i + 1 + ". " + subject.getSubjectName());
        }
        consoleIO.print("과목 번호를 입력하세요: ");
        int selectedIndex = sc.nextInt();
        if (selectedIndex < 0 || selectedIndex > scoreManager.getSubjectStore().size()) {
            System.out.println("잘못된 과목 번호입니다.");
            return;
        }
        Subject selectedSubject = scoreManager.getSubjectStore().get(selectedIndex);


        consoleIO.print("등록할 시험 회차를 입력하세요 (1회차부터 10회차까지 가능): ");
        int iteration = sc.nextInt();
        if (iteration < 1 || iteration > 10) {
            consoleIO.print("잘못된 회차입니다.");
            return;
        }

        // 이미 회차에 점수가 등록되어 있는지 확인
        boolean iterationExit = false;
        for (Score testscore : scoreManager.getScoreStore()) {
            if (testscore.returnFindingStudentId().equals(studentId) && testscore.returnFindingSubjectId().equals(selectedSubject.getSubjectId()) && testscore.getIteration() == iteration) {
                iterationExit = true;
                break;
            }
        }
        if (iterationExit) {
            consoleIO.print("이미 해당 회차에 점수가 등록되어 있습니다.");
            return;
        }
        int testsscore;
        do {
            System.out.print("점수를 입력하세요: ");
            testsscore = sc.nextInt();
            if (testsscore < 0 || testsscore > 100) {
                System.out.println("올바른 점수를 입력해주세요");
            }
        } while (testsscore < 0 || testsscore > 100);



        char scoreGrade;
        if (selectedSubject.getSubjectType().equals(DataInitializer.getSubjectTypeMandatory())) {
            if (testsscore >= 95)
                scoreGrade = 'A';
            else if (testsscore >= 90)
                scoreGrade = 'B';
            else if (testsscore >= 80)
                scoreGrade = 'C';
            else if (testsscore >= 70)
                scoreGrade = 'D';
            else if (testsscore >= 60)
                scoreGrade = 'F';
            else
                scoreGrade = 'N';
        } else {
            if (testsscore >= 90)
                scoreGrade = 'A';
            else if (testsscore >= 80)
                scoreGrade = 'B';
            else if (testsscore >= 70)
                scoreGrade = 'C';
            else if (testsscore >= 60)
                scoreGrade = 'E';
            else if (testsscore >= 50)
                scoreGrade = 'F';
            else
                scoreGrade = 'N';


            Score score = new Score(studentId, selectedSubject.getSubjectId(), iteration, testsscore, scoreGrade);
            scoreManager.getScoreStore().add(score);

            consoleIO.print(iteration + "회차 " + selectedSubject.getSubjectName() + "시험 점수:" + testsscore + " 등급:" +scoreGrade + " 등록 완료");


        }
    }


    private void displayScoreView() {


    }
}



