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
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.Player;

import java.io.IOException;

public class StartController {

    @FXML
    private TextField usernameField;

    @FXML
    private Button playButton;
    private Stage stage;
    private Scene scene;
    private Parent roor;
    private LogicGame partita;


    @FXML
    public void initialize(ActionEvent e) throws IOException {
        if (verication()) {
            Player p = new Player(usernameField.getText());
            switchToTable();
        }
    }

    @FXML
    public boolean verication() {
        if (usernameField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hey!");
            alert.setHeaderText(null);
            alert.setContentText("Choose a username");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void switchToTable() throws IOException {
        Stage currentStage = (Stage) playButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/us/teamronda/briscola/gui/fxmls/table.fxml"));
        Parent root = fxmlLoader.load();
        Scene newScene = new Scene(root);
        currentStage.setScene(newScene);
        currentStage.show();
    }
}
