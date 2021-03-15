package com.miniface.utils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperPDF {

	public static ServletOutputStream getJasperPDFPost(int userID, String username, String fullName, String placeholder, ServletOutputStream outStream) {
		try {

			//String path = ContextListener.jasperPostPath; //ovo radi!
			JasperReport report = (JasperReport) JRLoader.loadObject(new File("C:\\Users\\darjan.crncic\\workspaces\\web-development\\MiniFaceApp\\PostReport.jasper"));
			
			
			Connection connection = ConnectionClass.getConnection();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("user_id", userID);
			parameters.put("username", username);
			parameters.put("user_full_name", fullName);
			parameters.put("placeholder", placeholder);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
			ConnectionClass.closeConnection(connection);
			//JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\darjan.crncic\\workspaces\\web-development\\MiniFaceApp\\test.pdf");
					
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
			
			return outStream;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
