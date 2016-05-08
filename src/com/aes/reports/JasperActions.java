package com.aes.reports;

import com.aes.data.domain.Client;
import com.aes.data.domain.Survey;
import com.aes.data.domain.SurveyReadings;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

public class JasperActions extends ActionSupport{
	
	/** List to use as our JasperReports dataSource. */
	private List<Client> allClients;
	private Map<String, Object> reportParams=null;
	private String clientName;
	private String surveyName;
	/** List to use as our JasperReports dataSource. */
	private List<SurveyReadings> surveyReadings;
	private String pageSizeOption;
	private String fromDate;
	private String toDate;
	private String stringToSearch;
	
	
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
	
	
	
	public String pagerAction() {

		try {
			HhiService.updateDbSetting(HhiService.PAGER_OPTION, pageSizeOption);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Action.SUCCESS;
	}
	
	public String viewClients() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<Client> clients = null;
		HttpSession session = request.getSession();
		session.setAttribute("reportsContent", "clientsview");
		String setting;

		try {
			clients = HhiService.getAllClients();
			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("reportsContent", "error");
			e.printStackTrace();
			return Action.ERROR;
		}
		session.setAttribute("pageSize", setting);
		request.setAttribute("clients", clients);
		
		
		return Action.SUCCESS;
	}
	
	public String execute() throws Exception {
		 
 
        return Action.SUCCESS;
    }
	
	public String menuAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("reportsContent", "menu");

		return Action.SUCCESS;
	}
	
	public String viewSurveys() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<Survey> surveys = null;
		HttpSession session = request.getSession();
		session.setAttribute("reportsContent", "surveysview");
		String setting;

		try {
			surveys = HhiService.getAllStartedSurveys();
			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("reportsContent", "error");
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


	public List<SurveyReadings> getSurveyReadings(){
		List<SurveyReadings> surveyReadings = new ArrayList<SurveyReadings>();
		if(surveyName != null){
			try{
				Survey survey = HhiService.getSurveyBySurveyName(surveyName);
				surveyReadings = new ArrayList<SurveyReadings>(survey.getSurveyReadingses());
			}
			catch(PersistanceException e){
				
			}
			
		}
		return surveyReadings;
	}
	
	public String getTitle(){
		Survey survey = null;
		
		try{
			survey = HhiService.getSurveyBySurveyName(surveyName);
			
		}
		catch(PersistanceException e){
			
		}
		String title = "AES Survey Report for Survey "+surveyName;
		
		return survey == null ? title : title+" as at "+survey.getSurveyDate()+
				"Status :"+survey.getStatus();
		
	}
	
	public Map<String, Object> getReportParams(){
		reportParams = new HashMap<String, Object>();
		
        reportParams.put("Title", getTitle());
        return reportParams;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getStringToSearch() {
		return stringToSearch;
	}

	public void setStringToSearch(String stringToSearch) {
		this.stringToSearch = stringToSearch;
	}



	public void setReportParams(Map<String, Object> reportParams) {
		this.reportParams = reportParams;
	}
}
