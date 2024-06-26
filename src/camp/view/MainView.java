package camp.view;

import camp.utility.ConsoleIO;

public class MainView {
    private final StudentView studentView;
    private final ScoreView scoreView;
    private final ConsoleIO consoleIO;

    public MainView(StudentView studentView, ScoreView scoreView, ConsoleIO consoleIO) {
        this.studentView = studentView;
        this.scoreView = scoreView;
        this.consoleIO = consoleIO;
    }

    public void displayMainView() {
        boolean flag = true;
        while (flag) {
            consoleIO.print("\n==================================");
            consoleIO.print("메인 메뉴");
            consoleIO.print("1. 수강생 관리");
            consoleIO.print("2. 점수 관리");
            consoleIO.print("3. 프로그램 종료");
            int input = consoleIO.getIntInput("관리 항목을 선택하세요...");

            switch (input) {
                case 1:
                    studentView.handleStudentMenu();
                    break;
                case 2:
                    scoreView.handleScoreMenu(); // 점수 관리 메뉴
                    break;
                case 3:
                    flag = false;
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 되돌아갑니다!");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        consoleIO.print("프로그램 오류 발생!");
                        flag = false;
                    }
                    break;
            }
        }
    }

}
