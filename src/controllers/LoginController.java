package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
	
	private static List<String> sessionCodes = new ArrayList<String>();
	
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
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			try {
				if (Authenticator.authenticate(username, password)) {
					request.setAttribute("username", username);
				
					String sessionCode = generateSessionCode();
					sessionCodes.add(sessionCode);
					sessionCodes.add(request.getRequestedSessionId());
					Cookie sessionCookie = new Cookie("sessionCode", sessionCode);
					sessionCookie.setMaxAge(60*60*24);
					response.addCookie(sessionCookie);
					response.getWriter().print("200_good_login");
					//request.getRequestDispatcher("/welcome.jsp").forward(request, response);
				}
				else {
					//response.getWriter().print("403_bad_login");
					Authenticator.register(username, password);
				}
			
		} catch (Exception e) {
			System.out.println("Could not connect to user db: " + e);
		}
	}
}
