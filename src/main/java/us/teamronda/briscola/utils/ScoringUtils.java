package us.teamronda.briscola.utils;

import lombok.experimental.UtilityClass;
import us.teamronda.briscola.api.Card;

import java.util.Collection;

@UtilityClass
public class ScoringUtils {

    public final int MAX_POINTS = 120;

    public int calculatePoints(Collection<Card> cards) {
        return cards.stream().mapToInt(Card::getPoints).sum();
    }
}
