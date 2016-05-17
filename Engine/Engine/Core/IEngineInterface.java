package Core;

public interface IEngineInterface {
    public String promptPlayer(String message);
    public void messagePlayer(String message);
    public void displayCards(Card[] cards);
}
