package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.miniface.controllers.JSONServlet;
import com.miniface.entities.ProracuniEntryEntity;
import com.miniface.services.ProracuniServiceImpl;
import com.miniface.utils.ConcatSQLSearch;
import com.miniface.utils.InputValidator;
import com.miniface.utils.JasperPDF;
import com.miniface.utils.RequestValidator;

@WebServlet("/Proracuni")
public class Proracuni extends JSONServlet {
	private static final long serialVersionUID = 1L;

	public Proracuni() {
		super();
	}

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

		String operation = RequestValidator.validateRequest(request, "operation");
		String groupID = RequestValidator.validateRequest(request, "groupID");
		ProracuniServiceImpl ps = new ProracuniServiceImpl();
		JSONArray arr = new JSONArray();

		if (operation.equals("getGroups")) {
			arr = ps.getGroups();
		}

		if (operation.equals("getEntries") && groupID != null) {
			arr = ps.getEntries(Integer.parseInt(groupID));
		}

		if (operation.equals("getPrice")) {
			arr = ps.getPrice();
		}

		if (operation.equals("getTypes")) {
			arr = ps.getTypes();
		}

		if (arr != null) {
			json.put("data", arr);
			json.put("message", "Transaction successful");
			json.put("status", "success");
		} else {
			json.put("message", "Error in proracuni");
			json.put("status", "error");
		}
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());

	}

	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {

		String ID = RequestValidator.validateRequest(request, "id");
		String operation = RequestValidator.validateRequest(request, "operation");
		String name = RequestValidator.validateRequest(request, "name");
		String type = RequestValidator.validateRequest(request, "type");
		String local = RequestValidator.validateRequest(request, "local");
		String returnTrip = RequestValidator.validateRequest(request, "returnTrip");
		String weight = RequestValidator.validateRequest(request, "weight");
		String groupID = RequestValidator.validateRequest(request, "groupID");
		String price = RequestValidator.validateRequest(request, "price");

		ProracuniServiceImpl ps = new ProracuniServiceImpl();
		InputValidator validator = new InputValidator();
		JSONArray arr = new JSONArray();
		int result = 0;

		if (operation.equals("proracuni_createGroup")) {
			result = ps.createGroup(name);
		}

		if (operation.equals("proracuni_addEntry")) {
			ProracuniEntryEntity entry = new ProracuniEntryEntity(Integer.parseInt(type), Integer.parseInt(local), Boolean.parseBoolean(returnTrip), Integer.parseInt(weight), Integer.parseInt(groupID));
			String[] errorMessages = validator.validateInput(entry);
			if (errorMessages.length == 0) {
				result = ps.updateEntry(entry, "add");
			}
		}

		if (operation.equals("proracuni_editEntry") && ID != null) {
			ProracuniEntryEntity entry = new ProracuniEntryEntity(Integer.parseInt(type), Integer.parseInt(local), Boolean.parseBoolean(returnTrip), Integer.parseInt(weight), Integer.parseInt(groupID));
			entry.setID(Integer.parseInt(ID));
			String[] errorMessages = validator.validateInput(entry);
			if (errorMessages.length == 0) {
				result = ps.updateEntry(entry, "edit");
			}
		}

		if (operation.equals("proracuni_deleteEntry") && ID != null) {
			ProracuniEntryEntity entry = new ProracuniEntryEntity();
			entry.setID(Integer.parseInt(ID));
			if (entry.getID() > 0) {
				result = ps.updateEntry(entry, "delete");
			}
		}

		if (operation.equals("proracuni_updatePrice") && ID != null) {
			if (Integer.parseInt(ID) > 0 && Float.parseFloat(price) >= 0) {
				result = ps.updatePrice(Integer.parseInt(ID), Float.parseFloat(price));
			}
		}

		if (operation.equals("preview")) {
			String sent = RequestValidator.validateRequest(request, "sent");
			String startDate = RequestValidator.validateRequest(request, "startDate");
			String endDate = RequestValidator.validateRequest(request, "endDate");
			String[] types = request.getParameterValues("types[]");
			String group = RequestValidator.validateRequest(request, "group");

			if (sent != null && startDate != null && endDate != null && group != null && types != null && types.length > 0) {
				String query = ConcatSQLSearch.createPricingPreview(sent, startDate, endDate, types, group);
				arr = ps.getPreviewData(query);
				json.put("data", arr);
				result = 1;
			}
		}

		if (operation.equals("proracuni_lockGroup") && Integer.parseInt(ID) > 0) {
			result = ps.lockGroup(Integer.parseInt(ID));
		}
		
		if(operation.equals("proracuni_savePreview")) {
			JSONTokener jsonToken = new JSONTokener(request.getInputStream());
			JSONObject jsonRequest = new JSONObject(jsonToken);
			JSONArray previewParts = new JSONArray(jsonRequest.getJSONArray("previewParts"));
			
			result = ps.savePreviewPart(previewParts);
			JSONObject previewJSON = new JSONObject();
			previewJSON.put("previewID", result);
			json.put("data", previewJSON);
		}
		
		if(operation.equals("proracuni_downloadPreview")) {
			String previewID = RequestValidator.validateRequest(request, "previewID");
		
			String previewPDF = JasperPDF.getJasperPDFPreview(Integer.parseInt(previewID));
			
			response.setCharacterEncoding("UTF-8");
	    	json.put("data", previewPDF);	
	    	
	    	result = 1;
		}
		
		if (result == 0) {
			json.put("message", "Error in proracuni");
			json.put("status", "error");
		} else {
			json.put("message", "Transaction successful");
			json.put("status", "success");
		}
		
		response.getWriter().write(json.toString());
	}

}
