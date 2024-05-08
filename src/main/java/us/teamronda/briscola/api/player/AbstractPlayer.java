package us.teamronda.briscola.api.player;

import lombok.AccessLevel;
import us.teamronda.briscola.Deck;
import us.teamronda.briscola.api.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractPlayer implements IPlayer {

    // These are default values related to our implementation
    public static final int DEFAULT_SIZE_HAND = 3;
    private static final int DEFAULT_STARTING_POINTS = 0;

    private final String username;
    protected final List<Card> hand;

    // Values used in overridden methods
    @Getter(AccessLevel.NONE) private final boolean bot;
    @Getter(AccessLevel.NONE) protected int points;

    public AbstractPlayer(String username) {
        this(username, false);
    }

    public AbstractPlayer(String username, boolean bot) {
        this.username = username;
        this.bot = bot;

        this.hand = new ArrayList<>(DEFAULT_SIZE_HAND);
        this.points = DEFAULT_STARTING_POINTS;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void fillHand(Deck deck) {
        for (int i = 0; i < DEFAULT_SIZE_HAND - hand.size(); i++) {
            hand.add(deck.popCard());
        }
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public boolean isBot() {
        return bot;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username=" + username +
                "points=" + points +
                "hand=" + hand +
                '}';
    }
}