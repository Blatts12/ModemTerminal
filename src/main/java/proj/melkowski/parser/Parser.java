package proj.melkowski.parser;

import proj.melkowski.uicomponents.console.ConsoleManager;

import java.util.ArrayList;
import java.util.Arrays;

public final class Parser {

    private static ConsoleManager consoleManager = ConsoleManager.getInstance();

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static String charArrayToHexString(char[] chars) {
        char[] hexChars = new char[chars.length * 3];
        int i = 0;
        for (char c : chars) {
            int v = c & 0xFF;
            hexChars[i * 3] = HEX_ARRAY[v >>> 4];
            hexChars[i * 3 + 1] = HEX_ARRAY[v & 0x0F];
            hexChars[i * 3 + 2] = ' ';
            i++;
        }

        return new String(hexChars).trim();
    }

    public static String charArrayToString(char[] chars) {
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            if (c < 0x20 || c >= 128) {
                sb.append(getTokenFromChar(c));
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static byte[] hexStringToByteArray(String hexString) {

        String[] split = hexString.trim().replaceAll(" +", " ").split(" ");
        ArrayList<String> doublets = new ArrayList<>();
        for (String s : split) {
            doublets.addAll(Arrays.asList(s.split("(?<=\\G.{2})")));
        }
        byte[] output = new byte[doublets.size()];
        int i = 0;
        for (String d : doublets) {
            output[i++] = (byte) Integer.parseInt(d, 16);
        }
        return output;
    }

    public static String stringHexToString(String hexString) {
        String[] split = hexString.trim().replaceAll(" +", " ").split(" ");
        StringBuilder output = new StringBuilder();
        for (String s : split) {
            for (String doublet : s.split("(?<=\\G.{2})")) {
                int v = Integer.parseInt(doublet, 16);
                if (v < 0x20 || v >= 128 || v == 0x5C) {
                    output.append(getTokenFromChar((char)v));
                    continue;
                }
                output.append((char)v);
            }
        }
        return output.toString();
    }

    public static String stringToHexString(String s) {
        StringBuilder output = new StringBuilder();
        StringBuilder token = new StringBuilder();
        boolean tokenReadMode = false;
        for (char c : s.toCharArray()) {
            if (!tokenReadMode) {
                if (c == '\\') {
                    tokenReadMode = true;
                    token.append(c);
                    continue;
                }
                int v = c & 0xFF;
                output.append(HEX_ARRAY[v >>> 4]).append(HEX_ARRAY[v & 0x0F]).append(' ');
            } else {
                if (c == '\\') {
                    token.append(c);
                    output.append(getHexStringFromToken(token.toString().toUpperCase()) + " ");
                    token.setLength(0);
                    tokenReadMode = false;
                    continue;
                }
                token.append(c);
            }
        }
        if (tokenReadMode)
            output.append(getHexStringFromToken(token.toString()) + " ");
        return output.toString().trim();
    }

    private static String getTokenFromChar(char c) {
        switch (c) {
            case 0x5C:
                return "\\\\";
            case 0:
                return "\\NUL\\";
            case 1:
                return "\\SOH\\";
            case 2:
                return "\\STX\\";
            case 3:
                return "\\ETX\\";
            case 4:
                return "\\EOT\\";
            case 5:
                return "\\ENQ\\";
            case 6:
                return "\\ACK\\";
            case 7:
                return "\\BEL\\";
            case 8:
                return "\\BS\\";
            case 9:
                return "\\TAB\\";
            case 10:
                return "\\LF\\";
            case 11:
                return "\\VT\\";
            case 12:
                return "\\FF\\";
            case 13:
                return "\\CR\\";
            case 14:
                return "\\SO\\";
            case 15:
                return "\\SI\\";
            case 16:
                return "\\DLE\\";
            case 17:
                return "\\DC1\\";
            case 18:
                return "\\DC2\\";
            case 19:
                return "\\DC3\\";
            case 20:
                return "\\DC4\\";
            case 21:
                return "\\NAK\\";
            case 22:
                return "\\SYN\\";
            case 23:
                return "\\ETB\\";
            case 24:
                return "\\CAN\\";
            case 25:
                return "\\EM\\";
            case 26:
                return "\\SUB\\";
            case 27:
                return "\\ESC\\";
            case 28:
                return "\\FS\\";
            case 29:
                return "\\GS\\";
            case 30:
                return "\\RS\\";
            case 31:
                return "\\US\\";
            default:
                int v = c & 0xFF;
                return "\\0x" + HEX_ARRAY[v >>> 4] + HEX_ARRAY[v & 0x0F] + "\\";
        }
    }

    private static String getHexStringFromToken(String token) {
        switch (token) {
            case "\\\\":
                return "5C";
            case "\\NUL\\":
                return "00";
            case "\\SOH\\":
                return "01";
            case "\\STX\\":
                return "02";
            case "\\ETX\\":
                return "03";
            case "\\EOT\\":
                return "04";
            case "\\ENQ\\":
                return "05";
            case "\\ACK\\":
                return "06";
            case "\\BEL\\":
                return "07";
            case "\\BS\\":
                return "08";
            case "\\TAB\\":
                return "09";
            case "\\LF\\":
                return "0A";
            case "\\VT\\":
                return "0B";
            case "\\FF\\":
                return "0C";
            case "\\CR\\":
                return "0D";
            case "\\SO\\":
                return "0E";
            case "\\SI\\":
                return "0F";
            case "\\DLE\\":
                return "10";
            case "\\DC1\\":
                return "11";
            case "\\DC2\\":
                return "12";
            case "\\DC3\\":
                return "13";
            case "\\DC4\\":
                return "14";
            case "\\NAK\\":
                return "15";
            case "\\SYN\\":
                return "16";
            case "\\ETB\\":
                return "17";
            case "\\CAN\\":
                return "18";
            case "\\EM\\":
                return "19";
            case "\\SUB\\":
                return "1A";
            case "\\ESC\\":
                return "1B";
            case "\\FS\\":
                return "1C";
            case "\\GS\\":
                return "1D";
            case "\\RS\\":
                return "1E";
            case "\\US\\":
                return "1F";
            default:
                if (token.length() == 6) {
                    String preToken = token.substring(1,3);
                    if (!preToken.equals("0X")) {
                        consoleManager.addWarning("Wrong token in send text field: " + token);
                        return "";
                    }
                    String miniToken = token.substring(3,5);
                    try {
                        int v = Integer.parseInt(miniToken, 16);
                        return miniToken;
                    } catch (Exception e){}
                }
        }
        consoleManager.addWarning("Wrong token in send text field: " + token);
        return "";
    }
}
