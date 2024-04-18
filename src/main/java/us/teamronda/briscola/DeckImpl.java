package us.teamronda.briscola;

import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.cards.CardType;
import us.teamronda.briscola.api.cards.Seed;
import us.teamronda.briscola.api.deck.AbstractDeck;

import java.util.Collections;

public class DeckImpl extends AbstractDeck {

    @Override
    public void create() {
        cards.clear();

        for (CardType type : CardType.values()) {
            for (Seed seed : Seed.values()) {
                cards.add(new Card(type, seed));
            }
        }
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card takeBriscola() {
        if (isEmpty()) {
            return null;
        }
        return cards.getLast();
    }

    @Override
    public Card popCard() {
        if (isEmpty()) {
            return null;
        }
        Card card = cards.getFirst();
        cards.remove(card);
        return card;
    }
}
