package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public void updateRoundScoreBySubject() {
        consoleIO.print("========점수 수정=======");
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("수정 할 회차 입력 : ");

        if (scoreStore.isEmpty()) {
            consoleIO.print("현재 어떤 정보 점수도 기록되어있지 않습니다.");
            return;
        }

        Score foundScore = scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId) && score.getIteration() == scoreId)
                .findFirst().orElse(null);

        if (foundScore == null) {
            consoleIO.print("해당 점수 정보를 찾을 수 없습니다.");
            return;
        }

        consoleIO.print("현재 점수: " + foundScore.getScore());
        int newScore = consoleIO.getIntInput("새로운 점수 입력 (0 ~ 100): ");
        if (newScore < 0 || newScore > 100) {
            consoleIO.print("점수는 0에서 100 사이의 값이어야 합니다.");
            return;
        }
        foundScore.setScore(newScore);

        // 과목 유형 확인
        String subjectType = subjectStore.stream()
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .findFirst()
                .map(Subject::getSubjectType)
                .orElse("Unknown");

        String newGrade = determineGrade(subjectType, newScore);
        foundScore.setGrade(newGrade);
        consoleIO.print("점수가 성공적으로 업데이트되었습니다. 새 등급: " + newGrade);
    }



    // 수강생의 과목별 시험 회차 및 점수 등록
    public void createScore() {
        String studentId = consoleIO.getStringInput("관리할 수강생의 ID를 입력하세요:");
        consoleIO.print("시험 점수를 등록합니다...");

        Student selectedStudent = studentStore.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (selectedStudent == null) {
            consoleIO.print("해당 수강생을 찾을 수 없습니다. 다시 확인해주세요.");
            return;
        }

        consoleIO.print("등록할 과목을 선택하세요:");
        for (int i = 0; i < subjectStore.size(); i++) {
            consoleIO.print((i + 1) + ". " + subjectStore.get(i).getSubjectName());
        }

        int selectedIndex = consoleIO.getIntInput("과목 번호를 입력하세요:");
        if (selectedIndex < 1 || selectedIndex > subjectStore.size()) {
            consoleIO.print("잘못된 과목 번호입니다. 다시 입력해 주세요.");
            return;
        }
        Subject selectedSubject = subjectStore.get(selectedIndex - 1);

        int iteration = consoleIO.getIntInput("등록할 시험 회차를 입력하세요 (1회차부터 10회차까지 가능):");
        if (iteration < 1 || iteration > 10) {
            consoleIO.print("잘못된 회차입니다. 1에서 10 사이의 값을 입력하세요.");
            return;
        }

        boolean iterationExists = scoreStore.stream()
                .anyMatch(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(selectedSubject.getSubjectId()) && score.getIteration() == iteration);

        if (iterationExists) {
            consoleIO.print("이미 해당 회차에 점수가 등록되어 있습니다.");
            return;
        }

        int testScore = consoleIO.getIntInput("점수를 입력하세요 (0 ~ 100):");
        if (testScore < 0 || testScore > 100) {
            consoleIO.print("점수는 0에서 100 사이의 값이어야 합니다.");
            return;
        }

        String grade = determineGrade(selectedSubject.getSubjectType(), testScore);

        Score newScore = new Score(studentId, selectedSubject.getSubjectId(), iteration, testScore, grade);
        scoreStore.add(newScore);

        consoleIO.print(selectedStudent.getStudentId() + "." + selectedStudent.getStudentName() + "님의 " + iteration + "회차 " + selectedSubject.getSubjectName() + " 시험 점수: " + testScore + " 등급: " + grade + " 등록 완료");
    }


    public void removeRoundScoreBySubject() {
        consoleIO.print("========점수 삭제=======");
        String studentId = consoleIO.getStringInput("수강생의 고유 번호를 입력하세요 : ");
        String subjectId = consoleIO.getStringInput("과목의 고유 번호를 입력하세요 : ");
        int scoreId = consoleIO.getIntInput("삭제 할 회차 입력 : ");
        int findFlag = 0;
        // 기능 구현 (삭제 할 과목 및 회차, 점수)
        String removeCheck = consoleIO.getStringInput("해당하는 "+studentId+"학생의 "+subjectId+"과목의 "+scoreId+"회차 점수를 삭제하시겠습니까?(y/n) : ");
        if(removeCheck.equals("y") || removeCheck.equals("Y") ){
            for (Score findingScore : scoreStore){
                if(findingScore.returnFindingStudentId().equals(studentId)) {
                    if(findingScore.returnFindingSubjectId().equals(subjectId)) {
                        if (findingScore.returnFindingScoreId() == scoreId){
                            scoreStore.remove(findingScore);
                            consoleIO.print("삭제 완료!");
                            findFlag++;
                            break;
                        }
                        else{
                            consoleIO.print("해당하는 학생의 과목 점수가 없습니다.");
                        }
                    }
                }
            }
        }
        else{
            consoleIO.print("---취소를 선택하셨습니다. 삭제를 취소합니다.---");
        }
        if (findFlag == 0){
            consoleIO.print("해당 정보를 찾지 못해 삭제하지 못했습니다.");
        }
    }






    // 수강생의 특정 과목 회차별 등급 조회
    public void inquireRoundGradeBySubject() {
        String studentId = consoleIO.getStringInput("\n관리할 수강생의 번호를 입력하시오..."); // 관리할 수강생 고유 번호
        String subjectId = consoleIO.getStringInput("\n조회하고 싶은 과목 번호을 입력하시오..."); // 조회할 과목 고유 번호
        System.out.println("회차별 등급을 조회합니다...");
        List<Score> filterScore = scoreStore.stream()
                .filter(score -> score.returnFindingStudentId().equals(studentId) && score.returnFindingSubjectId().equals(subjectId))
                .toList();

        if(filterScore.isEmpty()){
            System.out.println("조회할 정보가 없습니다.");
        } else {
            for(Score score : filterScore){
                System.out.println(score.returnFindingScoreId() + "회차" + "등급: " + score.getTestRate());
            }
            System.out.println("\n등급 조회 성공!");
        }
    }
    // 수강생의 과목별 평균 등급 조회
    public void inquireAvgRateBySubject() {
        String studentId = consoleIO.getStringInput("\n관리할 수강생의 번호를 입력하시오..."); // 관리할 수강생 고유 번호
        String subjectId = consoleIO.getStringInput("\n조회하고 싶은 과목 번호를 입력하시오..."); // 조회할 과목 고유 번호
        System.out.println("과목별 평균 등급을 조회합니다...");

        // 평균 점수 계산
        double avgScore = scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId))
                .mapToDouble(Score::getScore)
                .average()
                .orElse(Double.NaN); // 평균이 없을 경우 NaN 반환

        // 과목 이름과 과목 유형 얻기
        subjectStore.stream()
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .findFirst()
                .ifPresent(subject -> {
                    System.out.println(subject.getSubjectName() + " 과목의 평균 점수: " + avgScore);
                    // 등급 결정 및 출력
                    String grade = determineGrade(subject.getSubjectName(), avgScore);
                    System.out.println("평균 등급: " + grade);
                });

        System.out.println("\n평균 등급 조회 성공!");
    }
    // 특정 상태 수강생들의 필수 과목 평균 등급을 조회
    public void inquireStatusAvgBySubject() {
        Map<String, List<Map<String, String>>> filteredScores = filterScores();
        if (filteredScores == null || filteredScores.isEmpty()) {
            consoleIO.print("선택한 기준에 맞는 점수 데이터가 없습니다.");
            return;
        }
        Map<String, Map<String, String>> gradesByStudent = calculateGradesByStudent(filteredScores);
        gradesByStudent.forEach((studentName, grades) -> {
            consoleIO.print(studentName + "의 평균 등급은 다음과 같습니다:");
            grades.forEach((subject, grade) -> consoleIO.print(subject + ": " + grade));
        });
    }

    public Map<String, List<Map<String, String>>> filterScores() {
        consoleIO.print("\n상태를 선택해주세요:");
        consoleIO.print("1. Green");
        consoleIO.print("2. Yellow");
        consoleIO.print("3. Red");
        consoleIO.print("4. Unknown");
        consoleIO.print("5. 취소");

        int choice = consoleIO.getIntInput("조회할 상태를 선택하세요:");
        if (choice == 5) {
            consoleIO.print("취소되었습니다.");
            return null;
        }

        String[] statuses = {"Green", "Yellow", "Red", "Unknown"};
        String selectedStatus = (choice >= 1 && choice <= 4) ? statuses[choice - 1] : null;
        if (selectedStatus == null) {
            consoleIO.print("잘못된 입력입니다. 다시 시도하세요.");
            return null;
        }

        List<String> filteredStudentIds = studentStore.stream()
                .filter(student -> student.getStudentStatus().equalsIgnoreCase(selectedStatus))
                .map(Student::getStudentId)
                .toList();

        List<String> mandatorySubjectIds = subjectStore.stream()
                .filter(subject -> "MANDATORY".equals(subject.getSubjectType()))
                .map(Subject::getSubjectId)
                .toList();

        Map<String, String> studentIdToName = studentStore.stream()
                .collect(Collectors.toMap(Student::getStudentId, Student::getStudentName));

        Map<String, String> subjectIdToName = subjectStore.stream()
                .collect(Collectors.toMap(Subject::getSubjectId, Subject::getSubjectName));

        return scoreStore.stream()
                .filter(score -> filteredStudentIds.contains(score.getStudentId()) && mandatorySubjectIds.contains(score.getSubjectId()))
                .collect(Collectors.groupingBy(
                        score -> studentIdToName.get(score.getStudentId()),
                        Collectors.mapping(
                                score -> {
                                    Map<String, String> details = new HashMap<>();
                                    details.put("subjectName", subjectIdToName.get(score.getSubjectId()));
                                    details.put("iteration", String.valueOf(score.getIteration()));
                                    details.put("score", String.valueOf(score.getScore()));
                                    return details;
                                },
                                Collectors.toList()
                        )
                ));
    }

    public Map<String, Map<String, String>> calculateGradesByStudent(Map<String, List<Map<String, String>>> scoresByStudent) {
        Map<String, Map<String, String>> finalGrades = new HashMap<>();
        scoresByStudent.forEach((studentName, scores) -> {
            Map<String, Double> subjectScores = new HashMap<>();
            scores.forEach(score -> {
                String subjectName = score.get("subjectName");
                Integer scoreValue = Integer.parseInt(score.get("score")); // String을 Integer로 변환
                subjectScores.merge(subjectName, scoreValue.doubleValue(), Double::sum);
            });
            Map<String, String> gradesBySubject = new HashMap<>();
            subjectScores.forEach((subjectName, totalScore) -> {
                double averageScore = totalScore / scores.stream()
                        .filter(s -> s.get("subjectName").equals(subjectName))
                        .count();
                gradesBySubject.put(subjectName, determineGrade(subjectName, averageScore)); // 여기 수정됨
            });
            finalGrades.put(studentName, gradesBySubject);
        });
        return finalGrades;
    }

    private String determineGrade(String subjectName, double averageScore) {
        boolean isMandatory = subjectStore.stream()
                .anyMatch(subject -> subject.getSubjectName().equals(subjectName) && "MANDATORY".equals(subject.getSubjectType()));

        if (isMandatory) {
            if (averageScore >= 95) return "A";
            else if (averageScore >= 90) return "B";
            else if (averageScore >= 80) return "C";
            else if (averageScore >= 70) return "D";
            else if (averageScore >= 60) return "F";
            else return "N"; // 60점 미만
        } else {
            if (averageScore >= 90) return "A";
            else if (averageScore >= 80) return "B";
            else if (averageScore >= 70) return "C";
            else if (averageScore >= 60) return "D";
            else if (averageScore >= 50) return "F";
            else return "N"; // 50점 미만
        }
    }
}