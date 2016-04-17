package com.aes.service;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class StandAlone {

	
	public  void sendEmail(String user, String pass, String from,  
            String recipient,String subject, String message)  
           throws MessagingException { 

System.out.println(":::::: Reciepient :::::: "+recipient);
java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
  
       // Set the host smtp address  
       Properties props = new Properties();  
       props.put("mail.smtp.starttls.enable", "true");  
       props.put("mail.smtp.host", "smtp.gmail.com");  
       props.put("mail.transport.protocol", "smtp");  
       props.put("mail.smtp.auth", "true"); 
       props.put("mail.smtp.port",25);

       
       Session session = null;
       String protocol = "smtp";

  
       Authenticator auth = new SMTPAuthenticator(user, pass); 
       
        session = Session.getInstance(props, auth);  
  
       session.setDebug(true);  
  
       // create a message  
       Message msg = new MimeMessage(session);  
  
       // set the from and to address  
       InternetAddress addressFrom = new InternetAddress(from);  
       msg.setFrom(addressFrom);  
       
       String [] recipients = {recipient};
  
       InternetAddress[] addressTo = new InternetAddress[recipients.length];  
       for (int i = 0; i < recipients.length; i++) {  
           addressTo[i] = new InternetAddress(recipients[i]);  
       }  
       msg.setRecipients(Message.RecipientType.TO, addressTo);  
  
       // Setting the Subject and Content Type  
       msg.setSubject(subject);  
       msg.setContent("Hie There", "text/html");  
       //Transport.send(msg); 
       
       /*URLName urln = new URLName("smtp", "smtp.gmail.com", 25, "", user, pass);
       
       com.sun.mail.smtp.SMTPSSLTransport trans = new com.sun.mail.smtp.SMTPSSLTransport(session, urln);
       trans.setStartTLS(true);
     
       trans.send(msg);
       trans.close();*/
      Transport t = session.getTransport(protocol);
       try {
      	 t.connect(user, pass);
      	 //t.s
           t.sendMessage(msg, msg.getAllRecipients());
       } finally {
           t.close();
       }


       
   }  
	
	
	public static void main(String args[]) throws Exception
	{
		
		
		StandAlone mailutils = new StandAlone();
		mailutils.sendEmail("levi739y", "stargate1981","levi739y@gmail.com","elvicek@yahoo.com", "test","test");
		
	}
  
   /** 
    * SimpleAuthenticator is used to do simple authentication when the SMTP 
    * server requires it. 
    */  
   private static class SMTPAuthenticator extends javax.mail.Authenticator {  
  
       private String username;  
  
       private String password;  
  
       public SMTPAuthenticator(String username, String password) {  
           this.username = username;  
           this.password = password;  
       }  
  
       public PasswordAuthentication getPasswordAuthentication() {  
           return new PasswordAuthentication(username, password);  
       }  
   }  
	
}
