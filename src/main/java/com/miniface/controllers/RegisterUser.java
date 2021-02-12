package com.miniface.controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.miniface.entities.FaceUserEntity;
import com.miniface.services.FaceUserServiceImpl;
import com.miniface.utils.Hasher;
import com.miniface.utils.InputValidator;

@WebServlet("/register")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	static final Logger LOGGER = Logger.getLogger(RegisterUser.class); 
	
    public RegisterUser() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/register_user.jsp");
		request.setAttribute("errorMessages", null);
		view.forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("User registration:");
		FaceUserEntity faceUser = new FaceUserEntity();
		
		InputValidator inputValidator = new InputValidator();
		
		String name = request.getParameter("form-firstName");	
		String surname = request.getParameter("form-lastName");
		String username = request.getParameter("form-username");
		String email = request.getParameter("form-email");
		String password = request.getParameter("form-password");
		
		faceUser.setName(name);
		faceUser.setSurname(surname);
		faceUser.setEmail(email);
		faceUser.setUsername(username);
		faceUser.setPassword(password);

		String[] errorMessages = inputValidator.validateInput(faceUser);

		if(errorMessages.length == 0) {
			faceUser.setPassword(Hasher.getHashed(password));
			FaceUserServiceImpl fUserService = new FaceUserServiceImpl();
			int result = fUserService.addFaceUser(faceUser);
			
			if(result == 1) {
				System.out.println("User added successfully");
				response.sendRedirect("login");
			}else {
				System.out.println("User not added");
			}
			
		}else {
			request.setAttribute("errorMessages", errorMessages);
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/register_user.jsp");	
			view.forward(request, response);
		}
		
	}

}
