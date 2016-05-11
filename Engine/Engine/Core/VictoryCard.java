package Core;

public class VictoryCard extends Card {
    private final int value;

    public VictoryCard(int id, int amount, int cost, String name, String description, int value) {
        super(id, amount, cost, name, description);
        this.value = value;
    }
    
    public VictoryCard(int id, int amount, int cost, String name, String description, int startAmount, int value) {
        super(id, amount, cost, name, description, startAmount);
        this.value = value;
    }
}
