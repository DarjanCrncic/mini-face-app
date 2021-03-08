package com.miniface.websocket;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import org.json.JSONObject;

public class MessageDecoder implements Decoder.Text<JSONObject> {

    @Override
    public JSONObject decode(final String textMessage) throws DecodeException {
        JSONObject jsonObject = new JSONObject(textMessage);
        return jsonObject;
    }

	@Override
	public void init(EndpointConfig config) {
		
	}

	@Override
	public void destroy() {
		
	}

	@Override
	public boolean willDecode(String s) {
		return true;
	}

}
