package Core;

public interface IEngineInterface {
    public Card promptPlayerCards(String prompt, Card[] cards, boolean canExit);
    public Player promptPlayerPlayer(String prompt, Player[] players, boolean canExit) ;
    public void messagePlayer(String message);
}
