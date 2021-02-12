package com.miniface.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class SqlReader {
	
	static final Logger LOGGER = Logger.getLogger(SqlReader.class);
	
	public String getQuery(String searchedQuerry) {
		String output = null;

		try (InputStream fs = this.getClass().getClassLoader().getResourceAsStream("/myFile.txt");
				DataInputStream is = new DataInputStream(fs);
				BufferedReader bfr = new BufferedReader(new InputStreamReader(is));) {

			final int rhs = 1;

			String line;
			while ((line = bfr.readLine()) != null) {
				if (!line.startsWith("#") && !line.isEmpty()) {
					String[] pair = line.trim().split("=");
					output = pair[rhs].trim();
				}
			}

		} catch (IOException ex) {
			LOGGER.error("IOException in getQuery", ex);
		}

		System.out.println(output);
		return output;
	}
}
