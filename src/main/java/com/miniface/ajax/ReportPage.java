package com.miniface.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.miniface.controllers.JSONServlet;
import com.miniface.utils.JasperPDF;

@WebServlet("/ReportPage")
public class ReportPage extends JSONServlet {
	private static final long serialVersionUID = 1L;
	
	private static String pdfReportInfo;
       
    public ReportPage() {
        super();
    }

	@Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "inline; filename=" + "report.pdf");
		ServletOutputStream outStream = response.getOutputStream();
		
		outStream = JasperPDF.getJasperPDFPost(Integer.parseInt((String) session.getAttribute("userID")), (String) session.getAttribute("username"), session.getAttribute("name")+" "+session.getAttribute("surname"), pdfReportInfo, outStream);
		outStream.flush();
		outStream.close();
	}


	@Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException, JSONException {
		
		JSONTokener jsonToken = new JSONTokener(request.getInputStream());
		JSONObject jsonRequest = new JSONObject(jsonToken);
		JSONArray commentNumbers = new JSONArray(jsonRequest.getJSONArray("commentNumbers"));
		JSONArray commentOperations = new JSONArray(jsonRequest.getJSONArray("commentOperations"));
		JSONArray likeNumbers = new JSONArray(jsonRequest.getJSONArray("likeNumbers"));
		JSONArray likeOperations = new JSONArray(jsonRequest.getJSONArray("likeOperations"));
		String startDate = new String((String) jsonRequest.get("startDate"));
		String endDate = new String((String) jsonRequest.get("endDate"));
		String titleKeyword = new String(jsonRequest.getString("titleKeyword"));
		
		StringBuilder str = new StringBuilder();
		
		if(!titleKeyword.isEmpty() && !titleKeyword.isBlank()) {
			str.append(" AND ");
			str.append("UPPER(fp.title) LIKE '%" + titleKeyword.toUpperCase() + "%'");
		}
		
		for(int i=0; i<commentOperations.length(); i++) {
			str.append(" AND comment_counter.value ");
			str.append(commentOperations.get(i)+" "+commentNumbers.get(i));
		}
		
		for(int i=0; i<likeOperations.length(); i++) {
			str.append(" AND counter.value ");
			str.append(likeOperations.get(i)+" "+likeNumbers.get(i));
		}
		
		str.append(" AND ");
		str.append("(fp.creation_time BETWEEN to_date('" + startDate + "', 'MM/DD/YYYY') AND to_date('" + endDate +"', 'MM/DD/YYYY')+1)" );
		
		pdfReportInfo = str.toString();
		
		
		if(pdfReportInfo!=null) {
			json.put("status", "success");
			json.put("message", "successfully created PDF file");
		}else {
			json.put("status", "error");
			json.put("message", "something went wrong with creating PDF");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

}
