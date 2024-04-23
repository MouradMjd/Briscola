package us.teamronda.briscola.api.player;

import us.teamronda.briscola.DeckImpl;
import us.teamronda.briscola.api.cards.Card;

import java.util.List;

/**
 * This interfaces define some generic methods
 * needed for the Player object
 */
public interface IPlayer {

    void addCard(Card card);

    Card pollCard(int index);

    void getHand(DeckImpl deck);

    void addPoints(List<Card> cards);

    void subtractPoints(List<Card> cards);
}
