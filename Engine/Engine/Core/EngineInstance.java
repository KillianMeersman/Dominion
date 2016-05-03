package Core;

import Core.Card;

public class EngineInstance {
    private Game game;
    
    public void init(String[] playerNames, int[] actionDeck) {
        
        game = new Game(playerNames, CardRepository.getInstance().getCardsById(actionDeck));
    }
    
    public String[] getActionCards() {
        
    }
    
    public String[] getHandStrings(String playerName) {
        Player player = game.getPlayer(playerName);
        String[] out = new String[player.hand.size()];
        for (int i = 0; i < player.hand.size(); i++) {
            out[i] = player.hand.get(i).toString();
        }
        return out;
    }
}