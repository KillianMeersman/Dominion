package WebCore;

import Core.Card;
import Core.Game;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GameController
 */
@WebServlet("/GameServlet")
public class GameServlet extends HttpServlet {
    
    private static int currentId = 0;
    private static ArrayList<GameSession> gameSessions = new ArrayList<>();
    
    private static synchronized int newId() {
        return ++currentId;
    }
    
    private static synchronized void addSession(GameSession session) {
        gameSessions.add(session);
    }
    
    private static GameSession getSessionByGame(Game game) {
        for (GameSession session : gameSessions) {
            if (session.getGame() == game) {
                return session;
            }
        }
        return null;
    }
    
    private static GameSession getSessionBySession(HttpSession target) {
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
    private GameSession newGame(HttpSession httpSession, String[] playerNames, String[] deck) {
        Card[] cards = new Card[deck.length];
        for (int i = 0; i < deck.length; i++) {
            cards[i] = Core.CardRepository.getInstance().getCardById(Integer.parseInt(deck[i]));
        }
        GameSession session = new GameSession(newId(), httpSession, playerNames, cards);
        addSession(session);
        return session;
    }
    
    private String[] makeCardArray() {
        ArrayList<Card> cards = Core.CardRepository.getInstance().getAllCards();
        String[] out = new String[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            out[cards.get(i).getId()] = cards.get(i).getName();
        }
        return out;
    }
    
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameServlet() {
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
                response.getWriter().write(Arrays.toString(makeCardArray()));
                break;
            case "new":
                request.getSession(true);
                GameSession session = newGame(request.getSession(true), request.getParameterValues("playernames")[0].split(","), request.getParameterValues("deck")[0].split(","));
                session.start();
                while (session.getBackLog() == null) {try {
                    Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
}
                System.out.println("sending: " + session.getBackLog());
                response.getWriter().write(session.getBackLog());
                break;
            default:
                GameSession s = getSessionBySession(request.getSession());
                s.setResponse(request);
                while (s.getBackLog() == null) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
                response.getWriter().write(s.getBackLog());
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
}
