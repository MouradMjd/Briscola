package us.teamronda.briscola.api.player;

import us.teamronda.briscola.DeckImpl;
import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractPlayer implements IPlayer {

    // These are default values related to our implementation
    private static final int DEFAULT_SIZE_HAND = 3;
    private static final int DEFAULT_STARTING_POINTS = 0;

    protected final String username;
    protected final List<Card> hand;
    protected int points;

    public AbstractPlayer(String username) {
        this.username = username;
        this.hand = new ArrayList<>(DEFAULT_SIZE_HAND);
        this.points = DEFAULT_STARTING_POINTS;
    }

    @Override
    public void getHand(DeckImpl deck) {
        for (int i = 0; i < DEFAULT_SIZE_HAND; i++) {
            hand.add(deck.popCard());
        }
    }

    public String toStringHand() {
        return "Player{" +
                "username=" + username +
                "hand=" + hand +
                '}';
    }
}