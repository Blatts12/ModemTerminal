package proj.melkowski.connection.receive;

import com.fazecast.jSerialComm.SerialPort;
import proj.melkowski.frame.Frame;
import proj.melkowski.connection.ProcessData;

import java.util.Arrays;
import java.util.TimerTask;

public class ReceiveTimer extends TimerTask {

    private SerialPort port;

    private static final int ARR_LEN = 384;
    private static char[] emptyArray = new char[ARR_LEN];
    private char[] receivedData = new char[ARR_LEN];
    private int receivedDataIndex = 0;

    private byte[] oneByte = new byte[1];
    private ProcessReceivedData prd = new ProcessReceivedData();

    public ReceiveTimer(SerialPort port) {
        this.port = port;
    }

    @Override
    public void run() {
        int numberOfDataAvailable = port.bytesAvailable();
        if (numberOfDataAvailable == 0) {
            if (receivedDataIndex != 0) {
                printData();
                prd.setFrameStatus(ProcessReceivedData.FSS.SEARCH_FOR_START);
            }
            return;
        }
        port.readBytes(oneByte,1L);

        switch (prd.process(oneByte[0])) {
            case FOUND_START_FRAME:
                if (receivedDataIndex != 0) {
                    printData();
                }
                addData();
                break;
            case FOUND_FRAME:
                addData();
                processFrame(prd.getFrame());
                printData();
                prd.setSearchForStart();
                break;
            case NOTHING:
                addData();
                break;
        }

    }

    private void addData() {
        receivedData[receivedDataIndex] = (char)oneByte[0];
        if (++receivedDataIndex == ARR_LEN)
            printData();
    }

    private void printData() {
        char[] data = Arrays.copyOf(receivedData, receivedDataIndex);
        ProcessData.processData(data);
        receivedDataIndex = 0;
        receivedData = emptyArray;
    }

    private void processFrame(Frame frame) {
        ProcessData.processFrame(frame);
    }
}
