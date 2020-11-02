package proj.melkowski.connection;

import com.fazecast.jSerialComm.SerialPort;
import proj.melkowski.connection.receive.ReceiveTimer;
import proj.melkowski.connection.send.Sender;
import proj.melkowski.uicomponents.console.ConsoleManager;
import proj.melkowski.uicomponents.console.SimpleConsole;
import proj.melkowski.uicomponents.textarea.ReceivedDataArea;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class ConnectionManager {

    private ScheduledExecutorService ses;
    private static ProcessData processData;
    private static Sender sender;
    private static PortWrapper port;
    private static ConsoleManager consoleManager = ConsoleManager.getInstance();

    public ConnectionManager() {
        if (Holder.INSTANCE != null) {
            throw new IllegalStateException("ConnectionManager already constructed");
        }
    }

    public static ConnectionManager getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }

    public Boolean open(SerialPort serialPort) {
        port.setSerialPort(serialPort);
        boolean opened = port.open();
        if (opened) {
            consoleManager.addInfo("Connected to " + port.getSystemPortName());
            startReceiveTimer(port.getSerialPort());
        } else {
            consoleManager.addError("Can't connect to " + port.getSystemPortName());
        }
        return opened;
    }

    public Boolean close() {
        if (port.isCloseable()) {
            boolean closed = port.close();
            if (closed) {
                consoleManager.addInfo("Port " + port.getSystemPortName() + " closed");
                stopReceiveTimer();
            } else {
                consoleManager.addError("Can't close " + port.getSystemPortName());
            }

            return closed;
        }
        return false;
    }

    private void startReceiveTimer(SerialPort port) {
        ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new ReceiveTimer(port), 0L, 3L, TimeUnit.MILLISECONDS);
        consoleManager.addInfo("Receive Timer has been started");
    }

    private void stopReceiveTimer() {
        if (ses != null) {
            ses.shutdown();
            ses = null;
            consoleManager.addInfo("Receive Timer has been stopped");
        }
    }

    public void startApp(ReceivedDataArea rda) {
        processData = new ProcessData(rda);
        sender = new Sender();
        port = new PortWrapper();
    }

    public void stopApp() {
        stopReceiveTimer();
        close();
    }

    public static void send(byte[] data, int repeats) {
        for (int i = 0 ; i < repeats ; i++) {
            sender.send(port.getSerialPort(), data);
        }
    }
    public static void send(byte[] data) {
        sender.send(port.getSerialPort(), data);
    }
    public static void sendNoLines(byte[] data) { sender.sendWithNoLines(port.getSerialPort(), data); }



}
