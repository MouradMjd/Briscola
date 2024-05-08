package us.teamronda.briscola.utils;

import lombok.experimental.UtilityClass;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.cards.ICard;

import java.util.Collection;

@UtilityClass
public class ScoringUtils {

    public final int MAX_POINTS = 120;

    public int calculatePoints(Collection<ICard> cards) {
        return cards.stream().mapToInt(ICard::getPoints).sum();
    }
}
