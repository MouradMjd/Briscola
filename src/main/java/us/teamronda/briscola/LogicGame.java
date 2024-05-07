package us.teamronda.briscola;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import us.teamronda.briscola.api.Card;
import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.AbstractPlayer;
import us.teamronda.briscola.utils.ScoringUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class LogicGame extends AbstractGameLoop {
    /**
     * we create the deck to use it in all our methods
     */
    private final DeckImpl deck;
    private final Scanner scanner;
    private int totalPoints;
    @FXML
    private VBox table;
    public LogicGame() {
        start();

        this.deck = new DeckImpl();
        this.scanner = new Scanner(System.in);
        this.totalPoints = 0;
    }

    @Override
    /**
     * initial part of the game that does not have to be done recursively
     */
    public void start() {
        // Crea il mazzo
        deck.create();
        //take out the briscola
        deck.takeBriscola();
        System.out.println("La briscola è: " + deck.getTrumpCard());

        System.out.print("Inserisci il nome:");
        String input = scanner.next();
        addPlayer(new Player(input));
        addPlayer(new Player("bot_lillo"));
        
        // Dai le carte in mano a tutti
        giveHand(deck);
        viewAllPlayerHands();

        // Inizia un giocatore a caso
        Collections.shuffle(players);
    }

    /**
     * this method have to deal with all the recursive part of the game logic for 2 players
     */
    //@Override
    public void tick2() {
        for (AbstractPlayer player : players) {
            System.out.printf("Player %s inserisci l'indice della carta:%n", player.getUsername());
            int cardIndex = scanner.nextInt()-1;
            Card Cartchosefirstplayer = player.pollCard(cardIndex);
            System.out.printf("Hai scelto la carta: %s%n", Cartchosefirstplayer.toString());
            cardsPlayed.put(player,Cartchosefirstplayer);
        }

        /**
         * for loop for the logic of the cards played
          */
        for (int i = 0; i < players.size()-1; i++) {
            /**
             * 1 case where the first card played is a Trump and the second card is not
             */
            if (deck.hasTrumpSeed(cardsPlayed.get(players.get(i)))&&!deck.hasTrumpSeed(cardsPlayed.get(players.get(i+1))))
            {
                players.get(i).addPoints(cardsPlayed.values());
            }
            /**
             * 2 case where the second card played is a Trump and the first card is not
             */
            if (!deck.hasTrumpSeed(cardsPlayed.get(players.get(i)))&&deck.hasTrumpSeed(cardsPlayed.get(players.get(i+1))))
            {
                players.get(i+1).addPoints(cardsPlayed.values());
            }
            /**
             * 3 case where the first card played is not Trump and the second card is not a Trump
             */
            if (!deck.hasTrumpSeed(cardsPlayed.get(players.get(i)))&&!deck.hasTrumpSeed(cardsPlayed.get(players.get(i+1))))
            {
                if(cardsPlayed.get(players.get(i)).seed()==cardsPlayed.get(players.get(i+1)).seed())
                {
                    if (cardsPlayed.get(players.get(i)).getPoints() >cardsPlayed.get(players.get(i+1)).getPoints())
                    {
                        players.get(i).addPoints(cardsPlayed.values());
                    }
                    else
                    {
                        players.get(i+1).addPoints(cardsPlayed.values());
                    }
                }
                else
                {
                    players.get(i).addPoints(cardsPlayed.values());
                }

            }
            /**
             * 4 case where the first card played is a Trump and the second card is a Trump
             */
            if (deck.hasTrumpSeed(cardsPlayed.get(players.get(i)))&&deck.hasTrumpSeed(cardsPlayed.get(players.get(i+1))))
            {
                if (cardsPlayed.get(players.get(i)).getPoints() > cardsPlayed.get(players.get(i+1)).getPoints())
                {
                    players.get(i).addPoints(cardsPlayed.values());
                }
                else
                {
                    players.get(i+1).addPoints(cardsPlayed.values());
                }
            }
        }

        players.forEach(player -> System.out.printf("Il giocatore %s ha %d punti.%n",
                player.getUsername(),
                player.getPoints()));

        cardsPlayed.clear();
    }

    /**
     * this method have to deal with all the recursive part of the game logic for more players
     */
    @Override
    public void tick() {
        for (AbstractPlayer player : players) {
            Card chosenCard;
            if(player.getUsername().equalsIgnoreCase("bot_lillo"))
            {
                //how the bot plays (he's literally a bot)
                Random x = new Random();
                chosenCard = player.pollCard(x.nextInt(0, player.getHand().size()));
            }
            else
            {
                //how the real person plays
                System.out.printf("Player %s inserisci l'indice della carta: ", player.getUsername());
                int cardIndex = scanner.nextInt() - 1;
                chosenCard = player.pollCard(cardIndex);
            }

            System.out.printf("%s ha scelto la carta: %s%n", player.getUsername(), chosenCard.toString());
            cardsPlayed.put(player,chosenCard);
        }

        Card winnerCard = null;
        AbstractPlayer winnerPlayer = null;

        for (Map.Entry<AbstractPlayer, Card> entry : cardsPlayed.entrySet()) {
            Card card = entry.getValue();
            if (winnerCard == null) {
                winnerCard = card;
                winnerPlayer = entry.getKey();
            } else if (winnerCard.seed().equals(card.seed())) {
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

        // Update the points
        for (AbstractPlayer otherPlayer : players) {
            if (otherPlayer.equals(winnerPlayer)) {
                totalPoints += otherPlayer.addPoints(cardsPlayed.values());
                break; // Just stop right here, since we already updated the winner's points
            }
        }

        // Print current scores
        players.forEach(player -> System.out.printf("Il giocatore %s ha %d punti.%n",
                player.getUsername(),
                player.getPoints()));

        // Clear played cards
        cardsPlayed.clear();

        // Make the winner play first next round.
        // (It works because we support only two players)
        players.remove(winnerPlayer);
        players.addFirst(winnerPlayer);

        // Make the players draw a card from the deck
        for (AbstractPlayer player : players) {
            player.addCard(deck.popCard());
        }
    }

    @Override
    public void stop() {
        Collections.sort(players);
        System.out.println("Classifica:");
        for (int i = 0; i < players.size(); i++) {
            AbstractPlayer player = players.get(i);
            System.out.printf("%d. %s con %d punti!%n", i + 1, player.getUsername(), player.getPoints());
        }
    }

    /**
     * Method that stops the game by seeing if the deck is empty
     * and if the player have no cards in hand
     */
    @Override
    public boolean isGameOngoing() {
        return totalPoints != ScoringUtils.MAX_POINTS;
    }
}
