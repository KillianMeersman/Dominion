package Core;

class ActionCard extends Card {

    protected ActionCardMode cardMode;
    private java.lang.reflect.Method method;
    private java.lang.reflect.Method initMethod;
    protected String[] parameterMessages;
    protected String[] actionMessages;
    protected String actionTitle;

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
            method = this.getClass().getMethod(getName(), new Class[]{Player.class});
        } catch (NoSuchMethodException e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - no method");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - general reflection error");
        }
        
        try {
            initMethod = this.getClass().getMethod("init" + getName());
            initMethod.invoke(this);
        } catch (NoSuchMethodException e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - no init method");
        } catch (Exception e) {
            System.out.println("ERROR: Failed to build card: " + getName() + " - general reflection error");
        }
    }

    protected void execute() {
        try {
            method.invoke(this);
        } catch (Exception e) {
            System.out.println("ERROR: Failed to execute card method: " + this.getName());
        }

    }

    public static void initcellar() {

    }

    public static void cellar(Player player) {
        player.addAction(1);
    }
}
