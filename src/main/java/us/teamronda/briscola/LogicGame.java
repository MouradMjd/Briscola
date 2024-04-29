package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.AbstractPlayer;

import java.util.*;

public class LogicGame extends AbstractGameLoop {
    /**
     * we create the deck to use it in all our methods
     */
    private final DeckImpl deck;
    private final Scanner scanner;

    public LogicGame() {
        this.deck = new DeckImpl();
        this.scanner = new Scanner(System.in);
    }

    @Override
    /**
     * initial part of the game that does not have to be done recursively
     */
    public void start() {
        // Crea il mazzo
        deck.create();
        System.out.print("Numero di giocatori (Max 5): ");
        int nPlayers = scanner.nextInt();

        if (nPlayers>5||nPlayers<0){
            System.out.println("Numero invalido di giocatori");
            System.exit(1);
        }

        if (nPlayers % 2 != 0) {
            deck.popcardformore();
        }

        deck.takeBriscola();
        System.out.println("La briscola è: " + deck.getTrumpCard());

        for (int i = 0; i < nPlayers; i++) {
            System.out.print("Inserisci il nome p" + (i+1) +": ");
            String input = scanner.next();
            addPlayer(new Player(input));
        }
        
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
     * @return
     */
    @Override
    public void tick() {
        for (AbstractPlayer player : players) {
            System.out.printf("Player %s inserisci l'indice della carta:%n", player.getUsername());
            int cardIndex = scanner.nextInt() - 1;

            Card chosenCard = player.pollCard(cardIndex);
            System.out.printf("Hai scelto la carta: %s%n", chosenCard.toString());
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
                otherPlayer.addPoints(cardsPlayed.values());
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
        this.movePlayerToFirstPosition(winnerPlayer);

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
            System.out.printf("%d. %s con %d punti!%n", i, player.getUsername(), player.getPoints());
        }
    }

    /**
     * Method that stops the game by seeing if the deck is empty
     * and if the player have no cards in hand
     */
    @Override
    public boolean isGameOngoing() {
        boolean deckEmpty = deck.isEmpty();
        boolean handsEmpty = players.stream()
                .map(AbstractPlayer::getHand)
                .allMatch(List::isEmpty);

        return !deckEmpty || !handsEmpty;
    }
}
