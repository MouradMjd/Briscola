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

    public Optional<Card> popBriscola() {
        // TODO: IMPLEMENT
        // DO NOT PUT IT THE INTERFACE
        return null;
    }

    @Override
    public Optional<Card> popCard() {
        //TODO: IMPLEMENT
        return null;
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
