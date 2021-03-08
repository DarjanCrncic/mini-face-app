package com.miniface.services;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.miniface.entities.FaceUserEntity;
import com.miniface.entities.InfoEntity;

public interface FaceUserService {
	
	public int addFaceUser(FaceUserEntity user);
	
	public JSONObject loginFaceUser(String username, String password);
	
	public int sendFriendRequest(int userID, int friendID);
	
	public JSONArray showFriendsList(int userID);

	public JSONArray findOtherPeople(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition);

	public JSONArray showFriendPendingRequests(int userID);

	public int updateFriendRequest(int friendID, int userID, String updateType);

	public JSONArray showGroupPendingRequsts(int userID);

	public int updateImage(int userID, InputStream inputStream);

	public InfoEntity getUserInfo(int userID);

	public int updateInfo(int userID, String username, String country, String city, String age, String gender);

	public int updateNotify(int parseInt, boolean notify);
	
	
}
