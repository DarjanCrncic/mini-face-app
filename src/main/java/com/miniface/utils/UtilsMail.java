
package com.miniface.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

public class UtilsMail {
	
	static final Logger LOGGER = Logger.getLogger(UtilsMail.class);
	
	public static void sendEmail(String toEmail, String text, String subject) {
		
		final String fromEmail = "minifaceapp@gmail.com";
		final String password = "izhcfpnhajjzqjsm";
				
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		};
		
		Session session = Session.getInstance(props, auth);
		
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
			message.setSubject(subject);
			message.setText(text);
			
			Transport.send(message);
			
			System.out.println("mail sent");
		}catch (MessagingException e) {
			LOGGER.error(e);
		}
	}
	
}
