package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.cards.Card;
import us.teamronda.briscola.api.game.AbstractGameLoop;
import us.teamronda.briscola.api.player.AbstractPlayer;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        System.out.println("La briscola è: " + deck.getTrumpCard());

        // Crea il primo giocatore
        System.out.print("Inserisci il nome p1: ");
        String input = scanner.nextLine();
        addPlayer(new Player(input));

        // Crea il secondo giocatore
        System.out.print("Inserisci il nome p2: ");
        input = scanner.nextLine();
        addPlayer(new Player(input));

        // Dai le carte in mano a tutti
        giveHand(deck);
        viewAllPlayerHands();

        // Inizia un giocatore a caso
        Collections.shuffle(listPlayers);
    }

    /**
     * this method have to deal with all the recursive part of the game logic
     * @return
     */
    @Override
    public void tick() {

        for (AbstractPlayer player : listPlayers) {
            System.out.printf("Player %s inserisci l'indice della carta:%n", player.getUsername());
            int cardIndex = scanner.nextInt()-1;
            Card Cartchosefirstplayer = player.pollCard(cardIndex);
            System.out.printf("Hai scelto la carta: %s%n", Cartchosefirstplayer.toString());
            cardsPlayed.put(player,Cartchosefirstplayer);
        }



        for (int i = 0; i < listPlayers.size()-1; i++) {
            if (deck.hasTrumpSeed(cardsPlayed.get(listPlayers.get(i)))&&!deck.hasTrumpSeed(cardsPlayed.get(listPlayers.get(i+1))))
            {
                listPlayers.get(i).addPoints(cardsPlayed.values());
            }
            if (!deck.hasTrumpSeed(cardsPlayed.get(listPlayers.get(i)))&&deck.hasTrumpSeed(cardsPlayed.get(listPlayers.get(i+1))))
            {
                listPlayers.get(i+1).addPoints(cardsPlayed.values());
            }
        }

        listPlayers.forEach(player -> System.out.printf("Il giocatore %s ha %d punti.%n",
                player.getUsername(),
                player.getPoints()));

        cardsPlayed.clear();
    }

    @Override
    public void stop() {
        // TODO: show the ranking
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

    public int getPointsOnTheTable() {
        return cardsPlayed.values()
                .stream()
                .mapToInt(Card::getPoints)
                .sum();
    }
}
