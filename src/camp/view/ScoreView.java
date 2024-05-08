package camp.view;

import camp.service.ScoreManager;
import camp.utility.ConsoleIO;

public class ScoreView {
    private ScoreManager scoreManager;
    private ConsoleIO consoleIO;

    public ScoreView(ScoreManager scoreManager, ConsoleIO consoleIO) {
        this.scoreManager = scoreManager;
        this.consoleIO = consoleIO;
    }


}
