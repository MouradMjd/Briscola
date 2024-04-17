package dev.starless.briscola.api.player;

import dev.starless.briscola.api.cards.Card;

import java.util.List;

public interface IPlayer {

    void addCard(Card card);

    Card pollCard(int index);

    void addPoints(List<Card> cards);

    void subtractPoints(List<Card> cards);
}
