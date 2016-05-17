package Core;

import java.util.ArrayList;

class ActionCard extends Card {

    protected ActionCardMode cardMode;
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
    
    public static void cellar(Game game, Player player) {
        while(player.inActionMode) {
            game.displayCards(player.hand);
            Card.transferCard(game.promptPlayerCard("Which card do you wish to discard?", true), player.hand, player.discard, true, true);
            // player.gain()
        }
        
    }
    
    public static void chapel(Game game, Player player) {
        for (int i = 0; i < 4; i++) {
            Card.transferCard(game.promptPlayerCard("Which card do you wish to trash? >", true), player.hand, game.getSupply().trash, true, true);
        }
    }
}
