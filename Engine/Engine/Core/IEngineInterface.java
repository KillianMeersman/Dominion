package Core;

public interface IEngineInterface {
    public Card promptPlayerCards(Game game, String prompt, Card[] cards, boolean canExit);
    public Player promptPlayerPlayer(Game game, String prompt, Player[] players, boolean canExit) ;
    public void messagePlayer(Game game, String message);
}
