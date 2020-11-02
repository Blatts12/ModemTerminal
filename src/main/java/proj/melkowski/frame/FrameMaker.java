package proj.melkowski.frame;

public class FrameMaker {
    public static final byte[] ACK_CODE = {0x06};
    public static final byte[] RESET_FRAME = {0x02, 0x00, 0x3C, 0x3C, 0x00};
    public static final byte[] PHY_FRAME = {0x02, 0x02, 0x08, 0x00, 0x10, 0x1A, 0x00};
    public static final byte[] DL_FRAME = {0x02, 0x02, 0x08, 0x00, 0x11, 0x1B, 0x00};

    public static byte[] getFrameFromStringCode(String code) {
        switch (code) {
            case "PHY":
                return PHY_FRAME;
            case "DL":
                return DL_FRAME;
        }
        return null;
    }

    public static byte[] createByteArrayFrame(String layer, byte[] data, String modulation, boolean coded) {
        byte[] frame = new byte[6 + data.length];
        frame[0] = 0x02; //frameStart
        frame[1] = (byte)(data.length + 0x01); // data len
        frame[2] = getCommandCode(layer); //command code

        String sCoded = coded ? "1" : "0";
        String sModulation = getModulation(modulation);
        frame[3] = (byte)Integer.parseInt("0"+ sCoded + sModulation + "0100",2);

        int i = 4;
        for (byte b : data) {
            frame[i++] = b;
        }

        short checksum = getChecksum(frame[1], frame[2], frame[3], data);
        frame[frame.length - 2] = (byte)(checksum & 0x00FF);
        frame[frame.length - 1] = (byte)((checksum & 0xFF00) >> 8);

        return frame;
    }

    private static short getChecksum(byte dataLen, byte command, byte modulation, byte[] data) {
        short checksum = dataLen;
        checksum += command;
        checksum += modulation;

        for (byte b : data) {
            checksum += b;
        }

        return checksum;
    }

    private static byte getCommandCode(String s) {
        switch (s) {
            case "DL":
                return 0x50;
            case "PHY":
                return 0x24;
        }

        return 0x00;
    }

    private static String getModulation(String s) {
        switch (s) {
            case "BPSK":
                return "00";
            case "Q-PSK":
                return "01";
            case "8-PSK":
                return "10";
        }
        return "";
    }
}
