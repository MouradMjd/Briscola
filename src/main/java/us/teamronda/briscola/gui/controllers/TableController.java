package us.teamronda.briscola.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TableController {

    @FXML private HBox playerBox;
    @FXML private HBox opponentBox;

    @FXML private Label opponentPointsLabel;
    @FXML private Label playerPointsLabel;

    @FXML private Label turnLabel;
    @FXML private Label timeLabel;

    // Viene chiamato automaticamente da JavaFX
    // appena viene mostrata la finestra
    public void initialize() {

    }
}
