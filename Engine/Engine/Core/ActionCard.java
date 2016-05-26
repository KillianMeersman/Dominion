package Core;

import java.util.ArrayList;

class ActionCard extends Card {

    private java.lang.reflect.Method method;
    boolean canEnd = false;
    

    protected ActionCard(int id, int amount, int cost, String name, String description) {
        super(id, amount, cost, name, description);
        init();
    }

    protected ActionCard(int id, int amount, int cost, String name, String description, int startAmount) {
        super(id, amount, cost, name, description, startAmount);
        init();
    }
    
    private void init() {
        try {
            method = this.getClass().getMethod(getName(), Game.class, Player.class);
        } catch (NoSuchMethodException e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - no method");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - general reflection error");
        }
    }

    protected void execute(Game game, Player player) {
        try {
            player.inActionMode = true;
            method.invoke(this, game, player);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to execute card method: " + this.getName());
        }

    }
    
    // Card-specific functions
    
    public static void cellar(Game game, Player player) { //Revision
        while(player.inActionMode) {
            Card.transferCard(game.view.promptPlayerCards(game, "Which card do you wish to discard?", Card.listToArray(player.hand), 1, player.getHand().size(), true, "none")
                    , player.hand, player.discard, true, true);
            player.drawFromDeck(1);
        }
    }
    
    public static void chapel(Game game, Player player) {
        int i = 0;
        while (i < 5) {
            Card[] cards = game.view.promptPlayerCards(game, "Which card do you wish to trash?", Card.listToArray(player.hand), 1, 4, true, "none");
            Card.transferCard(cards, player.hand, game.getSupply().trash, true, true);
            i += cards.length;
        }
    }
    
    public static void moat(Player player) {
        player.drawFromDeck(2);
        //TODO  When another player plays an Attack card, you may reveal this from your hand. 
        //      If you do, you are unaffected by that Attack
    }
    
    public static void chancellor(Game game, Player player) {
        player.addCoins(2);
        if (game.view.promptPlayerBoolean(game, "Would you like to put your deck in the discard pile?", "yes", "no")){
            Card.transferCards(player.deck, player.discard, true);
        }
        //TODO  REVISE
    }
    
    public static void village(Player player) {
        player.drawFromDeck(1);
        player.addAction(2);
    }
    
    public static void woodcutter(Player player){
        player.addBuy(1);
        player.addCoins(2);
    }
    
    public static void workshop(Player player, Game game){
        ArrayList<Card> promptCards = new ArrayList<>();
        for (Card card : game.getSupply().getAllCardsUnique()){
            if(card.getCost() <= 4){
            promptCards.add(card);
            }
        }
        Card[] cards = game.view.promptPlayerCards(game,("You can gain a card up to " + 4 + " coins."), Card.listToArray(promptCards), 1, 1, false, "none");
        //TODO  Gain a card costing up to 4 Coins.
    }
    
    public static void bureaucrat(){
        //TODO  Gain a silver card; put it on top of your deck. 
        //      Each other player reveals a Victory card from his hand and puts it on his deck 
        //      (or reveals a hand with no Victory cards).
    }
    
    public static void feast(){
        //TODO  Trash this card. Gain a card costing up to 5 Coins.
    }
    
    public static void gardens(){
        //TODO  Worth 1 Victory for every 10 cards in your deck (rounded down).
    }
    
    public static void militia(Player player, Game game){  //REVISE
        player.addCoins(2);
        for (Player otherplayer : game.getPlayers()){   // Loop om elke andere speler te overlopen
            if (otherplayer.equals(player)){}           // Checked of de andere speler niet de speler is die de kaart heeft gelegd
            else{
                while(otherplayer.hand.size()<3){       // Loop zolang de hand meer als 3 kaarten heeft
                    Card.transferCard(otherplayer.hand.get(otherplayer.hand.size() - 1), otherplayer.hand, otherplayer.discard, true, true);   ;
                }   // ^ neemt telkens de laatste kaart van de hand naar discard
            }
        }
    }
    
    public static void moneylender(Player player, Game game){
        if (player.hand.contains(CardRepository.getInstance().getCardByName("copper"))){
            if (game.view.promptPlayerBoolean(game, "Would you like to trash a copper?", "yes", "no")){
                Card.transferCard(CardRepository.getInstance().getCardByName("copper"), player.hand, game.getSupply().trash, true, true);
                player.addCoins(3);
            }
        }
            
        //TODO  Revision
    }
    
    public static void remodel(Player player, Game game){
        if (player.hand.size() > 0){
            int newcost = 0;
            Card[] cards = game.view.promptPlayerCards(game, "Which card do you wish to trash?", Card.listToArray(player.hand), 1, 1, false, "none");
            newcost = (cards[0].getCost() + 2);
            Card.transferCard(cards, player.hand, game.getSupply().trash, true, true);
            
            ArrayList<Card> promptCards = new ArrayList<>();
            for (Card card : game.getSupply().getAllCardsUnique()){
                if(card.getCost() <= newcost){
                    promptCards.add(card);
                }
            }
            
            cards = game.view.promptPlayerCards(game,("You can gain a card up to " + newcost + " coins."), Card.listToArray(promptCards), 1, 1, false, "none");
            Card.transferCard(cards, game.getSupply().getAllCards(), player.discard, false, true);
            for (Card card : cards){
                game.getSupply().reduceAmount(card);
            }
        }
        
        //TODO  Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.
    }
    
    public static void smithy(Player player){
        player.drawFromDeck(3);
    }
    
    public static void spy(Player player){
        player.drawFromDeck(1);
        player.addAction(1);
        //TODO  Each player (including you) reveals the top card of his deck and either discards it or puts it back, your choice.                
    }
    
    public static void thief() {
        //TODO  Each other player reveals the top 2 cards of his deck. If they revealed any Treasure cards, they trash one of them that you choose. 
        //      You may gain any or all of these trashed cards. They discard the other revealed cards.
    }
    
    public static void throneroom(){
        //TODO  Choose an Action card in your hand. Play it twice.
    }
    
    public static void councilroom(Player player, Game game){
        player.drawFromDeck(4);
        player.addBuy(1);
        for (Player otherplayer : game.getPlayers()){
            if (otherplayer.equals(player)){
            }else{
                otherplayer.drawFromDeck(1);
            }
        }
        //TODO  Revise
    }
    
    public static void festival(Player player){
        player.addAction(2);
        player.addBuy(1);
        player.addCoins(2);
    }
    
    public static void laboratory(Player player){
        player.drawFromDeck(2);
        player.addAction(1);
    }
    
    public static void library(){
        //TODO  Draw until you have 7 cards in hand. You may set aside any Action cards drawn this way,
        //      as you draw them; discard the set aside cards after you finish drawing.
    }
    
    public static void market(Player player){
        player.drawFromDeck(1);
        player.addAction(1);
        player.addBuy(1);
        player.addCoins(1);
    }
    
    public static void mine(){
        //TODO  Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.
    }
    
    public static void witch(Player player){
        player.drawFromDeck(2);
        //TODO  Each other player gains a Curse card.
    }
    
    public static void adventurer(){
        


        //TODO  Reveal cards from your deck until you reveal 2 Treasure cards. 
        //      Put those Treasure cards into your hand and discard the other revealed cards.
    }
    
}
