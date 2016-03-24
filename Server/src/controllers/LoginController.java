package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.SecureRandom;
import java.util.*;
import java.math.BigInteger;
import model.Authenticator;

/**
 * Servlet implementation class logincontroller
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static List<User> authorizedUsers = new ArrayList<User>();
	
	public static List<User> getAuthorizedUsers() {
		return authorizedUsers;
	}
	
	private String generateSessionCode() {
		SecureRandom random = new SecureRandom();
		
		return new BigInteger(128, random).toString(64);
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sessionId = request.getRequestedSessionId();
		if (request.getParameter("action").equals("login")) {
			try {
				if (Authenticator.authenticate(username, password)) { // Good password
					
					User user = new User(sessionId, username);
					authorizedUsers.add(user);
					session.setAttribute("username", username);
					//response.getWriter().print("200_good_login");
					request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
				}
				else { // Wrong password
					response.getWriter().print("bad_login");
					//Authenticator.register(username, password);
				}
			
			} catch (Exception e) { // Error retrieving user from database
				if (e.getMessage() == "no_such_user")
				{
					response.getWriter().print("user_not_found");
				}
				else {
					System.out.print("Could not connect to user db: " + e);
					response.getWriter().print("general_error");
				}
			}
		}
		else {
			try {
				Authenticator.register(username, password);
				response.getWriter().print("user_registered");
			} catch (Exception e) {
				if (e.getMessage() == "user_already_exists") {
					response.getWriter().print("user_not_available");
				}
			}
		}
	}
}
