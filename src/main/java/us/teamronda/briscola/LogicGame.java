package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.game.AbstractGameLoop;

import java.util.Scanner;

public class LogicGame extends AbstractGameLoop {

    public void Game() {
      start();
      while (true)
      {
          boolean ok=tick();
          if (!ok)
          {
              break;
          }
      }
      stop();
    }

    @Override
    public void start() {
        DeckImpl mazzo = new DeckImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome p1: ");
        String input = scanner.nextLine();
        Player p1 = new Player(input);
        input = scanner.nextLine();
        Player p2 = new Player(input);
        scanner.close();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean tick() {

        return false;
    }
}
