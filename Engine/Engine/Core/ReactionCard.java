package Core;


class ReactionCard extends ActionCard {
    
    protected ReactionCard(int id, int amount, int cost, String name, String description) {
        super(id, amount, cost, name, description);
    }
    
    protected ReactionCard(int id, int amount, int cost, String name, String description, int startingAmount) {
        super(id, amount, cost, name, description, startingAmount);
    }
    
    public void initmoat() {
        cardMode = ActionCardMode.MODE_ACTION;
        actionTitle = "Discard any number of cards; +1 card per discard";
        actionMessages = new String[1];
        actionMessages[0] = "To discard >";
    }

    public static void moat(Player player) {

    }
}