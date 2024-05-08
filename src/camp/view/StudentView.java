package camp.view;


import camp.service.StudentManager;
import camp.utility.ConsoleIO;

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
                    // 수험생 생성 로직
                    break;
                case 2:
                    // 수험생 조회 로직
                    break;
                case 3:
                    // 특정 수험생 검색 로직
                    break;
                case 4:
                    running = false; // 수험생 관리 반복 로직 탈출
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요."); // 수험생 관리 반복 로직 다시 반복
                    break;
            }
        }
    }
}
