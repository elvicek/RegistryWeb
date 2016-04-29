package com.aes.data.actions;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.aes.business.CellgroupEditData;
import com.aes.business.MemberEditData;
import com.aes.business.SendSMS;
import com.aes.data.domain.Address;
import com.aes.data.domain.Client;
import com.aes.data.domain.Dbsettings;
import com.aes.data.domain.Person;
import com.aes.data.domain.Role;
import com.aes.data.domain.User;
import com.aes.exceptions.UsersExistInRoleException;
import com.aes.exceptions.PersistanceException;
import com.aes.local.model.BirthdayCalendar;
import com.aes.service.HhiService;
import com.aes.service.MailServiceClient;
import com.aes.utils.BirthdayCalendarComparator;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Member;
import com.hhiregistry.model.MemberGroups;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class ClientActions extends ActionSupport {

	private String clientName;
	private String groupName;
	private String clientNameToDelete;
	private Role role;
	private Client client;
	private Person person;
	private String editTitle;
	private Address address;
	private String birthday;
	private Boolean validationRequest;
	private Address clientAddress;

	private Integer tab;
	private String stringToSearch;
	private String pageSizeOption;
	private String editMaritalStatus;
	private String confirm;

	private Logger logger = Logger.getLogger(ClientActions.class.getName());

	public String menuAction() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("clientContent", "menu");

		return Action.SUCCESS;
	}

	public String clientInput() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("clientContent", "input");
		return Action.SUCCESS;
	}

	public String clientEdit() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		Client clientToEdit = retrieveClientToEdit();
		this.person = clientToEdit.getPerson();
		this.address = person.getAddress();
		this.clientAddress = clientToEdit.getAddress();
		
		session.setAttribute("client", clientToEdit);
		
		
		session.setAttribute("clientContent", "edit");
		return Action.SUCCESS;
	}

	private Client retrieveClientToEdit() {
		Client client = null;
		DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		try {
			client = HhiService.getClientByClientName(clientName);
			Person person = client.getPerson();

			this.editTitle = person.getTitle();

			if (person.getBirthday() != null) {
				this.birthday = sdf.format(person.getBirthday());
			}

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return client;
	}

	public String viewUsers() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<User> users = null;
		HttpSession session = request.getSession();
		session.setAttribute("memberContent", "userview");
		String setting;

		try {
			users = HhiService.getAllUsers();
			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("memberContent", "error");
			e.printStackTrace();
			return Action.ERROR;
		}
		session.setAttribute("pageSize", setting);
		// If a search was initiated from the search bar;
		if (this.stringToSearch == null || !(this.stringToSearch.trim().length() > 0)) {
			request.setAttribute("users", users);
		} else {
			// System.out.println("::::::::::::::::::Executed NOT
			// NULL::::::::::::::::::::"+stringToSearch);
			List<User> trimmedMembers = new ArrayList<User>();

			for (User matchUser : users) {
				if (matchUser.getPerson().getName().toUpperCase().contains(stringToSearch.toUpperCase())
						|| matchUser.getPerson().getSurname().toUpperCase().contains(stringToSearch.toUpperCase())) {
					trimmedMembers.add(matchUser);
				}
			}

			request.setAttribute("users", trimmedMembers);
		}
		return Action.SUCCESS;
	}

	public String exportClients() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		session.setAttribute("memberContent", "clientview");
		return Action.SUCCESS;
	}




	public String saveClient() {

		// this.member.setSex(sex);

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

		Date localBirthday = null;

		setClientAuditData(request);

		try {

			localBirthday = sdf.parse(this.birthday);
			this.person.setBirthday(localBirthday);
			person.setAddress(address);
			client.setAddress(this.clientAddress);
			client.setPerson(person);
			HhiService.save(client);
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

	public String viewClients() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		List<Client> clients = null;
		HttpSession session = request.getSession();
		session.setAttribute("clientContent", "view");
		String setting;

		try {
			clients = HhiService.getAllClients();
			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			session.setAttribute("clientContent", "error");
			e.printStackTrace();
			return Action.ERROR;
		}
		session.setAttribute("pageSize", setting);
		// If a search was initiated from the search bar;
		if (this.stringToSearch == null || !(this.stringToSearch.trim().length() > 0)) {
			request.setAttribute("clients", clients);
		} else {

			List<Client> trimmedClients = new ArrayList<Client>();

			for (Client matchClient : clients) {
				if (matchClient.getPerson().getName().toUpperCase().contains(stringToSearch.toUpperCase())
						|| matchClient.getPerson().getSurname().toUpperCase().contains(stringToSearch.toUpperCase())) {
					trimmedClients.add(matchClient);
				}
			}

			request.setAttribute("clients", trimmedClients);
		}
		return Action.SUCCESS;
	}

	public String updateClient() {

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date localBirthday = null;
		setClientAuditData(request);

		person.setAddress(address);
		client.setPerson(person);
		client.setAddress(clientAddress);

		try {
			localBirthday = sdf.parse(this.birthday);
			this.person.setBirthday(localBirthday);
			Client aClient = HhiService.getClientByClientName(client.getClientName());
			person.setPersonId(aClient.getPerson().getPersonId());
			address.setAddressId(aClient.getPerson().getAddress().getAddressId());
			client.setClientId(aClient.getClientId());
			HhiService.update(client);
		} catch (Exception e) {
			return Action.ERROR;

		}

		request.getSession().setAttribute("memberMessage", "updateRecord");
		request.getSession().setAttribute("clientContent", "success");
		return Action.SUCCESS;

	}

	public String deleteClient() {
		String[] clientsToDelete = null;

		if (clientNameToDelete.split(":").length == 0) {

			clientsToDelete[0] = clientNameToDelete;

		} else {

			clientsToDelete = clientNameToDelete.split(":");

		}

		for (String name : clientsToDelete) {

			HhiService.deleteClient(name);

		}

		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("memberMessage", "deleteRecord");
		request.getSession().setAttribute("clientContent", "success");
		return Action.SUCCESS;
	}

	private void setClientAuditData(HttpServletRequest request) {
		String principal = request.getUserPrincipal().getName();
		this.client.setCreatedBy(principal);
		Date date = new Date();
		this.client.setCreatedDate(date);
		this.person.setCreatedBy(principal);
		this.person.setCreatedDate(date);
		this.address.setCreatedBy(principal);
		this.address.setCreatedDate(date);
	}
	
	

	public String saveSuccessAction() {
		logger.log(Level.INFO, "Saved Record successfully");
		return Action.SUCCESS;

	}

	public String deleteSuccessAction() {
		logger.log(Level.INFO, "Deleted Record successfully");
		return Action.SUCCESS;

	}

	public String updateSuccessAction() {
		logger.log(Level.INFO, "Updated Record successfully");
		return Action.SUCCESS;

	}

	public List<Groups> getOptions() {

		List<Groups> groups = null;

		groups = HhiService.getGroups();
		// System.out.println("List Size "+groups.size());

		return groups;
	}

	public List<Dbsettings> getTitles() {

		List<Dbsettings> titles = null;
		try {
			if (this.editTitle == null) {
				titles = HhiService.getDbSetting(HhiService.TITLES);
			} else {
				titles = HhiService.getDbSetting(HhiService.TITLES, editTitle);

			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return titles;
	}

	public List<Dbsettings> getMaritalStatus() {

		List<Dbsettings> maritalStatus = null;
		try {
			if (this.editMaritalStatus == null) {
				maritalStatus = HhiService.getDbSetting(HhiService.SURVEY_STATUS);
			} else {
				maritalStatus = HhiService.getDbSetting(HhiService.SURVEY_STATUS, editMaritalStatus);
			}

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maritalStatus;
	}

	public List<Dbsettings> getSexes() {

		List<Dbsettings> sexes = null;
		try {
			sexes = HhiService.getDbSetting(HhiService.SEX);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sexes;
	}

	private List<Member> getAllSelectedMembers(String[] ids) {
		List<Member> members = null;
		List<Member> allMembers = new ArrayList<Member>();
		Member member = null;

		for (int i = 0; i < ids.length; i++) {
			Groups groups = null;
			String trimmmeId = ids[i].trim();
			// System.out.println("ID's "+ids[i]);
			try {
				if ((ids[i] != "00") && (trimmmeId.length() > 0))
					;

				// groups = HhiService.getRoleById(Integer.valueOf(ids[i]));

				// if(groups.getMembers().size() > 1){
				members = new ArrayList<Member>(groups.getMembers());

				for (Member memberAdd : members) {
					if (memberAdd.getEmail() != null) {

						allMembers.add(member);
					}
				}

			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return allMembers;
	}

	private String returnGroupIdWithMember(String[] ids) {

		for (int i = 0; i < ids.length; i++) {
			Groups groups = null;
			String trimmmeId = ids[i].trim();
			// System.out.println("ID's "+ids[i]);
			try {
				if ((ids[i] != "00") && (trimmmeId.length() > 0))
					;
				{

				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// } catch (PersistanceException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }

		}

		return null;
	}

	public Integer getPageSize() {

		String setting = null;
		int i = 1;
		HttpServletRequest request = ServletActionContext.getRequest();
		ServletContext ctx = request.getSession().getServletContext();
		try {

			setting = HhiService.getDbSettingDescription(HhiService.PAGER_OPTION);

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Integer.valueOf(setting);
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

	public String writeAddresses() {

		ServletContext ctx = ServletActionContext.getServletContext();
		HttpSession session = ServletActionContext.getRequest().getSession();
		try {
			List<Client> clients = HhiService.getAllClients();
			FileWriter outFile = new FileWriter(HhiService.EMAIL_ADDRESSES_TEXT_FILE);
			PrintWriter out = new PrintWriter(outFile);

			for (Client client : clients) {

				if (client.getPerson().getEmail() != null) {

					out.println(client.getPerson().getEmail());
				}

			}

			out.close();

		} catch (IOException e) {
			e.printStackTrace();

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		session.setAttribute("adminContent", "write");
		return Action.SUCCESS;

	}

	public void validate() {

		HttpServletRequest request = ServletActionContext.getRequest();
		if (request.getRequestURI().contains("clientsave")) {

			try {
				String clientName = request.getParameter("client.clientName");
				Client client = HhiService.getClientByClientName(clientName);

				if (client != null) {

					logger.log(Level.SEVERE, "Validating Condition Errorror:::::::::::::::::::: "
							+ request.getParameter("client.clientName"));
					addFieldError("client.clientName", "Client" + "'" + client.getClientName()
							+ "' has been taken and aready exists in Database.");
				}

			} catch (PersistanceException e) {

				logger.log(Level.SEVERE, e.getMessage());
			}
		}
	}

	public List<User> getUsers() {

		List<User> users = null;
		try {
			users = HhiService.getAllUsers();
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return users;

	}

	public List<BirthdayCalendar> getBirthdayCalendar() {

		List<User> users = null;
		List<BirthdayCalendar> birthdayCalendar = new ArrayList<BirthdayCalendar>();
		try {
			users = HhiService.getAllUsers();

		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (User user : users) {

			DateTime birthdate = new DateTime(user.getPerson().getBirthday());
			DateTime currentDate = new DateTime();

			if (user.getPerson().getBirthday() != null && (birthdate.getMonthOfYear() == currentDate.getMonthOfYear())
					&& (0 <= (birthdate.getDayOfMonth() - currentDate.getDayOfMonth())
							&& (birthdate.getDayOfMonth() - currentDate.getDayOfMonth()) <= 7)) {
				// int diff =
				// birthdate.getDayOfMonth()-currentDate.getDayOfMonth();
				System.out.println("Member Qualified Bithday " + user.getPerson().getName() + " "
						+ user.getPerson().getSurname() + " " + user.getPerson().getBirthday());
				Calendar birthday = Calendar.getInstance();
				birthday.setTime(user.getPerson().getBirthday());
				int oldYear = birthday.YEAR;
				String years = String.valueOf(birthday.get(Calendar.YEAR));
				int age = currentDate.getYear() - new Integer(years);
				int margin = (birthdate.getDayOfMonth() - currentDate.getDayOfMonth());

				String day = "";
				DateTime actualBirthDay = birthdate.monthOfYear().addToCopy(12 * age);
				DateTime.Property pDoW = actualBirthDay.dayOfWeek();
				if (margin == 0) {
					day = "Today";
				} else {
					day = pDoW.getAsText();
				}

				String month = birthdate.monthOfYear().getAsText();
				String year = currentDate.year().getAsText();

				BirthdayCalendar calendar = new BirthdayCalendar(user, day, month, year, birthdate, age, margin);
				birthdayCalendar.add(calendar);

			}
			// BirthdayCalendar calendar = new BirthdayCalendar();

		}
		Collections.sort(birthdayCalendar, new BirthdayCalendarComparator());
		return birthdayCalendar;

	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public Integer getTab() {
		return tab;
	}

	public void setTab(Integer tab) {
		this.tab = tab;
	}

	public String getStringToSearch() {
		return stringToSearch;
	}

	public void setStringToSearch(String stringToSearch) {
		this.stringToSearch = stringToSearch;
	}

	public String getPageSizeOption() {
		return pageSizeOption;
	}

	public void setPageSizeOption(String pageSizeOption) {
		this.pageSizeOption = pageSizeOption;
	}

	public String getEditTitle() {
		return editTitle;
	}

	public void setEditTitle(String editTitle) {
		this.editTitle = editTitle;
	}

	public String getEditMaritalStatus() {
		return editMaritalStatus;
	}

	public void setEditMaritalStatus(String editMaritalStatus) {
		this.editMaritalStatus = editMaritalStatus;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}

	public String getClientNameToDelete() {
		return clientNameToDelete;
	}

	public void setClientNameToDelete(String clientNameToDelete) {
		this.clientNameToDelete = clientNameToDelete;
	}

	public Address getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(Address clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

}
