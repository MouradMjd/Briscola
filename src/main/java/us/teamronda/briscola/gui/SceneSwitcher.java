package us.teamronda.briscola.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Setter;

import java.io.IOException;
import java.util.Objects;

/**
 * Enables a controller to switch between {@link Scene scenes}
 * by giving a generic node as a starting point.
 */

@Setter(AccessLevel.PROTECTED)
public class SceneSwitcher {

    private Node sceneHolder;

    public void switchTo(Guis gui) {
        Objects.requireNonNull(sceneHolder);

        // Get the current stage from the node
        Stage stage = (Stage) sceneHolder.getScene().getWindow();
        Objects.requireNonNull(stage);

        // Try to load the new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gui.getPath()));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            Platform.exit();
        }
    }
}
