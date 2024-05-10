package camp.view;

import camp.model.Score;
import camp.service.ScoreManager;
import camp.utility.ConsoleIO;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ScoreView {
    private final ScoreManager scoreManager;
    private final ConsoleIO consoleIO;

    public ScoreView(ScoreManager scoreManager, ConsoleIO consoleIO) {
        this.scoreManager = scoreManager;
        this.consoleIO = consoleIO;
    }

    public void handleScoreMenu() {
        boolean flag = true;
        while (flag) {
            consoleIO.print("\n==================================\n");
            consoleIO.print("점수 관리 실행 중...");
            consoleIO.print("1. 수강생의 과목별 시험 회차 및 점수 등록");
            consoleIO.print("2. 수강생의 과목별 회차 점수 수정");
            consoleIO.print("3. 수강생의 특정 과목 회차별 등급 조회");
            consoleIO.print("4. 수강생의 과목별 평균 등급 조회");
            consoleIO.print("5. 특정 상태 수강생들의 필수 과목 평균 등급 조회");
            consoleIO.print("6. 수강생의 과목별 시험점수 삭제");
            consoleIO.print("7. 메인 화면 이동");
            int input = consoleIO.getIntInput("관리 항목을 선택하세요...");

            switch (input) {
                case 1:
                    createScoreProcess();
                    break;
                case 2:
                    updateRoundScoreProcess();
                    break;
                case 3:
                    inquireRoundGradeProcess();
                    break;
                case 4:
                    inquireAvgRateProcess();
                    break;
                case 5:
                    inquireStatusAvgProcess();
                    break;
                case 6:
                    removeRoundScoreProcess();
                    break;
                case 7:
                    flag = false;
                    consoleIO.print("메인 메뉴로 돌아갑니다.");
                    break;
                default:
                    consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
            }
        }
    }

    private void createScoreProcess() {
        String studentId, subjectId;
        int iteration, testScore;

        while (true) {
            studentId = consoleIO.getStringInput("수강생ID를 입력하세요:");
            if (studentId.isEmpty() || !scoreManager.isValidStudentId(studentId)) {
                consoleIO.print("유효한 수강생 ID를 입력하세요. 입력하신 ID는 존재하지 않거나 비어 있습니다.");
                continue;
            }
            break;
        }

        while (true) {
            subjectId = consoleIO.getStringInput("과목ID를 입력하세요:");
            if (subjectId.isEmpty() || !scoreManager.isValidSubjectId(subjectId)) {
                consoleIO.print("유효한 과목 ID를 입력하세요. 입력하신 ID는 존재하지 않거나 비어 있습니다.");
                continue;
            }
            break;
        }

        while (true) {
            iteration = consoleIO.getIntInput("등록할 시험 회차를 입력하세요 (1회차부터 10회차까지 가능):");
            if (iteration < 1 || iteration > 10) {
                consoleIO.print("회차 번호는 1에서 10 사이여야 합니다.");
                continue;
            }
            break;
        }

        while (true) {
            testScore = consoleIO.getIntInput("점수를 입력하세요 (0 ~ 100):");
            if (testScore < 0 || testScore > 100) {
                consoleIO.print("점수는 0에서 100 사이의 값이어야 합니다.");
                continue;
            }
            break;
        }

        if (scoreManager.createScore(studentId, subjectId, iteration, testScore)) {
            consoleIO.print("점수 등록 성공!");
        } else {
            consoleIO.print("점수 등록 실패. 정보를 확인해주세요.");
        }
    }


    private void updateRoundScoreProcess() {
        String studentId, subjectId;
        int scoreId, newScore;

        while (true) {
            studentId = consoleIO.getStringInput("수강생ID를 입력하세요:");
            if (studentId.isEmpty() || !scoreManager.isValidStudentId(studentId)) {
                consoleIO.print("수강생 ID는 비어 있을 수 없으며, 유효한 수강생 ID를 입력하세요.");
                continue;
            }
            break;
        }

        while (true) {
            subjectId = consoleIO.getStringInput("과목ID를 입력하세요:");
            if (subjectId.isEmpty() || !scoreManager.isValidSubjectId(subjectId)) {
                consoleIO.print("과목 ID는 비어 있을 수 없으며, 유효한 과목 ID를 입력하세요.");
                continue;
            }
            break;
        }
        scoreId = consoleIO.getIntInput("수정 할 회차 입력:");

        if (scoreId < 1 || scoreId > 10) {
            consoleIO.print("회차 번호는 1에서 10 사이여야 합니다.");
            return;
        }

        newScore = consoleIO.getIntInput("새로운 점수 입력 (0 ~ 100):");
        if (newScore < 0 || newScore > 100) {
            consoleIO.print("점수는 0에서 100 사이의 값이어야 합니다.");
            return;
        }

        if (scoreManager.updateRoundScoreBySubject(studentId, subjectId, scoreId, newScore)) {
            consoleIO.print("점수 수정 성공!");
        } else {
            consoleIO.print("점수 수정 실패. 정보를 확인해주세요.");
        }
    }
    private void inquireRoundGradeProcess() {
        String studentId;
        while (true) {
            studentId = consoleIO.getStringInput("수강생ID를 입력하세요:");
            if (studentId.isEmpty() || !scoreManager.isValidStudentId(studentId)) {
                consoleIO.print("유효한 수강생 ID를 입력하세요.");
                continue;
            }
            break;
        }

        String subjectId;
        while (true) {
            subjectId = consoleIO.getStringInput("과목ID를 입력하세요:");
            if (subjectId.isEmpty() || !scoreManager.isValidSubjectId(subjectId)) {
                consoleIO.print("유효한 과목 ID를 입력하세요.");
                continue;
            }
            break;
        }


        List<Score> scores = scoreManager.inquireRoundGradeBySubject(studentId, subjectId);
        if (scores.isEmpty()) {
            consoleIO.print("해당 수강생과 과목에 대한 점수 정보가 없습니다.");
        } else {
            StringBuilder gradesOutput = new StringBuilder("회차별 등급 조회 결과:\n");
            for (Score score : scores) {
                gradesOutput.append("회차 ").append(score.getIteration()).append(": 점수 ")
                        .append(score.getScore()).append(", 등급 ").append(score.getGrade()).append("\n");
            }
            consoleIO.print(gradesOutput.toString());
        }
    }

    private void inquireAvgRateProcess() {
        String studentId;
        while (true) {
            studentId = consoleIO.getStringInput("수강생ID를 입력하세요:");
            if (studentId.isEmpty() || !scoreManager.isValidStudentId(studentId)) {
                consoleIO.print("수강생 ID는 비어 있을 수 없으며 유효해야 합니다.");
                continue;
            }
            break;
        }

        String subjectId;
        while (true) {
            subjectId = consoleIO.getStringInput("과목ID를 입력하세요:");
            if (subjectId.isEmpty() || !scoreManager.isValidSubjectId(subjectId)) {
                consoleIO.print("과목 ID는 비어 있을 수 없으며 유효해야 합니다.");
                continue;
            }
            break;
        }


        double avgRate = scoreManager.inquireAvgRateBySubject(studentId, subjectId);
        if (Double.isNaN(avgRate)) {
            consoleIO.print("해당 과목에 대한 평균 점수 정보가 없습니다.");
        } else {
            consoleIO.print("과목의 평균 등급: " + avgRate);
        }
    }

    private void inquireStatusAvgProcess() {
        List<String> validStatuses = Arrays.asList("Green", "Yellow", "Red", "Unknown");
        String status = consoleIO.getStringInput("조회할 수강생 상태를 입력하세요 (Green, Yellow, Red, Unknown):").trim();

        while (!validStatuses.contains(status)) {
            consoleIO.print("입력한 상태가 유효하지 않습니다. Green, Yellow, Red, Unknown 중 하나를 입력해주세요.");
            status = consoleIO.getStringInput("조회할 수강생 상태를 입력하세요 (Green, Yellow, Red, Unknown):").trim();
        }

        Map<String, String> avgGrades = scoreManager.inquireStatusAvgBySubject(status);

        if (avgGrades.isEmpty()) {
            consoleIO.print("해당 상태의 수강생이 없거나 데이터가 부족합니다.");
        } else {
            avgGrades.forEach((studentName, grade) -> consoleIO.print(studentName + "의 필수 과목 평균 등급: " + grade));
        }
    }

    private void removeRoundScoreProcess() {
        String studentId, subjectId;
        int iteration;

        while (true) {
            studentId = consoleIO.getStringInput("수강생ID를 입력하세요:");
            if (studentId.isEmpty() || !scoreManager.isValidStudentId(studentId)) {
                consoleIO.print("수강생 ID는 비어 있을 수 없으며, 유효한 수강생 ID를 입력하세요.");
                continue;
            }
            break;
        }

        while (true) {
            subjectId = consoleIO.getStringInput("과목ID를 입력하세요:");
            if (subjectId.isEmpty() || !scoreManager.isValidSubjectId(subjectId)) {
                consoleIO.print("과목 ID는 비어 있을 수 없으며, 유효한 과목 ID를 입력하세요.");
                continue;
            }
            break;
        }

        while (true) {
            try {
                iteration = consoleIO.getIntInput("삭제 할 회차 입력:");
                if (iteration < 1 || iteration > 10) {
                    consoleIO.print("회차 번호는 1에서 10 사이여야 합니다.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                consoleIO.print("유효한 회차 번호를 입력해주세요.");
            }
        }

        String confirmation = consoleIO.getStringInput("정말로 삭제하시겠습니까? (y/n):").trim().toLowerCase();
        if (confirmation.equals("y")) {
            if (scoreManager.removeRoundScoreBySubject(studentId, subjectId, iteration)) {
                consoleIO.print("점수 삭제 성공!");
            } else {
                consoleIO.print("점수 삭제 실패. 정보를 확인해주세요.");
            }
        } else if (confirmation.equals("n")) {
            consoleIO.print("점수 삭제가 취소되었습니다.");
        } else {
            consoleIO.print("잘못된 입력입니다. 'y' 또는 'n'을 입력해주세요.");
        }
    }

}
