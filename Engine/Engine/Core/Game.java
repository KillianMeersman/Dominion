package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
class Game {
	private GamePhase gamePhase = null;
	private List<Player> players = new ArrayList<Player>();
	private Instant beginTime = null;
	//public Supply supply = new Supply();
	
	// Getters & Setters
	public List<Player> getPlayers() {
		return players;
	}
        
        public Player getPlayer(String playerName) {
            for (int i = 0; i < players.size(); i++) {
                if (players.get(i).name == playerName) {
                    return players.get(i);
                }
            }
            return null;
        }
        
	@Override
 	public String toString() {
		String output = "[" + players.get(0).name;
		for (int i = 1; i < players.size(); i++) {
			output += ", " + players.get(i).name;
		}
		output += "] " + getDuration().toMinutes() + " minutes";
		return output;
	}
	
	public Duration getDuration() {
		return Duration.between(beginTime, Instant.now());
	}
	
	public Game(String[] playerNames) {
            for (int i = 0; i < playerNames.length; i++) {
                    Player player = new Player(playerNames[i]);
                    players.add(player);
            }
            this.gamePhase = GamePhase.ACTION_PHASE;
            this.beginTime = Instant.now();
	}

	public void init() {
            for (int i = 0; i < players.size(); i++) {
                    players.get(i);
            }
	}
}

enum GamePhase {
	ACTION_PHASE,
	BUY_PHASE,
	CLEANUP_PHASE
}