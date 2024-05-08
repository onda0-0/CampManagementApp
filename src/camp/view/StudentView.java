package camp.view;


import camp.model.Student;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;

import java.util.List;


public class StudentView {
    // 필드
    private StudentManager studentManager; // 수강생 관리 객체
    private ConsoleIO consoleIO; // 입출력 유틸리티 객체

    // 생성자
    public StudentView(StudentManager studentManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.consoleIO = consoleIO;
    }

    // 수강생 관리 메뉴 핸들링 메서드
    public void handleStudentMenu() {
        boolean running = true;
        while (running) {
            consoleIO.print("==================================");
            consoleIO.print("수강생 관리 실행 중...");
            consoleIO.print("1. 수강생 등록");
            consoleIO.print("2. 수강생 목록 조회");
            consoleIO.print("3. 수강생 정보(수정/삭제)");
            consoleIO.print("4. 메인 화면 이동");
            int choice = consoleIO.getIntInput("관리 항목을 선택하세요...");
            switch (choice) {
                case 1:
                    // 수강생 생성 로직
                    break;
                case 2:
                    displayStudents();  // 수강생 조회 로직
                    break;
                case 3:
                    // 수강생 정보 검색 로직
                    break;
                case 4:
                    running = false; // 수강생 관리 반복 로직 탈출
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요."); // 수강생 관리 로직 다시 반복
                    break;
            }
        }
    }


    // 수강생 조회 로직
    private void displayStudents() {
        boolean running = true;
        while (running) {
            consoleIO.print("\n수강생 목록 조회:");
            consoleIO.print("1. 전체 수강생 조회");
            consoleIO.print("2. 상태별 수강생 목록 조회");
            consoleIO.print("3. 수강생 관리 메뉴로 돌아가기");

            int choice = consoleIO.getIntInput("조회 항목을 선택하세요:");
            switch (choice) {
                case 1:
                    displayAllStudents() ;// 전체 수강생 목록 조회 메서드 호출
                    running = false; // 메서드 호출 후 수강생 조회 로직 반복 로직 탈출
                    break;
                case 2:
                    // 상태별 수강생 목록 조회 메서드 호출
                    running = false; // 메서드 호출 후 수강생 조회 로직 반복 로직 탈출
                    break;
                case 3:
                    running = false; // 수강생 조회 로직 반복 로직 탈출
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요."); // 수강생 조회 로직 다시 반복
                    break;
            }
        }
    }

    // 전체 수강생 목록 조회 메서드
    private void displayAllStudents() {
        List<Student> students =   studentManager.getAllStudents();
        if (students.isEmpty()) {
            consoleIO.print("등록된 수강생이 없습니다.");
        } else {
            students.forEach(student -> {
                // 아이디와 이름만 출력
                String studentInfo = String.format("ID: %s, Name: %s", student.getStudentId(), student.getStudentName());
                consoleIO.print(studentInfo);
            });
        }
    }
}
