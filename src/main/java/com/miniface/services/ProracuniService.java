package com.miniface.services;

import org.json.JSONArray;

import com.miniface.entities.ProracuniEntryEntity;

public interface ProracuniService {
	
	public int createGroup(String name);

	int updateEntry(ProracuniEntryEntity entry, String operation);

	JSONArray getEntries(int groupID);

	JSONArray getPrice();

	int updatePrice(int priceEntryID, float price);

	JSONArray getTypes();

	int lockGroup(int groupID);

	JSONArray getPreviewData(String query);

	int savePreviewPart(JSONArray previewParts);

}
