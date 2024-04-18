package us.teamronda.briscola.api;

import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.player.AbstractPlayer;
import lombok.Getter;

import java.util.List;

@Getter
public class Player extends AbstractPlayer {

    public Player(String nome) {
        super(nome);
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    @Override
    public Card pollCard(int index) {
        if (index < 0 || index >= hand.size()) {
            throw new IllegalArgumentException("Index can be only 0 to " + hand.size());
        }

        return hand.remove(index);
    }

    public void addPoints(List<Card> cardsWon) {
        points += cardsWon.stream().mapToInt(Card::getPoints).sum();
    }

    @Override
    public void subtractPoints(List<Card> cards) {
        // NOT USED IN OUR IMPLEMENTATION
    }
}
