package proj.melkowski.connection.receive;

import javafx.application.Platform;
import proj.melkowski.frame.Frame;

public class ProcessReceivedData {

    public ProcessReceivedData() {
    }

    private FSS frameStatus = FSS.SEARCH_FOR_START;

    private byte frameStart;
    private byte dataLength;
    private byte commandCode;
    private byte[] dataArray;
    private short checksum;

    private int len;
    private short checksumProcess;

    public SDS process(byte b) {

        if (b == 0x02 || b == 0x03) {
            frameStart = b;
            frameStatus = FSS.GET_DATA_LEN;
            return SDS.FOUND_START_FRAME;
        }

        switch (frameStatus) {
            case GET_DATA_LEN:
                len = 0;
                checksumProcess = dataLength = b;
                dataArray = new byte[dataLength];
                frameStatus = FSS.GET_COMMAND;
                break;
            case GET_COMMAND:
                commandCode = b;
                checksumProcess += commandCode;
                if (dataLength == 0) {
                    frameStatus = FSS.GET_CHSUM1;
                    break;
                }
                frameStatus = FSS.GET_DATA;
                break;
            case GET_DATA:
                dataArray[len] = b;
                checksumProcess += dataArray[len];
                if (++len == dataLength)
                    frameStatus = FSS.GET_CHSUM1;
                break;
            case GET_CHSUM1:
                checksum = b;
                frameStatus = FSS.GET_CHSUM2;
                break;
            case GET_CHSUM2:
                checksum += (short) b << 8;
                frameStatus = FSS.SEARCH_FOR_START;
                return SDS.FOUND_FRAME;
            case SEARCH_FOR_START:
            default:
        }

        return SDS.NOTHING;
    }

    public Frame getFrame() {
        return new Frame(frameStart, dataLength, commandCode, dataArray, checksum, checksumProcess);
    }
    public void setFrameStatus(FSS fss) {
        frameStatus = fss;
    }
    public void setSearchForStart() {
        frameStatus = FSS.SEARCH_FOR_START;
    }
    public enum FSS { // Frame Search Status
        SEARCH_FOR_START, GET_DATA_LEN, GET_COMMAND, GET_DATA, GET_CHSUM1, GET_CHSUM2;
    }
    public enum SDS { // Search Data Status
        FOUND_START_FRAME, NOTHING, FOUND_FRAME;
    }
}
