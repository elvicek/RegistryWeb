package com.aes.data.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.aes.data.domain.Client;
import com.aes.data.domain.Dbsettings;
import com.aes.data.domain.Survey;
import com.aes.data.domain.User;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.aes.utils.SurveyStatus;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class SurveyActions extends ActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Survey survey;
	private String clientName;
	private String surveyRequestedDate;
	private String editSurveyType;
	
	private Logger logger = Logger.getLogger(SurveyActions.class.getName());
	
	
	
	
	
	public String saveSurvey() {


		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		Date localBirthday = null;

		setSurveyAuditData(request);

		try {

			localBirthday = sdf.parse(this.surveyRequestedDate);
			User adminUser = HhiService.getUserByUserName("admin");
			Client client = HhiService.getClientByClientName(clientName);
			this.survey.getClients().add(client);
			this.survey.setUser(adminUser);
			this.survey.setSurveyRequestedDate(localBirthday);
			survey.setStatus(SurveyStatus.NEW.toString());
			HhiService.save(survey);
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
			
			if(clientName == null){
				clients = fullList;
			}
			else{
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
		
			for(Client client : fullList){
				if(!client.getClientName().equalsIgnoreCase(clientName)){
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
	
	
	
	

}
