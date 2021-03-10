package com.miniface.utils;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperPDF {

	public static boolean getJasperPDFPost(int userID, String username, String fullName) {
		try {

			JasperReport report = (JasperReport) JRLoader.loadObject(new File("C:\\Users\\darjan.crncic\\workspaces\\web-development\\MiniFaceApp\\PostReport.jasper"));

			Connection connection = ConnectionClass.getConnection();
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("user_id", userID);
			parameters.put("username", username);
			parameters.put("user_full_name", fullName);

			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
			ConnectionClass.closeConnection(connection);
			JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\darjan.crncic\\workspaces\\web-development\\MiniFaceApp\\test.pdf");

			System.out.println("Pdf file successfully created");
			return true;
		} catch (Exception e) {
			System.out.println("Something went wrong when creating pdf file");
			e.printStackTrace();
			return false;
		}
	}
}
