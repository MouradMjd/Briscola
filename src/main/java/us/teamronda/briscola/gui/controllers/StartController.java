package us.teamronda.briscola.gui.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.Player;

import java.io.IOException;
import java.util.Objects;

public class StartController {
    @Getter
    private static StartController Start = new StartController();

    @FXML public TextField usernameField;
    @FXML private Button playButton;

    @FXML
    public void initialize(ActionEvent e) throws IOException {
        if (verication()) {
            LogicGame.getInstance().addPlayer(new Player(usernameField.getText()));
            switchToTable();
        }
    }

    @FXML
    public boolean verication() {
        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("Set a username");
            alert.showAndWait();
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
}
