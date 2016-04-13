package Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EngineController {
	private EngineController() {};
	private EngineController instance = new EngineController();
	
	private static List<Game> games = new ArrayList<Game>();
	private static boolean running = true;
	
	// Methods
	private static void processInput(String input) {
		switch(input) {
		case "games":
			listGames();
			break;
		case "help":
			System.out.println("Commands: games, exit");
			break;
		case "newgame":
			String[] names = {"killian", "seppe", "robin"};
			newGame(names);
			break;
		case "exit":
			running = false;
			break;
		default:
			System.out.println("Unknown command");
		}
	}
	
	private static void listGames() {
		String title = (games.size() == 0) ? "No ongoing games" : games.size() + " ongoing games:";
		System.out.println(title);
		for (int i = 0; i < games.size(); i++) {
			System.out.println(i + " - " + games.get(i).toString());
		}
	}
	
	public EngineController getInstance() {
		return instance;
	}
	
	public static void newGame(String[] playerNames) {
		Game game = new Game(playerNames);
		games.add(game);
	}
	
	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);
		System.out.println("-- Dominion Engine --");
		
		while (running) {
			System.out.print("> ");
			processInput(in.nextLine());
		}
		System.out.print("Bye bye");
		in.close();
	}
}