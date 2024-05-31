package us.teamronda.briscola;

import lombok.Getter;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.deck.AbstractDeck;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.Guis;
import us.teamronda.briscola.gui.controllers.RankingController;
import us.teamronda.briscola.gui.controllers.TableController;
import us.teamronda.briscola.objects.Deck;
import us.teamronda.briscola.objects.Player;
import us.teamronda.briscola.utils.ScoringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class LogicGame extends AbstractGameLoop {

    @Getter
    private static final LogicGame instance = new LogicGame();

    @Getter
    private final Deck deck;
    private int totalPoints;
    private int totalCardsPlayed;
    private int ticksNumber;

    public LogicGame() {
        this.deck = new Deck();
        this.totalPoints = 0;
        this.totalCardsPlayed = 0;
        this.ticksNumber = 1;
    }

    @Override
    public void start() {
        // Create the deck
        // and choose the trump card
        deck.create();

        // Add the bot
        addPlayer(new Player("bot_lillo", true));

        // Shuffle play
        shufflePlayers();

        // Distribute cards to all players
        fillHands(deck);

        // Initialize the turn label and start the timer
        TableController.getInstance().updateTurnLabel(ticksNumber);
        TableController.getInstance().startTimer();

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
            // The bots play a random card
            this.tick(currentPlayer, currentPlayer.pollCard(ThreadLocalRandom.current().nextInt(0, currentPlayer.getHand().size())));
        }
    }

    @Override
    public void tick(IPlayer player, ICard playedCard) {
        // Register the played card
        cardsPlayed.put(player.getUsername(), playedCard);

        // Increment the player loops variable
        playerIndex++;
        // Increment the number of cards played
        totalCardsPlayed++;

        // Update the player's hand
        TableController.getInstance().updateHand(player);
        // Update the table
        TableController.getInstance().addPlayedCard(playedCard);

        // Everyone has played, so let's see who won!
        if (cardsPlayed.size() == getPlayerCount()) {
            ICard winnerCard = null;
            IPlayer winnerPlayer = null;

            // We are taking in account player order when checking who won the round
            for (IPlayer listPlayer : getPlayers()) {
                ICard card = cardsPlayed.get(listPlayer.getUsername());
                // This if covers the first iteration of the for loop
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
                        // If the cards are worth the same points, the highest card wins:
                        // we use the ordinal of the CardType enum to evaluate that
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
            int deltaPoints = winnerPlayer.addPoints(cardsPlayed.values());
            //int deltaPoints = updatePoints(winnerPlayer, cardsPlayed.values());
            totalPoints += deltaPoints;
            // Increment turn number
            ticksNumber++;

            // Update the points on the GUI
            if (winnerPlayer.isBot()) {
                TableController.getInstance().updatePointsLabel(winnerPlayer.getPoints(), 0);
            } else {
                TableController.getInstance().updatePointsLabel(0, winnerPlayer.getPoints());
            }

            // Clear played cards
            cardsPlayed.clear();

            // Make the winner play first next round.
            // (It works because we support only two players)
            orderPlayers(winnerPlayer);

            // Make the players draw a card from the deck
            fillHands(deck);

            // Wait for the player to click the button
            TableController.getInstance().showDeltaPoints(!winnerPlayer.isBot(), deltaPoints);
            TableController.getInstance().setNextButtonVisibility(true);
        } else {
            // If the human has already played
            // check if other bots need to play as well
            if (!player.isBot()) {
                this.tickBots();
            }
        }
    }

    /**
     * Update the gui and start another turn.
     */
    public void nextTurn() {
        // The players already drew cards (look at the tick() method)
        // and were ordered correctly already.
        // So let's just update every element in the gui.
        TableController controller = TableController.getInstance();
        // Clear the table
        controller.clearTable();
        // Update the turn label
        controller.updateTurnLabel(ticksNumber);

        // Update the players' hands
        getPlayers().forEach(controller::updateHand);
        // Update the deck
        controller.popDeckCards(getPlayerCount());
        // Unlock the handBox of the player,
        // which were blocked by the CardComponent#setOnMouseClicked method.
        controller.updateHandStatus(false);

        if (isGameOngoing()) {
            // Make sure we can loop again
            // through all the players
            playerIndex = 0;

            // Make the bots play again
            tickBots();
        } else {
            // If the game is over just stop, duh
            stop();
        }
    }

    @Override
    public void stop() {
        // Stop the timer
        TableController.getInstance().stopTimer();

        // LogicGame#getPlayers returns an immutable list
        List<IPlayer> players = new ArrayList<>(getPlayers());
        Collections.sort(players); // Sort the players

        // Switch to the new gui
        TableController.getInstance().switchTo(Guis.RANKING);
        RankingController.getInstance().putRanking(players);

        // Reset the objects
        cardsPlayed.clear();
        getPlayers().forEach(this::removePlayer);

        // Reset the variables
        totalPoints = 0;
        totalCardsPlayed = 0;
        playerIndex = 0;
        ticksNumber = 1;
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
     * Checks if the sum of the players' points is different from the maximum possible value
     * and if there are other cards that still have to be played.
     *
     * @return true if the game can still continue, false otherwise
     */
    @Override
    public boolean isGameOngoing() {
        // We are using integer variables incremented manually
        // to avoid looping over the players' hands and over the deck every time.
        // Since this method is called every tick it should be pretty fast:
        // but we have only two players, so it does not really matter.
        return totalPoints != ScoringUtils.MAX_POINTS || totalCardsPlayed != deck.getMaxSize();
    }

    public boolean isDraw() {
        for (int i = 0; i < instance.getPlayers().size() - 1; i++) {
            if (instance.getPlayers().get(i).getPoints() != instance.getPlayers().get(i + 1).getPoints()) {
                return false;
            }
        }
        return true;
    }
}
