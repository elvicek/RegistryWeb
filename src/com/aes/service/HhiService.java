package com.aes.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.aes.data.actions.DataActions;
import com.aes.data.dao.RoleDao;
import com.aes.data.domain.Client;
import com.aes.data.domain.Dbsettings;
import com.aes.data.domain.Role;
import com.aes.data.domain.User;
import com.aes.exceptions.UsersExistInRoleException;
import com.aes.exceptions.PersistanceException;
import com.aes.utils.HibernateUtils;
import com.hhiregistry.model.Cellgroup;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Member;
import com.hhiregistry.model.MemberGroups;

public class HhiService {

	public static int TITLES = 1;
	public static int SEX = 2;
	public static int ROLES = 3;
	public static int SURVEY_TYPE = 4;
	public static int SURVEY_STATUS = 5;
	public static int UNIT = 6;
	public static int UNIT_TYPE = 7;
	public static int PAGER_OPTION = 8;
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

			session.saveOrUpdate(o);
			//session.lock(o, LockMode.NONE);

			tx.commit();
			//session.flush();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}
	
	public static void updateDbSetting(int type, String description)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Dbsettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			
			Dbsettings dbsetting = (Dbsettings) query.uniqueResult();
			
			tx.commit();
			session.flush();
			session.close();

			dbsetting.setDescription(description);
			update(dbsetting);

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

	public static List<User> getAllUsers() throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from User";
			Query query = session.createQuery(queryString);

			List<User> users = (List<User>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return users;
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException();
		}

	}
	
	public static List<Client> getAllClients() throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Client";
			Query query = session.createQuery(queryString);

			List<Client> clients = (List<Client>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return clients;
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

	public static void deleteUser(String userName) {

		Session session = getSession();
		Transaction tx = session.beginTransaction();
		User user;
		
		try {
			
			
			user = getUserByUserName(userName);
			session.delete(user);
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
			throws ConstraintViolationException, UsersExistInRoleException {

		Cellgroup cellgroup;
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			cellgroup = getCellGroupsById(cellgroupName);

			if (cellgroup.getMembers().size() > 0) {

				throw new UsersExistInRoleException(cellgroup.getGroupName());
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
			throws ConstraintViolationException, UsersExistInRoleException {

		Groups group;
		Session session = getSession();
		Transaction tx = session.beginTransaction();

		try {
			group = getGroupsByName(groupName);

			if (group.getMembers().size() > 0) {

				throw new UsersExistInRoleException(group.getGroupName());
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
	
	public static void deleteRoleById(String roleId)
			throws ConstraintViolationException, UsersExistInRoleException {

		Role role;
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		
		try {
			role = getRoleById(roleId);
			if(role != null && isNotNullOrEmpty(new ArrayList<User>(role.getUsers()))){
				throw new UsersExistInRoleException("users exist in role ["+role
						.getRoleName()+"]");
			}

			session.delete(role);
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

	public static List<Dbsettings> getDbSetting(int type)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Dbsettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			List<Dbsettings> settings = (List<Dbsettings>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return settings;

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static String getDbSettingDescription(int type)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from HhiSettings where type = :type";
			String queryString = "from Dbsettings where type = :type";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);

			
			Dbsettings dbSetting = (Dbsettings) query.uniqueResult();
			tx.commit();
			session.flush();
			session.close();

			return dbSetting.getDescription();

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static List<Dbsettings> getDbSetting(int type, String description)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			// String queryString = "from Dbsettings where type = :type";
			String queryString = "from Dbsettings where type = :type and description != :description";
			Query query = session.createQuery(queryString);
			query.setInteger("type", type);
			query.setString("description", description);

			List<Dbsettings> settings = (List<Dbsettings>) query.list();

			tx.commit();
			session.flush();
			session.close();

			return settings;

		} catch (Exception e) {

			throw new PersistanceException();
		}

	}

	public static List<Dbsettings> getDbSettings() {

		Session session = getSession();
		Transaction tx = session.beginTransaction();

		// String queryString = "from HhiSettings where type = :type";
		String queryString = "from Hhisettings";
		Query query = session.createQuery(queryString);
		// query.setInteger("type", type);

		List<Dbsettings> settings = (List<Dbsettings>) query.list();

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

	public static List<Role> getAllRoles() throws PersistanceException {

		Session session = getSession();
		Transaction tx = session.beginTransaction();

		String queryString = "from Role";
		Query query = session.createQuery(queryString);
		// query.setInteger("type", type);

		List<Role> roles = (List<Role>) query.list();

		tx.commit();
		session.flush();
		session.close();

		return roles;

	}

	private static RoleDao getRoleDao() {
		
		return new RoleDao();
	}

	public static Role getRoleById(String roleId)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Role where ROLE_NAME = :roleId";
			Query query = session.createQuery(queryString);
			query.setString("roleId", roleId);

			Object obj = query.uniqueResult();
			Role role;

			if (obj == null) {
				role = null;
			} else {
				role = (Role) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return role;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}
	
	public static User getUserByUserName(String username)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from User where username = :username";
			Query query = session.createQuery(queryString);
			query.setString("username", username);

			Object obj = query.uniqueResult();
			User user;

			if (obj == null) {
				user = null;
			} else {
				user = (User) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return user;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}
	
	public static Client getClientByClientName(String clientName)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Client where clientName = :clientName";
			Query query = session.createQuery(queryString);
			query.setString("clientName", clientName);

			Object obj = query.uniqueResult();
			Client client;

			if (obj == null) {
				client = null;
			} else {
				client = (Client) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return client;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}
	
	public static Client getClientById(Integer clientId)
			throws PersistanceException {

		try {
			Session session = getSession();
			Transaction tx = session.beginTransaction();

			String queryString = "from Client where CLIENT_ID = :clientId";
			Query query = session.createQuery(queryString);
			query.setInteger("clientId", clientId);

			Object obj = query.uniqueResult();
			Client client;

			if (obj == null) {
				client = null;
			} else {
				client = (Client) obj;

			}

			tx.commit();
			session.flush();
			session.close();

			return client;
		} catch (Exception e) {
			throw new PersistanceException();
		}
	}
	
	private static boolean isNotNullOrEmpty(List<User> users) {
		return users != null && !users.isEmpty();
		
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
