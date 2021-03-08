package com.miniface.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryHolder {
	public static boolean initialized = false;
	public static Object initMutex = new Object();
	
	{
		loadProperties();
	}

    public static void loadProperties() {
    	synchronized(initMutex) {
    		if(!initialized) {
    			InputStream propertyStream = QueryHolder.class.getResourceAsStream("/com/properties/query.properties");
    			Properties properties = new Properties();
    			
    			try {
    				properties.load(propertyStream);
    				readProperties(properties);
    				initialized = true;
    			} catch (IOException ex) {
    				ex.printStackTrace(System.out);
    			}
    		}
    	}
    }

    private static void readProperties(Properties properties) {
        SQL.REGISTER_USER = properties.getProperty("registerUser");
        SQL.LOGIN_USER = properties.getProperty("loginUser");
        SQL.CREATE_POST = properties.getProperty("createPost");
        SQL.CONNECT_GROUP_TO_POST = properties.getProperty("connectGroupToPost");
        SQL.CONNECT_USER_TO_FRIEND = properties.getProperty("connectUserToFriend");
        SQL.CONNECT_USER_TO_GROUP = properties.getProperty("connectUserToGroup");
        SQL.CONNECT_USER_TO_POST = properties.getProperty("connectUserToPost");
        SQL.CREATE_FRIEND_REQUEST = properties.getProperty("createFriendRequest");
        SQL.CREATE_GROUP = properties.getProperty("createGroup");
        SQL.CONNECT_GROUP_TO_POST = properties.getProperty("connectGroupToPost");
        SQL.CREATE_GROUP_REQUEST = properties.getProperty("createGroupRequest");
        SQL.GET_LIST_OF_FRIENDS = properties.getProperty("getListOfFriends");
        SQL.GET_VISSIBLE_POSTS = properties.getProperty("getVissiblePosts");
        SQL.GET_POST_BY_ID = properties.getProperty("getPostFromUserById");
        SQL.DELETE_POST_BY_ID = properties.getProperty("deletePostById");
        SQL.DELETE_POST_FROM_USER = properties.getProperty("deletePostFromUser");
        SQL.EDIT_POST_BY_ID = properties.getProperty("editPostById");
        SQL.GET_OTHER_PEOPLE = properties.getProperty("getOtherPeople");
        SQL.GET_ALL_FRIEND_PENDING_REQUESTS = properties.getProperty("getAllFriendPendingRequests");
        SQL.UPDATE_FRIEND_REQUEST_STATUS = properties.getProperty("updateFriendRequestStatus");
        SQL.ADD_TO_FRIENDS_TABLE = properties.getProperty("addToFriendsTable");
        SQL.GET_LIST_OF_GROUPS = properties.getProperty("getListOfGroups");
        SQL.DELETE_POST_FROM_GROUP = properties.getProperty("deletePostFromGroup");
        SQL.GET_GROUP_MEMBERS = properties.getProperty("getGroupMembers");
        SQL.GET_NOT_GROUP_MEMBERS = properties.getProperty("getNotGroupMembers");
        SQL.GET_ALL_GROUP_PENDING_REQUESTS = properties.getProperty("getAllGroupPendingRequests");
        SQL.UPDATE_GROUP_REQUEST_STATUS = properties.getProperty("updateGroupRequestStatus");
        SQL.SHOW_POSTS_FOR_GROUP = properties.getProperty("showPostsForGroup");
        SQL.EDIT_GROUP = properties.getProperty("editGroup");
        SQL.LIKE_POST = properties.getProperty("likePost");
        SQL.CREATE_COMMENT = properties.getProperty("createComment");
        SQL.GET_COMMENTS_FOR_POST = properties.getProperty("getCommentsForPost");
        SQL.GET_LIKES_FOR_COMMENT = properties.getProperty("getLikesForComment");
        SQL.GET_LIKES_FOR_POST = properties.getProperty("getLikesForPost");
        SQL.LIKE_COMMENT = properties.getProperty("likeComment");
        SQL.DELETE_COMMENT = properties.getProperty("deleteComment");
        SQL.UPDATE_USER_IMAGE = properties.getProperty("updateUserImage");
        SQL.GET_USER_INFO = properties.getProperty("getUserInfo");
        SQL.UPDATE_USER_INFO = properties.getProperty("updateUserInfo");
        SQL.UPDATE_USER_NOTIFY = properties.getProperty("updateUserNotify");
    }
    
    public static class SQL {
        public static String UPDATE_USER_IMAGE;
        public static String GET_USER_INFO;
		public static String REGISTER_USER;
        public static String LOGIN_USER;
        public static String CREATE_POST;
        public static String CONNECT_USER_TO_POST;
        public static String CONNECT_GROUP_TO_POST;
        public static String CREATE_GROUP;
        public static String CONNECT_USER_TO_GROUP;
        public static String CONNECT_USER_TO_FRIEND;
        public static String CREATE_FRIEND_REQUEST;
        public static String CREATE_GROUP_REQUEST;
        public static String FIND_USER_ID_BY_USERNAME;
        public static String GET_LIST_OF_FRIENDS;
        public static String GET_VISSIBLE_POSTS;
        public static String GET_POST_BY_ID;
        public static String DELETE_POST_BY_ID;
        public static String DELETE_POST_FROM_USER;
        public static String DELETE_POST_FROM_GROUP;
        public static String EDIT_POST_BY_ID;
        public static String GET_OTHER_PEOPLE;
        public static String GET_ALL_FRIEND_PENDING_REQUESTS;
        public static String UPDATE_FRIEND_REQUEST_STATUS;
        public static String ADD_TO_FRIENDS_TABLE;
        public static String GET_LIST_OF_GROUPS;
        public static String GET_GROUP_MEMBERS;
        public static String GET_NOT_GROUP_MEMBERS;
        public static String GET_ALL_GROUP_PENDING_REQUESTS;
        public static String UPDATE_GROUP_REQUEST_STATUS;
        public static String SHOW_POSTS_FOR_GROUP;
        public static String EDIT_GROUP;
        public static String LIKE_POST;
        public static String LIKE_COMMENT;
        public static String CREATE_COMMENT;
        public static String GET_COMMENTS_FOR_POST;
        public static String GET_LIKES_FOR_POST;
        public static String GET_LIKES_FOR_COMMENT;
        public static String DELETE_COMMENT;
        public static String UPDATE_USER_INFO;
        public static String UPDATE_USER_NOTIFY;
       
    }
}
