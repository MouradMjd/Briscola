package us.teamronda.briscola.api.game;

/**
 * This interface represents a simple game loop
 */
public interface GameLoop {

    // This method sets up the game
    void start();

    // This method stops the game and
    // prepares for the next game
    boolean stop();

    // This method represents a single turn
    // played by all the users
    boolean tick();
}
