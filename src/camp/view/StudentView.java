package camp.view;


import camp.service.StudentManager;
import camp.utility.ConsoleIO;

public class StudentView {
    private StudentManager studentManager;
    private ConsoleIO consoleIO;

    public StudentView(StudentManager studentManager, ConsoleIO consoleIO) {
        this.studentManager = studentManager;
        this.consoleIO = consoleIO;
    }


}
