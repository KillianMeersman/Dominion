package Engine;
import java.util.ArrayList;
import java.util.List;

// Represents an active game - base class
public class Game {
	private GamePhase gamePhase = null;
	private Player[] players;
	private List<Card> supply = new ArrayList<Card>();
	
	public Player[] getPlayers() {
		return players;
	}
	
	public Game(Player[] players, GamePhase gamePhase) {
		this.players = players;
		this.gamePhase = gamePhase;
	}
}

enum GamePhase {
	BUY_PHASE,
	ACTION_PHASE,
	CLEANUP_PHASE
}