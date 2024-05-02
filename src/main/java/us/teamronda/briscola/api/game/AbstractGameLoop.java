package us.teamronda.briscola.api.game;

import us.teamronda.briscola.DeckImpl;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.player.AbstractPlayer;
import us.teamronda.briscola.api.player.IPlayer;

import java.util.*;

public abstract class AbstractGameLoop implements GameLoop {

    /*
    We use a set since players need to be unique:
    two players cannot share the same username.
     */
    protected final List<AbstractPlayer> players;
    protected Map<AbstractPlayer,Card> cardsPlayed;

    public AbstractGameLoop() {
        this.players = new ArrayList<>();
        this.cardsPlayed = new HashMap<>();
    }

    // Starts the game loop
    public void startGameLoop() {
        this.start();
        while (this.isGameOngoing())
        {
            this.tick();
        }
        this.stop();
    }

    /*
    This method returns a boolean for convenience purposes:
    if false is returned then the username of the player needs to be changed.
    */
    public boolean addPlayer(AbstractPlayer player) {
        if (isUsernameDuplicate(player.getUsername())) return false;

        return players.add(player);
    }

    // Removes a player from the game
    public void removePlayer(AbstractPlayer player) {
        players.remove(player);
    }

    private boolean isUsernameDuplicate(String username) {
        return players.stream().anyMatch(player -> player.getUsername().equalsIgnoreCase(username));
    }

    // Returns how many players are participating in the game
    public int getPlayerCount() {
        return players.size();
    }

    public void giveHand(DeckImpl deck) {
        for (IPlayer player : players) {
            player.fillHand(deck);
        }
    }

    public void viewAllPlayerHands() {
        for (AbstractPlayer player : players) {
            System.out.println(player.toStringHand());
        }
    }
}
