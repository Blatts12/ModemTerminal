package proj.melkowski.uicomponents.textarea;

import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import proj.melkowski.parser.Parser;

import java.util.ArrayList;


public class ReceivedDataArea extends TextArea {
    private static CheckBox hex;

    private ArrayList<char[]> charsInTA = new ArrayList<>();

    public ReceivedDataArea() {
        Platform.runLater(() -> {
            hex = (CheckBox) getScene().lookup("#hexReceived");
        });
    }

    public void append(char[] data) {
            if (data.length > 0) {
                charsInTA.add(data);
                if (!hex.isSelected())
                    appendString(Parser.charArrayToString(data), data.length);
                else
                    appendString(Parser.charArrayToHexString(data), data.length);
            }
    }

    public void changeCoding(boolean hex) {
        clear();
        if (!hex) {
            for (char[] chars : charsInTA) {
                appendString(Parser.charArrayToString(chars), chars.length);
            }
        } else {
            for (char[] chars : charsInTA) {
                appendString(Parser.charArrayToHexString(chars), chars.length);
            }
        }
    }

    private void appendString(String s, int len) {
        appendText("[" + s + "] {" + len + "}\r\n");
    }

    public void clearArea() {
        charsInTA.clear();
        clear();
    }
}
