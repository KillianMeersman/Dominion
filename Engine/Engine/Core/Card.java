package Core;

class Card {
    private int id;
    private final int amount;
    private final int cost;
    private final String name;
    private final String description;
    
    public int getId() {
        return id;
    }
    
    public int getAmount() {
        return amount;
    }
    
    public int getCost() {
        return cost;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return this.name + " - " + this.description;
    }
    
    public Card(int id, int amount, int cost, String name, String description) {
     Card card = this;
     this.id = id;
     this.amount = amount;
     this.cost = cost;
     this.name = name;
     this.description = description;      
    }
}
