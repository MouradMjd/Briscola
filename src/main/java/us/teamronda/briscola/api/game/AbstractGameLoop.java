package us.teamronda.briscola.api.game;

import us.teamronda.briscola.Deck;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.player.AbstractPlayer;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.controllers.TableController;

import java.util.*;

public abstract class AbstractGameLoop implements GameLoop {

    // We use a set since players need to be unique:
    // two players cannot share the same username.
    private final List<IPlayer> players;

    protected Map<IPlayer, ICard> cardsPlayed;
    protected int playerIndex;

    public AbstractGameLoop() {
        this.players = new ArrayList<>();
        this.cardsPlayed = new HashMap<>();
        this.playerIndex = 0;
    }

    /**
     * This method returns a boolean for convenience purposes:
     * if false is returned then the username of the player needs to be changed.
     *
     * @param player {@link IPlayer} object to be added
     * @return true if the underlying set of players was modified
     */
    public boolean addPlayer(IPlayer player) {
        if (isUsernameDuplicate(player.getUsername())) return false;

        return players.add(player);
    }

    /**
     * Removes a player from the game
     *
     * @param player {@link IPlayer} to be removed
     */
    public void removePlayer(IPlayer player) {
        players.remove(player);
    }

    @Override
    public IPlayer getWhoIsPlaying() {
        return playerIndex < players.size() ? players.get(playerIndex) : null;
    }

    private boolean isUsernameDuplicate(String username) {
        return players.stream().anyMatch(player -> player.getUsername().equalsIgnoreCase(username));
    }

    /**
     * Update the points of a player using the points
     * from a set of cards
     *
     * @param winner {@link IPlayer} player
     * @param cards A {@link Collection} of {@link ICard}
     * @return the points the cards were worth
     */
    public int updatePoints(IPlayer winner, Collection<ICard> cards) {
        for (IPlayer otherPlayer : players) {
            if (otherPlayer.equals(winner)) {
                return otherPlayer.addPoints(cards);
            }
        }
        return -1;
    }

    /**
     * Gets the players in the game
     *
     * @return An <b>IMMUTABLE</b> List of players
     */
    public List<IPlayer> getPlayers() {
        return List.copyOf(players);
    }

    /**
     * Returns how many players are participating in the game
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Utility method that fills the players' hand until
     * the limit set in {@link AbstractPlayer} is met
     *
     * @param deck The {@link us.teamronda.briscola.api.deck.IDeck} to draw from
     */
    public void fillHands(Deck deck) {
        for (IPlayer player : players) {
            player.fillHand(deck);
        }
    }

    /**
     * Used at game start to select a random order
     */
    public void orderPlayers() {
        Collections.shuffle(players);
    }

    /**
     * Make the winner play and keep the rest of the turn order
     *
     * @param winner The {@link IPlayer} that won the round
     */
    public void orderPlayers(IPlayer winner) {
        players.remove(winner);
        players.addFirst(winner);

        playerIndex = 0;
    }
}
