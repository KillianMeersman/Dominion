package Engine;

class Card {
    public String name;
    public String description;
    public java.lang.reflect.Method method;
    
    public void execute(String name) {
        try {
            method.invoke(this);
        }
        catch (Exception e) {
            System.out.println("Failed to execute card method: " + name);
        }
       
    }
    
    public void init() {
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
    
    // Lots and lots of functions
    
    private static void cellar(String name, Player player) {
        boolean playerDone = false;
        CardEffect.addAction(player, 1);
        while (!playerDone) {
            
        }
    }
}
