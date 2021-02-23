package com.miniface.services;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserService {
	
	public int addFaceUser(FaceUserEntity user);
	
	public JSONObject loginFaceUser(String username, String password);
	
	public int sendFriendRequest(int userID, String friendUsername);
	
	public JSONArray showFriendsList(int userID);
	
	
}
