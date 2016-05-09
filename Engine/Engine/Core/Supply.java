package Core;

import java.util.ArrayList;

class Supply { 
    public static final int COPPER_START_AMOUNT = 7;    // How many copper cards does each player start with
    public static final int ESTATE_START_AMOUNT = 3;    // How many estate cards does each player start with
    public static final int ACTIONSETS = 10;            // How many sets of action cards per game
    public static final int ACTIONSETAMOUNT = 25;       // How many cards per set
    
    private ArrayList<ArrayList<TreasureCard>> treasureCards = new ArrayList<>();
    private ArrayList<ArrayList<VictoryCard>> victoryCards = new ArrayList<>();
    private ArrayList<ArrayList<ActionCard>> actionSets = new ArrayList<>();
    private ArrayList<ActionCard> curseCards = new ArrayList<>();
    private ArrayList<Card> trash = new ArrayList<>();
    
    public Supply() {   // Construct with default deck
        
    }
    
    public Supply(ActionCard[] actionDeck) throws Exception {
        if (actionDeck.length > ACTIONSETS) { throw new Exception("too many actionCards"); }
        for (ActionCard card : actionDeck) {
            ArrayList<ActionCard> set = new ArrayList<>();
            for (int i = 0; i < ACTIONSETAMOUNT; i++) {
                set.add(card);
            }
            actionSets.add(set);
        }
    }
}