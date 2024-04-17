package dev.starless.briscola.api.deck;

import dev.starless.briscola.api.cards.Card;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDeck implements IDeck {

    protected final List<Card> mazzo;

    public AbstractDeck() {
        this.mazzo = new ArrayList<>();
    }
}

