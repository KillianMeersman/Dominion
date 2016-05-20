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

/**
 * Servlet implementation class GameController
 */
@WebServlet("/GameController")
public class GameController extends HttpServlet implements IEngineInterface {
    
    private int currentId = 0;
    private ArrayList<Core.Game> games = new ArrayList<Game>();
    
    private synchronized int newId() {
        return ++currentId;
    }
    
    private synchronized void addGame(Game game) {
        games.add(game);
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
            case "new":
                response.getWriter().write(newGame(request.getParameterValues("playernames"), request.getParameterValues("deck"))); // new game
                break;
            case "buy":
                
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
    
    /**
     * 
     * Takes raw client input and converts it to a game instance
     */
    private int newGame(String[] playerNames, String[] deck) {
        Card[] cards = new Card[deck.length];
        for (int i = 0; i < deck.length; i++) {
            cards[i] = Core.CardRepository.getInstance().getCardById(Integer.parseInt(deck[i]));
        }
        int id = newId();
        Game game = new Game(id, this, playerNames, cards);
        addGame(game);
        return id;
    }
    
    private void buy(String cardId, String[] treasureCards) {
        
    }

    @Override
    public Card promptPlayerCards(String prompt, Card[] cards, boolean canExit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player promptPlayerPlayer(String prompt, Player[] players, boolean canExit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void messagePlayer(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
