package dev.starless.briscola.api.deck;

import dev.starless.briscola.api.cards.Card;

public interface IDeck {

    void create();

    void shuffle();

    Card popCard();

    int getSize();

    boolean isEmpty();
}
