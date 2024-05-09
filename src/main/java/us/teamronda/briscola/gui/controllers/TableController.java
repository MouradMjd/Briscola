package us.teamronda.briscola.gui.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.components.CardAssets;
import us.teamronda.briscola.gui.components.CardComponent;
import us.teamronda.briscola.utils.TimerUtils;

import java.util.*;
import java.util.stream.Collectors;

public class TableController {

    @Getter private static TableController instance = new TableController();

    @FXML private StackPane deckBox; // Rettangolo del mazzo
    @FXML private HBox playerBox; // Rettangolo del giocatore
    @FXML private HBox opponentBox; // Rettangolo del bot
    @FXML @Getter private HBox cardsPlayed;
    @FXML private Label opponentPointsLabel;
    @FXML private Label playerPointsLabel;

    @FXML private Label turnLabel;
    @FXML private Label timeLabel;

    // Viene chiamato automaticamente da JavaFX
    // appena viene mostrata la finestra
    @FXML
    public void initialize() {
        // JavaFX uses reflection to access the controller
        // and inject the FXML fields, so creating a new static
        // instance (like for this controller) will not work.
        instance = this;
        CardAssets.load(); // Load card assets

        LogicGame game = LogicGame.getInstance();
        game.start();

        // Fill the players' hands
        game.getPlayers().forEach(this::updateHand);

        List<CardComponent> cardComponents = game.getRemainingCards().stream()
                .map(card -> new CardComponent(card, false, true))
                .collect(Collectors.toCollection(ArrayList::new));

        CardComponent trumpCard = cardComponents.getLast();
        trumpCard.rotateHorizontally();
        trumpCard.flip();

        Collections.reverse(cardComponents);

        deckBox.getChildren().addAll(cardComponents);
    }

    public void updateHandStatus(boolean disable) {
        playerBox.setDisable(disable);
    }

    public void updatePoints(int opPoints, int playerPoints) {
        if (opPoints == 0) {
            playerPointsLabel.setText("Punti: " + playerPoints);
        } else {
            opponentPointsLabel.setText("Punti: " + opPoints);
        }
    }

    public void updateHand(IPlayer player) {
        HBox box = player.isBot() ? opponentBox : playerBox;
        box.getChildren().clear();

        List<CardComponent> cardComponents = player.getHand().stream()
                .map(card -> new CardComponent(card, !player.isBot(), player.isBot()))
                .collect(Collectors.toCollection(ArrayList::new));

        box.getChildren().addAll(cardComponents);
    }

    public void clearTable() {
        opponentBox.getChildren().clear();
        playerBox.getChildren().clear();
        cardsPlayed.getChildren().clear();
    }

    public void winnerPopup(IPlayer winner) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hey!");
        alert.setHeaderText(null);
        alert.setContentText("WINNER: " + winner.getUsername());
        alert.show();

        TimerUtils.schedule(() -> Platform.runLater(alert::close), 1500);
    }
}
