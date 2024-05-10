package us.teamronda.briscola.gui.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.components.CardAssets;
import us.teamronda.briscola.gui.components.CardComponent;
import us.teamronda.briscola.utils.TimerUtils;

import java.text.SimpleDateFormat;
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

    @FXML @Getter private Label turnLabel;
    @FXML private Label timeLabel;

    // We are formatting a time duration and not a date,
    // but this works for our purposes (the hour value gets set
    // automatically to 1 AM for some reason, but we do not show that)
    private final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    private Timeline timeline;

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

        // Fill the deck box with CardComponents
        List<CardComponent> cardComponents = game.getRemainingCards().stream()
                .map(card -> new CardComponent(card, false, true))
                .collect(Collectors.toCollection(ArrayList::new));

        CardComponent trumpCard = cardComponents.getLast();
        trumpCard.rotateHorizontally();
        trumpCard.flip();

        // The stackpane places the first elements of the list
        // at the bottom, so we need to reverse it
        Collections.reverse(cardComponents);

        deckBox.getChildren().addAll(cardComponents);
    }

    public void updateHandStatus(boolean disable) {
        playerBox.setDisable(disable);
    }

    public void updateTurnLabel(int turns) {
        turnLabel.setText("Turn #" + turns);
    }

    public void updatePointsLabel(int opPoints, int playerPoints) {
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

    public void popDeckCards(int amount) {
        // If the deckBox has more children than the amount of cards
        // this should never happen, but better safe than sorry
        int childrenNumber = deckBox.getChildren().size();
        if (childrenNumber > amount) {
            // We are using a method exclusive to the ObservableList object:
            // https://openjfx.io/javadoc/21/javafx.base/javafx/collections/ObservableList.html#remove(int,int)

            // We need to remove the last elements in the list,
            // because they are the ones visible
            deckBox.getChildren().remove(childrenNumber - amount, childrenNumber);
        } else {
            deckBox.getChildren().clear();
        }
    }

    public void startTimer(long startTime) {
        // You never know...
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(
                Duration.seconds(1D),
                e -> {
                    long elapsed = System.currentTimeMillis() - startTime;
                    timeLabel.setText(sdf.format(new Date(elapsed)));
                }
        ));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play();
    }

    public void stopTimer() {
        if (timeline != null) timeline.stop();
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

        TimerUtils.schedule(() -> Platform.runLater(alert::close), 1500);
        alert.showAndWait();
    }
}
