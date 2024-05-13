package us.teamronda.briscola;

import lombok.Getter;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.deck.AbstractDeck;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.components.CardComponent;
import us.teamronda.briscola.gui.controllers.StartController;
import us.teamronda.briscola.gui.controllers.TableController;
import us.teamronda.briscola.utils.ScoringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LogicGame extends AbstractGameLoop {

    @Getter private static LogicGame instance = new LogicGame();

    @Getter private final Deck deck;
    private int totalPoints;
    private int turnNumber;

    public LogicGame() {
        this.deck = new Deck();
        this.totalPoints = 0;
        this.turnNumber = 1;
    }

    @Override
    public void start() {
        // Create the deck and shuffle it
        deck.create();

        // Select the trump card
        deck.selectTrumpCard();

        // Add the bot
        addPlayer(new Player("bot_lillo", true));

        // Shuffle play
        orderPlayers();

        // Distribute cards to all players
        fillHands(deck);

        // Initialize the turn label and start the timer
        TableController.getInstance().updateTurnLabel(turnNumber);
        TableController.getInstance().startTimer(System.currentTimeMillis());

        // Start game "loop"
        tickBots();
    }

    /**
     * This method makes the bots play
     * until a real player is found.
     */
    private void tickBots() {
        IPlayer currentPlayer;
        while ((currentPlayer = getWhoIsPlaying()) != null && currentPlayer.isBot()) {
            // Make the player played
            this.tick(currentPlayer, currentPlayer.pollCard(ThreadLocalRandom.current().nextInt(0, currentPlayer.getHand().size())));
        }
    }

    @Override
    public void tick(IPlayer player, ICard playedCard) {
        // Register the played card
        cardsPlayed.put(player, playedCard);

        // Update the player's hand
        TableController.getInstance().updateHand(player);
        // Update the table
        TableController.getInstance().getCardsPlayed().getChildren().add(new CardComponent(playedCard, false));

        // Everyone has played, so let's see who won!
        if (cardsPlayed.size() == getPlayerCount()) {
            ICard winnerCard = null;
            IPlayer winnerPlayer = null;

            // Evaluate correctly the card order
            for (IPlayer listPlayer : getPlayers()) {
                ICard card = cardsPlayed.get(listPlayer);
                if (winnerCard == null) {
                    winnerCard = card;
                    winnerPlayer = listPlayer;
                } else if (winnerCard.getSeed().equals(card.getSeed())) {
                    // If the points are different, the highest wins
                    if (card.getPoints() > winnerCard.getPoints()) {
                        winnerCard = card;
                        winnerPlayer = listPlayer;
                    } else if (card.getPoints() == winnerCard.getPoints() &&
                            card.getType().ordinal() > winnerCard.getType().ordinal()) {
                        // If the points are the same, the highest card wins
                        winnerCard = card;
                        winnerPlayer = listPlayer;
                    }
                } else if (deck.hasTrumpSeed(card)) {
                    // At this point we do not need to check if the
                    // winnerCard is a trump card, because that case
                    // (both trump cards) is handled by the previous condition.
                    winnerCard = card;
                    winnerPlayer = listPlayer;
                }
            }

            // This should never happen!
            if (winnerPlayer == null) {
                System.out.println("Nobody won this round! Something horrible has happened!");
                return;
            }

            // Update the winner's points
            // and add them to the total
            totalPoints += updatePoints(winnerPlayer, cardsPlayed.values());
            // Increment turn number
            turnNumber++;

            // Update the points on the GUI
            if (winnerPlayer.isBot()) {
                TableController.getInstance().updatePointsLabel(winnerPlayer.getPoints(), 0);
            } else {
                TableController.getInstance().updatePointsLabel(0, winnerPlayer.getPoints());
            }

            // Show who won
            TableController.getInstance().winnerPopup(winnerPlayer);

            // Clear played cards
            cardsPlayed.clear();

            // Make the winner play first next round.
            // (It works because we support only two players)
            orderPlayers(winnerPlayer);

            // Make the players draw a card from the deck
            fillHands(deck);

            // If the game is still ongoing
            if (isGameOngoing()) {
                TableController controller = TableController.getInstance();
                // Clear the table
                controller.clearTable();
                // Update the turn label
                controller.updateTurnLabel(turnNumber);

                // Update the player's hands
                getPlayers().forEach(controller::updateHand);
                // Update the deck
                controller.popDeckCards(getPlayerCount());
                // Unblock the handBox of the player
                TableController.getInstance().updateHandStatus(false);

                // Make the bots play again
                this.tickBots();
            } else {
                TableController controller = TableController.getInstance();
                // Clear the table
                controller.clearTable();
                // Update the turn label
                controller.updateTurnLabel(turnNumber);

                // Update the player's hands
                getPlayers().forEach(controller::updateHand);
                // Unblock the handBox of the player
                TableController.getInstance().updateHandStatus(false);
                // Otherwise, just stop the game
                try {
                    stop();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        } else if (!player.isBot()) { // If the real player has played
            playerIndex++;
            // make the bots play as well
            this.tickBots();
        } else {
            playerIndex++;
        }
    }

    @Override
    public void stop() throws IOException {
        // Stop the timer
        TableController.getInstance().stopTimer();

        // LogicGame#getPlayers returns an immutable list
        List<IPlayer> players = new ArrayList<>(getPlayers());
        Collections.sort(players);

        StringBuilder classifica=new StringBuilder();
        System.out.println("Classifica:");
        for (int i = 0; i < players.size(); i++) {
            IPlayer player = players.get(i);
            classifica.append( i + 1+player.getUsername()+"con"+ player.getPoints()+"\n");

        }
        TableController.getInstance().Popup(classifica.toString());

        // Reset the variables
        totalPoints = 0;
        playerIndex = 0;
        turnNumber = 1;
        TableController.getInstance().swichtostart();
    }

    /**
     * This method directly calls {@link AbstractDeck#getCards()}
     *
     * @return the cards left in the deck
     */
    public List<ICard> getRemainingCards() {
        return deck.getCards();
    }

    /**
     * Method that stops the game by seeing if the deck is empty
     * and if the player have no cards in hand
     *
     * @return true if the game can still continue, false otherwise
     */
    @Override
    public boolean isGameOngoing() {
        return totalPoints != ScoringUtils.MAX_POINTS;
    }
    public void reset()
    {
        instance=new LogicGame();
    }
}
