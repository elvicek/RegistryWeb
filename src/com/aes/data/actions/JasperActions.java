package com.aes.data.actions;

import com.aes.data.domain.Client;
import com.aes.data.domain.Survey;
import com.aes.data.domain.SurveyReadings;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;

public class JasperActions extends ActionSupport{
	
	/** List to use as our JasperReports dataSource. */
	private List<Client> allClients;
	private String clientName;
	private String surveyName;
	/** List to use as our JasperReports dataSource. */
	private List<SurveyReadings> surveyReadings;
	private String pageSizeOption;
	
	public List<Client> getAllClients(){
		 List<Client> clients = new ArrayList<Client>();
	try {
		clients= HhiService.getAllClients();
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return clients;
	}
	
	public List<SurveyReadings> getSurveyReadings(){
		List<SurveyReadings> readings = new ArrayList<SurveyReadings>();
		
		try{
			
			Survey survey = HhiService.getSurveyBySurveyName(surveyName);
			readings = new ArrayList<SurveyReadings>(survey.getSurveyReadingses());
			
		}
		catch(PersistanceException e){
			
		}
		
		return readings;
	}
	
	public String pagerAction() {

		try {
			HhiService.updateDbSetting(HhiService.PAGER_OPTION, pageSizeOption);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Action.SUCCESS;
	}
	
	public String getPageSizeOption() {
		return pageSizeOption;
	}

	public void setPageSizeOption(String pageSizeOption) {
		this.pageSizeOption = pageSizeOption;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSurveyName() {
		return surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}
}
