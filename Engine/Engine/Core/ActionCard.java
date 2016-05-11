package Core;

class ActionCard extends Card {
    private java.lang.reflect.Method method;
    
    protected ActionCard(int id, int amount, int cost, String name, String description) {
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
    
    protected ActionCard(int id, int amount, int cost, String name, String description, int startAmount) {
        super(id, amount, cost, name, description, startAmount);
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
    
    protected void execute() {
        try {
            method.invoke(this);
        }
        catch (Exception e) {
            System.out.println("Failed to execute card method: " + this.getName());
        }
       
    }
    
    private static void cellar() {
        boolean playerDone = false;
        //CardEffect.addAction(player, 1);
        while (!playerDone) {
            
        }
    }
}
