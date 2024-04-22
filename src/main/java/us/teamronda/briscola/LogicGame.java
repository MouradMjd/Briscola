package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;
import us.teamronda.briscola.api.game.AbstractGameLoop;

import java.util.Scanner;

public class LogicGame extends AbstractGameLoop {

    public void Game() {
        DeckImpl mazzo = new DeckImpl();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il nome p1: ");
        String input = scanner.nextLine();
        Player p1 = new Player(input);
        input = scanner.nextLine();
        Player p2 = new Player(input);
        scanner.close();
        while (true) {

        }
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void tick() {

    }
}
