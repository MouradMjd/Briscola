package us.teamronda.briscola.gui.components.hand;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import us.teamronda.briscola.gui.Constants;
import us.teamronda.briscola.gui.components.card.CardComponent;

import java.util.ArrayList;
import java.util.List;

public class HandBox extends HBox {

    private final List<CardComponent> cardComponents;

    public HandBox(List<CardComponent> components) {
        this.cardComponents = new ArrayList<>();
        components.forEach(this::addCard);

        this.setPrefSize(USE_COMPUTED_SIZE, Constants.CARD_HEIGHT);
        this.setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new Insets(Constants.INNER_SPACING / 2D, 0, Constants.INNER_SPACING / 2D, 0));
        this.setSpacing(Constants.INNER_SPACING);
    }

    public void addCard(CardComponent component) {
        component.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            cardComponents.remove(component);
            this.getChildren().remove(component);
        });

        cardComponents.add(component);
        this.getChildren().add(component);
    }
}
