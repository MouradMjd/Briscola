package us.teamronda.briscola.api.player;

import us.teamronda.briscola.DeckImpl;
import us.teamronda.briscola.api.Card;

import java.util.Collection;

/**
 * This interfaces define some generic methods
 * needed for the Player object
 */
public interface IPlayer extends Comparable<IPlayer> {

    void addCard(Card card);

    Card pollCard(int index);

    void fillHand(DeckImpl deck);

    int addPoints(Collection<Card> cards);

    int subtractPoints(Collection<Card> cards);

    int getPoints();
}
