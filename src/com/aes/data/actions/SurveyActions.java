package com.aes.data.actions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.aes.data.domain.Survey;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.opensymphony.xwork2.Action;

public class SurveyActions {
	
	private Survey survey;
	private String client;
	private String surveyRequestedDate;
	
	private Logger logger = Logger.getLogger(SurveyActions.class.getName());
	
	
	
	
	
	public String saveClient() {

		// this.member.setSex(sex);

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		Date localBirthday = null;

		//setClientAuditData(request);

		try {

			localBirthday = sdf.parse(this.surveyRequestedDate);
			this.survey.setSurveyRequestedDate(localBirthday);
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
		request.getSession().setAttribute("clientContent", "success");

		return Action.SUCCESS;
	}
	
	
	
	public void validate() {
	
	}
	
	
	
	
	
	
	public Survey getSurvey() {
		return survey;
	}
	public void setSurvey(Survey survey) {
		this.survey = survey;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getSurveyRequestedDate() {
		return surveyRequestedDate;
	}
	public void setSurveyRequestedDate(String surveyRequestedDate) {
		this.surveyRequestedDate = surveyRequestedDate;
	}
	
	
	

}
