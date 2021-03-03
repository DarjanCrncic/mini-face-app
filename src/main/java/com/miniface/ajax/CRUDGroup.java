package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;
import com.miniface.entities.FaceGroupEntity;
import com.miniface.services.FaceGroupServiceImpl;
import com.miniface.utils.InputValidator;
import com.miniface.utils.RequestValidator;

@WebServlet("/CRUDGroup")
public class CRUDGroup extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public CRUDGroup() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException, JSONException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException, JSONException {
		String name = RequestValidator.validateRequest(request, "name");
		String description = RequestValidator.validateRequest(request, "description");
		String operation = RequestValidator.validateRequest(request, "operation");
		String groupID = RequestValidator.validateRequest(request, "groupID");

		InputValidator inputValidator = new InputValidator();
		FaceGroupServiceImpl fgsi = new FaceGroupServiceImpl();
		FaceGroupEntity group = new FaceGroupEntity();

		if (operation.equals("create")) {

			group.setDescription(description);
			group.setName(name);
			group.setOwnerId(Integer.parseInt((String) session.getAttribute("userID")));

			String[] errorMessages = inputValidator.validateInput(group);

			if (errorMessages.length == 0) {

				int result = 0;
				result = fgsi.createEditDeleteGroup(group, groupID, operation);

				if (result == 0) {
					json.put("message", "Error creating group");
					json.put("status", "error");
				} else {
					json.put("message", "Transaction successful");
					json.put("status", "success");
				}
			} else {
				json.put("message", "Invalid inputs when creating group");
				json.put("status", "error");
			}
		}

		if (operation.equals("edit") && groupID != null) {

			group.setDescription(description);
			group.setName(name);
			group.setOwnerId(Integer.parseInt((String) session.getAttribute("userID")));
			String[] errorMessages = inputValidator.validateInput(group);

			if (errorMessages.length == 0) {

				int result = 0;
				result = fgsi.createEditDeleteGroup(group, groupID, operation);

				if (result == 0) {
					json.put("message", "Error editing group");
					json.put("status", "error");
				} else {
					json.put("message", "Transaction successful");
					json.put("status", "success");
				}
			} else {
				json.put("message", "Invalid inputs when editing group");
				json.put("status", "error");
			}
		}

		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}
}
