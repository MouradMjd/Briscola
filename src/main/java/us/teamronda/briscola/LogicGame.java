package us.teamronda.briscola;

import lombok.Getter;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.cards.ICard;
import us.teamronda.briscola.api.deck.AbstractDeck;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.IPlayer;
import us.teamronda.briscola.gui.AnimationType;
import us.teamronda.briscola.gui.components.CardComponent;
import us.teamronda.briscola.gui.controllers.StartController;
import us.teamronda.briscola.gui.controllers.TableController;
import us.teamronda.briscola.utils.ScoringUtils;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class LogicGame extends AbstractGameLoop {

    @Getter private static final LogicGame instance = new LogicGame();
    @Getter private static  Player p;

    @Getter private final Deck deck;
    private int totalPoints;
    Timer executor = new Timer();

    public LogicGame() {
        this.deck = new Deck();
        this.totalPoints = 0;
    }
    public static void  initp(String name)
    {
        p=new Player(name);
    }

    @Override
    public void start() {
        // Crea il mazzo
        deck.create();

        //take out the briscola
        deck.takeBriscola();
        System.out.println("La briscola è: " + deck.getTrumpCard());

        // Aggiungi il bot
        addPlayer(new Player("bot_lillo", true));
        //player
        addPlayer(p);

        // Inizia un giocatore a caso
        orderPlayers();

        // Dai le carte in mano a tutti
        fillHands(deck);

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
        cardsPlayed.put(player, playedCard);
        TableController.getInstance().getCardsplayed().getChildren().add(new CardComponent(playedCard, AnimationType.NONE));

        // Everyone has played, so let's see who won!
        if (cardsPlayed.size() == getPlayerCount()) {

            ICard winnerCard = null;
            IPlayer winnerPlayer = null;
            try{
                Thread.sleep(3000);
            }catch(Exception e){
                e.printStackTrace();
            }
            for (Map.Entry<IPlayer, ICard> entry : cardsPlayed.entrySet()) {
                ICard card = entry.getValue();
                if (winnerCard == null) {
                    winnerCard = card;
                    winnerPlayer = entry.getKey();
                } else if (winnerCard.getSeed().equals(card.getSeed())) {
                    if (card.getPoints() > winnerCard.getPoints()) {
                        winnerCard = card;
                        winnerPlayer = entry.getKey();
                    }
                } else if (deck.hasTrumpSeed(card)) {
                    // At this point we do not need to check if the
                    // winnerCard is a trump card, because that case
                    // (both trump cards) is handled by the previous condition.
                    winnerCard = card;
                    winnerPlayer = entry.getKey();
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

            // Clear played cards
            cardsPlayed.clear();

            // Make the winner play first next round.
            // (It works because we support only two players)
            orderPlayers(winnerPlayer);

            // If the game is still ongoing
            if (isGameOngoing()) {
                // Clear the table
                TableController.getInstance().clearTable();
                // Make the players draw a card from the deck
                TableController.getInstance().fillPlayerHands(this);

                // Make the bots play again
                this.tickBots();
            } else {
                stop();
            }
        } else if(!player.isBot()) { // If the real player has played
            playerIndex++;
            // make the bots play as well
            this.tickBots();
        } else {
            playerIndex++;
        }
    }

    @Override
    public void stop() {
        // LogicGame#getPlayers returns an immutable list
        List<IPlayer> players = new ArrayList<>(getPlayers());
        Collections.sort(players);

        System.out.println("Classifica:");
        for (int i = 0; i < players.size(); i++) {
            IPlayer player = players.get(i);
            System.out.printf("%d. %s con %d punti!%n", i + 1, player.getUsername(), player.getPoints());
        }
    }

    /**
     * This method directly calls {@link AbstractDeck#getCards()}
     *
     * @return the cards left in the deck
     */
    public List<Card> getRemainingCards() {
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
}
