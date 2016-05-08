package com.aes.reports;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import net.sf.jasperreports.engine.JasperCompileManager;

import com.aes.data.domain.Client;
import com.aes.exceptions.PersistanceException;
import com.aes.service.HhiService;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;

public class JasperActionsbk extends ActionSupport {
 
    /** List to use as our JasperReports dataSource. */
    private List<Person> myList;
 
    public String execute() throws Exception {
 
        // Create some imaginary persons.
        Person p1 = new Person(new Long(1), "Patrick", "Lightbuddie");
        Person p2 = new Person(new Long(2), "Jason", "Carrora");
        Person p3 = new Person(new Long(3), "Alexandru", "Papesco");
        Person p4 = new Person(new Long(4), "Jay", "Boss");
 
        // Store people in our dataSource list (normally they would come from a database).
        myList = new ArrayList<Person>();
        myList.add(p1);
        myList.add(p2);
        myList.add(p3);
        myList.add(p4);
 
        // Normally we would provide a pre-compiled .jrxml file
        // or check to make sure we don't compile on every request.
        try {
            
            JasperCompileManager.compileReportToFile(
                    "./app-config/jasper/survey_jasper_template.jrxml",
                    "./app-config/jasper/survey_compiled_template.jasper");
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR;
        }
       
        return SUCCESS;
    }
    
   
 
    public List<Person> getMyList() {
        return myList;
    }
}
