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
    private void newGame(HttpSession httpSession, String[] playerNames, String[] deck) {
        Card[] cards = new Card[deck.length];
        for (int i = 0; i < deck.length; i++) {
            cards[i] = Core.CardRepository.getInstance().getCardById(Integer.parseInt(deck[i]));
        }
        addSession(new GameSession(httpSession, playerNames, cards));
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
           
                newGame(request.getSession(true), request.getParameterValues("playernames")[0].split(","), request.getParameterValues("deck")[0].split(","));
                
 
                // new game
                break;
            case "test":
            	response.getWriter().write("?action=buy&parameters=[4,1,1]&hand=[1,4,12,34,2]");
            	break;
                
            case "play":
 
            	request.getParameter("cardId");
            default:
                GameSession session = getSessionBySession(request.getSession());
                session.setResponse(request);
                response.getWriter().write(session.getBackLog());

            	
            	
            	
        
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
