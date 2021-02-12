package com.miniface.utils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

public class Hasher {
	
	static final Logger LOGGER = Logger.getLogger(Hasher.class);
	
	public static String getHashed(String password) throws UnsupportedEncodingException {
		
		byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
		
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 512, 64);
		
		SecretKeyFactory factory = null;
		byte[] hash = new byte[password.length()];
		try {
			factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			hash = factory.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.error("NoSuchAlgorithmException in getHashed", ex);
		} catch (InvalidKeySpecException ex) {
			LOGGER.error("InvalidKeySoecException in getHashed", ex);
		}
		
		String output = null;
	
		output = Hex.encodeHexString(hash);

		return output;
	}
}
