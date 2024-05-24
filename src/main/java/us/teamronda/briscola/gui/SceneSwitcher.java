package us.teamronda.briscola.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Setter;
import us.teamronda.briscola.utils.AlertBuilder;

import java.io.IOException;

/**
 * Enables a controller to switch between {@link Scene scenes}
 * by giving a generic node as a starting point.
 */

@Setter(AccessLevel.PROTECTED)
public class SceneSwitcher {

    private Node sceneHolder;

    public void switchTo(Guis gui) {
        if (sceneHolder == null) {
            AlertBuilder.withType(Alert.AlertType.ERROR)
                    .title("Fatal Error")
                    .content("This gui was not initialized correctly! (null stageHolder)")
                    .showAndWait();

            Platform.exit();
            return;
        }

        // Get the current stage from the node
        Stage stage;
        try {
            stage = (Stage) sceneHolder.getScene().getWindow();
        } catch (ClassCastException ex) {
            AlertBuilder.withType(Alert.AlertType.ERROR)
                    .title("Fatal Error")
                    .content("This gui was not initialized correctly! (window is not a Stage)")
                    .showAndWait();
            return;
        }

        // Try to load the new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(gui.getPath()));
        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            Platform.exit();
        }
    }
}
