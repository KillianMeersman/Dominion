package Engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class EngineController {
	private EngineController() {};
	
	protected static List<Game> games = new ArrayList<Game>();
	public static boolean running = true;
	
	// Methods
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("-- Dominion Engine --");
		
		while (running) {
			System.out.print("> ");
			ConsoleController.processInput(in.nextLine());
		}
		System.out.println("Bye bye");
		in.close();
	}
}