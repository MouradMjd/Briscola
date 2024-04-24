package us.teamronda.briscola;

import lombok.Getter;
import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.cards.CardType;
import us.teamronda.briscola.api.cards.Seed;
import us.teamronda.briscola.api.deck.AbstractDeck;

import java.util.Collections;

@Getter
public class DeckImpl extends AbstractDeck {

    private Card trumpCard;

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

        // Mischialo sto mazzo
        this.shuffle();
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

        return cards.removeFirst();
    }

    /**
     * Checks if a card has the same seed of the
     * trump card.
     *
     * @param card Card object to check
     */
    public boolean hasTrumpSeed(Card card) {
        return trumpCard != null && trumpCard.seed().equals(card.seed());
    }
    public void takeBriscola()
    {
        trumpCard = popCard();
        cards.addLast(trumpCard);
    }
    public void popcardformore()
    {
        cards.remove(new Card(CardType.DUE,Seed.BASTONI));
    }
}
