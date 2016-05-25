package Core;

import java.util.ArrayList;

public class Supply {

    protected static final String[] defaultActionSet = {"cellar", "market", "militia", "mine", "moat", "remodel", "smithy", "village", "woodcutter", "workshop"};
    protected static final int ACTIONSETS = 10;            // How many sets of action cards per game
    protected static final int ACTIONSETAMOUNT = 25;       // How many cards per set

    protected ArrayList<Card> treasureCards = new ArrayList<>();
    protected int[] treasureAmount;
    protected ArrayList<Card> victoryCards = new ArrayList<>();
    protected int[] victoryAmount;
    protected ArrayList<Card> actionSets = new ArrayList<>();
    protected int[] actionAmount;
    protected ArrayList<Card> trash = new ArrayList<>();
    
    public int getCardAmount(Card target) {
        int out = 0;
        for (Card card : getAllCards()) {
            if (card == target) {
                out += 1;
            }
        }
        return out;
    }
    
    public void reduceAmount(Card card) {
        if (card instanceof ActionCard) {
            actionAmount[actionSets.indexOf(card)] -= 1;
        }
        else if (card instanceof TreasureCard) {
            treasureAmount[treasureCards.indexOf(card)] -= 1;
        }
        else if (card instanceof VictoryCard) {
            victoryAmount[victoryCards.indexOf(card)] -= 1;
        }
    }
    
    public ArrayList<Card> getAllCards() {
        ArrayList<Card> out = new ArrayList<>();
        for (int i = 0; i < treasureCards.size(); i++) {
            for (int i2 = 0; i2 < treasureAmount[i]; i2++)
            out.add(treasureCards.get(i));
        }
        for (int i = 0; i < victoryCards.size(); i++) {
            for (int i2 = 0; i2 < victoryAmount[i]; i2++)
            out.add(victoryCards.get(i));
        }
        for (int i = 0; i < actionSets.size(); i++) {
            for (int i2 = 0; i2 < actionAmount[i]; i2++)
            out.add(actionSets.get(i));
        }
        return out;
    }

    public ArrayList<Card> getAllCardsUnique() {
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : treasureCards) {
            out.add(card);
        }
        for (Card card : victoryCards) {
            out.add(card);
        }
        for (Card card : actionSets) {
            out.add(card);
        }
        return out;
    }

    public ArrayList<Card> getTreasureCards() {
        return treasureCards;
    }

    public ArrayList<Card> getVictoryCards() {
        return victoryCards;
    }

    public ArrayList<Card> getActionCards() {
        return actionSets;
    }

    protected Supply(byte playerAmount) {   // Construct with default deck
        ActionCard[] set = new ActionCard[ACTIONSETS];
        for (int i = 0; i < ACTIONSETS; i++) {
            set[i] = (ActionCard) CardRepository.getInstance().getCardByName(defaultActionSet[i]);
        }
        setActionSets(set);
        initTreasureCards(playerAmount);
        initVictoryCards(playerAmount);
    }

    protected Supply(byte playerAmount, ActionCard[] actionDeck) throws Exception {
        if (actionDeck.length > ACTIONSETS) {
            throw new Exception("too many actionCards");
        }
        setActionSets(actionDeck);
        initTreasureCards(playerAmount);
        initVictoryCards(playerAmount);
    }

    private void setActionSets(ActionCard[] actionDeck) {
        actionAmount = new int[actionDeck.length];
        for (int i = 0; i < actionDeck.length; i++) {
            actionSets.add(i, actionDeck[i]);
            actionAmount[i] = ACTIONSETAMOUNT;
        }
    }

    private void initVictoryCards(byte playerAmount) {
        int amount = playerAmount > 2 ? 12 : 8;
        ArrayList<VictoryCard> cards= CardRepository.getInstance().getVictoryCards();
        for (int i = 0; i < cards.size(); i++) {
            victoryCards.add(i, cards.get(i));
        }
        victoryAmount = new int[victoryCards.size()];
        for (int i = 0; i < cards.size(); i++) {
            victoryAmount[i] = amount;
        }
    }

    private void initTreasureCards(byte playerAmount) {
        ArrayList<TreasureCard> cards = CardRepository.getInstance().getTreasureCards();
        for (int i = 0; i < cards.size(); i++) {
            treasureCards.add(i, cards.get(i));
        }
        treasureAmount = new int[treasureCards.size()];
        for (int i = 0; i < cards.size(); i++) {
            treasureAmount[i] = (cards.get(i).getAmount() - (cards.get(i).getStartingAmount() * playerAmount));
        }
    }
}