package com.aes.data.actions;

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
import com.aes.data.domain.User;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.aes.service.MailSender;
import com.aes.utils.SurveyStatus;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.dsn.message_deliverystatus;
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
	private String stringToSearch;

	private Logger logger = Logger.getLogger(SurveyActions.class.getName());
	private String surveyName;
	private String surveyNameToDelete;

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
			notifyDirectors(survey);
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

	private void notifyDirectors(Survey survey) throws PersistanceException {
		Role role = HhiService.getRoleById(HhiService.DIRECTOR_ROLE);
		List<User> users = new ArrayList<User>(role.getUsers());
		// String clientName = ((Client)
		// survey.getClients().toArray()[0]).getClientName();
		String clientName = survey.getClient().getClientName();
		dispatchMessages(users, clientName);

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

	public String exportSurveys() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("surveyContent", "view");
		return Action.SUCCESS;
	}

	private void dispatchMessages(List<User> users, String clientName) {
		ServletContext ctx = ServletActionContext.getServletContext();
		MailSender mailSender = new MailSender(ctx, users, HhiService.SURVEY_CREATED_MSG.replace("?client", clientName),
				HhiService.SURVEY_CREATED_SBJ.replace("?client", clientName));
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
		if (isAdminUser(request)) {
			surveys = getAdminJobs();
		}

		
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

	private List<Survey> getAdminJobs() throws PersistanceException {
		List<Survey> surveys;
		surveys = HhiService.getNewSurveys();
		return surveys;

	}

	private boolean isAdminUser(HttpServletRequest request) {
		return request.isUserInRole(HhiService.ADMIN_ROLE);

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

}
