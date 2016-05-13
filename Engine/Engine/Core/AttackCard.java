package Core;

class AttackCard extends ActionCard {
    
    protected AttackCard(int id, int amount, int cost, String name, String description) {
        super(id, amount, cost, name, description);
    }
    
    protected AttackCard(int id, int amount, int cost, String name, String description, int startingAmount) {
        super(id, amount, cost, name, description, startingAmount);
    }
    
}
