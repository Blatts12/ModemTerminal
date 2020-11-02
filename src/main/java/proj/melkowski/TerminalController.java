package proj.melkowski;

import com.fazecast.jSerialComm.SerialPort;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import proj.melkowski.connection.ConnectionManager;
import proj.melkowski.frame.FrameMaker;
import proj.melkowski.uicomponents.checkbox.CodedCheckBox;
import proj.melkowski.uicomponents.combobox.PortsComboBox;
import proj.melkowski.uicomponents.console.ConsoleManager;
import proj.melkowski.uicomponents.console.SimpleConsole;
import proj.melkowski.uicomponents.spinner.RepeatSpinner;
import proj.melkowski.uicomponents.textarea.ReceivedDataArea;
import proj.melkowski.uicomponents.textfield.SendTextField;
import proj.melkowski.uicomponents.togglegroup.LayerToggleGroup;
import proj.melkowski.uicomponents.togglegroup.ModulationToggleGroup;


public class TerminalController {

    @FXML
    private Button bConnection;
    @FXML
    private Button bSend;
    @FXML
    private Button bRefresh;
    @FXML
    private Button bReset;
    @FXML
    private PortsComboBox cbPorts;
    @FXML
    private RadioButton layerPHY;
    @FXML
    private LayerToggleGroup Layer;
    @FXML
    private RadioButton layerDL;
    @FXML
    private RadioButton modulationBPSK;
    @FXML
    private ModulationToggleGroup Modulation;
    @FXML
    private RadioButton modulationQPSK;
    @FXML
    private RadioButton modulation8PSK;
    @FXML
    private CodedCheckBox modulationCoded;
    @FXML
    private SendTextField textFieldSend;
    @FXML
    private CheckBox hexSend;
    @FXML
    private RepeatSpinner sRepeats;
    @FXML
    private ReceivedDataArea receivedDataArea;
    @FXML
    private CheckBox hexReceived;
    @FXML
    private SimpleConsole simpleConsole;
    @FXML
    private CheckBox wrapText;
    @FXML
    private CheckBox biggerFont;
    @FXML
    private ScrollPane consolePane;

    private boolean connected = false;
    private ConnectionManager connectionManager = ConnectionManager.getInstance();
    private ConsoleManager consoleManager = ConsoleManager.getInstance();

    @FXML
    void initialize() {
        connectionManager.startApp(receivedDataArea);
        ConsoleManager.setSimpleConsole(simpleConsole);
        Layer.init();
        Modulation.init(modulationCoded);
        modulationCoded.init(Modulation);

        if (cbPorts.selectSTMsPortCOM())
            connectionAction(null);
    }

    public void stopApp() {
        connectionManager.close();
        connectionManager.stopApp();
    }


    //CONNECTION
    @FXML
    void connectionAction(ActionEvent event) {
        SerialPort port = cbPorts.getPort();
        if (!connected) { // Connect
            if (port == null) // No port selected
                return;

            connected = connectionManager.open(cbPorts.getPort());
            if (connected) {
                bConnection.setText("Close");
                cbPorts.changeEnable();
                bSend.setDisable(false);
                bRefresh.setDisable(true);
                bReset.setDisable(false);
                Modulation.enable();
                Layer.reset(false);
                return;
            }
            return;
        }
        // Disconnect
        if (connectionManager.close()) {
            bConnection.setText("Open");
            cbPorts.changeEnable();
            Layer.disable();
            Modulation.disable();
            bSend.setDisable(true);
            bRefresh.setDisable(false);
            bReset.setDisable(true);
            connected = false;
        }
    }

    @FXML
    void resetModem(ActionEvent event) {
        ConnectionManager.send(FrameMaker.RESET_FRAME);
        Layer.reset(false);
    }

    @FXML
    void refreshPorts(ActionEvent event) {
        cbPorts.refreshPorts();
        if (cbPorts.selectSTMsPortCOM())
            connectionAction(null);
    }

    //SEND
    @FXML
    void send(ActionEvent event) {
        byte[] data = textFieldSend.getByteArrayToSend(hexSend.isSelected());
        if (data.length > 256) {
            consoleManager.addWarning("Max length of data to send is 256, current length: " + data.length);
            return;
        }
        byte[] frameToSend = FrameMaker.createByteArrayFrame(Layer.getSelectedToggle().getUserData().toString(), data, Modulation.getSelectedToggle().getUserData().toString(), modulationCoded.isSelected());
        ConnectionManager.send(frameToSend, sRepeats.getValue());
        //ConnectionManager.send(data);
    }

    @FXML
    void clearSend(ActionEvent event) {
        textFieldSend.clear();
    }

    @FXML
    void changeSendCoding(ActionEvent event) {
        textFieldSend.changeCoding(hexSend.isSelected());
    }

    //RECEIVE
    @FXML
    void clearReceived(ActionEvent event) {
        receivedDataArea.clearArea();
    }

    @FXML
    void changeReceiveCoding(ActionEvent event) {
        receivedDataArea.changeCoding(hexReceived.isSelected());
    }

    //CONSOLE
    @FXML
    void clearConsole(ActionEvent event) {
        simpleConsole.clear();
    }

    @FXML
    void changeFont(ActionEvent event) {
        simpleConsole.biggerFont(biggerFont.isSelected());
    }

    @FXML
    void changeWrapText(ActionEvent event) {
        simpleConsole.wrapText(wrapText.isSelected());
    }
}
