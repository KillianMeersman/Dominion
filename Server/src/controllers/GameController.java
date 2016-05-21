package controllers;

import Core.Card;
import Core.Game;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Core.IEngineInterface;
import Core.Player;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GameController
 */
@WebServlet("/GameController")
public class GameController extends HttpServlet implements IEngineInterface {
    
    private int currentId = 0;
    private ArrayList<GameSession> gameSessions = new ArrayList<>();
    
    private synchronized int newId() {
        return ++currentId;
    }
    
    private synchronized void addSession(GameSession session) {
        gameSessions.add(session);
    }
    
    private GameSession getSessionByGame(Game game) {
        for (GameSession session : gameSessions) {
            if (session.getGame() == game) {
                return session;
            }
        }
        return null;
    }
    
    private GameSession getSessionBySession(HttpSession target) {
        for (GameSession session : gameSessions) {
            if (session.getHttpSession() == target) {
                return session;
            }
        }
        return null;
    }
        
    /**
     * 
     * Takes raw client input and converts it to a game instance
     */
    private void newGame(HttpSession httpSession, String[] playerNames, String[] deck) {
        Card[] cards = new Card[deck.length];
        for (int i = 0; i < deck.length; i++) {
            cards[i] = Core.CardRepository.getInstance().getCardById(Integer.parseInt(deck[i]));
        }
        Game game = new Game(this, playerNames, cards);
        addSession(new GameSession(httpSession, game));
    }
    
    
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameController() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (request.getParameter("action")) {
            case "getCards":
                for (Card card : Core.CardRepository.getInstance().getAllCards()) {
                    
                }
            case "new":
                request.getSession(true);
                newGame(request.getSession(), request.getParameterValues("playernames"), request.getParameterValues("deck")); // new game
                break;
            case "play":
                Game game = getSessionBySession(request.getSession()).getGame();
            try {
                game.playActionCard(Core.CardRepository.getInstance().getCardById(Integer.parseInt(request.getParameter("cardId"))));
                while (getSessionBySession(request.getSession()).backlog == null) {
                    
                }
            } catch (Exception e) {
                // kak
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

    @Override
    public Card promptPlayerCards(Game game, String prompt, Card[] cards, boolean canExit) {
        getSessionByGame(game).setBacklog();
    }

    @Override
    public Player promptPlayerPlayer(Game game, String prompt, Player[] players, boolean canExit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messagePlayer(Game game, String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
