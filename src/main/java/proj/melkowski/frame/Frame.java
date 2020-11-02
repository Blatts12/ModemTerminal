package proj.melkowski.frame;

import java.util.ArrayList;
import java.util.List;

public class Frame {
    private byte frameStart;
    private byte dataLength;
    private byte commandCode;
    private byte[] dataArray;
    private short checksum;
    private short checksumProcess = 0x0000;

    public Frame(byte frameStart, byte dataLength, byte commandCode, byte[] dataArray, short checksum, short checksumProcess) {
        this.frameStart = frameStart;
        this.dataLength = dataLength;
        this.commandCode = commandCode;
        this.dataArray = dataArray;
        this.checksum = checksum;
        this.checksumProcess = checksumProcess;
    }

    public Frame(byte frameStart, byte dataLength, byte commandCode, byte[] dataArray, short checksum) {
        this.frameStart = frameStart;
        this.dataLength = dataLength;
        this.commandCode = commandCode;
        this.dataArray = dataArray;
        this.checksum = checksum;
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public byte[] getDataArray() {
        return dataArray;
    }
    public boolean equalChecksums() {
        return checksum == checksumProcess;
    }
}
