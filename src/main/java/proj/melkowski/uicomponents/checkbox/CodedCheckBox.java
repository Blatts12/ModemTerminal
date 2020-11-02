package proj.melkowski.uicomponents.checkbox;

import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import proj.melkowski.uicomponents.console.ConsoleManager;
import proj.melkowski.uicomponents.togglegroup.ModulationToggleGroup;

public class CodedCheckBox extends CheckBox {

    private boolean reset = true;
    private ConsoleManager consoleManager = ConsoleManager.getInstance();
    private ModulationToggleGroup modulation;

    public void init(ModulationToggleGroup modulation) {
        this.modulation = modulation;
    }

    public CodedCheckBox() {
        selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (reset) {
                reset = false;
                return;
            }
            String code = ((RadioButton)modulation.getSelectedToggle()).getUserData().toString();
            if (code.equals("8PSK"))
                return;
            consoleManager.addInfo("Changed modulation to " + code + (isSelected() ? " coded" : ""));
        });
    }
}
