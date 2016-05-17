package Core;


class ReactionCard extends ActionCard {
    
    protected ReactionCard(int id, int amount, int cost, String name, String description) {
        super(id, amount, cost, name, description);
    }
    
    protected ReactionCard(int id, int amount, int cost, String name, String description, int startingAmount) {
        super(id, amount, cost, name, description, startingAmount);
    }

    public static void moat(Player player) {
        
    }
}