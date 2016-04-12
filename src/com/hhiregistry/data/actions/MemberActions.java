package com.hhiregistry.data.actions;

import java.text.DateFormat;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.hhiregistry.business.MemberEditData;
import com.hhiregistry.exceptions.PersistanceException;
import com.hhiregistry.model.Address;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Hhisettings;
import com.hhiregistry.model.Member;
import com.hhiregistry.model.MemberGroups;
import com.hhiregistry.service.HhiService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class MemberActions  extends ActionSupport{
	
	
	private Member member;
	private Address address;
	private Cellgroup cellgroup;
	private String title;
	private Character sex;
	private String waterBaptised;
	private String employmentStatus;
	private String content;
	private Logger logger =  Logger.getLogger(DataActions.class.getName());
	private String birthday;
	private String convertedDate;
	private Integer memberId;
	private String editTitle;
	private String editEmploymentStatus;
	private String editMaritalStatus;
	private String editBaptised;
	private String editCellgroup;
	private String memberIdToDelete;
	private String pageSizeOption;
	private String groupsSelect;
	private String groupsAllocated;
	private String leftNumber;
	private String stringToSearch;
	

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getConvertedDate() {
		return convertedDate;
	}

	public void setConvertedDate(String convertedDate) {
		this.convertedDate = convertedDate;
	}

	/**
	 * @return the member
	 */
	public Member getMember() {
		return member;
	}

	/**
	 * @param member the member to set
	 */
	public void setMember(Member member) {
		this.member = member;
	}
	
	
	public String memberInput(){
		
		
		
		return Action.SUCCESS;
	}
	
	
public String memberAction(){
	
	HttpServletRequest request = ServletActionContext.getRequest();
	
	String path = request.getRequestURI();
	HttpSession session = request.getSession();
	DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
	List<Member> members = null;
	String setting = null;
	
		if(path.contains("menu")){
			session.setAttribute("memberContent", "menu");
			
		}
		else if (path.contains("edit")){
			//int memberId = Integer.valueOf(request.getParameter("memberId"));
			Member member = null;
			try {
				member = HhiService.getMemberById(memberId);
				
			
			
			
			this.editTitle = member.getTitle();
			this.editEmploymentStatus = member.getEmploymentStatus();
			this.editMaritalStatus = member.getMaritalStatus();
			this.editCellgroup = member.getCellgroup().getGroupName();
			if(member.getWaterBaptised()){
				this.editBaptised = "BAPTISED";
			}
			else {
				this.editBaptised = "UNBAPTISED";
			}
			
			this.birthday = sdf.format(member.getBirthday());
			this.convertedDate = sdf.format(member.getSavedDate());
			
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Action.SUCCESS;
			
		}
		 finally{
			 MemberEditData data = new MemberEditData();
				data.setBaptised(editBaptised);
				data.setBirthday(birthday);
				data.setConvertedDate(convertedDate);
				session.setAttribute("Member", member);
				session.setAttribute("editdata", data);
				session.setAttribute("memberContent", "edit");
		 }
			
		}
		else if (path.contains("success")){
			session.setAttribute("memberContent", "success");
			
		}	
		else if (path.contains("error")){
			session.setAttribute("memberContent", "error");
			
		}
		
		else if (path.contains("view")){
			session.setAttribute("memberContent", "view");
			
			try {
				members = HhiService.getAllMembers();
				//setting = HhiService.getHhiSettingDescription(HhiService.PAGER_OPTION);
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				session.setAttribute("memberContent", "error");
				e.printStackTrace();
				return Action.ERROR;
			}
			//session.setAttribute("pageSize", setting);
			if(this.stringToSearch == null ||!(this.stringToSearch.trim().length()> 0 )){
				//System.out.println("::::::::::::::::::Executed::::::::::::::::::::"+stringToSearch);
				//session.setAttribute("members", members);
				request.setAttribute("members", members);
			}else{
				//System.out.println("::::::::::::::::::Executed NOT NULL::::::::::::::::::::"+stringToSearch);
				List<Member> trimmedMembers = new ArrayList<Member> () ;
				
				
				for(Member matchMember: members){
					if(matchMember.getName().toUpperCase().contains(stringToSearch.toUpperCase())||matchMember.getSurname().toUpperCase().contains(stringToSearch.toUpperCase()) ){
						trimmedMembers.add(matchMember);
					}
				}
				//session.removeAttribute("members");
				//session.setAttribute("members", trimmedMembers);
				request.setAttribute("members", trimmedMembers);
			}
			
			
		}
		else {
			session.setAttribute("memberContent", "input");
			
		}
		
		//content="data/membermenu.jsp";
		return Action.SUCCESS;
	}
	
	public List<Hhisettings> getTitles(){
		
		List<Hhisettings> titles = null;
		try {
			if(this.editTitle == null){
			titles = HhiService.getHhiSetting(HhiService.TITLES);
			}
			else{
				titles = HhiService.getHhiSetting(HhiService.TITLES,editTitle);
				
			
			}
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return titles;
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
		/*String[] params = (String[])ctx.getAttribute("pagerParams");
		
		String[] sortedList = new String[params.length];
		sortedList[0] = setting;
		
		for(String sort: params){
			if(!sort.equals(setting)){
				sortedList[i] = sort;
				i++;
			}
		}
		request.getSession().setAttribute("sortedPagerParams",sortedList);
		//System.out.println(":::::::: Page Size "+sortedList.length);
*/		return Integer.valueOf(setting);
	}
	
	public String memberSearchAction(){
		//System.out.println("memberSearchAction :: Execiuted");
		return Action.SUCCESS;
	}
	
	public String pagerAction(){
		//System.out.println("::: Updating Pager "+pageSizeOption);
		
		try {
			HhiService.updateHhiSetting(HhiService.PAGER_OPTION, pageSizeOption);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
	      

			

			
			try {
				 Member member = HhiService.getMemberById(memberId);
				 groups = new ArrayList(member.getGroups());
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		
		return groups;
	}


public List<Groups> getOptions(){
	
	List<Groups> groups = null;
	try {
		
		groups = HhiService.getFilteredGroupsById(Integer.valueOf(memberId));
		//System.out.println("List Size "+groups.size());
		
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	return groups;
}
	
	
	public String memberSave(){
		
		
		//this.member.setSex(sex);

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		Date localBirthday = null;
		Date localsavedDate = null;
		try {
			
			HhiService.save(address);
			localBirthday= sdf.parse(this.birthday);
			localsavedDate = sdf.parse(this.convertedDate);
		} catch (PersistanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(waterBaptised.equalsIgnoreCase("Baptised")){
			member.setWaterBaptised(true);
		}else
		{
			member.setWaterBaptised(false);
		}
		Cellgroup memberGroup = null;
		try {
			 memberGroup = HhiService.getCellGroupsById(cellgroup.getGroupName());
		} catch (PersistanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//this.member.setCellgroup(cellgroup);
		this.member.setCellgroup(memberGroup);
		this.member.setAddress(address);
		this.member.setBirthday(localBirthday);
		this.member.setSavedDate(localsavedDate);
		this.member.setCreatedBy(request.getUserPrincipal().getName());
		this.member.setCreatedDate(new Date());
		
		logger.log(Level.INFO,"Set Size is "+memberGroup.getMembers().size());
		try{
			if(member.getNewMember() == true){
				//System.out.println("NEW Member :::::::::::::::::::::::"+member.getNewMember());
				Groups  newMemberGroup = HhiService.getGroupsByName(HhiService.NEW_MEMBER_GROUP) ;
				//HhiService.addMemberToGroup(newMemberGroup, member);
				member.addGroup(newMemberGroup);
			}
			HhiService.save(member);
			HhiService.addMemberToCellgroup(memberGroup, member);
			//System.out.println("NEW MEMBER "+member.getNewMember());
			
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Database Error Occures. Please ensure you have connection to database or record does not exist");
			e.printStackTrace();
			return Action.ERROR;
			
		}
		request.getSession().setAttribute("memberMessage", "saveRecord");
		
		return Action.SUCCESS;
	}
	
	
public String memberUpdate(){
		
		
		//this.member.setSex(sex);

		HttpServletRequest request = ServletActionContext.getRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		
		Date localBirthday = null;
		Date localsavedDate = null;
		try {
			//HhiService.update(address);
			localBirthday= sdf.parse(this.birthday);
			localsavedDate = sdf.parse(this.convertedDate);
		
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(waterBaptised.equalsIgnoreCase("Baptised")){
			member.setWaterBaptised(true);
		}else
		{
			member.setWaterBaptised(false);
		}
		Cellgroup memberGroup = null;
		try {
			 memberGroup = HhiService.getCellGroupsById(cellgroup.getGroupName());
		} catch (PersistanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//this.member.setCellgroup(cellgroup);
		this.member.setCellgroup(memberGroup);
		this.member.setAddress(address);
		this.member.setBirthday(localBirthday);
		this.member.setSavedDate(localsavedDate);
		this.member.setCreatedBy(request.getUserPrincipal().getName());
		this.member.setCreatedDate(new Date());
		try{
			if(member.getNewMember() == true){
				//System.out.println("NEW Member :::::::::::::::::::::::"+member.getNewMember());
				Groups  newMemberGroup = HhiService.getGroupsByName(HhiService.NEW_MEMBER_GROUP) ;
				//HhiService.addMemberToGroup(newMemberGroup, member);
				member.addGroup(newMemberGroup);
			}
			HhiService.update(member);
			
			
		}
		catch(PersistanceException e){
			e.printStackTrace();
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Database Error Occures. Please ensure you have connection to database or record does not exist");
			return Action.ERROR;
			
		}
		//request.getSession().setAttribute("savedMember", member);
		request.getSession().setAttribute("memberMessage", "updateRecord");
		return Action.SUCCESS;
	}


public String memberDelete(){
	
	String [] membersToDelete = null;
	//this.member.setSex(sex);

	//HttpServletRequest request = ServletActionContext.getRequest();
	if(memberIdToDelete.split(":").length == 0){
		
		membersToDelete[0] = memberIdToDelete;
		
		
		
	}
	else{
		
		membersToDelete = memberIdToDelete.split(":");
		
	}
	
	for(String id : membersToDelete){
		
		HhiService.deleteMember(Integer.valueOf(id));
		
		
	}
	//request.getSession().setAttribute("savedMember", member);
	HttpServletRequest request = ServletActionContext.getRequest();
	request.getSession().setAttribute("memberMessage", "deleteRecord");
	return Action.SUCCESS;
}


public String allocateGroups(){
	
	
	String[]ids = groupsAllocated != null ? groupsAllocated.split(","): null;
	Member member = null;
	/*Session session = HhiService.getSession();
	Transaction tx = session.beginTransaction();*/
	
	
	

	try {
		member = HhiService.getMemberById(memberId);
		//System.out.println("MemberID "+member.getMemberId());
	
	
	if(ids == null ){
		
		member.setGroups(new HashSet<Groups>());
		//HhiService.clearGroups(memberId);
	/*}else if(ids == null && groupsAllocated.trim().length() > 0){
		HhiService.clearGroups(memberId);
		
		Groups groups = HhiService.getGroupsById(Integer.valueOf(groupsAllocated.trim()));
		member.addGroup(groups);
		*/
	}else{
		//HhiService.clearGroups(memberId);
		member.setGroups(new HashSet<Groups>());
		for(String id : ids){
			
			Groups groups = HhiService.getGroupsById(Integer.valueOf(id.trim()));
			member.addGroup(groups);
		}
		
		/*tx.commit();
		session.flush();
		session.close();*/
		HhiService.update(member);
	}
	
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	HttpServletRequest request = ServletActionContext.getRequest();
	request.getSession().setAttribute("memberMessage", "updateRecord");
	return "success";
	
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

public List<Hhisettings> getHhisettings(){
	
	List<Hhisettings> hhisettings = HhiService.getHhiSettings();
	
	return hhisettings;
	
}


	
public List<Hhisettings> getStatus(){
		
		List<Hhisettings> status = null;
		try {
			if(this.editEmploymentStatus == null){
				status = HhiService.getHhiSetting(HhiService.EMPLOYMENT_STATUS);
			}
			else{
				status = HhiService.getHhiSetting(HhiService.EMPLOYMENT_STATUS,editEmploymentStatus);
			}
			
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}


public List<Hhisettings> getMaritalStatus(){
	
	List<Hhisettings> maritalStatus = null;
	try {
		if(this.editMaritalStatus == null){
			maritalStatus = HhiService.getHhiSetting(HhiService.MARITAL_STATUS);
		}
		else{
			maritalStatus = HhiService.getHhiSetting(HhiService.MARITAL_STATUS,editMaritalStatus);
		}
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return maritalStatus;
}
public List<Hhisettings> getSexes(){
	
	List<Hhisettings> sexes = null;
	try {
		sexes = HhiService.getHhiSetting(HhiService.SEX);
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return sexes;
}


public List<Hhisettings> getBaptism(){
	
	List<Hhisettings> baptism = null;
	try {
		if(editBaptised == null){
			baptism = HhiService.getHhiSetting(HhiService.WATERBAPTISED);
		}
		else{
			baptism = HhiService.getHhiSetting(HhiService.WATERBAPTISED,editBaptised);
		}
		
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return baptism;
}


public List<Cellgroup> getCellgroups(){
	
	
	List<Cellgroup> cellgroups = null;
	try {
		if(editCellgroup == null){
			cellgroups = HhiService.getAllCellGroups();
		}
		else{
			cellgroups = HhiService.getAllCellGroups(editCellgroup);
		}
		
	} catch (PersistanceException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return cellgroups;
}


/**
 * @return the title
 */
public final String getTitle() {
	return title;
}

/**
 * @param title the title to set
 */
public final void setTitle(String title) {
	this.title = title;
}

/**
 * @return the sex
 */
public final Character getSex() {
	return sex;
}

/**
 * @param sex the sex to set
 */
public final void setSex(Character sex) {
	this.sex = sex;
}

/**
 * @return the waterBaptised
 */
public final String getWaterBaptised() {
	return waterBaptised;
}

/**
 * @param waterBaptised the waterBaptised to set
 */
public final void setWaterBaptised(String waterBaptised) {
	this.waterBaptised = waterBaptised;
}

/**
 * @return the employmentStatus
 */
public final String getEmploymentStatus() {
	return employmentStatus;
}

/**
 * @param employmentStatus the employmentStatus to set
 */
public final void setEmploymentStatus(String employmentStatus) {
	this.employmentStatus = employmentStatus;
}

/**
 * @return the address
 */
public final Address getAddress() {
	return address;
}

/**
 * @param address the address to set
 */
public final void setAddress(Address address) {
	this.address = address;
}

/**
 * @return the cellgroup
 */
public final Cellgroup getCellgroup() {
	return cellgroup;
}

/**
 * @param cellgroup the cellgroup to set
 */
public final void setCellgroup(Cellgroup cellgroup) {
	this.cellgroup = cellgroup;
}

/**
 * @return the content
 */
public final String getContent() {
	return content;
}

/**
 * @param content the content to set
 */
public final void setContent(String content) {
	this.content = content;
}


//getters and setters not shown 
public void validate() { 
	//if (member.getTitle().contains("Select")) { 
	//	addFieldError("member.title", "Please Select  a Title for  member"); 
//} 
	/*try{
		member.getTitle();
	}
	catch (NullPointerException e){
		
		addFieldError("member.title", "Please Select  a Title for  member"); 
		System.out.println("Called Method");
	}
**/
}





public Integer getMemberId() {
	return memberId;
}

public void setMemberId(Integer memberId) {
	this.memberId = memberId;
}

public String getEditTitle() {
	return editTitle;
}

public void setEditTitle(String editTitle) {
	this.editTitle = editTitle;
}

public String getEditStatus() {
	return editEmploymentStatus;
}

public void setEditStatus(String editStatus) {
	this.editEmploymentStatus = editStatus;
}

public String getEditBaptised() {
	return editBaptised;
}

public void setEditBaptised(String editBaptised) {
	this.editBaptised = editBaptised;
}

public String getEditCellgroup() {
	return editCellgroup;
}

public void setEditCellgroup(String editCellgroup) {
	this.editCellgroup = editCellgroup;
}

public String getMemberIdToDelete() {
	return memberIdToDelete;
}

public void setMemberIdToDelete(String memberIdToDelete) {
	this.memberIdToDelete = memberIdToDelete;
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

public String getLeftNumber() {
	return leftNumber;
}

public void setLeftNumber(String leftNumber) {
	this.leftNumber = leftNumber;
}

public String getStringToSearch() {
	return stringToSearch;
}

public void setStringToSearch(String stringToSearch) {
	this.stringToSearch = stringToSearch;
}



public String getEditMaritalStatus() {
	return editMaritalStatus;
}

public void setEditMaritalStatus(String editMaritalStatus) {
	this.editMaritalStatus = editMaritalStatus;
}

public String getPageSizeOption() {
	return pageSizeOption;
}

public void setPageSizeOption(String pageSizeOption) {
	this.pageSizeOption = pageSizeOption;
}




}
