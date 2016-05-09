package Core;

public class ActionCard extends Card {
    private java.lang.reflect.Method method;
    
    public ActionCard(int id, int amount, int cost, String name, String description) {
        super(id, amount, cost, name, description);
        Card card = this;
        try {
            method = card.getClass().getMethod(name);
        }
        catch (NoSuchMethodException e) {
            System.out.println("Failed to build card: " + name + " - no such method");
        }
        catch (Exception e) {
            System.out.println("Failed to build card: " + name + " - general reflection error");
        } 
    }
    
    public void execute(String name) {
        try {
            method.invoke(this);
        }
        catch (Exception e) {
            System.out.println("Failed to execute card method: " + name);
        }
       
    }
    
    private static void cellar(String name, Player player) {
        boolean playerDone = false;
        //CardEffect.addAction(player, 1);
        while (!playerDone) {
            
        }
    }
}
