package com.miniface.websocket;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

public class MessageEncoder implements Encoder.Text<JSONObject> {

    @Override
    public String encode(final JSONObject message) throws EncodeException {
        return message.toString();
    }

	@Override
	public void init(EndpointConfig config) {
	
	}

	@Override
	public void destroy() {

	}

}
