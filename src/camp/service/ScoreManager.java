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

    //김현성 2024.05.07수정
    // 수강생의 과목별 회차 점수 수정
    public void updateRoundScoreBySubject(String studentId, String subjectId) {
        // 기능 구현 (수정할 과목 및 회차, 점수)
        consoleIO.print("---시험 점수를 수정합니다...---");
        // 기능 구현
        int scoreId = consoleIO.getIntInput("수정 할 회차 입력 : ");

        if (scoreStore.isEmpty()) {
            consoleIO.print("현재 어떤 정보 점수도 기록되어있지 않습니다.");
        } else {
            for (Score findingScore : scoreStore) {
                if (findingScore.returnFindingStudentId().equals(studentId)) { //학생 고유번호가 같은걸 찾고
                    if (findingScore.returnFindingScoreId() == scoreId) { //회자 번호가 같은걸 찾았다면
                        consoleIO.print("해당하는 회자를 잦았습니다. 현재점수(" + findingScore.returnFindingTestScore() + "점)");
                        int testScore = consoleIO.getIntInput("수정할 시험점수 입력( 0 ~ 100 ) : ");
                        if (testScore >= 0 && testScore <= 100) {
                            findingScore.modifiScore(testScore); //class 안에있는 수정 method
                        } else {
                            consoleIO.print("---수정할 시험점수가 0~100사이의 정수가 아닙니다.---");
                        }
                    } else {
                        consoleIO.print("---해당되는 회차의 점수가 없습니다.---");
                    }
                } else {
                    consoleIO.print("---해당되는 학생 정보가 없습니다.---");
                }
            }
        }
    }

    public void removeRoundScoreBySubject(String studentId, String subjectId) {
        // 기능 구현 (삭제 할 과목 및 회차, 점수)
        String removeCheck = consoleIO.getStringInput("해당 학생의 과목별 시험점수를 삭제하시겠습니까?(y/n) : ");
        if (removeCheck.equals("y") || removeCheck.equals("Y")) {
            for (Score findingScore : scoreStore) {
                if (findingScore.returnFindingStudentId().equals(studentId)) {
                    if (findingScore.returnFindingSubjectId().equals(subjectId)) {
                        scoreStore.remove(findingScore);
                        System.out.println("해당하는 학생의 과목 점수를 모두 삭제했습니다.");
                    } else {
                        System.out.println("해당하는 학생의 과목 점수가 없습니다.");
                    }
                }
            }
        } else {
            System.out.println("---취소를 선택하셨습니다. 삭제를 취소합니다.---");
        }
    }

    //  특정 상태와 필수 과목에 해당되는 객체를 scoreStore에서 찾아서 반환시키는 메서드
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
                .collect(Collectors.toList());

        List<String> mandatorySubjectIds = subjectStore.stream()
                .filter(subject -> "MANDATORY".equals(subject.getSubjectType()))
                .map(Subject::getSubjectId)
                .collect(Collectors.toList());

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
                gradesBySubject.put(subjectName, determineGrade(averageScore));
            });
            finalGrades.put(studentName, gradesBySubject);
        });
        return finalGrades;
    }



    // 점수에 따라 등급을 결정하는 메서드
    private String determineGrade(double score) {
        if (score >= 95) return "A";
        else if (score >= 90) return "B";
        else if (score >= 80) return "C";
        else if (score >= 70) return "D";
        else if (score >= 60) return "F";
        else return "N";
    }
}