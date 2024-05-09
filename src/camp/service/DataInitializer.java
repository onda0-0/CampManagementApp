package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.List;

// 데이터 초기화 서비스
public class DataInitializer {
    private static final String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static final String SUBJECT_TYPE_CHOICE = "CHOICE";

    // 과목 데이터 반환(idGenerator를 주입받아 과목별 숫자부여)
    public static List<Subject> initializeSubjects(IdSequenceGenerator idGenerator) {
        return List.of(
                new Subject(
                        idGenerator.generateSubjectId(),
                        "Java",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "객체지향",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "Spring",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "JPA",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "MySQL",
                        SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "디자인 패턴",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "Spring Security",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "Redis",
                        SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        idGenerator.generateSubjectId(),
                        "MongoDB",
                        SUBJECT_TYPE_CHOICE
                )
        );
    }


    // 학생 데이터 초기화
    public static List<Student> initializeStudents() {
        List<Student> students = new ArrayList<>();
        students.add(new Student("ST3", "홍길동", List.of("Java", "객체지향","Spring", "MySQL")));
        students.add(new Student("ST2", "이순신", List.of("Java", "객체지향","Spring", "JPA", "Redis")));
        return students;
    }

    // 점수 데이터 초기화
    public static List<Score> initializeScores() {
        List<Score> scores = new ArrayList<>();
        scores.add(new Score("ST2", "SU1", 1, 98, "A"));
        scores.add(new Score("ST3", "SU1", 1, 100, "A"));
        scores.add(new Score("ST3", "SU2", 1, 100, "A"));
        scores.add(new Score("ST3", "SU2", 2, 100, "A"));
        return scores;
    }
}
