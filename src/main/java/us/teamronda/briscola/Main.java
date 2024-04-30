package us.teamronda.briscola;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import us.teamronda.briscola.gui.Constants;
import us.teamronda.briscola.gui.components.AnimationType;
import us.teamronda.briscola.gui.components.card.CardAssets;
import us.teamronda.briscola.gui.components.card.CardComponent;
import us.teamronda.briscola.gui.components.deck.DeckComponent;
import us.teamronda.briscola.gui.components.hand.HandBox;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox();
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.prefWidthProperty().bind(stage.widthProperty());

        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER_LEFT);
        pane.setMinSize(
                Constants.WINDOW_WIDTH / 2D,
                Constants.WINDOW_HEIGHT / 2D + Constants.INNER_SPACING * 2
        );
        pane.getChildren().add(new DeckComponent());

        root.getChildren().addAll(
                new HandBox(List.of(
                        new CardComponent(CardAssets.OBSCURED_CARD, AnimationType.NONE),
                        new CardComponent(CardAssets.OBSCURED_CARD, AnimationType.NONE),
                        new CardComponent(CardAssets.OBSCURED_CARD, AnimationType.NONE)
                )),
                pane,
                new HandBox(List.of(
                        new CardComponent(CardAssets.CARD_1, AnimationType.UP),
                        new CardComponent(CardAssets.CARD_2, AnimationType.UP),
                        new CardComponent(CardAssets.CARD_3, AnimationType.UP)
                )));

        stage.setResizable(false);
        stage.setTitle("BriscolaFX 4.2.0");
        stage.setScene(new Scene(root, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        /*
        LogicGame game = new LogicGame();
        game.startGameLoop();
         */
    }
}