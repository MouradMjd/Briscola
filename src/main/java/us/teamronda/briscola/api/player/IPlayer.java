package us.teamronda.briscola.api.player;

import us.teamronda.briscola.objects.Deck;
import us.teamronda.briscola.api.cards.ICard;

import java.util.Collection;
import java.util.List;

/**
 * This interfaces define some generic methods
 * needed for the Player object
 */
public interface IPlayer extends Comparable<IPlayer> {

    String getUsername();

    void addCard(ICard card);

    ICard pollCard(int index);

    void pollCard(ICard card);

    void fillHand(Deck deck);

    int addPoints(Collection<ICard> cards);

    int subtractPoints(Collection<ICard> cards);

    List<ICard> getHand();

    int getPoints();

    boolean isBot();
}
