package proj.melkowski.uicomponents.combobox;

import com.fazecast.jSerialComm.SerialPort;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import proj.melkowski.uicomponents.console.ConsoleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PortsComboBox extends ComboBox {

    private ConsoleManager consoleManager = ConsoleManager.getInstance();

    public PortsComboBox() {
        super(getSerialPorts());

        setConverter(new StringConverter<SerialPort>() {
            @Override
            public String toString(SerialPort object) {
                return getName(object);
            }

            @Override
            public SerialPort fromString(String string) {
                List<SerialPort> ports = getItems();
                SerialPort sp = Stream.of(ports.toArray(new SerialPort[]{})).filter(port -> getName(port).equals(string)).findFirst().orElse(null);
                return sp;
            }
        });
    }

    public void refreshPorts() {
        setItems(getSerialPorts());
    }

    public SerialPort getPort() {
        SerialPort sp = (SerialPort) getSelectionModel().getSelectedItem();
        if (sp == null)
            consoleManager.addInfo("Select port");
        return sp;
    }

    private static ObservableList<SerialPort> getSerialPorts() {
        return FXCollections.observableArrayList(SerialPort.getCommPorts());
    }

    private static String getName(SerialPort object) {
        return object.getSystemPortName() + " - " + object.getPortDescription();
    }

    public void changeEnable() {
        setDisable(!isDisable());
    }

    public boolean selectSTMsPortCOM() {
        List<SerialPort> ports = getItems();

        List<SerialPort> stmPorts = Stream.of(ports.toArray(new SerialPort[]{})).filter(port -> getName(port).contains("STM")).collect(Collectors.toList());
        int size = stmPorts.size();
        consoleManager.addInfo("Found " + size + " STM COM port\\s");
        if (size == 1) getSelectionModel().select(stmPorts.get(0));
        return size == 1;

    }
}
