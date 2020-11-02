package proj.melkowski.uicomponents.togglegroup;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import proj.melkowski.uicomponents.console.ConsoleManager;

public class ModulationToggleGroup extends ToggleGroup {

    private boolean reset = true;
    private ConsoleManager consoleManager = ConsoleManager.getInstance();
    private CheckBox coded;
    private RadioButton BPSK;
    private RadioButton QPSK;
    private RadioButton _8PSK;

    public void init(CheckBox coded) {
        this.coded = coded;
        BPSK = getByUserData("B-PSK");
        QPSK = getByUserData("Q-PSK");
        _8PSK = getByUserData("8-PSK");
        disable();
    }

    public ModulationToggleGroup() {
        selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (reset) {
                reset = false;
                return;
            }
            ((RadioButton)oldValue).setDisable(false);
            ((RadioButton)newValue).setDisable(true);
            String code = newValue.getUserData().toString();
            if (code.equals("8-PSK")) {
                coded.setSelected(false);
                coded.setDisable(true);
            } else {
                coded.setDisable(false);
            }
            consoleManager.addInfo("Changed modulation to " + code + (coded.isSelected() ? " coded" : ""));
        });
    }

    public void disable() {
        BPSK.setDisable(true);
        QPSK.setDisable(true);
        _8PSK.setDisable(true);
        coded.setDisable(true);
    }

    public void enable() {
        _8PSK.setDisable(false);
        QPSK.setDisable(false);
        BPSK.setSelected(true);
        coded.setDisable(false);
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
