package Console;


import Core.EngineInstance;
import java.util.Scanner;


public class ConsoleGame {
	private int playerAmount;                                       // amount of players
	private String[] playerNames = null;                            // player names
	private boolean gameRunning = true;                             // state variable
        private EngineInstance engine;                                  // Instance of engine

        public void init() {
            engine = new EngineInstance();
            Scanner in = new Scanner(System.in);
            System.out.println("Welcome to dominion");

            System.out.print("Please choose the amount of players: ");
            playerAmount = Integer.parseInt(in.nextLine());
            while (playerAmount < 2 || playerAmount > 6) {
                String message = playerAmount > 6 ? "Please choose less than 6 players: " : "Please choose more than 2 players: ";
                System.out.print(message);
                playerAmount = Integer.parseInt(in.nextLine());
            }

            playerNames = new String[playerAmount];
            for (int i = 0; i < playerAmount; i++) {
                    System.out.print("Player " + (i + 1) + " name: ");
                    playerNames[i] = in.nextLine();
            }
            engine.init(playerNames);
            gameLoop();
        }
        
	private void gameLoop() {
            Scanner in = new Scanner(System.in);
            while (gameRunning) {
                for (int i = 0; i < playerAmount; i++) {
                    System.out.println("\n" + playerNames[i] + "'s turn");
                    System.out.println("------------------------");
                    System.out.println("Cards in hand");
                    for (String card: engine.getHandStrings(playerNames[i])) {
                        System.out.println(card);
                    }
                    in.nextLine();
                }
            }
	}
}
