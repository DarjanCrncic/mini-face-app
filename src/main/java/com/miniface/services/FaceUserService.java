package com.miniface.services;

import javax.json.JsonObject;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserService {
	
	public int addFaceUser(FaceUserEntity user);
	
	public JsonObject loginFaceUser(String username, String password);
}
