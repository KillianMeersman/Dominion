package Core;

public interface IEngineInterface {
    public Card promptPlayerCards(Game game, String prompt, Card[] cards, int minAmount, int maxAmount, boolean canExit, String visual);         // Make player choose between cards
    public Player promptPlayerPlayer(Game game, String prompt, Player[] players, boolean canExit);  // Make player choose between players
    public void messagePlayer(Game game, String message);
}
