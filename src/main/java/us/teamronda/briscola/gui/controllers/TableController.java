package us.teamronda.briscola.gui.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.components.CardAssets;
import us.teamronda.briscola.gui.components.CardComponent;
import us.teamronda.briscola.utils.TimerUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class TableController {

    @Getter
    private static TableController instance = new TableController();

    @FXML
    private StackPane deckBox; // Rettangolo del mazzo
    @FXML
    private HBox playerBox; // Rettangolo del giocatore
    @FXML
    private HBox opponentBox; // Rettangolo del bot
    @FXML
    @Getter
    private HBox cardsPlayed;
    @FXML
    private Label opponentPointsLabel;
    @FXML
    private Label playerPointsLabel;

    @FXML
    @Getter
    private Label turnLabel;
    @FXML
    private Label timeLabel;

    // We are formatting a time duration and not a date,
    // but this works for our purposes (the hour value gets set
    // automatically to 1 AM for some reason, but we do not show that)
    private final SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
    private Timeline timeline;

    // This method is called automatically by JavaFX
    @FXML
    public void initialize() {
        // JavaFX uses reflection to access the controller
        // and inject the FXML fields, so creating a new static
        // instance will not work.
        instance = this;
        CardAssets.load(); // Load card assets

        LogicGame game = LogicGame.getInstance();
        game.start();

        // Fill the players' hands
        game.getPlayers().forEach(this::updateHand);

        // Fill the deck box with downwards-facing CardComponents
        List<CardComponent> cardComponents = game.getRemainingCards().stream()
                .map(card -> new CardComponent(card, false, true))
                .collect(Collectors.toCollection(ArrayList::new));

        CardComponent trumpCard = cardComponents.getLast();
        trumpCard.rotateHorizontally();
        trumpCard.flip();

        // The first elements of the list are placed at the bottom
        // of the StackPane, so we need to reverse it
        Collections.reverse(cardComponents);

        deckBox.getChildren().addAll(cardComponents);
    }

    public void updateHandStatus(boolean disable) {
        playerBox.setDisable(disable);
    }

    // Used to update the time every second
    public void updateTurnLabel(int turns) {
        turnLabel.setText("Turno #" + turns);
    }

    /**
     * Updates the points label of the player or the opponent.
     *
     * @param opPoints the points of the bot
     * @param playerPoints the points of the player
     */
    public void updatePointsLabel(int opPoints, int playerPoints) {
        if (opPoints == 0) {
            playerPointsLabel.setText("Punti: " + playerPoints);
        } else {
            opponentPointsLabel.setText("Punti: " + opPoints);
        }
    }

    /**
     * Update the HBox corresponding to the player's hand
     *
     * @param player {@link IPlayer} object who needs their hand updated
     */
    public void updateHand(IPlayer player) {
        // We need to know which HBox to update
        HBox box = player.isBot() ? opponentBox : playerBox;
        box.getChildren().clear(); // Remove every child

        // Map the ICards to CardComponents...
        List<CardComponent> cardComponents = player.getHand().stream()
                .map(card -> new CardComponent(card, !player.isBot(), player.isBot()))
                .collect(Collectors.toCollection(ArrayList::new));

        // ...and add them to the HBox
        box.getChildren().addAll(cardComponents);
    }

    /**
     * Removes the last x of cards from the deckBox,
     * aka the cards that are visible.
     *
     * @param amount the amount of cards to remove
     */
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

    public void startTimer() {
        // You never know...
        if (timeline != null) timeline.stop();

        long startTime = System.currentTimeMillis();
        // Every second, update the timeLabel with the elapsed time
        timeline = new Timeline(new KeyFrame(
                Duration.seconds(1D),
                event -> {
                    long elapsed = System.currentTimeMillis() - startTime;
                    timeLabel.setText(sdf.format(new Date(elapsed)));
                }
        ));
        timeline.setCycleCount(Animation.INDEFINITE); // loop forever
        timeline.play(); // start the loop
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

        TimerUtils.schedule(() -> Platform.runLater(alert::close), 1000);
        alert.showAndWait();
    }

    public void Popup(String s) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("CLASSIFICA:");
        alert.setHeaderText(null);
        alert.setContentText(s);

        TimerUtils.schedule(() -> Platform.runLater(alert::close), 3000);
        alert.showAndWait();
    }

    public void switchToStart() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/us/teamronda/briscola/gui/fxmls/start.fxml"));
        Stage currentStage = (Stage) playerBox.getScene().getWindow();

        currentStage.close();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/images/program_icon.png"))));
        stage.setResizable(false);
        stage.setTitle("Briscola v6.9");

        try {
            stage.setScene(new Scene(fxmlLoader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.show();

    }
}
