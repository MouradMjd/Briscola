package dev.starless.briscola.api.deck;

import dev.starless.briscola.api.cards.Card;

import java.util.Optional;

public interface IDeck {

    void create();

    void shuffle();

    Optional<Card> popCard();

    int getSize();

    boolean isEmpty();
}
