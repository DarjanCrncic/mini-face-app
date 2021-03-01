package com.miniface.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserService {
	
	public int addFaceUser(FaceUserEntity user);
	
	public JSONObject loginFaceUser(String username, String password);
	
	public int sendFriendRequest(int userID, int friendID);
	
	public JSONArray showFriendsList(int userID);

	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition);

	public JSONArray showFriendPendingRequests(int userID);

	public int updateFriendRequest(int friendID, int userID, String updateType);

	public JSONArray showGroupPendingRequsts(int userID);
	
	
}
