package camp.service;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.utility.ConsoleIO;

import java.util.List;
import java.util.Optional;

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


}
