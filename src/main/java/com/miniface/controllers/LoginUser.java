package com.miniface.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.miniface.services.FaceUserServiceImpl;
import com.miniface.utils.Hasher;
import com.miniface.utils.LoginInputValidator;
import com.miniface.utils.RequestValidator;

@WebServlet("/login")
public class LoginUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static final Logger LOGGER = Logger.getLogger(LoginUser.class); 
	
	public LoginUser() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login_user.jsp");
		request.setAttribute("errorMessages", null);
		
		view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("User login:");

		String username = RequestValidator.validateRequest(request, "form-username");
		String password = RequestValidator.validateRequest(request, "form-password");
		
		// check for errors in inputs
		String[] errorMessages = null;
		errorMessages = LoginInputValidator.validateLoginInput(username, password);
		
		if (errorMessages.length == 0) {
			password = Hasher.getHashed(password);

			FaceUserServiceImpl fusi = new FaceUserServiceImpl();
			errorMessages = new String[1];
			JSONObject jsonUser = fusi.loginFaceUser(username, password);
			
			
			if (jsonUser != null) {
				System.out.println("Successfully logged in!");
			
				// Create Session and add user information
				HttpSession session = request.getSession();
				insertUserAttributes(session, jsonUser);
				session.setMaxInactiveInterval(30 * 60);

				// Create and add cookie
				Cookie cookie = new Cookie("username", username);
				response.addCookie(cookie);

				// Redirect to main page
				String encodedUrl = response.encodeRedirectURL("main");
				response.sendRedirect(encodedUrl);

			} else {
				// Error messages for incorrect login information
				errorMessages[0] = "Login failed";
				request.setAttribute("errorMessages", errorMessages);
				RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login_user.jsp");
				view.forward(request, response);
			}
		}else {
			// Error messages for invalid login inputs (null or empty)
			request.setAttribute("errorMessages", errorMessages);
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/login_user.jsp");
			view.forward(request, response);
		}

	}
	
	private void insertUserAttributes(HttpSession session, JSONObject jsonUser) {
		session.setAttribute("username", jsonUser.get("USERNAME").toString().replaceAll("^\"|\"$", ""));
		session.setAttribute("userID", jsonUser.get("ID").toString().replaceAll("^\"|\"$", ""));
		session.setAttribute("name", jsonUser.get("NAME").toString().replaceAll("^\"|\"$", ""));
		session.setAttribute("surname", jsonUser.get("SURNAME").toString().replaceAll("^\"|\"$", ""));
	}

}
