package us.teamronda.briscola.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.Player;

import java.io.IOException;

public class StartController {

    @Getter
    private static final StartController instance = new StartController();

    @FXML
    public TextField usernameField;
    @FXML
    private Button playButton;

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        // Check if the username is not empty
        if (verification()) {
            // Try to add the player to the game
            if (LogicGame.getInstance().addPlayer(new Player(usernameField.getText()))) {
                switchToTable();
            } else {
                showErrorAlert("Username already taken!");
            }
        }
    }

    public boolean verification() {
        if (usernameField.getText().isEmpty()) {
            showErrorAlert("Please enter a username!");
            return false;
        }
        return true;
    }

    public void switchToTable() throws IOException {
        Stage currentStage = (Stage) playButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/us/teamronda/briscola/gui/fxmls/table.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        currentStage.setScene(newScene);
        currentStage.show();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hey!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
