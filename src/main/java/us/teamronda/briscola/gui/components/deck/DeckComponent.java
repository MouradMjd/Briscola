package us.teamronda.briscola.gui.components.deck;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import us.teamronda.briscola.gui.Constants;
import us.teamronda.briscola.gui.components.AnimationType;
import us.teamronda.briscola.gui.components.card.CardAssets;
import us.teamronda.briscola.gui.components.card.CardComponent;

public class DeckComponent extends HBox {

    public DeckComponent() {
        this.setSpacing(-Constants.CARD_WIDTH + 5);
        this.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
        this.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < 3; i++) {
            CardComponent component = new CardComponent(
                    CardAssets.CARD_1,
                    i == 2 ? AnimationType.RIGHT : AnimationType.NONE,
                    true
            );
            component.setOnMouseClicked(event -> component.flip());

            this.getChildren().add(component);
        }
    }
}
