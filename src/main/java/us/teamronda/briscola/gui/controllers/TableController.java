package us.teamronda.briscola.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.gui.AnimationType;
import us.teamronda.briscola.gui.components.CardAssets;
import us.teamronda.briscola.gui.components.CardComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TableController {

    @FXML private StackPane deckBox;
    @FXML private HBox playerBox;
    @FXML private HBox opponentBox;

    @FXML private Label opponentPointsLabel;
    @FXML private Label playerPointsLabel;

    @FXML private Label turnLabel;
    @FXML private Label timeLabel;

    // Viene chiamato automaticamente da JavaFX
    // appena viene mostrata la finestra
    public void initialize() {
        CardAssets.load(); // Load on startup?

        LogicGame game = LogicGame.getInstance();
        game.start();

        List<CardComponent> cardComponents = game.getRemainingCards().stream()
                .map(card -> new CardComponent(card, AnimationType.NONE, true))
                .collect(Collectors.toCollection(ArrayList::new));

        CardComponent trumpCard = cardComponents.getLast();
        trumpCard.setAnimationType(AnimationType.RIGHT);
        trumpCard.rotateHorizontally();
        trumpCard.flip();

        Collections.reverse(cardComponents);

        deckBox.getChildren().addAll(cardComponents);
    }
}
