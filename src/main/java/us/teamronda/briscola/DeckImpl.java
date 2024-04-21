package us.teamronda.briscola;

import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.cards.CardType;
import us.teamronda.briscola.api.cards.Seed;
import us.teamronda.briscola.api.deck.AbstractDeck;

import java.util.Collections;

public class DeckImpl extends AbstractDeck {

    private Seed trumpSeed;

    @Override
    public void create() {
        // Remove any cards from the previous game
        cards.clear();

        // Populate the deck
        for (CardType type : CardType.values()) {
            for (Seed seed : Seed.values()) {
                cards.add(new Card(type, seed));
            }
        }

        /*
        We take the first card,
        store its seed in a variable
        and append it at the end of the deck
         */
        Card trumpCard = popCard();
        trumpSeed = trumpCard.seed();
        cards.addLast(trumpCard);
    }

    @Override
    public void shuffle() {
        Collections.shuffle(cards);
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

    /**
     * Checks if a card has the same seed of the
     * trump card.
     *
     * @param card Card object to check
     */
    public boolean hasTrumpSeed(Card card) {
        return trumpSeed != null && trumpSeed.equals(card.seed());
    }
}
