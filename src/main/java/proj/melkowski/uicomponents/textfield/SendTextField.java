package proj.melkowski.uicomponents.textfield;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import proj.melkowski.parser.Parser;
import proj.melkowski.uicomponents.console.ConsoleManager;

public class SendTextField extends TextField {

    private ConsoleManager consoleManager = ConsoleManager.getInstance();
    private CheckBox hexCheckbox;

    public SendTextField() {
        Platform.runLater(() -> {
            hexCheckbox = (CheckBox) getScene().lookup("#hexSend");
        });
    }

    public byte[] getByteArrayToSend(boolean hex) {
        String text = getText();
        byte[] output = new byte[0];
        if (text.length() == 0) {
            consoleManager.addInfo("Send text field is empty");
            return output;
        }

        if (hex) {
            if (!validateHex(text)) {
                consoleManager.addWarning("Wrong hex data in send text field");
                return output;
            }
            output = Parser.hexStringToByteArray(text);
        } else {
            output = text.getBytes();
        }
        return output;
    }

    public void changeCoding(boolean hex) {
        String text = getText();
        if (text.length() == 0)
            return;

        if (!hex) {
            if (!validateHex(text)) {
                hexCheckbox.setSelected(true);
                consoleManager.addWarning("Wrong hex data in send text field");
                return;
            }
            clear();
            setText(Parser.stringHexToString(text));
        }
        else {
            clear();
            setText(Parser.stringToHexString(text));
        }
    }

    private boolean validateHex(String text) {
        return text.matches("[A-Fa-f0-9 ]+");
    }
}
