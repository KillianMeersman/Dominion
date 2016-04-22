package Engine;

import static Engine.EngineController.games;

class ConsoleController {
    	public static void processInput(String input) {
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
			EngineController.running = false;
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
        
        private static void newGame(String[] playerNames) {
		Game game = new Game(playerNames);
		games.add(game);
	}
}
