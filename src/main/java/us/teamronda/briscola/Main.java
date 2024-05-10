package us.teamronda.briscola;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import us.teamronda.briscola.utils.TimerUtils;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public void start(Stage stage) throws IOException {
        // Load the main FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gui/fxmls/start.fxml"));
        // Set the program icon
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/icon.png"))));
        stage.setOnCloseRequest(event -> {
            TimerUtils.terminate(); // Terminate all timer tasks
            Platform.exit(); // Shutdown gracefully using JavaFX
        });
        stage.setResizable(false);
        stage.setTitle("Briscola v6.9");
        stage.setScene(new Scene(fxmlLoader.load()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}