package us.teamronda.briscola.gui.components;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import us.teamronda.briscola.LogicGame;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.controllers.TableController;

public class CardComponent extends StackPane {

    private static final long ANIMATION_DURATION = 125L;
    private static final int CARD_WIDTH = 89;
    private static final int CARD_HEIGHT = 168;

    private final Rectangle front;
    private final Rectangle back;

    private boolean transitioning;
    private boolean obscured;
    private final boolean playable;

    public CardComponent(ICard card, boolean playable) {
        this(card, playable, false);
    }

    public CardComponent(ICard card, boolean playable, boolean obscured) {
        this.transitioning = false;
        this.playable = playable;
        this.obscured = obscured;

        this.setPrefSize(CARD_WIDTH, CARD_HEIGHT);
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        front = createSide(CardAssets.getCardImage(card));
        back = createSide(CardAssets.BACK);
        if (obscured) {
            front.setScaleX(0);
        } else {
            back.setScaleX(0);
        }

        this.getChildren().addAll(back, front);
        this.setOnMouseClicked(event -> {
            if (!playable) return;

            // Prevent double-clicking
            this.setOnMouseClicked(MouseEvent::consume);

            // Block the handBox of the player
            TableController.getInstance().updateHandStatus(true);

            System.out.println("HO SCELTO LA CARTA: " + card.getType() + " di " + card.getSeed());
            // Prendo il giocatore che gioca
            IPlayer player = LogicGame.getInstance().getWhoIsPlaying();
            // Rimuovo la carta giocata
            player.pollCard(card);
            // Salvo l'azione del giocatore
            LogicGame.getInstance().tick(player, card);
        });
    }

    /*
    Using 3D is cringe, all my homies use 2D transitions.
    Thanks, Sergey!
    https://stackoverflow.com/a/19896794
     */
    public void flip() {
        if (transitioning) return;
        transitioning = true;

        ScaleTransition hideSide = new ScaleTransition(Duration.millis(ANIMATION_DURATION), obscured ? back : front);
        hideSide.setFromX(1);
        hideSide.setToX(0);

        ScaleTransition showSide = new ScaleTransition(Duration.millis(ANIMATION_DURATION), obscured ? front : back);
        showSide.setFromX(0);
        showSide.setToX(1);

        showSide.setOnFinished(event -> {
            obscured = !obscured;
            transitioning = false;
        });
        hideSide.setOnFinished(event -> showSide.play());
        hideSide.play();
    }

    public void rotateHorizontally() {
        RotateTransition rotation = new RotateTransition(Duration.millis(ANIMATION_DURATION), this);
        rotation.setAxis(new Point3D(0, 0, 1)); // Rotate on the Z axis
        rotation.setByAngle(90);
        rotation.play();
    }

    private Rectangle createSide(Image image) {
        Rectangle rectangle = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        rectangle.setFill(new ImagePattern(image));
        rectangle.setArcHeight(10D);
        rectangle.setArcWidth(10D);

        if (playable) {
            TranslateTransition animation = new TranslateTransition();
            animation.setNode(this);
            animation.setFromX(this.getLayoutX());
            animation.setFromY(this.getLayoutY());
            animation.setToY(this.getLayoutY() - 10);
            animation.setDuration(Duration.millis(200));

            rectangle.setOnMouseEntered(event -> {
                animation.stop();
                animation.setRate(1);
                animation.play();
            });

            rectangle.setOnMouseExited(event -> {
                animation.stop();
                animation.setRate(-1);
                animation.play();
            });
        }

        return rectangle;
    }
}
