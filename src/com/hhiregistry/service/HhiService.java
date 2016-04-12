package com.hhiregistry.service;

import java.util.ArrayList; 
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.hhiregistry.data.actions.DataActions;
import com.hhiregistry.exceptions.MemberExistsException;
import com.hhiregistry.exceptions.PersistanceException;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Hhisettings;
import com.hhiregistry.model.Member;
import com.hhiregistry.model.MemberGroups;
import com.hhiregistry.utils.HibernateUtils;

public class HhiService {

	public static int TITLES = 1;
	public static int SEX = 2;
	public static int WATERBAPTISED = 3;
	public static int EMPLOYMENT_STATUS = 4;
	public static int MARITAL_STATUS = 5;
	public static int PAGER_OPTION = 6;
	public static final String MESSAGE_TEMPLATE = "./app-config/template.html";
	public static final String EMAIL_ADDRESSES_TEXT_FILE = "./app-config/allAddresses.txt";
	public static String NEW_MEMBER_GROUP = "NEW MEMBERS";

	private static Session session = null;
	private static Logger logger = Logger.getLogger(HhiService.class.getName());

	public HhiService() {

	}

	public static void save(Object o) throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			session.save(o);

			tx.commit();
			session.flush();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static void update(Object o) throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			session.update(o);

			tx.commit();
			// session.flush();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}
	
	public static void updateHhiSetting(int type, String description)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Hhisettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			
			Hhisettings hhiSetting = (Hhisettings) query.uniqueResult();
			
			tx.commit();
			session.flush();
			session.close();

			hhiSetting.setDescription(description);
			update(hhiSetting);

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static Map<Integer, String> getAllMembersNameAndId()
			throws PersistanceException {
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();
			Map<Integer, String> membersById = new HashMap<Integer, String>();

			String queryString = "from Member";
			Query query = session.createQuery(queryString);

			List<Member> members = (List<Member>) query.list();
			for (Member member : members) {

				membersById.put(Integer.valueOf(member.getMemberId()), member.getName());

			}

			tx.commit();
			session.flush();
			session.close();

			return membersById;
		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static Map<Integer, String> getAllMembersFullNameAndId()
			throws PersistanceException {
		Session session;
		try {
			session = getSession();

			Transaction tx = session.beginTransaction();
			Map<Integer, String> membersByFullNameId = new HashMap<Integer, String>();

			String queryString = "from Member";
			Query query = session.createQuery(queryString);

			List<Member> members = (List<Member>) query.list();
			for (Member member : members) {

				membersByFullNameId.put(Integer.valueOf(member.getMemberId()), member.getName()
						+ " " + member.getSurname());

			}

			tx.commit();
			session.flush();
			session.close();

			return membersByFullNameId;

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static Member getMemberById(Integer id) throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member where MEMBER_ID = :id";
			Query query = session.createQuery(queryString);
			query.setInteger("id", id);

			Member member = (Member) query.uniqueResult();

			tx.commit();
			session.flush();
			session.close();

			return member;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	/*public static Member getMemberById(String id) throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member where MEMBER_ID = :id";
			Query query = session.createQuery(queryString);
			query.setString("id", id);

			Member member = (Member) query.uniqueResult();

			tx.commit();
			session.flush();
			session.close();

			return member;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}*/
	/*
	 * public static List<MemberGroups> getMemberGroupsById(Integer id) throws
	 * PersistanceException { List<MemberGroups> memberGroups = new
	 * ArrayList<MemberGroups>(); try { Session session = getSession();
	 * Transaction tx = session.beginTransaction();
	 * 
	 * String queryString = "from Member where MEMBER_ID = :id"; Query query =
	 * session.createQuery(queryString); query.setInteger("id", id);
	 * 
	 * Member member = (Member) query.uniqueResult(); //MemberGroups gp = new
	 * MemberGroups(new Member(), new Groups());
	 * 
	 * 
	 * memberGroups = new ArrayList<MemberGroups>(member.getMemberGroups());
	 * tx.commit(); session.flush(); session.close(); return memberGroups; }
	 * catch (Exception e) { e.printStackTrace(); throw new
	 * PersistanceException(); }
	 * 
	 * }
	 */

	public static List<Groups> getFilteredGroupsById(Integer id)
			throws PersistanceException {
		List<Groups> allGroups = getGroups();
		List<Groups> groups = new ArrayList<Groups>();
		// List<Groups> filteredGroups = null;
		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member where MEMBER_ID = :id";
			Query query = session.createQuery(queryString);

			query.setInteger("id", id);

			Member member = (Member) query.uniqueResult();
			// MemberGroups gp = new MemberGroups(new Member(), new Groups());

			allGroups.removeAll(member.getGroups());

			tx.commit();
			session.flush();
			session.close();
			return allGroups;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static List<Member> getAllMembers() throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member";
			Query query = session.createQuery(queryString);

			List<Member> members = (List<Member>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return members;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}
	
	public static List<Member> getAllNewMembers() throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member where NEW_MEMBER = true";
			Query query = session.createQuery(queryString);

			List<Member> members = (List<Member>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return members;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static List<Member> getAllMembers(Integer memberId)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Member where member_Id != :memberId";
			Query query = session.createQuery(queryString);
			query.setInteger("memberId", memberId);

			List<Member> members = (List<Member>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return members;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static void deleteMember(Integer memberId) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Member member;
		
		
		

		//List<MemberGroups> members = (List<MemberGroups>) query.list();
		

		try {
			
			
			member = getMemberById(memberId);
			
			//List<MemberGroups> groups = getMemberGroupsByMemberId(member.getMemberId());
			String queryString = "Delete from MemberGroups where MEMBER_ID = :memberId";
			if(member.getGroups()!=null){
			session.createQuery(queryString).setInteger("memberId", member.getMemberId()).executeUpdate();
			}
			/*List<MemberGroups> memberGroups = (List<MemberGroups>) query.list();
			System.out.println("Number of groups "+memberGroups.size());
			if(memberGroups.size() > 0){
			for(MemberGroups mgroups: memberGroups){
				session.delete(mgroups);
				
			}
			}*/
			session.delete(member);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		tx.commit();
		session.flush();
		session.close();
	}

	public static void clearGroups(Integer memberId) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		Member member = null;

		try {
			member = getMemberById(memberId);
			if (member.getGroups().size() > 0) {

				member.getGroups().clear();
			}

			session.update(member);
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// member.setGroups(new HashSet<Groups>(0));
		}

		tx.commit();
		session.flush();
		session.close();
	}

	public static void deleteCellgroup(String cellgroupName)
			throws ConstraintViolationException, MemberExistsException {

		Cellgroup cellgroup;
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			cellgroup = getCellGroupsById(cellgroupName);

			if (cellgroup.getMembers().size() > 0) {

				throw new MemberExistsException(cellgroup.getGroupName());
			}

			session.delete(cellgroup);

		} catch (ConstraintViolationException e) {
			e.printStackTrace();

			throw e;
		}

		catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.flush();
		session.close();

	}

	public static void deleteGroup(String groupName)
			throws ConstraintViolationException, MemberExistsException {

		Groups group;
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			group = getGroupsByName(groupName);

			if (group.getMembers().size() > 0) {

				throw new MemberExistsException(group.getGroupName());
			}

			session.delete(group);

		} catch (ConstraintViolationException e) {
			e.printStackTrace();

			throw e;
		}

		catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.flush();
		session.close();

	}
	
	public static void deleteGroupById(Integer groupId)
			throws ConstraintViolationException, MemberExistsException {

		Groups group;
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			group = getGroupsById(groupId);

			session.delete(group);

		} catch (ConstraintViolationException e) {
			e.printStackTrace();

			throw e;
		}

		catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tx.commit();
		session.flush();
		session.close();

	}

	public static void addMemberToCellgroup(Cellgroup cellgroup, Member member) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		cellgroup.getMembers().add(member);

		tx.commit();
		session.flush();
		session.close();
	}
	
	public static void addMemberToGroup(Groups group, Member member) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		group.getMembers().add(member);

		tx.commit();
		session.flush();
		session.close();
	}

	public static void removeMemberFromCellgroup(Cellgroup cellgroup,
			Member member) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		cellgroup.getMembers().remove(member);

		tx.commit();
		session.flush();
		session.close();
	}
	
	public static void removeMemberFromGroup(Groups group,
			Member member) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		group.getMembers().remove(member);

		tx.commit();
		session.flush();
		session.close();
	}

	public static void removeAllMembersFromCellgroup(Cellgroup cellgroup) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		List<Member> members = new ArrayList<Member>(cellgroup.getMembers());

		for (Member member : members) {
			member.setCellgroup(null);
		}

		tx.commit();
		session.flush();
		session.close();
		// return members;

	}

	public static List<Hhisettings> getHhiSetting(int type)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Hhisettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			List<Hhisettings> settings = (List<Hhisettings>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return settings;

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static String getHhiSettingDescription(int type)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Hhisettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			
			Hhisettings hhiSetting = (Hhisettings) query.uniqueResult();
			tx.commit();
			session.flush();
			session.close();

			return hhiSetting.getDescription();

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static List<Hhisettings> getHhiSetting(int type, String description)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Hhisettings where type = :type and description != :description";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);
			query.setString("description", description);

			List<Hhisettings> settings = (List<Hhisettings>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return settings;

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static List<Hhisettings> getHhiSettings() {

		Session session = getSession();
		Transaction tx = session.beginTransaction();

		// String queryString = "from HhiSettings where type = :type";
		String queryString = "from Hhisettings";
		Query query = session.createQuery(queryString);
		// query.setInteger("type", type);

		List<Hhisettings> settings = (List<Hhisettings>) query.list();

		tx.commit();
		session.flush();
		session.close();

		return settings;

	}

	public static List<Cellgroup> getAllCellGroups()
			throws PersistanceException {
		Session session;
		try {
			session = getSession();

			Transaction tx = session.beginTransaction();

			String queryString = "from Cellgroup";
			Query query = session.createQuery(queryString);

			List<Cellgroup> cellGroups = (List<Cellgroup>) query.list();

			tx.commit();
			session.flush();
			session.close();
			return cellGroups;

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static List<Groups> getGroups() {

		Session session = getSession();
		Transaction tx = session.beginTransaction();

		// String queryString = "from HhiSettings where type = :type";
		String queryString = "from Groups";
		Query query = session.createQuery(queryString);
		// query.setInteger("type", type);

		List<Groups> groups = (List<Groups>) query.list();

		tx.commit();
		session.flush();
		session.close();

		return groups;

	}

	public static List<Cellgroup> getAllCellGroups(String cellGroupName)
			throws PersistanceException {
		Session session;
		try {
			session = getSession();

			Transaction tx = session.beginTransaction();

			String queryString = "from Cellgroup where GROUP_NAME != :cellGroupName";
			Query query = session.createQuery(queryString);
			query.setString("cellGroupName", cellGroupName);

			List<Cellgroup> cellGroups = (List<Cellgroup>) query.list();

			tx.commit();
			session.flush();
			session.close();
			return cellGroups;

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static Cellgroup getCellGroupsById(String groupName)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Cellgroup where GROUP_NAME = :groupName";
			Query query = session.createQuery(queryString);
			query.setString("groupName", groupName);

			Object obj = query.uniqueResult();
			Cellgroup cellGroup;

			if (obj == null) {
				cellGroup = null;
			} else {
				cellGroup = (Cellgroup) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return cellGroup;
		} catch (Exception e) {
			throw new PersistanceException();
		}

	}

	public static List<Groups> getAllGroups() throws PersistanceException {
		Session session;
		try {
			session = getSession();

			Transaction tx = session.beginTransaction();

			String queryString = "from Groups";
			Query query = session.createQuery(queryString);

			List<Groups> groups = (List<Groups>) query.list();

			tx.commit();
			session.flush();
			session.close();
			return groups;

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}

	public static Groups getGroupsById(Integer groupId)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Groups where GROUP_ID = :groupId";
			Query query = session.createQuery(queryString);
			query.setInteger("groupId", groupId);

			Object obj = query.uniqueResult();
			Groups group;

			if (obj == null) {
				group = null;
			} else {
				group = (Groups) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return group;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}

	public static MemberGroups getMemberGroupsById(Integer groupId)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from MemberGroups where GROUP_ID = :groupId";
			Query query = session.createQuery(queryString);
			query.setInteger("groupId", groupId);

			Object obj = query.uniqueResult();
			MemberGroups memberGroups;

			if (obj == null) {
				memberGroups = null;
			} else {
				memberGroups = (MemberGroups) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return memberGroups;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}
	
	public static List<MemberGroups> getMemberGroupsByMemberId(Integer memberId)
	throws PersistanceException {

try {
	
	Transaction tx = session.beginTransaction();

	String queryString = "from MemberGroups where MEMBER_ID = :memberId";
	Query query = session.createQuery(queryString);
	query.setInteger("memberId", memberId);

	List<MemberGroups> memberGroups = (List<MemberGroups>) query.list();

	

	return memberGroups;
} catch (Exception e) {
	throw new PersistanceException();
}
}

	public static Groups getGroupsByName(String groupName)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Groups where GROUP_NAME = :groupName";
			Query query = session.createQuery(queryString);
			query.setString("groupName", groupName);

			Object obj = query.uniqueResult();
			Groups group;

			if (obj == null) {
				group = null;
			} else {
				group = (Groups) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return group;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}

	
	
	public static Object retrieveFromId(int idValue) {
		// AnnotationConfiguration config = new AnnotationConfiguration();
		// config.addAnnotatedClass(User.class);
		// SessionFactory factory= config.configure().buildSessionFactory();
		// Session session = factory.getCurrentSession();
		// session.beginTransaction();/*lets hope an id of 1 exists!*/
		// String queryString = "from User where id = :id";
		// Query query = session.createQuery(queryString);
		// query.setInteger("id", idValue);
		// Object queryResult = query.uniqueResult();
		// User user = (User)queryResult;session.getTransaction().commit();
		// return user;
		return null;
	}

	public void updateAll() {

		Session session = getSession();
		session.beginTransaction();
		List allUsers;
		System.out.println("Updating all records...");
		Query queryResult = session.createQuery("from User");
		allUsers = queryResult.list();
		System.out.println("# of rows: " + allUsers.size());
		for (int i = 0; i < allUsers.size(); i++) {
			// User user = (User) allUsers.get(i);
			// System.out.println(user);
			// user.setPassword("password");
			// session.update(user);
		}
		System.out.println("Database contents updated...");
		session.getTransaction().commit();
	}

	public void deleteAll() {

		Session session = getSession();
		List allUsers;
		session.beginTransaction();
		Query queryResult = session.createQuery("from User");
		allUsers = queryResult.list();
		for (int i = 0; i < allUsers.size(); i++) {
			// User user = (User) allUsers.get(i);
			// System.out.println(user);
			// session.delete(user);
		}
		session.getTransaction().commit();
	}

	public static Session getSession() {

		SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

		Session session = sessionFactory.openSession();

		return session;
	}

}
