package Core;

class Card {
    public String name;
    public String description;
    private java.lang.reflect.Method method;
    
    @Override
    public String toString() {
        return this.name + " - " + this.description;
    }
            
    public void execute(String name) {
        try {
            method.invoke(this);
        }
        catch (Exception e) {
            System.out.println("Failed to execute card method: " + name);
        }
       
    }
    
    public Card(String name, String description) {
     Card card = this;
     this.name = name;
     this.description = description;
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
    
    // Specific card functions
    
    private static void cellar(String name, Player player) {
        boolean playerDone = false;
        CardEffect.addAction(player, 1);
        while (!playerDone) {
            
        }
    }
}
