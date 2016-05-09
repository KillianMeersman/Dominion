package Console;

import Core.EngineInstance;
import Core.PlayerPlace;
import java.util.Scanner;

public class ConsoleGame {
	private boolean gameRunning = true;
        private EngineInstance engine;
        private String[] playerNames = null;

        public void init() {
            int playerAmount;
            
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
            int playerAmount = playerNames.length;
            Scanner in = new Scanner(System.in);
            
            while (gameRunning) {
                for (String playerName : playerNames) {
                    underlineOut("Dominion");
                    underlineOut("Player " + playerName + "'s turn");
                    for (String card: engine.getCardStrings(playerName, PlayerPlace.PLACE_HAND)) {
                        System.out.println(card);
                    }
                    in.nextLine();
                }
            }
	}
        
        private void underlineOut(String message) {
            System.out.println("\n" + message);
            for (int i = 0; i < message.length(); i++) {
                System.out.print("-");
            }
        }
}
