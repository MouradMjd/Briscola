package us.teamronda.briscola;

import us.teamronda.briscola.api.Player;

import java.util.Scanner;

public class LogicGame {

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
}
