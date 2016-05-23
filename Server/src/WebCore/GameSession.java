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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public GameSession(HttpSession httpSession, String[] playerNames, Card[] deck) {
        this.httpSession = httpSession;
        this.game = new Game(this, playerNames, deck);
        gameLoop();
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
    
    private String getListString(ArrayList<Card> source) {
        String out = "[";
        for (int i = 0; i < source.size() - 1; i++) {
            out += source.get(i).getId() + ",";
        }
        out += source.get(source.size() - 1).getId() + "]";
        return out;
    }
    
    private String getParameterString() {
        String out = "[";
        Player player = game.getActivePlayer();
        out += player.getCoins();
        out += "," + player.getBuys();
        out += "," + player.getActions() + "]";
        return out;
    }
    
    public void gameLoop() {
        while (gameRunning) {
            for (Player player : game.getPlayers()) {
                setBackLog("action=nextTurn&player=" + player.getId());
                while (player.getPhase() == PlayerPhase.PHASE_BUY) {
                    setBackLog("action=buy&parameters=[" + getParameterString() + "," + getListString(game.getActivePlayer().getHand()) + "]");
                    try {
                        game.buy(Integer.parseInt(response.getParameter("cardId")));
                    } catch (Exception ex) {
                        Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }
                
                while (player.getPhase() == PlayerPhase.PHASE_ACTION) {
                    setBackLog("action=action&parameters=[" + getParameterString() + "," + getListString(game.getActionCards()) + "]");
                    try {
                        game.playActionCard(Core.CardRepository.getInstance().getCardById(Integer.parseInt(response.getParameter("cardId"))));
                    } catch (Exception ex) {
                        Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                if (player.getPhase() == PlayerPhase.PHASE_CLEANUP) {
                    game.cleanup();
                }
            }
        }
    }
    
    @Override
    public Card[] promptPlayerCards(Game game, String prompt, Card[] cards, int minAmount, int maxAmount, boolean canExit, String visual) {
        backLog = "action=promptCards&parameters=" + prompt + ",[";
        for (Card card : cards) {
            backLog += card.getId();
        }
        backLog += "]," + "," + minAmount + "," + maxAmount + "," + canExit;
        waitForResponse();
        String[] in = emptyResponse().getParameterValues("cardId");
        Card[] out = new Card[in.length];
        for (int i = 0; i < in.length; i++) {
            out[i] = Core.CardRepository.getInstance().getCardById(Integer.parseInt(in[i]));
        }
        return out;
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
