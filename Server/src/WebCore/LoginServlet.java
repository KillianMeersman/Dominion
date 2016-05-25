package WebCore;

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

/**
 * Servlet implementation class logincontroller
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String generateSessionCode() {	// Generates a session code for cookies - NOT CURRENTLY USED
		SecureRandom random = new SecureRandom();
		
		return new BigInteger(128, random).toString(64);
	}
	
	private boolean checkInput(String username, String password) {
		if (username != "" && password != "") {
			return true;
		}
		else {
			return false;
		}
	}
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		HttpSession session = request.getSession();	// Generate session object for access to session variables
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (checkInput(username, password)) {
		String sessionId = request.getRequestedSessionId();	// Content of JSESSIONID cookie (auto-generated cookie, contains a session specific code)
			if (request.getParameter("action").equals("login")) {
				try {
					if (Users.Authenticator.authenticate(username, password)) { // Good password
						
						session.setAttribute("username", username);	// Set a session variable
						response.getWriter().print("200_good_login");
						//request.getRequestDispatcher("/welcome.jsp").forward(request, response); // Send welcome.jsp
					}
					else { // Wrong password
						response.getWriter().print("bad_login"); // Send "bad_login" message
						//Authenticator.register(username, password);
					}
				
				} catch (Exception e) { // Error retrieving user from database
					if (e.getMessage() == "no_such_user") // Send "no_such_user" message
					{
						response.getWriter().print("user_not_found"); // Send "user_not_found" message
					}
					else {
						System.out.print("Could not connect to user db: " + e);
						response.getWriter().print("general_error"); // Send "general_error" message
					}
				}
			}
			else {
				try {
					Users.Authenticator.register(username, password); // Register user
					response.getWriter().print("user_registered"); // Send "user_registered" message
				} catch (Exception e) {
					if (e.getMessage() == "user_already_exists") {
						response.getWriter().print("user_not_available"); // Send "user_already_exists" message
					}
				}
			}
		}
		else {
			response.getWriter().print("field_empty");
		}
	}
}
