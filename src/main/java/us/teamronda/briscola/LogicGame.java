package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.game.AbstractGameLoop;

import java.util.Scanner;

public class LogicGame extends AbstractGameLoop {
    /**
     * we create the deck to use it in all our methods
     */
    DeckImpl mazzo;

    public LogicGame() {
        this.mazzo = new DeckImpl();
    }

    /**
     * part of game logic that calls game management methods to handle future changes
     */
    public void Game() {
      start();
      while (true)
      {
          if (stop()) {
              break;
          }
      }
    }

    @Override
    /**
     * initial part of the game that does not have to be done recursively
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome p1: ");
        String input = scanner.nextLine();
        players.add(new Player(input));
        input = scanner.nextLine();
        players.add(new Player(input));
        givehand(mazzo);
        scanner.close();
    }

    /**
     * this method have to deal with all the recursive part of the game logic
     * @return
     */
    @Override
    public boolean tick() {
        return false;
    }
    /**
     *method that stops the game by seeing if the deck ii empty
     */
    @Override
    public boolean stop() {
    return mazzo.isEmpty();
    }

  


}
