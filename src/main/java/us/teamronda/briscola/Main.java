package us.teamronda.briscola;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import us.teamronda.briscola.utils.TimerUtils;

import java.io.IOException;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmls/start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setOnCloseRequest(event -> {
            TimerUtils.terminate(); // Terminate all timer tasks
            Platform.exit(); // Shutdown gracefully using JavaFX
        });
        stage.setResizable(false);
        stage.setTitle("Briscola v6.9");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}