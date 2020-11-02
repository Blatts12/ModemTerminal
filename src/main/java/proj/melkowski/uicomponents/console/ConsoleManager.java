package proj.melkowski.uicomponents.console;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ConsoleManager {

    public static SimpleConsole simpleConsole;

    public ConsoleManager() {
        if (Holder.INSTANCE != null) {
            throw new IllegalStateException("ConsoleManager already constructed");
        }
    }

    private static class Holder {
        private static final ConsoleManager INSTANCE = new ConsoleManager();
    }

    public static ConsoleManager getInstance() {
        return Holder.INSTANCE;
    }

    public static void setSimpleConsole(SimpleConsole simpleConsole) {
        ConsoleManager.simpleConsole = simpleConsole;
    }

    public void addInfo(String info) {
        simpleConsole.addInfo(info);
    }

    public void addSuccess(String success) { simpleConsole.addSuccess(success); }

    public void addError(String error) {
        simpleConsole.addError(error);
    }

    public void addWarning(String warning) {
        simpleConsole.addWarning(warning);
    }

    public void addReceivedDataError(String rdError) {
        simpleConsole.addReceivedDataError(rdError);
    }

    public void addReceivedDataSuccess(String rdSuccess) {
        simpleConsole.addReceivedDataSuccess(rdSuccess);
    }

    public void addReceivedDataInfo(String rdInfo) {
        simpleConsole.addReceivedDataInfo(rdInfo);
    }
}
