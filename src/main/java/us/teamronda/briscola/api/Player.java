package us.teamronda.briscola.api;

import lombok.Getter;
import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.player.AbstractPlayer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class Player extends AbstractPlayer {

    public Player(String username) {
        super(username);
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

    public void addPoints(Collection<Card> cardsWon) {
        points += cardsWon.stream().mapToInt(Card::getPoints).sum();
    }

    @Override
    public void subtractPoints(Collection<Card> cards) {
        // NOT USED IN OUR IMPLEMENTATION
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Player otherPlayer)) return true;

        return this.username.equals(otherPlayer.username);
    }
}
