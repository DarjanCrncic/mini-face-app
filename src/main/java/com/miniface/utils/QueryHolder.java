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
    }
    
    public static class SQL {
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
    }
}
