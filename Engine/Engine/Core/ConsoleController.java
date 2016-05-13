package Core;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleController {

    private static List<Game> games = new ArrayList<Game>();
    private static boolean running = true;
    
    private static void processInput(String input) {
            switch(input.toLowerCase()) {
            case "games":
                    listGames();
                    break;
            case "help":
                    System.out.println("Commands: games, exit");
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
    
    protected static void addGame(Game game) {
        games.add(game);
    }
    
    /*
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (running) {
            processInput(in.next());
        }
    }
*/
}
