package us.teamronda.briscola.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.AnimationType;
import us.teamronda.briscola.gui.components.CardAssets;
import us.teamronda.briscola.gui.components.CardComponent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TableController {

    @Getter private static TableController instance = new TableController();

    @FXML private StackPane deckBox; // Rettangolo del mazzo
    @FXML private HBox playerBox; // Rettangolo del giocatore
    @FXML private HBox opponentBox; // Rettangolo del bot
    @FXML @Getter private HBox cardsplayed;
    @FXML private Label opponentPointsLabel;
    @FXML private Label playerPointsLabel;

    @FXML private Label turnLabel;
    @FXML private Label timeLabel;

    // Viene chiamato automaticamente da JavaFX
    // appena viene mostrata la finestra
    public void initialize() {
        instance = this;
        CardAssets.load(); // Load on startup?

        LogicGame game = LogicGame.getInstance();
        game.start();

        fillPlayerHands(game);

        List<CardComponent> cardComponents = game.getRemainingCards().stream()
                .map(card -> new CardComponent(card, AnimationType.NONE, true))
                .collect(Collectors.toCollection(ArrayList::new));

        CardComponent trumpCard = cardComponents.getLast();
        trumpCard.rotateHorizontally();
        trumpCard.flip();

        Collections.reverse(cardComponents);

        deckBox.getChildren().addAll(cardComponents);
    }

    public void fillPlayerHands(LogicGame game) {
        //give cards
        for (IPlayer player : game.getPlayers()) {
            player.fillHand(game.getDeck());

            if (player.isBot()) {
                for (ICard iCard : player.getHand()) {
                    opponentBox.getChildren().add(new CardComponent(iCard, AnimationType.NONE, true));
                }
            } else {
                for (ICard iCard : player.getHand()) {
                    playerBox.getChildren().add(new CardComponent(iCard, AnimationType.UP));
                }
            }
        }
    }

    public void clearTable() {
        opponentBox.getChildren().clear();
        playerBox.getChildren().clear();
        cardsplayed.getChildren().clear();
    }
}
