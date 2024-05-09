package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;

import java.util.ArrayList;
import java.util.Arrays;
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


    // 학생 데이터  ArrayList를 사용해 동적배열로 반환 (빈상태로 반환)
    public static List<Student> initializeStudents(IdSequenceGenerator idGenerator) {
        return new ArrayList<>(
                List.of(
                        new Student(
                                idGenerator.generateStudentId(),
                                "류선재",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","디자인 패턴","MongoDB"))
                        ),
                        new Student(
                                idGenerator.generateStudentId(),
                                "김혜윤",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","Spring","JPA","Redis","MongoDB"))
                        ),
                        new Student(
                                idGenerator.generateStudentId(),
                                "류시우",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","MongoDB"))
                        )
                        ,new Student(
                                idGenerator.generateStudentId(),
                                "김도영",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","MongoDB"))
                        ),
                        new Student(
                                idGenerator.generateStudentId(),
                                "강예서",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","MongoDB"))
                        ),
                        new Student(
                                idGenerator.generateStudentId(),
                                "고윤정",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","MongoDB"))
                        ),
                        new Student(
                                idGenerator.generateStudentId(),
                                "이정하",
                                new ArrayList<>(Arrays.asList("객체지향","MySQL","JPA","Redis","MongoDB"))
                        )

                )
        );
    }

    // 점수 데이터  ArrayList를 사용해 동적배열로 반환 (빈상태로 반환)
    public static List<Score> initializeScores() {
     //   return new ArrayList<>();
        return List.of(
                new Score(
                        "ST1",
                        "SU1",
                        1,
                        96,
                        'A'
                ),
                new Score(
                        "ST1",
                        "SU1",
                        2,
                        92,
                        'B'
                ),
                new Score(
                        "ST1",
                        "SU1",
                        3,
                        97,
                        'A'
                ),
                new Score(
                        "ST1",
                        "SU2",
                        1,
                        95,
                        'A'
                ),
                new Score(
                        "ST1",
                        "SU2",
                        2,
                        82,
                        'C'
                ),
                new Score(
                        "ST2",
                        "SU3",
                        1,
                        91,
                        'B'
                )
        );
    }
}
