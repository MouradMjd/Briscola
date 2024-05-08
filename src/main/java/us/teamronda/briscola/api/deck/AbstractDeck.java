package us.teamronda.briscola.api.deck;

import us.teamronda.briscola.api.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides some generic methods which
 * can be used in every card game
 */
public abstract class AbstractDeck implements IDeck {

    private static final int DEFAULT_DECK_SIZE = 40;

    // We used a List to take advantage of the built-in
    // Collections#shuffle static method to handle deck shuffling
    protected final List<Card> cards;

    public AbstractDeck() {
        this.cards = new ArrayList<>(DEFAULT_DECK_SIZE);
    }

    /**
     * @return an <b>IMMUTABLE</b> List of cards left in the deck
     */
    public List<Card> getCards() {
        return List.copyOf(cards);
    }

    /**
     * @return the starting size of the deck
     */
    public int getMaxSize() {
        return DEFAULT_DECK_SIZE;
    }

    /**
     * @return the number of cards in the deck currently
     */
    public int getCardsRemaining() {
        return cards.size();
    }

    /**
     * @return true is the deck has no cards
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

