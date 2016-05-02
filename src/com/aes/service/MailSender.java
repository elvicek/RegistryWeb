package com.aes.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;

import com.aes.data.domain.User;

public class MailSender implements Runnable {
	
	private List<User> users;
	private String mainMessage;
	private String subject;
	private ServletContext ctx;
	
	public  MailSender(ServletContext ctx, List<User> users, String mainMessage,String subject) {
		this.users = users;
		this.mainMessage = mainMessage;
		this.subject = subject;
		this.ctx = ctx;
	}

	@Override
	public void run() {
		sendEmailNotification(ctx,users, mainMessage, subject);
		
	}
	
	public static void sendEmailNotification(ServletContext ctx, List<User> users, String mainMessage,String subject) {
		
		
		String user = ctx.getInitParameter("user");
		String pass = ctx.getInitParameter("pass");
		String from = ctx.getInitParameter("from");
		String recipient;

		System.out.println(":::::: Sending Mail ::::::::::::::");

		
			for (User mailUser : users) {
				if (mailUser.getPerson().getEmail() != null &&mailUser.getPerson().getEmail().trim().length() > 0) {
					try {
						String lineSep = System.getProperty("line.separator");
						FileReader fr = new FileReader(HhiService.MESSAGE_TEMPLATE);
						BufferedReader bf = new BufferedReader(fr);

						String data = "";
						String message = null;
						StringBuffer strbuffer = new StringBuffer();
						strbuffer = populateMessage(mainMessage, mailUser, lineSep, bf, strbuffer);

						message = strbuffer.toString();
						recipient = mailUser.getPerson().getEmail();

						MailServiceClient mailServiceClient = new MailServiceClient();

						mailServiceClient.sendEmail(user, pass, from, recipient, subject, message);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MessagingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						
					}

				}

			}

		
	}

	private static StringBuffer populateMessage(String mainMessage, User mailUser, String lineSep, BufferedReader bf,
			StringBuffer strbuffer) throws IOException {
		String data;
		while ((data = bf.readLine()) != null) {
			strbuffer.append(data);
			strbuffer.append(lineSep);
		}

		// Send mail
		strbuffer = strbuffer.replace(strbuffer.indexOf("?1"), strbuffer.indexOf("?1") + 2,
				mailUser.getPerson().getName());
		strbuffer = strbuffer.replace(strbuffer.indexOf("?2"), strbuffer.indexOf("?2") + 2,
				mailUser.getPerson().getSurname());
		strbuffer = strbuffer.replace(strbuffer.indexOf("?3"), strbuffer.indexOf("?3") + 2,
				mainMessage);
		return strbuffer;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getMainMessage() {
		return mainMessage;
	}

	public void setMainMessage(String mainMessage) {
		this.mainMessage = mainMessage;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public ServletContext getCtx() {
		return ctx;
	}

	public void setCtx(ServletContext ctx) {
		this.ctx = ctx;
	}
	
	

}
