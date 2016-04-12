package com.hhiregistry.data.actions;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.marre.sms.SmsException;

import com.hhiregistry.business.CellgroupEditData;
import com.hhiregistry.business.SendSMS;
import com.hhiregistry.exceptions.MemberExistsException;
import com.hhiregistry.exceptions.PersistanceException;
import com.hhiregistry.local.model.BirthdayCalendar;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Member;
import com.hhiregistry.model.MemberGroups;
import com.hhiregistry.service.HhiService;
import com.hhiregistry.service.MailServiceClient;
import com.hhiregistry.utils.BirthdayCalendarComparator;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;


public class AdminActions extends ActionSupport {
	
	private Integer groupId;
	private Integer groupIdMembers;
	private String groupName;
	private String cellgroupName;
	private Groups groups;
	private Boolean validationRequest;
	private String groupNameMembers;
	private String groupToDelete;
	private String groupsToSend;
	private String messageGroup;
	private String messageIndividual;
	private String messageBirthday;
	private String groupsSelect;
	private String groupsAllocated;
	private String subject;
	private String memberIdToSms;
	private String individualsSms;
	private String birthdaySms;
	private String groupSms;
	private String message;
	private Integer tab;
	
	private Logger logger =  Logger.getLogger(AdminActions.class.getName());
	
	
public String groupAction(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		
		
		//List<Member> members = HhiService.getAllMembers();
		List<Groups> groups;
		try {
			groups = HhiService.getAllGroups();
		} catch (PersistanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			session.setAttribute("adminContent", "error");
			return Action.ERROR;
			
			
		}
		if(path.contains("menu")){
			session.setAttribute("adminContent", "menu");
			
		}
		
		else if(path.contains("groupSMS")){
			
			tab = 1;
			session.setAttribute("adminContent", "sms");
			
			
		}
		else if(path.contains("individualSMS")){
			
			tab = 2;
			session.setAttribute("adminContent", "sms");
			
			
		}
		else if(path.contains("birthdaySMS")){
			
			tab = 3;
			session.setAttribute("adminContent", "sms");
			
			
		}
		
		else if(path.contains("Email")){
			
			session.setAttribute("adminContent", "email");
			
		}
		else if(path.contains("write")){
			session.setAttribute("adminContent", "write");
			
		}
		
		else if(path.contains("emailTextFile")){
			session.setAttribute("adminContent", "write");
			
		}
		
		else if (path.contains("edit")){
			session.setAttribute("adminContent", "edit");
			Groups group = null;
			//CellgroupEditData data = new CellgroupEditData();
			
			//Member member = null;
			try {
				
				group = HhiService.getGroupsById(groupId);
				
				try{
					
					/*if(cellgroup.getMemberId() != null){
						member = HhiService.getMemberById(cellgroup.getMemberId());
						data.setMemberId(member.getMemberId());
						data.setMemberName(member.getName());
						//this.editMemberId = cellgroup.getMemberId().toString();
					}*/
				}
				
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			session.setAttribute("group", group);
			//session.setAttribute("celleditdata", data);
			
		}
		else if (path.contains("view")){
			
			
			//session.setAttribute("memberMap", memberMap);
			session.setAttribute("adminContent", "view");
			session.setAttribute("groups", groups);
			
			
			
		}
		else if (path.contains("membership")){
			
			session.setAttribute("adminContent", "membership");
			Groups group;
			try {
				 group = HhiService.getGroupsById(this.groupIdMembers);
			} catch (PersistanceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return Action.ERROR;
			}
			
			List<Member> members = new ArrayList<Member>();
			
			try{
		     members = new ArrayList<Member>(group.getMembers());
			}catch (Exception e){
				
			}
			
			session.setAttribute("membersBygroup", members);
			session.setAttribute("title", "Members for "+group.getGroupName());
			
			
			
		}
		
		else if (path.contains("success")){
			session.setAttribute("adminContent", "success");
			
		}	
		else if (path.contains("error")){
			session.setAttribute("adminContent", "error");
			
		}	
		else {
			session.setAttribute("adminContent", "input");
			
		}
		
		//System.out.println("Size of Members "+members.size());
		
		
		
		//request.getSession().setAttribute("selectMembers", selectMembers);
		
		
		return Action.SUCCESS;
		
		
	}


public String saveGroup(){
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	//Set<Member> members = new HashSet<Member>();
	
	
	this.groups.setCreatedBy(request.getRemoteUser());
	this.groups.setCreatedDate(new Date());
	try{
		HhiService.save(groups);
	}
	catch(Exception e){
		//logger.log(Level.SEVERE,"Database Error Occured. Please ensure you have connection to database or record does not exist");
		return Action.ERROR;
		
	}
	
	
	//request.getSession().setAttribute("savedCellGroup", cellgroup);
	request.getSession().setAttribute("groupMessage", "saveRecord");
	return Action.SUCCESS;
	
	
	
	
}


public String signOutAction(){
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	request.getSession().invalidate();
	
	
		return Action.SUCCESS;
		
	}
	
	
	
	


public String updateGroup(){
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	
	
	this.groups.setCreatedBy(request.getRemoteUser());
	this.groups.setCreatedDate(new Date());
	
	
	try{
		HhiService.update(groups);
	}
	catch(Exception e){
		//logger.log(Level.SEVERE,"Database Error Occured. Please ensure you have connection to database or record does not exist");
		return Action.ERROR;
		
	}
	
	
	//request.getSession().setAttribute("savedCellGroup", cellgroup);
	request.getSession().setAttribute("groupMessage", "updateRecord");
	return Action.SUCCESS;
	
	
	
	
}

public String groupDelete(){
	
	String [] groupsToDelete = null;
	//this.member.setSex(sex);

	//HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletRequest request = ServletActionContext.getRequest();
	if(groupToDelete.split(":").length == 0){
		
		groupsToDelete[0] = groupToDelete;
		
		
		
	}
	else{
		
		groupsToDelete = groupToDelete.split(":");
		
	}
	try{
	for(String id : groupsToDelete){
	
		try {
			HhiService.deleteGroupById(Integer.valueOf(id));
		} catch (MemberExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getSession().setAttribute("groupMessage", "groupDeleteError");
			
			return Action.SUCCESS;
		}
		
		
		
		
	}
	}catch(ConstraintViolationException e){
		request.getSession().setAttribute("groupMessage", "cellgroupDeleteError");
		return Action.SUCCESS;
	}
	//request.getSession().setAttribute("savedMember", member);
	
	request.getSession().setAttribute("groupMessage", "deleteRecord");
	return Action.SUCCESS;
}

public List<Groups> getAllocated(){
	
	/*List<Groups> groups = new ArrayList<Groups>();
	try {
		
		List<MemberGroups> memberGroups = HhiService.getMemberGroupsById(memberId);
		for(MemberGroups membGroups: memberGroups){
			groups.add(membGroups.getGroups());
		
		}
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
     
      
      List<Groups> groups = new ArrayList<Groups>();
	
	return groups;
}


public List<Groups> getOptions(){

	List<Groups> groups = null;
	
		
		groups = HhiService.getGroups();
		//System.out.println("List Size "+groups.size());
		
return groups;
}



public String dispatchSMS(){
	Set <String> uniqueNumbers = new HashSet <String>();
	List <Member> members;
	String message = "";
	List <Member> allMembers = new ArrayList<Member> ();
	//String trimmedGroupsToSend = groupsToSend.trim();
	
	ServletContext ctx = ServletActionContext.getServletContext();
	HttpSession session = ServletActionContext.getRequest().getSession();
	String username = ctx.getInitParameter("username");
	String password = ctx.getInitParameter("password");
	String clientId = ctx.getInitParameter("clientId");
	String senderNumber = ctx.getInitParameter("senderNumber");
	
	//System.out.println(" Grooups To Send :::::::::::::::::::"+groupsToSend);
	
	String[]ids = groupsToSend != null ? groupsToSend.split(","): null;
	Member member = null;
	/*Session session = HhiService.getSession();
	Transaction tx = session.beginTransaction();*/
	
	
//	System.out.println(" Members To Send :::::::::::::::::::"+memberIdToSms.length());
	if((individualsSms.equals("true") || birthdaySms.equalsIgnoreCase("true")) && groupSms.equalsIgnoreCase("false") ){
		
		String [] membersToSms = null;
		//this.member.setSex(sex);

		//HttpServletRequest request = ServletActionContext.getRequest();
		if(memberIdToSms.split(":").length == 0){
			
			membersToSms[0] = memberIdToSms;
			
			try {
				allMembers.add(HhiService.getMemberById(Integer.valueOf(membersToSms[0])));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			
			membersToSms = memberIdToSms.split(":");
			Member smsMember;
			for(String id : membersToSms){
				
				 try {
					smsMember = HhiService.getMemberById(Integer.valueOf(id));
					allMembers.add(smsMember);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (PersistanceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
			
		}
		
		
		
	}
	
	else if(groupsToSend.contains("all")){
		
		
		try {
			allMembers = HhiService.getAllMembers();
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	else{
		
		for(int i = 0 ; i < ids.length ; i++){
			Groups groups = null;
			
			try {
				if(ids[i]!= "00");
				System.out.println("Values ::::::::"+ids[i]);
				groups = HhiService.getGroupsById(Integer.valueOf(ids[i]));
				members = new ArrayList<Member>(groups.getMembers());
				allMembers.addAll(members);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
		
//		SendSMS sendSMS = new SendSMS();
//		
//		sendSMS.setUsername(username);
//		sendSMS.setPassword(password);
//		sendSMS.setClientId(clientId);
//		sendSMS.setSender(senderNumber);
//		sendSMS.createTransport();
//		sendSMS.setMessage(this.message);
//		
//		
//		
//		
//		for(Member amember: allMembers){
//			
//			try {
//				sendSMS.sendSMS(amember.getCellphone());
//			} catch (SmsException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//		}
	if(individualsSms.equals("true")) {
		
		message = this.messageIndividual;
	}
	else if(birthdaySms.equalsIgnoreCase("true")){
		
		message = this.messageBirthday;
	}else{
		message = this.messageGroup;
	}
			
			
		
	for(Member setMember: allMembers){
		uniqueNumbers.add(setMember.getCellphone());
	}

	try{
	for(String anumber: uniqueNumbers){
		URL url = new URL("http://api.clickatell.com/http/sendmsg?user="+
				username+"&password="+password+"&api_id="+clientId+"&to="+
				anumber);
				//amember.getCellphone()+"&text="+msgParam);
				//amember.getCellphone());
		//make connection, use post mode, and send query
		//System.out.println("URL Conn ::::::::"+url.toString());
		URLConnection urlc = url.openConnection();
		urlc.setDoOutput(true);
		urlc.connect();
		OutputStreamWriter out = new OutputStreamWriter(
				urlc.getOutputStream());
		String messages = URLEncoder.encode(message, "UTF-8");
out.write("text="+messages);
out.close();
BufferedReader in = new BufferedReader(
        new InputStreamReader(
        		urlc.getInputStream()));
String response;
while ((response = in.readLine()) != null) {
  System.out.println("::: Service Response  :::"+response);
}
in.close();


		//urlc.connect();
		//String responseCode = urlc.getR
	}
	}catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
		
	}
		//urlc.setDoOutput(true);
		//urlc.setAllowUserInteraction(false);
		//String encodedPost = URLEncoder.encode(ret);
		//PrintStream ps = new PrintStream(urlc.getOutputStream());
		
		// send query
	
	session.setAttribute("groupMessage", "smsDeliverySuccess");
	return Action.SUCCESS;
}

public String dispatchEmail(){
	ServletContext ctx = ServletActionContext.getServletContext();
	HttpSession session = ServletActionContext.getRequest().getSession();
	String user = ctx.getInitParameter("user");
	String pass = ctx.getInitParameter("pass");
	String from = ctx.getInitParameter("from");
	
	System.out.println(":::::: Sending Mail ::::::::::::::" );
	
	

	Set <Member> uniqueMembers ;
	List <Member> members;
	List <Member> allMembers = new ArrayList<Member> ();
	String  recipient;
	ArrayList<String> memberAddresses = new ArrayList<String>();
	//String trimmedGroupsToSend = groupsToSend.trim();
	
	
	
	String[]ids = groupsToSend != null ? groupsToSend.split(","): null;
	
	/*Session session = HhiService.getSession();
	Transaction tx = session.beginTransaction();*/
	
	
	try {
	
	if(groupsToSend.contains("all")){
		
		
		
			allMembers = HhiService.getAllMembers();
			
		
		
	}
	else{
		
		allMembers = getAllSelectedMembers(ids);
		
	}
		
		
		
		
		
		
		if(allMembers.size() == 1){
			
			allMembers.clear();
			
			Integer groupId = Integer.valueOf(returnGroupIdWithMember(ids));
			
		
			MemberGroups memberGroups = HhiService.getMemberGroupsById(groupId);
			
			//List<Member> foundMember.add(allMembers);
			//Member member = HhiService.getMemberById(group.)
			allMembers.add(memberGroups.getMember());
		}
		
		for(Member mailMember : allMembers){
			if(mailMember.getEmail() != null && mailMember.getEmail().trim().length() > 0 ){
			try {
			String lineSep = System.getProperty("line.separator");
			FileReader fr = new FileReader(HhiService.MESSAGE_TEMPLATE);
			BufferedReader bf = new BufferedReader(fr);
			
			String data = "";
			String message = null;
			StringBuffer strbuffer = new StringBuffer();
			while ((data=bf.readLine()) != null){
				strbuffer.append(data);
				strbuffer.append(lineSep);
			}
			
			// Send mail
			strbuffer = strbuffer.replace(strbuffer.indexOf("?1"),strbuffer.indexOf("?1")+2,mailMember.getName());
			strbuffer = strbuffer.replace(strbuffer.indexOf("?2"),strbuffer.indexOf("?2")+2,mailMember.getSurname());
			strbuffer = strbuffer.replace(strbuffer.indexOf("?3"),strbuffer.indexOf("?3")+2,this.message);
			
			message = strbuffer.toString();
			recipient = mailMember.getEmail();
						
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
				return Action.ERROR;
			}
			
			}
		
			
		}
		
		//recipients = (String[]) memberAddresses.toArray();
		
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	session.setAttribute("groupMessage", "emailDeliverySuccess");
	return Action.SUCCESS;
}

private List<Member> getAllSelectedMembers(String[] ids) {
	List <Member> members = null;
	List <Member> allMembers = new ArrayList<Member> ();
	Member member = null;
	
	for(int i = 0 ; i < ids.length ; i++){
		Groups groups = null;
		String trimmmeId = ids[i].trim(); 
		//System.out.println("ID's "+ids[i]);
		try {
			if((ids[i]!= "00") && (trimmmeId.length() > 0));
			
			groups = HhiService.getGroupsById(Integer.valueOf(ids[i]));
			
			//if(groups.getMembers().size() > 1){
				members = new ArrayList<Member>(groups.getMembers());
			//}
			/*else if (groups.getMembers().size()  == 1){
				List<Member> foundMember = new ArrayList<Member>();
				Integer groupId = Integer.valueOf(returnGroupIdWithMember(ids));
				MemberGroups memberGroups = HhiService.getMemberGroupsById(groupId);
				foundMember.add(memberGroups.getMember());
				members = foundMember;
				
			}*/
			
			
			for(Member memberAdd : members ){
				if(memberAdd.getEmail() != null){
					
					allMembers.add(member);
				}
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	return allMembers;
}

private String returnGroupIdWithMember(String[] ids) {
	
	
	for(int i = 0 ; i < ids.length ; i++){
		Groups groups = null;
		String trimmmeId = ids[i].trim(); 
		//System.out.println("ID's "+ids[i]);
		try {
			if((ids[i]!= "00") && (trimmmeId.length() > 0));{
			
			groups = HhiService.getGroupsById(Integer.valueOf(ids[i]));
			if (groups.getMembers().size() > 0){
				return ids[i];
			}
			
			
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	return null;
}

public Integer getPageSize() {

	String setting = null;
	int i =1;
	HttpServletRequest request = ServletActionContext.getRequest();
	ServletContext ctx = request.getSession().getServletContext();
	try {
		
		setting = HhiService.getHhiSettingDescription(HhiService.PAGER_OPTION);
		
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return Integer.valueOf(setting);
}

public String writeAddresses(){
	
	ServletContext ctx = ServletActionContext.getServletContext();
	HttpSession session = ServletActionContext.getRequest().getSession();
	try {
		List<Member> members = HhiService.getAllMembers();
		FileWriter outFile = new FileWriter(HhiService.EMAIL_ADDRESSES_TEXT_FILE);
	    PrintWriter out = new PrintWriter(outFile);
		
		for(Member member : members){
			
			if (member.getEmail()!= null){
				
				out.println(member.getEmail());	
			}
			
		}
		
		 out.close();
		
	} catch (IOException e){
		           e.printStackTrace();
		          
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	session.setAttribute("adminContent", "write");
	return Action.SUCCESS;
	
}

public void validate(){
	
	HttpServletRequest request = ServletActionContext.getRequest();
	List <Member> allMembers = new ArrayList<Member> ();
	
	
	logger.log(Level.INFO,"UI ::::::::: ::::: "+request.getRequestURI());
	if(request.getRequestURI().contains("send")){
		String[]ids = groupsToSend != null ? groupsToSend.split(","): null;
		
		
		if(groupSms.equals("true") && messageGroup.equals("")){
			tab= 1;
			request.setAttribute("tab", tab);
			System.out.println(":::::::: MSG :::::::::: "+messageGroup);
			addFieldError("messageGroup", "Please Input a message to send to members.");
			
		}
		else if(individualsSms.equals("true") && messageIndividual.equals("")){
			tab= 2;
			request.setAttribute("tab", tab);
			addFieldError("messageIndividual", "Please Input a message to send to members.");
			
		}
		else if (birthdaySms.equals("true") && messageBirthday.equals("")){
			tab= 3;
			request.setAttribute("tab", tab);
			addFieldError("messageBirthday", "Please Input a message to send to members.");
			
		}
		
		else if (individualsSms.equalsIgnoreCase("false") && birthdaySms.equalsIgnoreCase("false") && (groupsToSend != null) && groupsToSend.trim().length() == 0 ){
			tab= 1;
			request.setAttribute("tab", tab);
			addFieldError("groupsAllocated", "Please Select at least 1 group to Send Email.");
			
		}
		
		else if (individualsSms.equalsIgnoreCase("true") && birthdaySms.equalsIgnoreCase("false") && !(memberIdToSms.trim().length() > 0)){
			tab=2;
			request.setAttribute("tab", tab);
			addFieldError("messageIndividual", "Please Select at least 1 member to Send Message.");
			
			
		}
		else if (individualsSms.equalsIgnoreCase("false") && birthdaySms.equalsIgnoreCase("true") && !(memberIdToSms.trim().length() > 0)){
			tab=3;
			request.setAttribute("tab", tab);
			addFieldError("messageBirthday", "Please Select at least 1 member to Send Message.");
			
			
		}
		
		else if(individualsSms.equalsIgnoreCase("false") && birthdaySms.equalsIgnoreCase("false")){
			
			
			List<String> numberList = new ArrayList<String>();
			for(int i = 0; i < ids.length;i++){
				if(!ids[i].equals("all"))
					numberList.add(ids[i]);
			}
			//numberIds = (String[])numberList.toArray();
			String numberIds [] = new String[numberList.size()];
			for(int i = 0; i <  numberList.size() ; i++){
				numberIds[i] = numberList.get(i);
				
			}
				
			allMembers = getAllSelectedMembers(numberIds);
			if(allMembers.size() == 0){
				addFieldError("groupsAllocated", "No Members found in the selected groups");
			}
			
		}
	}
	if(!request.getRequestURI().contains("groupsinput") && request.getParameterMap().containsKey("validationRequest")){ //Prevent Error forward from validating again
		
	
		try {
			String groupName = request.getParameter("groups.groupName");
			Groups group = HhiService.getGroupsByName(groupName);
			
			
			if ((group!= null && !(request.getParameterMap().containsKey("groupsedit")) )){
				
				logger.log(Level.SEVERE,"Validating Condition Errorror:::::::::::::::::::: "+request.getParameter("groups.groupName"));
				addFieldError("groups.groupName", "'" + group.getGroupName() + "' has been taken and aready exists in Database.");
			}
			
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	
}

public List<Member> getMembers(){
	
	List<Member> members = null;
	try {
		members = HhiService.getAllMembers();
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return members;
	
}

public List<BirthdayCalendar> getBirthdayCalendar(){
	
	List<Member> members = null;
	List<BirthdayCalendar> birthdayCalendar = new ArrayList<BirthdayCalendar>();
	try {
		members = HhiService.getAllMembers();
		
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	for(Member member: members){
		
		
			
			DateTime birthdate = new DateTime(member.getBirthday());
			DateTime currentDate = new DateTime();
			
			
			
			if(member.getBirthday()!= null && (birthdate.getMonthOfYear()== currentDate.getMonthOfYear()) && (0 <= (birthdate.getDayOfMonth()-currentDate.getDayOfMonth()) && (birthdate.getDayOfMonth()-currentDate.getDayOfMonth()) <=7)){
				//int diff = birthdate.getDayOfMonth()-currentDate.getDayOfMonth();
				System.out.println("Member Qualified Bithday "+member.getName()+" "+member.getSurname()+" "+member.getBirthday());
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(member.getBirthday());
				int oldYear = birthday.YEAR;
				String  years = String.valueOf(birthday.get(Calendar.YEAR));
				int age = currentDate.getYear() - new Integer(years);
				int margin =(birthdate.getDayOfMonth()-currentDate.getDayOfMonth());
				//System.out.println("birth day using jod "+birthdate.toString()+"using calendar "+oldYear+" New year "+years);
				//System.out.println("current year "+currentDate.getYear());
				//System.out.println("age "+age);
				
				String day = "";
				DateTime actualBirthDay = birthdate.monthOfYear().addToCopy(12*age) ;
				DateTime.Property pDoW = actualBirthDay.dayOfWeek();
				if(margin == 0 ){
					day = "Today";
				}else{
					day = pDoW.getAsText();
				}
				 
				String month = birthdate.monthOfYear().getAsText();
				String year = currentDate.year().getAsText();
				
				
				BirthdayCalendar calendar = new BirthdayCalendar(member,day,month,year,birthdate,age,margin);
				birthdayCalendar.add(calendar);
				
			}
			//BirthdayCalendar calendar = new BirthdayCalendar();
			
		
		
	}
	Collections.sort(birthdayCalendar,new BirthdayCalendarComparator());
	return birthdayCalendar;
	
}

public Integer getGroupId() {
	return groupId;
}


public void setGroupId(Integer groupId) {
	this.groupId = groupId;
}


public Groups getGroups() {
	return groups;
}


public void setGroups(Groups groups) {
	this.groups = groups;
}


public Boolean getValidationRequest() {
	return validationRequest;
}


public void setValidationRequest(Boolean validationRequest) {
	this.validationRequest = validationRequest;
}


public String getGroupName() {
	return groupName;
}


public void setGroupName(String groupName) {
	this.groupName = groupName;
}


public String getCellgroupName() {
	return cellgroupName;
}


public void setCellgroupName(String cellgroupName) {
	this.cellgroupName = cellgroupName;
}


public String getGroupNameMembers() {
	return groupNameMembers;
}


public void setGroupNameMembers(String groupNameMembers) {
	this.groupNameMembers = groupNameMembers;
}


public String getGroupToDelete() {
	return groupToDelete;
}


public void setGroupToDelete(String groupToDelete) {
	this.groupToDelete = groupToDelete;
}


public String getGroupsToSend() {
	return groupsToSend;
}


public void setGroupsToSend(String groupsToSend) {
	this.groupsToSend = groupsToSend;
}




public String getMessageGroup() {
	return messageGroup;
}


public void setMessageGroup(String messageGroup) {
	this.messageGroup = messageGroup;
}


public String getMessageIndividual() {
	return messageIndividual;
}


public void setMessageIndividual(String messageIndividual) {
	this.messageIndividual = messageIndividual;
}


public String getMessageBirthday() {
	return messageBirthday;
}


public void setMessageBirthday(String messageBirthday) {
	this.messageBirthday = messageBirthday;
}


public String getGroupsSelect() {
	return groupsSelect;
}


public void setGroupsSelect(String groupsSelect) {
	this.groupsSelect = groupsSelect;
}


public String getGroupsAllocated() {
	return groupsAllocated;
}


public void setGroupsAllocated(String groupsAllocated) {
	this.groupsAllocated = groupsAllocated;
}


public String getSubject() {
	return subject;
}


public void setSubject(String subject) {
	this.subject = subject;
}


public Integer getGroupIdMembers() {
	return groupIdMembers;
}


public void setGroupIdMembers(Integer groupIdMembers) {
	this.groupIdMembers = groupIdMembers;
}


public String getMemberIdToSms() {
	return memberIdToSms;
}


public void setMemberIdToSms(String memberIdToSms) {
	this.memberIdToSms = memberIdToSms;
}


public String getIndividualsSms() {
	return individualsSms;
}


public void setIndividualsSms(String individualsSms) {
	this.individualsSms = individualsSms;
}


public String getMessage() {
	return message;
}


public void setMessage(String message) {
	this.message = message;
}


public Integer getTab() {
	return tab;
}


public void setTab(Integer tab) {
	this.tab = tab;
}


public String getBirthdaySms() {
	return birthdaySms;
}


public void setBirthdaySms(String birthdaySms) {
	this.birthdaySms = birthdaySms;
}


public String getGroupSms() {
	return groupSms;
}


public void setGroupSms(String groupSms) {
	this.groupSms = groupSms;
}


	

}
