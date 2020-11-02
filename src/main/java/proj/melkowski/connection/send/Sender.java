package proj.melkowski.connection.send;

import com.fazecast.jSerialComm.SerialPort;
import proj.melkowski.uicomponents.console.ConsoleManager;

public class Sender {

    private ConsoleManager consoleManager = ConsoleManager.getInstance();

    public void send(SerialPort port, byte[] data) {
        if (data != null && data.length != 0) {
            port.setRTS();
            port.setDTR();
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int successfullySentBytes = port.writeBytes(data, data.length);
            if (successfullySentBytes == data.length) {
                consoleManager.addSuccess("All data successfully has been sent");
            } else if (successfullySentBytes == 0) {
                consoleManager.addError("Error while data sending");
            } else {
                consoleManager.addWarning("Only partial data has been sent");
            }
            port.clearRTS();
            port.clearDTR();
        }
    }

    public void sendWithNoLines(SerialPort port, byte[] data) {
        int successfullySentBytes = port.writeBytes(data, data.length);
        if (successfullySentBytes == data.length) {
            consoleManager.addSuccess("All data successfully has been sent");
        } else if (successfullySentBytes == 0) {
            consoleManager.addError("Error while data sending");
        } else {
            consoleManager.addWarning("Only partial data has been sent");
        }
    }
}
