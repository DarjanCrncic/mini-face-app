package com.miniface.websocket;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.json.JSONObject;

@ServerEndpoint(value="/chat", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class SEndpoint {
	
	static Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session) {
		peers.add(session);
	}
	
	@OnMessage
	public void onMessage(JSONObject message, Session session) throws IOException, EncodeException{
		for(Session peer: peers) {
			if(!session.getId().equals(peer.getId())) {
				peer.getBasicRemote().sendObject(message);
			}
		}
	}
	
	@OnClose
	public void onClose(Session session) throws IOException, EncodeException{
		peers.remove(session);
	}
}
