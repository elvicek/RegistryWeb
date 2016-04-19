package com.aes.data.actions;

import java.util.ArrayList;   
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.exception.ConstraintViolationException;

import com.aes.business.CellgroupEditData;
import com.aes.business.MemberEditData;
import com.aes.exceptions.UsersExistInRoleException;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Hhisettings;
import com.hhiregistry.model.Member;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class DataActions extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer member;
	private Member memberData;
	private Hhisettings hhisettings;
	private Cellgroup cellgroup;
	private String cellgroupName;
	private Boolean validationRequest;
	private String editMemberId;
	private String cellgroupToDelete;
	private String cellgroupNameMembers;
	
	//private Logger log = new Logger(DataActions.class.getName());
	private Logger logger =  Logger.getLogger(DataActions.class.getName());
	
	public DataActions(){
		
	}
	
	private String saveMember(){
		
		
		HhiService serv = new HhiService();
		
		try {
			serv.save(member);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Action.SUCCESS;
		
		
		
	}
	
	
	public String cellGroupAction(){
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getRequestURI();
		HttpSession session = request.getSession();
		
		//List<Member> members = HhiService.getAllMembers();
		List<Cellgroup> groups;
		try {
			groups = HhiService.getAllCellGroups();
		} catch (PersistanceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			session.setAttribute("cellGroupContent", "error");
			return Action.ERROR;
			
			
		}
		if(path.contains("menu")){
			session.setAttribute("cellGroupContent", "menu");
			
		}
		else if (path.contains("edit")){
			session.setAttribute("cellGroupContent", "edit");
			Cellgroup cellgroup = null;
			CellgroupEditData data = new CellgroupEditData();
			
			Member member = null;
			try {
				
				cellgroup = HhiService.getCellGroupsById(cellgroupName);
				
				try{
					
					if(cellgroup.getMemberId() != null){
						member = HhiService.getMemberById(cellgroup.getMemberId());
						data.setMemberId(Integer.valueOf(member.getMemberId()));
						data.setMemberName(member.getName());
						this.editMemberId = cellgroup.getMemberId().toString();
					}
				}
				
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			
			
			session.setAttribute("CellGroup", cellgroup);
			session.setAttribute("celleditdata", data);
			
		}
		else if (path.contains("view")){
			session.setAttribute("cellGroupContent", "view");
			List<String> selectMembers = new ArrayList<String>();
			Map<Integer, String> memberMap;
			try {
				memberMap = HhiService.getAllMembersFullNameAndId();
			} catch (PersistanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return Action.ERROR;
			}
			
			
			session.setAttribute("memberMap", memberMap);
			session.setAttribute("cellGroups", groups);
			
			
			
		}
		else if (path.contains("membership")){
			
			session.setAttribute("cellGroupContent", "membership");
			Cellgroup cellgroup;
			try {
				 cellgroup = HhiService.getCellGroupsById(this.cellgroupNameMembers);
			} catch (PersistanceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return Action.ERROR;
			}
			
			List<Member> members = new ArrayList<Member>();
			
		     members = new ArrayList<Member>(cellgroup.getMembers());
			
			
			session.setAttribute("membersBycell", members);
			session.setAttribute("title", "Members for "+cellgroupNameMembers);
			
			
			
		}
		
		else if (path.contains("success")){
			session.setAttribute("cellGroupContent", "success");
			
		}	
		else if (path.contains("error")){
			session.setAttribute("cellGroupContent", "error");
			
		}	
		else {
			session.setAttribute("cellGroupContent", "input");
			
		}
		
		//System.out.println("Size of Members "+members.size());
		
		
		
		//request.getSession().setAttribute("selectMembers", selectMembers);
		
		
		return Action.SUCCESS;
		
		
	}
	
	
	
	public String saveCellGroup(){
			
			
			HttpServletRequest request = ServletActionContext.getRequest();
			//Set<Member> members = new HashSet<Member>();
			
			//System.out.println("Member "+request.getParameter("member"));
			//System.out.println("Saving :::: "+cellgroup.getGroupName()+" "+cellgroup.getGroupLocation());
			this.cellgroup.setMemberId(member);
			//this.cellgroup.setMembers(members);
			this.cellgroup.setCreatedBy(request.getRemoteUser());
			this.cellgroup.setCreatedDate(new Date());
			
			System.out.println("Saving :::: "+cellgroup.getGroupName()+" "+cellgroup.getGroupLocation()+" "+cellgroup.getMemberId());
			try{
				HhiService.save(cellgroup);
			}
			catch(Exception e){
				logger.log(Level.SEVERE,"Database Error Occured. Please ensure you have connection to database or record does not exist");
				return Action.ERROR;
				
			}
			
			
			//request.getSession().setAttribute("savedCellGroup", cellgroup);
			request.getSession().setAttribute("cellgroupMessage", "saveRecord");
			return Action.SUCCESS;
			
			
			
			
		}
	
	
	public String updateCellGroup(){
		
		
		HttpServletRequest request = ServletActionContext.getRequest();
		
		
		this.cellgroup.setMemberId(member);
		this.cellgroup.setCreatedBy(request.getRemoteUser());
		this.cellgroup.setCreatedDate(new Date());
		
		
		try{
			HhiService.update(cellgroup);
		}
		catch(Exception e){
			logger.log(Level.SEVERE,"Database Error Occured. Please ensure you have connection to database or record does not exist");
			return Action.ERROR;
			
		}
		
		
		//request.getSession().setAttribute("savedCellGroup", cellgroup);
		request.getSession().setAttribute("cellgroupMessage", "updateRecord");
		return Action.SUCCESS;
		
		
		
		
	}
	
	public String cellgroupDelete(){
		
		String [] cellgroupsToDelete = null;
		//this.member.setSex(sex);

		//HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletRequest request = ServletActionContext.getRequest();
		if(cellgroupToDelete.split(":").length == 0){
			
			cellgroupsToDelete[0] = cellgroupToDelete;
			
			
			
		}
		else{
			
			cellgroupsToDelete = cellgroupToDelete.split(":");
			
		}
		try{
		for(String id : cellgroupsToDelete){
		
			try {
				HhiService.deleteCellgroup(id);
			} catch (UsersExistInRoleException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				request.getSession().setAttribute("cellgroupMessage", "cellgroupDeleteError");
				return Action.SUCCESS;
			}
			
			
			
			
		}
		}catch(ConstraintViolationException e){
			request.getSession().setAttribute("cellgroupMessage", "cellgroupDeleteError");
			return Action.SUCCESS;
		}
		//request.getSession().setAttribute("savedMember", member);
		
		request.getSession().setAttribute("cellgroupMessage", "deleteRecord");
		return Action.SUCCESS;
	}
	
	
//	public List<Member> getMembers(){
//		
//		List<Member> members = null;
//		
//		try {
//			
//			if(this.editMemberId == null){
//			members = HhiService.getAllUsers();
//			}
//			else{
//				members = HhiService.getAllMembers(Integer.valueOf(editMemberId));
//			}
//			
//		} catch (PersistanceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return members;
//		
//	}
	
	public void validate(){
		
				HttpServletRequest request = ServletActionContext.getRequest();
				
				if(!request.getRequestURI().contains("cellgroupinput") && request.getParameterMap().containsKey("validationRequest")){ //Prevent Error forward from validating again
				try {
					logger.log(Level.SEVERE,"Validating:::::::::::::::::::: "+request.getParameter("cellgroup.groupName")+" Condition "+(HhiService.getCellGroupsById(request.getParameter("cellgroup.groupName"))== null)+ request.getRequestURI());
				} catch (PersistanceException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					String groupName = request.getParameter("cellgroup.groupName");
					Cellgroup group = HhiService.getCellGroupsById(groupName);
					
					
					
					if ((group!= null && !(request.getParameterMap().containsKey("cellgroupedit")) )){
						
						logger.log(Level.SEVERE,"Validating Condition Errorror:::::::::::::::::::: "+request.getParameter("cellgroup.groupName"));
						addFieldError("cellgroup.groupName", "'" + cellgroup.getGroupName() + "' has been taken and aready exists in Database.");
					}
					
				} catch (PersistanceException e) {
					// TODO Auto-generated catch block
					
				}
				
				if(member != 0){
				try{
					List<Cellgroup> cellGroups = HhiService.getAllCellGroups();
					Integer memberId = member;
					logger.log(Level.SEVERE,"Member ID is :::::::::;;;;;;;;;;;;;;;;;:::::::::::::::::::::: "+memberId);
					
					for(Cellgroup cellgroup : cellGroups){
						
						
						if(!request.getParameterMap().containsKey("cellgroupedit")){
						if(Integer.valueOf(cellgroup.getMemberId()).compareTo(Integer.valueOf(memberId))== 0){
							
							
							
							addFieldError("member", "'" + HhiService.getMemberById(member).getName()+ "' Is Aready Leading Cell group "+cellgroup.getGroupName());
							logger.log(Level.SEVERE,"Member ID Exists Member ID Exists Member ID Exists:::::::::;;;;;;;;;;;;;;;;;:::::::::::::::::::::: "+memberId);
							break;
						}
						}else{
							if(Integer.valueOf(cellgroup.getMemberId()).compareTo(Integer.valueOf(memberId))== 0 && !(cellgroup.getGroupName().equalsIgnoreCase(request.getParameter("cellgroup.groupName")))){
								
								
								
								addFieldError("member", "'" + HhiService.getMemberById(member).getName()+ "' Is Aready Leading Cell group "+cellgroup.getGroupName());
								logger.log(Level.SEVERE,"Member ID Exists Member ID Exists Member ID Exists:::::::::;;;;;;;;;;;;;;;;;:::::::::::::::::::::: "+memberId);
								break;
							}	
						}
					}
					
				}catch(PersistanceException e){
					logger.log(Level.SEVERE,"Error Occured while retrieving Records from Database");
				}
				}
				
				}
				
			}
	
	
	public Cellgroup getCellgroup() {
		return cellgroup;
	}


	public void setCellgroup(Cellgroup cellgroup) {
		this.cellgroup = cellgroup;
	}


	

	
	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public Member getMemberData() {
		return memberData;
	}


	public void setMemberData(Member memberData) {
		this.memberData = memberData;
	}

	public String getCellgroupName() {
		return cellgroupName;
	}

	public void setCellgroupName(String cellgroupName) {
		this.cellgroupName = cellgroupName;
	}

	public Boolean getValidationRequest() {
		return validationRequest;
	}

	public void setValidationRequest(Boolean validationRequest) {
		this.validationRequest = validationRequest;
	}

	public String getEditMemberId() {
		return editMemberId;
	}

	public void setEditMemberId(String editMemberId) {
		this.editMemberId = editMemberId;
	}

	public String getCellgroupToDelete() {
		return cellgroupToDelete;
	}

	public void setCellgroupToDelete(String cellgroupToDelete) {
		this.cellgroupToDelete = cellgroupToDelete;
	}

	public String getCellgroupNameMembers() {
		return cellgroupNameMembers;
	}

	public void setCellgroupNameMembers(String cellgroupNameMembers) {
		this.cellgroupNameMembers = cellgroupNameMembers;
	}

}
