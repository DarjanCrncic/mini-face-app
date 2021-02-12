package com.miniface.services;

import com.miniface.entities.FaceUserEntity;

public interface FaceUserService {
	
	public int addFaceUser(FaceUserEntity user);
	
	public int loginFaceUser(String username, String password);
}
