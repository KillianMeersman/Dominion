package Core;

import Core.Card;
import java.util.ArrayList;

public class EngineInstance {
    private Game game;
    private static boolean started = false;
    
    public void init(String[] playerNames) {
        game = new Game(playerNames);
    }
    
    public void init(String[] playerNames, int[] actionDeck) {
        ActionCard[] aCards = new ActionCard[actionDeck.length];
        CardRepository.getInstance().getCardsById(actionDeck).toArray(aCards);
        
        game = new Game(playerNames, aCards);
    }
    
    public String[] getCardStrings(String playerName, PlayerPlace source) {
        Player player = game.getPlayer(playerName);
        ArrayList<Card> cards = player.resolvePlayerPlace(source);
        String[] out = new String[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            out[i] = cards.get(i).toString();
        }
        return out;
    }
    
    /**
     * Initializes the card database, needs to be run only once
     */
    public static void startEngine() {
        if (!started) {
            
        }
    }
}