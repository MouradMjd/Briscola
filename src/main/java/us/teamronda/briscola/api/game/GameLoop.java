package us.teamronda.briscola.api.game;

import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.player.IPlayer;

/**
 * This interface represents a simple game loop
 */
public interface GameLoop {

    // This method sets up the game
    void start();

    // This method stops the game and
    // prepares for the next game
    void stop();

    // This method returns true if the
    boolean isGameOngoing();

    // This method represents a single turn of a player
    void tick(IPlayer player, ICard playedCard);

    IPlayer getWhoIsPlaying();
}
