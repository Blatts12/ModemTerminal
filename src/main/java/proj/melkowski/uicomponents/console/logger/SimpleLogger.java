package proj.melkowski.uicomponents.console.logger;

import java.text.SimpleDateFormat;

public class SimpleLogger {
    private static SimpleDateFormat time_formatter = new SimpleDateFormat("HH:mm:ss.SSS");

    public String info(String info) {
        return timestamp() + " [INFO] " + info + "\n";
    }

    public String success(String success) {
        return timestamp() + " [SUCCESS] " + success + "\n";
    }

    public String warning(String warning) {
        return timestamp() + " [WARNING] " + warning + "\n";
    }

    public String error(String error) {
        return timestamp() + " [ERROR] " + error + "\n";
    }

    public String receivedDataError(String receivedDataError) {
        return timestamp() + " [REC_DATA_ERROR] " + receivedDataError + "\n";
    }

    public String receivedDataSuccess(String receivedDataSuccess) {
        return timestamp() + " [REC_DATA_SUCCESS] " + receivedDataSuccess + "\n";
    }

    public String receivedDataInfo(String receivedDataInfo) {
        return timestamp() + " [REC_DATA_INFO] " + receivedDataInfo + "\n";
    }

    private String timestamp() {
        return time_formatter.format(System.currentTimeMillis());
    }
}
