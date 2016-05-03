package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
class Game {
    private List<Player> players = new ArrayList<Player>();
    private Instant beginTime = null;
    private int turn = 0;
    private boolean gameRunning = true;
    private Supply supply;

    public List<Player> getPlayers() {
            return players;
    }

    public Player getPlayer(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getName().equals(playerName)) {
                return players.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
            String output = "[" + players.get(0).getName();
            for (Player player : players) {
                    output += ", " + player.getName();
            }
            output += "] " + getDuration().toMinutes() + " minutes";
            return output;
    }

    public Duration getDuration() {
            return Duration.between(beginTime, Instant.now());
    }

    public Game(String[] playerNames, List<Card> actionDeck) {
        for (String name : playerNames) {
                Player player = new Player(name);
                players.add(player);
        }
        supply = new Supply(actionDeck);
        
        this.beginTime = Instant.now();
    }
}