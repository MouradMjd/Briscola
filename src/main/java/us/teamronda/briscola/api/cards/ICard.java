package us.teamronda.briscola.api.cards;

public interface ICard {

    /**
     * Every card game has its own unique way to calculate a score.
     * This interface enables other developers to implement their own way
     * to calculate the score of a card.
     *
     * @return an integer representing the points the card is worth
     */
    int getPoints();
}
