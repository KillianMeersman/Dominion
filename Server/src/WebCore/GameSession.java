package WebCore;

import Core.Card;
import Core.Game;
import Core.IEngineInterface;
import Core.Player;
import Core.PlayerPhase;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GameSession implements IEngineInterface {
    private static final int AMOUNT_THREADS = 10;
    private static final ExecutorService threadpool = Executors.newFixedThreadPool(AMOUNT_THREADS);
    
    private String backLog = null;
    private HttpServletRequest response = null;
    
    private final HttpSession httpSession;
    private final Game game;
    private boolean gameRunning = true;
    
    public synchronized void setResponse(HttpServletRequest response) {
        this.response = response;
    }
    
    public String getBackLog() {
        return backLog;
    }
    
    public HttpSession getHttpSession() {
        return httpSession;
    }
    
    public Game getGame() {
        return game;
    }
    
    public GameSession(HttpSession httpSession, Game game) {
        this.httpSession = httpSession;
        this.game = game;
    }

    private HttpServletRequest emptyResponse() {
        HttpServletRequest r = response;
        response = null;
        return r;
    }
    
    private void setBackLog(String backlog) {
        this.backLog = backLog;
        waitForResponse();
    }
    
    private void waitForResponse() {
        while (response == null) {
            
        }
    }
    
    private String getHandString() {
        String out = "[";
        ArrayList<Card> hand = game.getActivePlayer().getHand();
        for (int i = 0; i < hand.size() - 1; i++) {
            out += hand.get(i).getId() + ",";
        }
        out += hand.get(hand.size() - 1).getId() + "]";
        return out;
    }
    
    private void gameLoop() {
        while (gameRunning) {
            for (Player player : game.getPlayers()) {
                setBackLog("player=" + player.getId() + "&phase=" + player.getPhase().toString() + "&hand=" + getHandString());
                while (player.getPhase() == PlayerPhase.PHASE_BUY) {
                    setBackLog();
                }
            }
        }
    }
    
    @Override
    public Card promptPlayerCards(Game game, String prompt, Card[] cards, int minAmount, int maxAmount, boolean canExit, String visual) {
        backLog = "action=promptCards&parameters=" + prompt + ",[";
        for (Card card : cards) {
            backLog += card.getId();
        }
        backLog += "]," + "," + minAmount + "," + maxAmount + "," + canExit;
        waitForResponse();
        
        return Core.CardRepository.getInstance().getCardById(
                Integer.parseInt(emptyResponse().getParameter("cardId")));
    }

    @Override
    public Player promptPlayerPlayer(Game game, String prompt, Player[] players, boolean canExit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messagePlayer(Game game, String message) {
        backLog = "action=message&parameters=[" + message + "]";
        waitForResponse();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
