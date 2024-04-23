package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.game.AbstractGameLoop;

import java.util.Scanner;

public class LogicGame extends AbstractGameLoop {

    DeckImpl mazzo;

    public LogicGame() {
        this.mazzo = new DeckImpl();
    }

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
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome p1: ");
        String input = scanner.nextLine();
        players.add(new Player(input));
        input = scanner.nextLine();
        players.add(new Player(input));

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
