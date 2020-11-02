package proj.melkowski;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/TerminalWindow.fxml"));
        Parent root = fxmlLoader.load();
        TerminalController controller = fxmlLoader.getController();
        primaryStage.setTitle("Terminal for Evalkit ST7580 by Jakub Melkowski");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon128x128.png")));
        primaryStage.setScene(new Scene(root, 800, 850));
        primaryStage.setResizable(false);
        primaryStage.setOnHidden(e -> {
            controller.stopApp();
            Platform.exit();
        });
        primaryStage.show();
    }
}
