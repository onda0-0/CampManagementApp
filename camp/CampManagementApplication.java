package camp;

import camp.model.Score;
import camp.model.Student;
import camp.model.Subject;
import camp.service.IdSequenceGenerator;
import camp.service.ScoreManager;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;
import camp.service.DataInitializer;
import camp.view.MainView;
import camp.view.ScoreView;
import camp.view.StudentView;

import java.util.List;


public class CampManagementApplication {
    public static void main(String[] args) {
        ConsoleIO consoleIO = new ConsoleIO();
        IdSequenceGenerator idGenerator = new IdSequenceGenerator();

        List<Student> studentStore = DataInitializer.initializeStudents();
        List<Subject> subjectStore = DataInitializer.initializeSubjects(idGenerator);
        List<Score> scoreStore = DataInitializer.initializeScores();

        StudentManager studentManager = new StudentManager(studentStore, subjectStore, consoleIO, idGenerator);
        ScoreManager scoreManager = new ScoreManager(scoreStore, subjectStore, studentStore, consoleIO);

        StudentView studentView = new StudentView(studentManager, consoleIO);
        ScoreView scoreView = new ScoreView(scoreManager, consoleIO);
        MainView mainView = new MainView(studentView, scoreView, consoleIO);

        mainView.displayMainView();
    }
}
