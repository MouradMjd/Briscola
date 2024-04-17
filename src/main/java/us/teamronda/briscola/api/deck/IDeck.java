package us.teamronda.briscola.api.deck;

import us.teamronda.briscola.api.cards.Card;

public interface IDeck {

    void create();

    void shuffle();

    Card popCard();

    int getSize();

    boolean isEmpty();
}
