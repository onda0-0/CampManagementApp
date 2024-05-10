package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScoreManager {
    private final List<Score> scoreStore;
    private final List<Subject> subjectStore;
    private final List<Student> studentStore;

    public ScoreManager(List<Score> scoreStore, List<Subject> subjectStore, List<Student> studentStore) {
        this.scoreStore = scoreStore;
        this.subjectStore = subjectStore;
        this.studentStore = studentStore;
    }

    public boolean updateRoundScoreBySubject(String studentId, String subjectId, int scoreId, int newScore) {
        if (scoreStore.isEmpty()) {
            return false;
        }

        Score foundScore = scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId) && score.getIteration() == scoreId)
                .findFirst().orElse(null);

        if (foundScore == null) {
            return false;
        }

        foundScore.setScore(newScore);
        String subjectType = subjectStore.stream()
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .findFirst()
                .map(Subject::getSubjectType)
                .orElse("해당 정보없음");

        String newGrade = determineGrade(subjectType, newScore);
        foundScore.setGrade(newGrade);
        return true;
    }

    public boolean createScore(String studentId, String subjectId, int iteration, int testScore) {
        Student selectedStudent = studentStore.stream()
                .filter(student -> student.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);

        if (selectedStudent == null) {
            return false;
        }

        boolean iterationExists = scoreStore.stream()
                .anyMatch(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId) && score.getIteration() == iteration);

        if (iterationExists) {
            return false;
        }

        Subject selectedSubject = subjectStore.stream()
                .filter(subject -> subject.getSubjectId().equals(subjectId))
                .findFirst().orElse(null);

        if (selectedSubject == null) {
            return false;
        }

        String grade = determineGrade(selectedSubject.getSubjectType(), testScore);
        Score newScore = new Score(studentId, subjectId, iteration, testScore, grade);
        scoreStore.add(newScore);
        return true;
    }

    public boolean removeRoundScoreBySubject(String studentId, String subjectId, int scoreId) {
        Score scoreToRemove = scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId) && score.getIteration() == scoreId)
                .findFirst().orElse(null);

        if (scoreToRemove == null) {
            return false;
        }

        scoreStore.remove(scoreToRemove);
        return true;
    }
    // 주어진 학생 ID에 해당하는 점수를 모두 삭제하는 메서드
    public void removeScoresByStudentId(String studentId) {
        scoreStore.removeIf(score -> score.getStudentId().equals(studentId));
    }
    public List<Score> inquireRoundGradeBySubject(String studentId, String subjectId) {
        return scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId))
                .collect(Collectors.toList());
    }

    public double inquireAvgRateBySubject(String studentId, String subjectId) {
        return scoreStore.stream()
                .filter(score -> score.getStudentId().equals(studentId) && score.getSubjectId().equals(subjectId))
                .mapToInt(Score::getScore)
                .average()
                .orElse(Double.NaN);
    }

    public Map<String, String> inquireStatusAvgBySubject(String status) {
        // 필수 과목 ID 목록을 가져옵니다.
        List<String> mandatorySubjectIds = subjectStore.stream()
                .filter(subject -> "MANDATORY".equals(subject.getSubjectType()))
                .map(Subject::getSubjectId)
                .toList();

        // 주어진 상태의 학생 ID와 이름을 매핑합니다.
        Map<String, String> studentIdToName = studentStore.stream()
                .filter(student -> student.getStudentStatus().equalsIgnoreCase(status))
                .collect(Collectors.toMap(Student::getStudentId, Student::getStudentName));

        // 각 학생의 평균 점수를 계산하고 등급으로 변환합니다.
        return studentIdToName.keySet().stream()
                .collect(Collectors.toMap(
                        studentIdToName::get, // 학생 이름
                        studentId -> {
                            double avgScore = scoreStore.stream()
                                    .filter(score -> score.getStudentId().equals(studentId) && mandatorySubjectIds.contains(score.getSubjectId()))
                                    .mapToInt(Score::getScore)
                                    .average()
                                    .orElse(0.0); // 평균 점수 계산
                            return determineGrade("MANDATORY",avgScore); // 평균 점수를 등급으로 변환
                        }
                ));
    }

    private String determineGrade(String subjectType, double score) {
        if (subjectType.equals("MANDATORY")) {
            if (score >= 95) return "A";
            else if (score >= 90) return "B";
            else if (score >= 80) return "C";
            else if (score >= 70) return "D";
            else if (score >= 60) return "F";
            else return "N";
        } else {
            if (score >= 90) return "A";
            else if (score >= 80) return "B";
            else if (score >= 70) return "C";
            else if (score >= 60) return "D";
            else if (score >= 50) return "F";
            else return "N";
        }
    }
    public boolean isValidStudentId(String studentId) {
        return studentStore.stream().anyMatch(student -> student.getStudentId().equals(studentId));
    }

    public boolean isValidSubjectId(String subjectId) {
        return subjectStore.stream().anyMatch(subject -> subject.getSubjectId().equals(subjectId));
    }
}
