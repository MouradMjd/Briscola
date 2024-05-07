package us.teamronda.briscola.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import us.teamronda.briscola.LogicGame;

import java.io.IOException;
import java.util.Objects;

public class TableController {

    @FXML private HBox playerBox;
    @FXML private HBox opponentBox;
    @FXML private Rectangle c1;
    @FXML private Rectangle c2;
    @FXML private Rectangle c3;
    @FXML private Rectangle bc1;
    @FXML private Rectangle bc2;
    @FXML private Rectangle bc3;
    @FXML private Rectangle cp1;
    @FXML private Rectangle bcp1;
    @FXML private Rectangle ideck;

    @FXML private Label opponentPointsLabel;
    @FXML private Label playerPointsLabel;

    @FXML private Label turnLabel;
    @FXML private Label timeLabel;
    private LogicGame game;

    // Viene chiamato automaticamente da JavaFX
    // appena viene mostrata la finestra
    public void initialize() {
    //deck card
        loadCardImage("mazzo.png",ideck);
    //bot cards
        loadCardImage("back.png",bc1);
        loadCardImage("back.png",bc2);
        loadCardImage("back.png",bc3);
    }
    // Metodo per caricare e visualizzare un'immagine all'interno di un rettangolo
    private void loadCardImage(String imagePath, Rectangle rectangle) {
        try {
            Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/cards/"+imagePath)));
            rectangle.setFill(new ImagePattern(cardImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
