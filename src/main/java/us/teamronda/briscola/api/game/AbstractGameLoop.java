package us.teamronda.briscola.api.game;

import us.teamronda.briscola.DeckImpl;
import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.player.AbstractPlayer;
import us.teamronda.briscola.api.player.IPlayer;

import java.util.*;

public abstract class AbstractGameLoop implements GameLoop {

    /*
    We use a set since players need to be unique:
    two players cannot share the same username.
     */
    protected final Set<AbstractPlayer> players;
    protected final List<AbstractPlayer> listPlayers;
    protected HashMap<AbstractPlayer,Card> cardsPlayed;
    private final Random rnd = new Random();

    public AbstractGameLoop() {
        this.players = new HashSet<>();
        this.listPlayers = new ArrayList<>(players);
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
        if (!players.add(player)) return false;
        listPlayers.add(player);
        return true;
    }

    // Removes a player from the game
    public void removePlayer(AbstractPlayer player) {
        players.remove(player);
        listPlayers.remove(player);
    }

    // Returns how many players are participating in the game
    public int getPlayerCount() {
        return players.size();
    }

    public void giveHand(DeckImpl deck) {
        for (IPlayer player : players) {
            player.getHand(deck);
        }
    }

    public void viewAllPlayerHands() {
        for (AbstractPlayer player : listPlayers) {
            System.out.println(player.toStringHand());
        }
    }
}
