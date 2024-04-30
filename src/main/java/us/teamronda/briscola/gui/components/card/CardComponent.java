package us.teamronda.briscola.gui.components.card;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import us.teamronda.briscola.gui.Constants;
import us.teamronda.briscola.gui.components.AnimationType;

import java.net.URL;

public class CardComponent extends StackPane {
    
    private final Rectangle front;
    private final Rectangle back;

    private final AnimationType animation;
    private boolean transitioning;
    private boolean obscured;

    public CardComponent(String imageAsset, AnimationType animation) {
        this(imageAsset, animation, false);
    }

    public CardComponent(String imageAsset, AnimationType animation, boolean obscured) {
        this.animation = animation;
        this.transitioning = false;
        this.obscured = obscured;

        this.setPrefSize(Constants.CARD_WIDTH, Constants.CARD_HEIGHT);
        this.setMinSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);

        front = createSide(imageAsset);
        back = createSide(CardAssets.OBSCURED_CARD);
        if (obscured) {
            front.setScaleX(0);
        } else {
            back.setScaleX(0);
        }

        this.getChildren().addAll(back, front);
    }

    public void flip() {
        if (transitioning) return;
        transitioning = true;

        ScaleTransition hideSide = new ScaleTransition(Duration.millis(125), obscured ? back : front);
        hideSide.setFromX(1);
        hideSide.setToX(0);

        ScaleTransition showSide = new ScaleTransition(Duration.millis(125), obscured ? front : back);
        showSide.setFromX(0);
        showSide.setToX(1);

        showSide.setOnFinished(event -> {
            obscured = !obscured;
            transitioning = false;
        });
        hideSide.setOnFinished(event -> showSide.play());
        hideSide.play();
    }

    private Rectangle createSide(String imageSource) {
        URL assetResource = getClass().getResource(imageSource);
        if (assetResource == null) {
            throw new IllegalArgumentException("Image asset not found: " + imageSource);
        }

        Rectangle rectangle = new Rectangle(100, 150);
        rectangle.setFill(new ImagePattern(new Image(assetResource.toString())));
        
        if (animation.hasAnimation()) {
            TranslateTransition translate = new TranslateTransition();
            translate.setNode(this);
            translate.setFromX(this.getLayoutX());
            translate.setFromY(this.getLayoutY());
            translate.setToY(animation.equals(AnimationType.UP) ? this.getLayoutY() - 10 : this.getLayoutY());
            translate.setToX(animation.equals(AnimationType.RIGHT) ? this.getLayoutX() + 10 : this.getLayoutX());
            translate.setDuration(Duration.millis(200));

            rectangle.setOnMouseEntered(event -> {
                System.out.println(event);

                translate.stop();
                translate.setRate(1);
                translate.play();
            });

            rectangle.setOnMouseExited(event -> {
                System.out.println(event);

                translate.stop();
                translate.setRate(-1);
                translate.play();
            });
        }
        
        return rectangle;
    }
}
