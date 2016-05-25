package WebCore;

import Core.Card;
import Core.Game;
import Core.IEngineInterface;
import Core.Player;
import Core.PlayerPhase;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.catalina.tribes.util.Arrays;

public class GameSession implements IEngineInterface, Runnable {

    private int id;
    private Thread thread;

    private String backLog = null;
    private HttpServletRequest response = null;

    private final HttpSession httpSession;
    private final Game game;

    public int getId() {
        return id;
    }

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

    public GameSession(int id, HttpSession httpSession, String[] playerNames, Card[] deck) {
        this.id = id;
        this.httpSession = httpSession;
        this.game = new Game(this, playerNames, deck);
    }

    private HttpServletRequest emptyResponse() {
        HttpServletRequest r = response;
        response = null;
        return r;
    }

    private void setBackLog(String backlog) {
        this.backLog = backlog;
        waitForResponse();
    }

    private void waitForResponse() {
        int i = 0;
        while (response == null & i < 1000) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
            i++;
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
    
    private String getSupplyString() {
        String[] cardId = new String[game.getSupply().getAllCardsUnique().size()];
        String[] cardAmount = new String[cardId.length];
        Card[] buyableCards = game.getBuyableCards();
        for (int i = 0; i < buyableCards.length; i++) {
            cardId[i] = String.valueOf(buyableCards[i].getId());
        }
        for (int i = 0; i < buyableCards.length; i++) {
            cardAmount[i] = String.valueOf(game.getSupply().getCardAmount(buyableCards[i]));
        }
        return "&supply=" + Arrays.toString(cardId) + "&supplyAmount=" + Arrays.toString(cardAmount);
    }

    private void gameLoop() {
        while (game.isRunning()) {
            Player player = game.getActivePlayer();
            setBackLog("action=player&player=" + player.getName());
            while (player.getPhase() == PlayerPhase.PHASE_BUY && game.isRunning()) {
                setBackLog("action=buy&parameters=" + getParameterString() + "&hand=" + player.getHand().toString() + getSupplyString());
                try {
                    game.buy(Core.CardRepository.getInstance().getCardById(id));
                } catch (Exception ex) {
                    Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            while (player.getPhase() == PlayerPhase.PHASE_ACTION && game.isRunning()) {
                setBackLog("action=action&parameters=[" + getParameterString() + "&hand=" + getListString(game.getActivePlayer().getHand()) + "]");
                try {
                    game.playActionCard(Core.CardRepository.getInstance().getCardById(Integer.parseInt(response.getParameter("cardId"))));
                } catch (Exception ex) {
                    Logger.getLogger(GameSession.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (player.getPhase() == PlayerPhase.PHASE_CLEANUP && game.isRunning()) {
                game.cleanup();
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

    @Override
    public boolean promptPlayerBoolean(Game game, String prompt, String yes, String no) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        gameLoop();
    }

    public void start() {
        if (thread == null) {
            thread = new Thread(this, Integer.toString(id));
            thread.start();
        }
    }
}
