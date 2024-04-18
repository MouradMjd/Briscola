package us.teamronda.briscola.api.deck;

import us.teamronda.briscola.api.cards.Card;

/**
 * This interface defines the methods needed
 * for a generic deck of cards
 */
public interface IDeck {

    // This method initializes the deck
    void create();

    /*
    This method shuffles the deck.
    Mostly here if a custom implementation of
    a shuffle algorithm is needed.
     */
    void shuffle();

    // Pops a card from the top of the deck
    Card popCard();
}
