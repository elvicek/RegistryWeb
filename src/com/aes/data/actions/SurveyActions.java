package com.aes.data.actions;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.struts2.ServletActionContext;

import com.aes.data.domain.Client;
import com.aes.data.domain.Dbsettings;
import com.aes.data.domain.Person;
import com.aes.data.domain.Role;
import com.aes.data.domain.Survey;
import com.aes.data.domain.SurveyReadings;
import com.aes.data.domain.User;
import com.aes.exceptions.PersistanceException;
import com.aes.local.model.MailMessage;
import com.aes.local.model.UserDetails;
import com.aes.service.HhiService;
import com.aes.service.MailSender;
import com.aes.utils.SurveyStatus;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.dsn.message_deliverystatus;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.xml.internal.ws.resources.DispatchMessages;

public class SurveyActions extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Survey survey;
	private String clientName;
	private String surveyRequestedDate;
	private String editSurveyType;
	private String editUnitType;
	private String editUnit;
	private String stringToSearch;

	private Logger logger = Logger.getLogger(SurveyActions.class.getName());
	private String surveyName;
	private String surveyNameToDelete;
	private String mode;
	private String userName;
	private SurveyReadings surveyReading;
	private String readingId;

	
	public String saveSurvey() {

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		Date localBirthday = null;

		setSurveyAuditData(request);

		try {

			localBirthday = sdf.parse(this.surveyRequestedDate);
			User adminUser = HhiService.getUserByUserName("admin");
			Client client = HhiService.getClientByClientName(clientName);
			// this.survey.getClients().add(client);
			this.survey.setClient(client);
			this.survey.setUser(adminUser);
			this.survey.setSurveyRequestedDate(localBirthday);
			survey.setStatus(SurveyStatus.NEW.toString());
			HhiService.save(survey);
			String message =  HhiService.SURVEY_CREATED_MSG.replace("?client", client.getClientName());
			String subject = HhiService.SURVEY_CREATED_SBJ.replace("?client", client.getClientName());
		    notifyUsers(getDirectors(), message, subject);
		} catch (PersistanceException e1) {
			logger.log(Level.SEVERE,
					"Database Error Occures. Please ensure you have connection to database or record does not exist");
			e1.printStackTrace();
			return Action.ERROR;

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.ERROR;
		}

		request.getSession().setAttribute("memberMessage", "saveRecord");
		request.getSession().setAttribute("surveyContent", "success");

		return Action.SUCCESS;
	}
	
	
	public String saveReading() {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		setReadingAuditData(request);

		try {

			Survey survey = HhiService.getSurveyBySurveyName(surveyName);
			this.surveyReading.setSurvey(survey);
			BigDecimal dynamicPressure = calculateDynamicPressure(surveyReading);
			surveyReading.setDynamicPressure(dynamicPressure);
			HhiService.save(surveyReading);
		} catch (PersistanceException e1) {
			logger.log(Level.SEVERE,
					"Database Error Occures. Please ensure you have connection to database or record does not exist");
			e1.printStackTrace();
			return Action.ERROR;

		} 

		request.setAttribute("memberMessage", "Reading saved successfully");
		request.getSession().setAttribute("surveyContent", "success");
		request.getSession().setAttribute("surveyInEdit", surveyName);

		return Action.SUCCESS;
	}
	
	public String updateReading() {

		HttpServletRequest request = ServletActionContext.getRequest();
		
		setReadingAuditData(request);

		try {

			Survey survey = HhiService.getSurveyBySurveyName(surveyName);
			this.surveyReading.setSurvey(survey);
			BigDecimal dynamicPressure = calculateDynamicPressure(surveyReading);
			surveyReading.setDynamicPressure(dynamicPressure);
			HhiService.update(surveyReading);
		} catch (PersistanceException e1) {
			logger.log(Level.SEVERE,
					"Database Error Occures. Please ensure you have connection to database or record does not exist");
			e1.printStackTrace();
			return Action.ERROR;

		} 

		request.setAttribute("memberMessage", "Reading updated successfully");
		request.getSession().setAttribute("surveyContent", "success");
		request.getSession().setAttribute("surveyInEdit", surveyName);

		return Action.SUCCESS;
	}

	private BigDecimal calculateDynamicPressure(SurveyReadings surveyReading) {

		BigDecimal dp = (new BigDecimal("0.5").multiply(surveyReading.getDensity()).multiply(surveyReading.getVelocity()).multiply(surveyReading.getVelocity()));
		return dp.setScale(2, BigDecimal.ROUND_UP);
	}


	private void setReadingAuditData(HttpServletRequest request) {
		String principal = request.getUserPrincipal().getName();
		Date date = new Date();
		this.surveyReading.setCreatedBy(principal);
		this.surveyReading.setCreatedDate(date);
		
	}


	private List<User> getDirectors() throws PersistanceException {
		Role role = HhiService.getRoleById(HhiService.DIRECTOR_ROLE);
		return new ArrayList<User>(role.getUsers());
	}
	
	private void notifyUsers(List<User> users,String message,String subject){
		dispatchMessages(users,message,subject);

	}
	
	private void notifyUsers(MailMessage mailMessage) {
		dispatchMessages(mailMessage.getUsers(),mailMessage.getMessage(),mailMessage.getSubject());

	}

	public String surveyEdit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Survey surveyToEdit = retrieveSurveyToEdit();

		session.setAttribute("survey", surveyToEdit);

		session.setAttribute("surveyContent", "edit");
		return Action.SUCCESS;
	}
	
	public String surveyEditReadings() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Survey aSurvey = null;
		try{
			aSurvey = HhiService.getSurveyBySurveyName(surveyName);
			
		}
		catch(PersistanceException e){
			return Action.ERROR;
		}

		session.setAttribute("survey", aSurvey);

		session.setAttribute("surveyContent", "readingsEdit");
		return Action.SUCCESS;
	}
	
	public String editReadingValues() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Survey aSurvey = null;
		SurveyReadings readings = null;
		try{
			aSurvey = HhiService.getSurveyBySurveyName(surveyName);
			readings = HhiService.getSurveyreadingById(Integer.valueOf(readingId));
			
		}
		catch(PersistanceException e){
			return Action.ERROR;
		}

		session.setAttribute("survey", aSurvey);
		session.setAttribute("surveyReading", readings);
		session.setAttribute("surveyContent", "readingsEdit");
		request.setAttribute("readingsMode", "edit");
		return Action.SUCCESS;
	}
	
	public List<SurveyReadings> getReadings(){
		Survey aSurvey = null;
		try{
			aSurvey = HhiService.getSurveyBySurveyName(surveyName);
			
		}
		catch(PersistanceException e){
			
		}

		return new ArrayList<SurveyReadings>(aSurvey.getSurveyReadingses());
	}
	
	public String surveyAttend() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Survey surveyToEdit = retrieveSurveyToEdit();
		
		if(request.isUserInRole(HhiService.DIRECTOR_ROLE) && surveyToEdit.getStatus().equalsIgnoreCase(SurveyStatus.NEW.toString()))
		{
			request.setAttribute("mode", "assign");
		}
		else{
			request.setAttribute("mode", "changeStatus");
			
		}

		
		session.setAttribute("survey", surveyToEdit);

		session.setAttribute("surveyContent", "attend");
		return Action.SUCCESS;
	}

	public String updateSuccessAction() {
		logger.log(Level.INFO, "Updated Record successfully");
		return Action.SUCCESS;

	}

	private Survey retrieveSurveyToEdit() {
		Survey survey = null;
		DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			survey = HhiService.getSurveyBySurveyName(surveyName);
			Client client = survey.getClient();
			this.clientName = client.getClientName();
			this.editSurveyType = survey.getSurveyType();

			if (survey.getSurveyRequestedDate() != null) {
				this.surveyRequestedDate = sdf.format(survey.getSurveyRequestedDate());
			}

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return survey;
	}

	public String updateSurvey() {

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date localBirthday = null;
		setSurveyAuditData(request);

		try {
			User adminUser = HhiService.getUserByUserName("admin");
			localBirthday = sdf.parse(this.surveyRequestedDate);
			this.survey.setSurveyRequestedDate(localBirthday);
			this.survey.setSurveyId(HhiService.getSurveyBySurveyName(survey.getSurveyName()).getSurveyId());
			Client aClient = HhiService.getClientByClientName(clientName);
			survey.setClient(aClient);
			survey.setUser(adminUser);
			HhiService.update(survey);
		} catch (Exception e) {
			return Action.ERROR;

		}

		request.getSession().setAttribute("memberMessage", "updateRecord");
		request.getSession().setAttribute("surveyContent", "success");
		return Action.SUCCESS;

	}public String updateSurveyStatus() {

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date localBirthday = null;
		MailMessage mailMessage = null;
		setSurveyAuditData(request);

		try {
			Survey aSurvey = HhiService.getSurveyBySurveyName(survey.getSurveyName());
			if(this.mode.equalsIgnoreCase("assign")){
				mailMessage = assignSurvey(aSurvey);
			}
			else{
				mailMessage = changeStatus(aSurvey);
			}
			
			HhiService.update(aSurvey);
			notifyUsers(mailMessage);
		} catch (Exception e) {
			return Action.ERROR;

		}

		request.getSession().setAttribute("memberMessage", "updateRecordStatus");
		request.getSession().setAttribute("surveyContent", "success");
		return Action.SUCCESS;

	}
	
	

	private MailMessage assignSurvey(Survey aSurvey) throws PersistanceException {
		User user = HhiService.getUserByUserName(userName);
		aSurvey.setUser(user);
		aSurvey.setStatus(SurveyStatus.ASSIGNED.toString());
		String subject = HhiService.SURVEY_ASSIGNED_SBJ.replace("?client", aSurvey.getClient().getClientName());
		String message = HhiService.SURVEY_ASSIGNED_MSG.replace("?client", aSurvey.getClient().getClientName());
		List<User> users = new ArrayList<User>();
		users.add(user);
		return new MailMessage(users, message, subject)	;	
	}

	private MailMessage changeStatus(Survey aSurvey) throws PersistanceException {
		String subject = HhiService.SURVEY_STATUS_SBJ.replace("?client", aSurvey.getClient().getClientName());
		MailMessage mailMessage = null;
		if(aSurvey.getStatus().equalsIgnoreCase(SurveyStatus.ASSIGNED.toString())){
			aSurvey.setStatus(SurveyStatus.IN_PROGRESS.toString());	
			String message = HhiService.SURVEY_STATUS_MSG.replace("?client", aSurvey.getClient().getClientName()).replace("?status", SurveyStatus.IN_PROGRESS.name());
			mailMessage = new MailMessage(getDirectors(),message,subject);
		}
		else if (aSurvey.getStatus().equalsIgnoreCase(SurveyStatus.IN_PROGRESS.toString())) {
			aSurvey.setStatus(SurveyStatus.COMPLETE.toString());
			String message = HhiService.SURVEY_STATUS_MSG.replace("?client", aSurvey.getClient().getClientName()).replace("?status", SurveyStatus.COMPLETE.name());
			mailMessage = new MailMessage(getDirectors(),message,subject);
		}
		
		return mailMessage;
		
	}

	public String surveySearchAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("seachString", stringToSearch);
		return Action.SUCCESS;
	}

	public String deleteSuccessAction() {
		logger.log(Level.INFO, "Deleted Record successfully");
		return Action.SUCCESS;

	}

	public String deleteSurvey() {
		String[] surveysToDelete = null;

		if (surveyNameToDelete.split(":").length == 0) {

			surveysToDelete[0] = surveyNameToDelete;

		} else {

			surveysToDelete = surveyNameToDelete.split(":");

		}

		for (String name : surveysToDelete) {

			HhiService.deleteSurveyByName(name);

		}

		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("memberMessage", "deleteRecord");
		request.getSession().setAttribute("surveyContent", "success");
		return Action.SUCCESS;
	}
	
	public String deleteSurveyReading() {
		String[] readingsToDelete = null;

		if (readingId.split(":").length == 0) {

			readingsToDelete[0] = readingId;

		} else {

			readingsToDelete = readingId.split(":");

		}

		for (String id : readingsToDelete) {

			HhiService.deleteSurveyReadingById(Integer.valueOf(id));

		}

		HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute("memberMessage", "Reading deleted successfully");
		request.getSession().setAttribute("surveyContent", "success");
		return Action.SUCCESS;
	}

	public String exportSurveys() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("surveyContent", "view");
		return Action.SUCCESS;
	}

	private void dispatchMessages(List<User> users, String message, String subject) {
		ServletContext ctx = ServletActionContext.getServletContext();
		MailSender mailSender = new MailSender(ctx, users,message,subject);
		Thread thread = new Thread(mailSender);
		thread.start();

	}

	public String viewSurveys() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<Survey> surveys = null;
		HttpSession session = request.getSession();
		session.setAttribute("surveyContent", "view");
		String setting;

		try {
			surveys = HhiService.getAllSurveys();
			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("surveyContent", "error");
			e.printStackTrace();
			return Action.ERROR;
		}
		session.setAttribute("pageSize", setting);
		// If a search was initiated from the search bar;

		/*
		 * if (this.stringToSearch == null ||
		 * !(this.stringToSearch.trim().length() > 0)) {
		 * request.setAttribute("surveys", surveys);
		 */
		if (request.getAttribute("seachString") == null) {
			request.setAttribute("surveys", surveys);
		} else {

			stringToSearch = (String) request.getAttribute("seachString");
			List<Survey> trimmedSurveys = new ArrayList<Survey>();

			for (Survey matchSurvey : surveys) {
				/*
				 * if
				 * (((Client)matchSurvey.getClients().toArray()[0]).getPerson().
				 * getName().toUpperCase().contains(stringToSearch.toUpperCase()
				 * )
				 * 
				 * ||
				 * ((Client)matchSurvey.getClients().toArray()[0]).getPerson().
				 * getSurname().toUpperCase().contains(stringToSearch.
				 * toUpperCase()) ||
				 * matchSurvey.getSurveyName().toUpperCase().contains(
				 * stringToSearch.toUpperCase()) ||
				 * ((Client)matchSurvey.getClients().toArray()[0]).getClientName
				 * ().contains(stringToSearch.toUpperCase())) {
				 * trimmedSurveys.add(matchSurvey);
				 */
				if (matchSurvey.getClient().getPerson().getName().toUpperCase().contains(stringToSearch.toUpperCase())
						|| matchSurvey.getClient().getPerson().getSurname().toUpperCase()
								.contains(stringToSearch.toUpperCase())
						|| matchSurvey.getSurveyName().toUpperCase().contains(stringToSearch.toUpperCase())
						|| matchSurvey.getClient().getClientName().contains(stringToSearch.toUpperCase())) {
					trimmedSurveys.add(matchSurvey);
				}
			}

			request.setAttribute("surveys", trimmedSurveys);
		}
		return Action.SUCCESS;
	}

	public String viewMyJobs() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<Survey> surveys = null;
		HttpSession session = request.getSession();
		session.setAttribute("surveyContent", "viewJobs");
		String setting;

		try {
		if (isDirectorUser(request)) {
			surveys = getDirectorJobs();
		}
		else {
			surveys = getNewJobsByUser(request);
		}

			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("surveyContent", "error");
			e.printStackTrace();
			return Action.ERROR;
		}
		session.setAttribute("pageSize", setting);
		// If a search was initiated from the search bar;

			request.setAttribute("surveys", surveys);
		
		return Action.SUCCESS;
	}

	private List<Survey> getNewJobsByUser(HttpServletRequest request) throws PersistanceException {
		String username = request.getUserPrincipal().getName();
		
		return HhiService.getCurrentJobsByUser(username);
	}

	private List<Survey> getDirectorJobs() throws PersistanceException {
		List<Survey> surveys;
		surveys = HhiService.getNewSurveys();
		return surveys;

	}

	private boolean isDirectorUser(HttpServletRequest request) {
		return request.isUserInRole(HhiService.DIRECTOR_ROLE);

	}

	public String saveSuccessAction() {
		logger.log(Level.INFO, "Saved Record successfully");
		return Action.SUCCESS;

	}

	public String createSurvey() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("surveyContent", "input");
		return Action.SUCCESS;
	}

	private void setSurveyAuditData(HttpServletRequest request) {
		String principal = request.getUserPrincipal().getName();
		Date date = new Date();
		this.survey.setCreatedBy(principal);
		this.survey.setCreatedDate(date);

	}

	public void validate() {

	}

	public List<Client> getClients() {

		List<Client> clients = null;
		try {
			List<Client> fullList = HhiService.getAllClients();

			if (clientName == null) {
				clients = fullList;
			} else {
				clients = removeEditClient(fullList);
			}

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clients;
	}
	
	public List<UserDetails> getUserDetails() {

		List<UserDetails> userDetails = new ArrayList<UserDetails>();
		try {
			List<User> fullList = HhiService.getAllUsers();

			for(User user :fullList){
				if( ((Role)user.getRoles().toArray()[0]).getRoleName().equalsIgnoreCase(HhiService.ENGINEER_ROLE)){
				UserDetails details = new UserDetails();
				details.setDisplayName(user.getPerson().getName()+" "+user.getPerson().getSurname()+" : "+user.getUsername());
				details.setUserName(user.getUsername());
				userDetails.add(details);
				}
				
			}

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userDetails;
	}

	private List<Client> removeEditClient(List<Client> fullList) {
		List<Client> trimmedList = new ArrayList<Client>();

		for (Client client : fullList) {
			if (!client.getClientName().equalsIgnoreCase(clientName)) {
				trimmedList.add(client);
			}
		}

		return trimmedList;
	}

	public List<Dbsettings> getSurveyTypes() {

		List<Dbsettings> surveyTypes = null;
		try {
			if (this.editSurveyType == null) {
				surveyTypes = HhiService.getDbSetting(HhiService.SURVEY_TYPE);
			} else {
				surveyTypes = HhiService.getDbSetting(HhiService.SURVEY_TYPE, editSurveyType);

			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return surveyTypes;
	}
	
	public List<Dbsettings> getReadingTypes() {

		List<Dbsettings> readingTypes = null;
		try {
			if (this.editSurveyType == null) {
				readingTypes = HhiService.getDbSetting(HhiService.UNIT_TYPE);
			} else {
		
				readingTypes = HhiService.getDbSetting(HhiService.UNIT_TYPE, editUnitType);

			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Dbsettings>();
	}

	
	public List<Dbsettings> getUnits() {

		List<Dbsettings> unit = null;
		try {
			if (this.editSurveyType == null) {
				unit = HhiService.getDbSetting(HhiService.UNIT);
			} else {
				unit = HhiService.getDbSetting(HhiService.UNIT, editUnit);

			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new ArrayList<Dbsettings>();
	}


	public Survey getSurvey() {
		return survey;
	}

	public void setSurvey(Survey survey) {
		this.survey = survey;
	}

	public String getSurveyRequestedDate() {
		return surveyRequestedDate;
	}

	public void setSurveyRequestedDate(String surveyRequestedDate) {
		this.surveyRequestedDate = surveyRequestedDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getEditSurveyType() {
		return editSurveyType;
	}

	public void setEditSurveyType(String editSurveyType) {
		this.editSurveyType = editSurveyType;
	}

	public String getStringToSearch() {
		return stringToSearch;
	}

	public void setStringToSearch(String stringToSearch) {
		this.stringToSearch = stringToSearch;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getSurveyNameToDelete() {
		return surveyNameToDelete;
	}

	public void setSurveyNameToDelete(String surveyNameToDelete) {
		this.surveyNameToDelete = surveyNameToDelete;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEditUnitType() {
		return editUnitType;
	}

	public void setEditUnitType(String editUnitType) {
		this.editUnitType = editUnitType;
	}

	public String getEditUnit() {
		return editUnit;
	}

	public void setEditUnit(String editUnit) {
		this.editUnit = editUnit;
	}


	public SurveyReadings getSurveyReading() {
		return surveyReading;
	}


	public void setSurveyReading(SurveyReadings surveyReading) {
		this.surveyReading = surveyReading;
	}


	public String getReadingId() {
		return readingId;
	}


	public void setReadingId(String readingId) {
		this.readingId = readingId;
	}


	

	

}
