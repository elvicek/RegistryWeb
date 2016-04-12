/**v
 * 
 */
package com.hhiregistry.listeners;

import java.util.ArrayList; 
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.joda.time.DateMidnight;
import org.joda.time.Days;

import com.hhiregistry.exceptions.PersistanceException;
import com.hhiregistry.model.Groups;
import com.hhiregistry.model.Member;
import com.hhiregistry.service.HhiService;

/**
 * @author elvicek
 *
 */
public final class Initializer implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		initialisePagerParams(event);

	}

	private void initialisePagerParams(ServletContextEvent event) {
		ServletContext ctx = event.getServletContext();
		
		String[] pageSize = ctx.getInitParameter("count").split(",");
		ctx.setAttribute("pagerParams", pageSize);
		
		Integer newMemberPeriod = Integer.valueOf(ctx.getInitParameter("newMemberPeriod"));
		List<Member> newMembers = new ArrayList<Member> ();
		
		try {
			 newMembers = HhiService.getAllNewMembers();
		} catch (PersistanceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (newMembers.size() > 0){
			Date currentDate = new Date();
			for(Member member: newMembers){
				int daysbetween = Days.daysBetween(new DateMidnight(currentDate), new DateMidnight(member.getCreatedDate())).getDays();
				if( daysbetween > newMemberPeriod ){
					Groups group;
					 try {
						group = HhiService.getGroupsByName(HhiService.NEW_MEMBER_GROUP);
						HhiService.removeMemberFromGroup(group, member);
						System.out.println("Member "+member.getName()+" "+member.getSurname()+" No Longer New Member");
					} catch (PersistanceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				//if(((currentDate.getTime()- member.getCreatedDate().getTime())/(1000*60*60*24)) > newMemberPeriod ){
					//update
				}
			}
			
		}
		
	}

}
