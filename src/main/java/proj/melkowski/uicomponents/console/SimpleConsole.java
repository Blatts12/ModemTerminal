package proj.melkowski.uicomponents.console;

import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import proj.melkowski.uicomponents.console.logger.SimpleLogger;

public class SimpleConsole extends TextFlow {

    private SimpleLogger simpleLogger = new SimpleLogger();
    private ScrollPane sp;

    public SimpleConsole(){
        super();
        Platform.runLater(() -> {
            sp = (ScrollPane) getScene().lookup("#consolePane");
        });
        getChildren().addListener(
                (ListChangeListener<Node>) ((change) -> {
                    Platform.runLater(() -> {
                        layout();
                        sp.layout();
                        sp.setVvalue(1d);
                    });
                }));
    }

    public void addInfo(String info) {
        Platform.runLater(() -> {
            Text infoText = new Text();
            infoText.setText(simpleLogger.info(info));
            infoText.setFill(Color.BLACK);
            getChildren().add(infoText);
        });
    }

    public void addSuccess(String success) {
        Platform.runLater(() -> {
            Text infoText = new Text();
            infoText.setText(simpleLogger.success(success));
            infoText.setFill(Color.GREEN);
            getChildren().add(infoText);
        });
    }

    public void addError(String error) {
        Platform.runLater(() -> {
            Text errorText = new Text();
            errorText.setText(simpleLogger.error(error));
            errorText.setFill(Color.RED);
            getChildren().add(errorText);
        });
    }

    public void addWarning(String warning) {
        Platform.runLater(() -> {
            Text warningText = new Text();
            warningText.setText(simpleLogger.warning(warning));
            warningText.setFill(Color.valueOf("908A00"));
            getChildren().add(warningText);
        });
    }

    public void addReceivedDataError(String rdError) {
        Platform.runLater(() -> {
            Text rdErrorText = new Text();
            rdErrorText.setText(simpleLogger.receivedDataError(rdError));
            rdErrorText.setFill(Color.ORANGERED);
            getChildren().add(rdErrorText);
        });
    }

    public void addReceivedDataSuccess(String rdSuccess) {
        Platform.runLater(() -> {
            Text rdSuccessText = new Text();
            rdSuccessText.setText(simpleLogger.receivedDataSuccess(rdSuccess));
            rdSuccessText.setFill(Color.FORESTGREEN);
            getChildren().add(rdSuccessText);
        });
    }

    public void addReceivedDataInfo(String rdInfo) {
        Platform.runLater(() -> {
            Text dataText = new Text();
            dataText.setText(simpleLogger.receivedDataInfo(rdInfo));
            dataText.setFill(Color.BLUE);
            getChildren().add(dataText);
        });
    }

    public void clear() {
        getChildren().clear();
    }

    public void wrapText(boolean wrap) {
        setPrefWidth(wrap ? 757d : Region.USE_COMPUTED_SIZE);
    }

    public void biggerFont(boolean bigger) {
        getStyleClass().remove(0);
        getStyleClass().add(bigger ? "consoleBig" : "consoleSmall");
    }
}
