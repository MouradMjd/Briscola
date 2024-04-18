package us.teamronda.briscola.api.player;

import us.teamronda.briscola.api.cards.Card;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class AbstractPlayer implements IPlayer {

    // These are default values related to our implementation
    private static final int DEFAULT_SIZE_HAND = 3;
    private static final int DEFAULT_STARTING_POINTS = 0;

    private final String name;
    protected final List<Card> hand;
    protected int points;

    public AbstractPlayer(String name) {
        this.name = name;
        this.hand = new ArrayList<>(3);
        this.points = DEFAULT_STARTING_POINTS;
    }
}
