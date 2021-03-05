package com.miniface.ajax;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONObject;

import com.miniface.controllers.JSONServlet;
import com.miniface.entities.InfoEntity;
import com.miniface.services.FaceUserServiceImpl;
import com.miniface.utils.RequestValidator;

@WebServlet("/ProfilePage")
public class ProfilePage extends JSONServlet {
	private static final long serialVersionUID = 1L;
       
    public ProfilePage() {
        super();
  }

    @Override
	protected void doGetLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
		
        FaceUserServiceImpl fusi = new FaceUserServiceImpl();
        InfoEntity infoEntity = new InfoEntity();
        infoEntity = fusi.getUserInfo(Integer.parseInt((String) session.getAttribute("userID")));
        String base64Image = null;
        ////////// conversion to base64Image
        InputStream photo = infoEntity.getImage();
        if(photo != null) {
        	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
             
            while ((bytesRead = photo.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);                  
            }
             
            byte[] imageBytes = outputStream.toByteArray();
            base64Image = Base64.getEncoder().encodeToString(imageBytes);
            outputStream.close();
        }
       
        JSONObject info = new JSONObject();
        info.put("IMAGE", base64Image);
        info.put("NAME", infoEntity.getName());
        info.put("USERNAME", infoEntity.getUsername());
        info.put("AGE", infoEntity.getAge());
        info.put("CITY", infoEntity.getCity());
        info.put("COUNTRY", infoEntity.getCountry());
        info.put("GENDER", infoEntity.getGender());
      
		json.put("data", info);
		json.put("message", "Transaction successful");
		json.put("status", "success");
	
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(json.toString());
	}

    @Override
	protected void doPostLoggedIn(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject json) throws ServletException, IOException {
    	
    	String type = RequestValidator.validateRequest(request, "type");
    	String username = RequestValidator.validateRequest(request, "username");
		String country = RequestValidator.validateRequest(request, "country");
		String city = RequestValidator.validateRequest(request, "city");
		String age = RequestValidator.validateRequest(request, "age");
		String gender = RequestValidator.validateRequest(request, "gender");
    	int result = 0;
        FaceUserServiceImpl fusi = new FaceUserServiceImpl();
    		
    	if(type.equals("updateImage")) {
    		InputStream photo = null; 
            
            Part filePart = request.getPart("image");
            if (filePart != null) {
              
                // obtains input stream of the upload file
                photo = filePart.getInputStream();
                result = fusi.updateImage(Integer.parseInt((String) session.getAttribute("userID")),photo);      
                photo.close();
                
                response.sendRedirect("main");
                return;
            }
        }
    	
    	if(type.equals("updateInfo")){
    		if(username==null || country==null || city==null || age==null || gender==null) {
    			json.put("message", "invalid input data");
    			json.put("status", "error");
    		}else {
    			
    			result = fusi.updateInfo(Integer.parseInt((String) session.getAttribute("userID")), username, country, city, age, gender);
    			if (result == 0) {
    				json.put("message", "Error editing info data");
    				json.put("status", "error");
    			} else {
    				json.put("message", "Transaction successful");
    				json.put("status", "success");
    			}
    		}
    		response.setCharacterEncoding("UTF-8");
    		response.getWriter().write(json.toString());
    	}
        
	}

}
