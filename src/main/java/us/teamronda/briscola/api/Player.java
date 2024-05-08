package us.teamronda.briscola.api;

import lombok.Getter;
import us.teamronda.briscola.api.cards.ICard;
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

    public Player(String username, boolean bot) {
        super(username, bot);
    }

    @Override
    public void addCard(Card card) {
        hand.add(card);
    }

    @Override
    public ICard pollCard(int index) {
        return hand.remove(index);
    }

    @Override
    public void pollCard(ICard card) {
        hand.remove(card);
    }

    @Override
    public int addPoints(Collection<ICard> cardsWon) {
        int addedPoints = ScoringUtils.calculatePoints(cardsWon);
        points += addedPoints;
        return addedPoints;
    }

    @Override
    public int subtractPoints(Collection<ICard> cards) {
        // NOT USED IN OUR IMPLEMENTATION
        return 0;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getUsername());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Player otherPlayer)) return true;

        return this.getUsername().equals(otherPlayer.getUsername());
    }

    @Override
    public int compareTo(IPlayer o) {
        return POINTS_COMPARATOR.compare(points, o.getPoints());
    }
}
