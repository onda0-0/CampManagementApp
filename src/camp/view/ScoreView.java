package camp.view;

import camp.service.ScoreManager;
import camp.utility.ConsoleIO;

public class ScoreView {
    private ScoreManager scoreManager;
    private ConsoleIO consoleIO;

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
                //case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
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



    private void displayScoreView() {

    }

}
