package proj.melkowski.connection;

import javafx.application.Platform;
import proj.melkowski.frame.CommandWrapper;
import proj.melkowski.frame.Frame;
import proj.melkowski.frame.FrameMaker;
import proj.melkowski.uicomponents.console.ConsoleManager;
import proj.melkowski.uicomponents.textarea.ReceivedDataArea;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessData {

    private static ReceivedDataArea rda;
    private static ConsoleManager consoleManager = ConsoleManager.getInstance();
    private static ExecutorService es = Executors.newCachedThreadPool();

    public ProcessData(ReceivedDataArea rda) {
        ProcessData.rda = rda;
    }

    public static void processFrame(Frame frame) {
            switch (getCommandWrapper(frame).getCommandCode()) {
                case 0x3E:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received BIO_ResetIndication");
                    break;
                case 0x3D:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received BIO_ResetConfirm");
                    break;
                case 0x26:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received PHY_DataIndication");
                    break;
                case 0x52:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received DL_DataIndication");
                    break;
                case 0x5A:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("DL_SnifferIndication");
                    break;
                case 0x56:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("SS_DataIndication");
                    break;
                case 0x5E:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("SS_SnifferIndication");
                    break;
                case 0x09:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received MIB_WriteConfirm");
                    break;
                case 0x0D:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received MIB_ReadConfirm");
                    break;
                case 0x11:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received MIB_EraseConfirm");
                    break;
                case 0x2D:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received PingConfirm");
                    break;
                case 0x25:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received PHY_DataConfirm");
                    break;
                case 0x51:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received DL_DataConfirm");
                    break;
                case 0x55:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    consoleManager.addReceivedDataSuccess("Received SS_DataConfirm");
                    break;
                case 0x6A:
                    ConnectionManager.sendNoLines(FrameMaker.ACK_CODE);
                    break;
            }
    }

    public static void processData(char[] data) {
        Platform.runLater(() -> {
            consoleManager.addReceivedDataInfo("Received new data with length " + data.length + " byte/s");
            rda.append(data);
        });
    }

    private static CommandWrapper getCommandWrapper(Frame f) {
        if (f.equalChecksums()) {
            consoleManager.addReceivedDataSuccess("Found correct frame");
            return new CommandWrapper(f.getCommandCode(), f.getDataArray());
        }
        consoleManager.addReceivedDataError("Found frame with incorrect checksum");
        return null;
    }
}
