package us.teamronda.briscola.gui.components;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import lombok.Setter;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.gui.AnimationType;

public class CardComponent extends StackPane {

    private static final long ANIMATION_DURATION = 125;
    private static final int CARD_WIDTH = 100;
    private static final int CARD_HEIGHT = 150;

    private final Rectangle front;
    private final Rectangle back;

    @Setter private AnimationType animationType;
    private boolean transitioning;
    private boolean obscured;

    public CardComponent(Card card, AnimationType animationType) {
        this(card, animationType, false);
    }

    public CardComponent(Card card, AnimationType animationType, boolean obscured) {
        this.animationType = animationType;
        this.transitioning = false;
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

        TranslateTransition animation = new TranslateTransition();
        animation.setNode(this);
        animation.setFromX(this.getLayoutX());
        animation.setFromY(this.getLayoutY());
        animation.setToY(animationType.equals(AnimationType.UP) ? this.getLayoutY() - 10 : this.getLayoutY());
        animation.setToX(animationType.equals(AnimationType.RIGHT) ? this.getLayoutX() + 10 : this.getLayoutX());
        animation.setDuration(Duration.millis(200));

        rectangle.setOnMouseEntered(event -> {
            if (!animationType.hasAnimation()) return;
            animation.stop();
            animation.setRate(1);
            animation.play();
        });

        rectangle.setOnMouseExited(event -> {
            if (!animationType.hasAnimation()) return;

            animation.stop();
            animation.setRate(-1);
            animation.play();
        });

        return rectangle;
    }
}
