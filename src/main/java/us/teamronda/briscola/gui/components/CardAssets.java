package us.teamronda.briscola.gui.components;

import javafx.scene.image.Image;
import lombok.experimental.UtilityClass;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.cards.CardType;
import us.teamronda.briscola.api.cards.Seed;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class CardAssets {

    private final String CARD_ASSETS_PATH = "/assets/cards/";

    public final Image BACK = new Image(CardAssets.class.getResource(CARD_ASSETS_PATH + "back.png").toString());
    private final Map<String, Image> CARDS_IMAGES = new HashMap<>();

    public void load() {
        CARDS_IMAGES.clear(); // Clear the map to avoid duplicates when (and if) reloading
        for (Seed seed : Seed.values()) {
            for (CardType type : CardType.values()) {
                String id = getId(seed, type);
                CARDS_IMAGES.put(id, new Image(CardAssets.class.getResource(CARD_ASSETS_PATH + id + ".png").toString()));
            }
        }
    }

    public Image getCardImage(Card card) {
        return CARDS_IMAGES.get(getId(card.seed(), card.type()));
    }

    private String getId(Seed seed, CardType type) {
        return type.name().toLowerCase().concat("_").concat(seed.name().toLowerCase());
    }
}
