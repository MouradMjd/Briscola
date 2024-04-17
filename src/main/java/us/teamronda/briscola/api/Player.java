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
        mano.add(card);
    }

    @Override
    public Card pollCard(int index) {
        if (index < 0 || index >= mano.size()) {
            throw new IllegalArgumentException("Index can be only 0 to " + mano.size());
        }

        return mano.remove(index);
    }

    public void addPoints(List<Card> cardsWon) {
        punti += cardsWon.stream().mapToInt(Card::getPunti).sum();
    }

    @Override
    public void subtractPoints(List<Card> cards) {
        // NOT USED IN OUR GAME
    }
}
