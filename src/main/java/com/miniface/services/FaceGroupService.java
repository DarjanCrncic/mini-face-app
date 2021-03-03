package com.miniface.services;

import org.json.JSONArray;

import com.miniface.entities.FaceGroupEntity;

public interface FaceGroupService {
	
	public int createEditDeleteGroup(FaceGroupEntity faceGroup, String groupID, String operation);

	public JSONArray showGroupsList(int userID);

	public JSONArray findGroups(int userID, JSONArray filters, JSONArray words, String logicalOperand, String wordPosition);

	public JSONArray listGroupMembers(int groupID);

	public JSONArray listNotGroupMembers(int groupID, int userID);

	public int sendGroupRequest(int receiverID, int groupID);

	public int updateGroupRequest(int receiverID, int groupID, String operation);
}
