package us.teamronda.briscola.api.player;

import us.teamronda.briscola.Deck;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.cards.ICard;

import java.util.Collection;

/**
 * This interfaces define some generic methods
 * needed for the Player object
 */
public interface IPlayer extends Comparable<IPlayer> {

    String getUsername();

    void addCard(Card card);

    Card pollCard(int index);

    void fillHand(Deck deck);

    int addPoints(Collection<ICard> cards);

    int subtractPoints(Collection<ICard> cards);

    int getPoints();

    boolean isBot();
}
