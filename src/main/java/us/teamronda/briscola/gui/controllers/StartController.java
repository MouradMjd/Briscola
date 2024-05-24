package us.teamronda.briscola.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.gui.Guis;
import us.teamronda.briscola.gui.SceneSwitcher;

import java.io.IOException;

public class StartController extends SceneSwitcher {

    @FXML
    public TextField usernameField;
    @FXML
    private Button playButton;

    public void initialize() {
        setSceneHolder(playButton);
    }

    @FXML
    public void onKeyPressed(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) startGame();
    }

    @FXML
    public void onButtonClicked(ActionEvent event) throws IOException {
        startGame();
    }

    private void startGame() {
        // Disable the button to prevent the user
        // from pressing enter and clicking the button
        // multiple times
        playButton.setDisable(true);

        // Check if the username is not empty
        if (verification()) {
            // Try to add the player to the game
            if (LogicGame.getInstance().addPlayer(new Player(usernameField.getText()))) {
                switchTo(Guis.TABLE);
                return;
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning!");
                alert.setContentText("Username already taken!");
                alert.showAndWait();
            }
        }

        // If we get here, it means that something went wrong
        playButton.setDisable(false);
    }

    public boolean verification() {
        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning!");
            alert.setContentText("The username must not be empty!");
            alert.showAndWait();
            return false;
        }
        return true;
    }
}
