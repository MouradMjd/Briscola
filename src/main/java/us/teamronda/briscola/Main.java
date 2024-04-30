package us.teamronda.briscola;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main2(String[] args) {
        LogicGame game = new LogicGame();
        game.startGameLoop();
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent view = FXMLLoader.load(getResource("start.fxml"));
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
}