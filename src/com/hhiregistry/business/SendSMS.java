package com.hhiregistry.business;

import java.io.IOException; 
import java.util.Properties;

import org.marre.SmsSender;
import org.marre.sms.SmsAddress;
import org.marre.sms.SmsException;
import org.marre.sms.SmsTextMessage;
import org.marre.sms.transport.SmsTransport;
import org.marre.sms.transport.SmsTransportManager;

public class SendSMS {
	
	private String username;
	private String password;
	private String clientId;
	private String sender;
	private String reciever;
	private String message;
	private SmsSender smsSender;
	private SmsTransport smsTransport;
	
	public void sendSMS(String reciever) throws SmsException, IOException{
		
		
		
		
		

		// Connect to clickatell
		smsTransport.connect();

		// Create the sms message
		SmsTextMessage textMessage = new SmsTextMessage(message);

		// Send the sms to "461234" from "461235"
		smsTransport.send(textMessage, new SmsAddress(reciever), new SmsAddress(sender));
				
		// Disconnect from clickatell
		smsTransport.disconnect();
	}
	
	public void createSender(){
		
		try {
			SmsSender smsSender = SmsSender.getClickatellSender(username, password,clientId);
			this.smsSender = smsSender;
		} catch (SmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void createTransport(){
		
		// The username, password and apiid is sent to the clickatell transport
		// in a Properties				
		Properties props = new Properties();

		props.setProperty("smsj.clickatell.username", username);
		props.setProperty("smsj.clickatell.password", password);
		props.setProperty("smsj.clickatell.apiid", clientId);

		
		// Load the clickatell transport
		SmsTransport transport;
		try {
			 transport = SmsTransportManager.getTransport("org.marre.sms.transport.clickatell.ClickatellTransport", props);
			 this.smsTransport = transport;
		} catch (SmsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReciever() {
		return reciever;
	}

	public void setReciever(String reciever) {
		this.reciever = reciever;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SmsSender getSmsSender() {
		return smsSender;
	}

	public void setSmsSender(SmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public SmsTransport getSmsTransport() {
		return smsTransport;
	}

	public void setSmsTransport(SmsTransport smsTransport) {
		this.smsTransport = smsTransport;
	}
	
	
	




}
