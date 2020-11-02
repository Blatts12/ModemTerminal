package proj.melkowski.connection;

import com.fazecast.jSerialComm.SerialPort;

public class PortWrapper {
    private SerialPort serialPort;

    public SerialPort getSerialPort() {
        return serialPort;
    }

    public void setSerialPort(SerialPort serialPort) {
        setParams(serialPort);
        this.serialPort = serialPort;
    }

    private void setParams(SerialPort port) {
        port.setComPortParameters(57600, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
    }

    public boolean close() {
        return serialPort.closePort();
    }

    public boolean open() {
        serialPort.clearRTS();
        boolean opened = serialPort.openPort();
        return opened;
    }

    public boolean isOpen() {
        return serialPort.isOpen();
    }

    public boolean isCloseable() { return serialPort != null && serialPort.isOpen(); }

    public String getSystemPortName() { return serialPort.getSystemPortName(); }
}
