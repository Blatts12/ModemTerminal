package proj.melkowski.frame;

public class CommandWrapper {

    private byte commandCode;
    private byte[] data;

    public CommandWrapper(byte commandCode, byte[] data) {
        this.commandCode = commandCode;
        this.data = data;
    }

    public byte getCommandCode() {
        return commandCode;
    }

    public byte[] getData() {
        return data;
    }
}
