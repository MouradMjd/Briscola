package us.teamronda.briscola.api;

import lombok.Getter;
import us.teamronda.briscola.api.player.AbstractPlayer;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.utils.ScoringUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

@Getter
public class Player extends AbstractPlayer {

    private static final Comparator<Integer> POINTS_COMPARATOR = Comparator.reverseOrder();

    public Player(String username) {
        super(username);
    }

    @Override
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

    @Override
    public int addPoints(Collection<Card> cardsWon) {
        int addedPoints = ScoringUtils.calculatePoints(cardsWon);
        points += addedPoints;
        return addedPoints;
    }

    @Override
    public int subtractPoints(Collection<Card> cards) {
        // NOT USED IN OUR IMPLEMENTATION
        return 0;
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

    @Override
    public int compareTo(IPlayer o) {
        return POINTS_COMPARATOR.compare(points, o.getPoints());
    }
}
