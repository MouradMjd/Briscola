package us.teamronda.briscola.api.deck;

import us.teamronda.briscola.api.cards.ICard;

/**
 * This interface defines the methods needed
 * for a generic deck of cards
 */
public interface IDeck {

    /**
     * This method initializes the deck
     */
    void create();

    /**
     * This method shuffles the deck.
     * Mostly here if a custom implementation of
     * a shuffle algorithm is needed.
     */
    void shuffle();

    /**
     * @return the number of cards in the deck currently
     */
    int getCardsRemaining();

    /**
     * @return true is the deck has no cards
     */
    boolean isEmpty();

    /**
     * Adds a card to the deck at a specified index
     * @param index index an integer between 0 and {@link #getCardsRemaining()} (inclusive)
     */
    void addCard(ICard card, int index);

    /**
     * Adds a card at the top of the deck.
     * Internally calls {@link #addCard(ICard, int)}
     * @param card {@link ICard} object
     */
    default void addCardToTop(ICard card) {
        addCard(card, 0);
    }

    /**
     * Adds a card at the bottom of the deck.
     * Internally calls {@link #addCard(ICard, int)}
     * @param card {@link ICard} object
     */
    default void addCardToBottom(ICard card) {
        addCard(card, getCardsRemaining());
    }

    /**
     * Pops a card from the top of the deck
     * @param index an integer between 0 and {@link #getCardsRemaining()} - 1  (inclusive)
     * @return {@link ICard} object
     */
    ICard popCard(int index);

    /**
     * Pops a card from the top of the deck
     * @return {@link ICard} object
     */
    default ICard popCardFromTop() {
        return popCard(0);
    }
}
