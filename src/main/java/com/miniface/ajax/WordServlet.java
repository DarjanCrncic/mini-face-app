package com.miniface.ajax;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;
import com.miniface.utils.RequestValidator;
import com.miniface.utils.WordDocument;


@WebServlet("/WordServlet")
public class WordServlet extends JSONServlet {
	private static final long serialVersionUID = 1L;
       

    public WordServlet() {
        super();
   }
    @Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
    	
    	
	}

    @Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
    	
    	String postID = RequestValidator.validateRequest(request, "postID");
    	
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	WordDocument.CreateDocumentDocx4j(baos, Integer.parseInt(postID));
    	byte[] doc = baos.toByteArray();
    	String base64Doc = Base64.getEncoder().encodeToString(doc);
    	baos.close();
    	
    	response.setCharacterEncoding("UTF-8");
    	json.put("data", base64Doc);
		response.getWriter().write(json.toString());
    	
//    	ServletOutputStream outStream = response.getOutputStream();
//  	response.setContentType("application/docx");
//    	WordDocument.CreateDocument(outStream);
//    	outStream.close();

	}

}
