package ua.nure.parkhomenko.SummaryTask4.web.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
	public static final String ADMIN_LOGIN = "pt.igorevna@gmail.com";
	public static final String PASSWORD = "L74Hzoxi3V";

	public static void sendMail(String mail, String subject, String message)
			throws AddressException, MessagingException {
		Message msg = new MimeMessage(getSession());
		msg.setFrom(new InternetAddress(ADMIN_LOGIN));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
		msg.setSubject(subject);
		msg.setText(message); 
		Transport.send(msg);
	}

	private static Session getSession() {
		Session session = Session.getInstance(getProperties(), new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ADMIN_LOGIN, PASSWORD);
			}
		});
		return session;
	}

	private static Properties getProperties() {
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.localhost", "localhost");
		return properties;
	}
}
