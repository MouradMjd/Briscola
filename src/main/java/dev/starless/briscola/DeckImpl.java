package dev.starless.briscola;

import dev.starless.briscola.api.cards.Card;
import dev.starless.briscola.api.cards.Seed;
import dev.starless.briscola.api.cards.CardType;
import dev.starless.briscola.api.deck.AbstractDeck;

import java.util.Collections;
import java.util.Optional;

public class DeckImpl extends AbstractDeck {

    @Override
    public void create() {
        mazzo.clear();

        for (CardType type : CardType.values()) {
            for (Seed seed : Seed.values()) {
                mazzo.add(new Card(type, seed));
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(mazzo);
    }

    public Card takeBriscola() {
        return mazzo.getLast();
    }

    @Override
    public Card popCard() {
        Card card=mazzo.getFirst();
        mazzo.remove(card);
        return card;
    }

    @Override
    public int getSize() {
        return mazzo.size();
    }

    @Override
    public boolean isEmpty() {
        return mazzo.isEmpty();
    }
}
