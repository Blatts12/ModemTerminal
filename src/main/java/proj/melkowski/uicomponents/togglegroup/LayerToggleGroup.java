package proj.melkowski.uicomponents.togglegroup;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import proj.melkowski.connection.ConnectionManager;
import proj.melkowski.frame.FrameMaker;
import proj.melkowski.uicomponents.console.ConsoleManager;

public class LayerToggleGroup extends ToggleGroup {

    private boolean reset = true;
    private ConsoleManager consoleManager = ConsoleManager.getInstance();
    private RadioButton DL;
    private RadioButton PHY;

    public void init() {
        DL = getByUserData("DL");
        PHY = getByUserData("PHY");
        disable();
    }

    public LayerToggleGroup() {
       selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
           if (reset) {
               reset = false;
               return;
           }
           ((RadioButton)oldValue).setDisable(false);
           ((RadioButton)newValue).setDisable(true);
           String code = newValue.getUserData().toString();
           consoleManager.addInfo(code + " request has been sent");
           ConnectionManager.send(FrameMaker.getFrameFromStringCode(code));
       });

    }

    public void reset(boolean activateListener) {
        reset = !activateListener;
        DL.setSelected(true);
        DL.setDisable(true);

        PHY.setSelected(false);
        PHY.setDisable(false);
    }

    public void disable() {
        DL.setDisable(true);
        PHY.setDisable(true);
    }

    private RadioButton getByUserData(String userData) {
        RadioButton[] radioButtons = getToggles().toArray(new RadioButton[0]);
        for (RadioButton r : radioButtons) {
            if (r.getUserData().toString().equals(userData))
                return r;
        }
        return null;
    }
}
