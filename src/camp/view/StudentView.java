package camp.view;

import camp.model.Student;
import camp.service.StudentManager;
import camp.utility.ConsoleIO;

import java.util.ArrayList;
import java.util.List;

public class StudentView {
    private StudentManager studentManager;
    private ConsoleIO consoleIO;

    public StudentView(StudentManager studentManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.consoleIO = consoleIO;
    }


}
