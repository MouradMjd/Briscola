package us.teamronda.briscola.api.game;

import us.teamronda.briscola.api.player.IPlayer;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractGameLoop implements GameLoop {

    /*
    We use a set since players need to be unique:
    two players cannot share the same username.
     */
    protected final Set<IPlayer> players;

    public AbstractGameLoop() {
        this.players = new HashSet<>();
    }

    /*
    This method returns a boolean for convenience purposes:
    if false is returned then the username of the player needs to be changed.
     */
    public boolean addPlayer(IPlayer player) {
        return players.add(player);
    }

    // Removes a player from the game
    public void removePlayer(IPlayer player) {
        players.remove(player);
    }

    // Returns how many players are participating in the game
    public int getPlayerCount() {
        return players.size();
    }
}
