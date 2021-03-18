package com.miniface.utils;

import org.json.JSONArray;

public class ConcatSQLSearch {

	public static String concatenate(String originalQuery, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, String[] caseAll) {

		StringBuilder str = new StringBuilder();

		str.append("SELECT * FROM( ");
		str.append(originalQuery);
		str.append(") WHERE ");

		for (int i = 0; i < filters.length(); i++) {
			if (filters.get(i).toString().equals("all")) {
				str.append("(");
				for (int j = 0; j < caseAll.length; j++) {
					if (wordPosition.equals("back")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
					} else if (wordPosition.equals("front")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
					} else {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
					}
					if (j < caseAll.length - 1) {
						str.append("OR ");
					}
				}
				str.append(")");
			} else {
				if (wordPosition.equals("back")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
				} else if (wordPosition.equals("front")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
				} else {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
				}
			}

			if (i < filters.length() - 1) {
				str.append(logicalOperand.toUpperCase() + " ");
			}
		}

		return str.toString();
	}

	public static String createSQLQueryAddition(String filter, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition, String[] caseAll) {

		StringBuilder str = new StringBuilder();
		str.append(filter + " (");
		for (int i = 0; i < filters.length(); i++) {
			if (filters.get(i).toString().contains("+")) {
				filters.put(i, "(" + filters.get(i).toString().replaceAll("\\+", " || ' ' || ") + ")");
			}
			if (filters.get(i).toString().equals("all")) {
				str.append("(");
				for (int j = 0; j < caseAll.length; j++) {
					if (wordPosition.equals("back")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
					} else if (wordPosition.equals("front")) {
						str.append("UPPER(" + caseAll[j] + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
					} else {
						str.append("UPPER(" + caseAll[j] + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
					}
					if (j < caseAll.length - 1) {
						str.append("OR ");
					}
				}
				str.append(")");
			} else {
				if (wordPosition.equals("back")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%" + words.get(i).toString().toUpperCase() + "' ");
				} else if (wordPosition.equals("front")) {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '" + words.get(i).toString().toUpperCase() + "%' ");
				} else {
					str.append("UPPER(" + filters.get(i).toString() + ") LIKE '%" + words.get(i).toString().toUpperCase() + "%' ");
				}
			}

			if (i < filters.length() - 1) {
				str.append(logicalOperand.toUpperCase() + " ");
			}
		}
		str.append(")");
		return str.toString();
	}

	public static String createPricingPreview(String sent, String startDate, String endDate, String[] types, String group) {
		StringBuilder str = new StringBuilder();

		str.append(" AND pg.sent = '");
		str.append(sent + "'");
		str.append(" AND (ps.creation_time BETWEEN to_date('" + startDate + "', 'MM/DD/YYYY') AND to_date('" + endDate + "', 'MM/DD/YYYY')+1)");

		if (!types[0].equals("all")) {
			str.append(" AND ps.type IN (");
			for (String type : types) {
				str.append(type + ",");
			}
			str.deleteCharAt(str.length() - 1);
			str.append(") ");
		}
		
		if(!group.equals("all")) {
			str.append(" AND ps.group_id = " + group);
		}
	
		return str.toString();
	}

}
