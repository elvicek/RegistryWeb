<!-- 
<span class="outer">
<table class="left_menu">
	<tr>
		<td><a href="<s:url action="home_action" namespace="/"/>">
Home</a></td> 
	</tr>
	<tr>
		<td> <a href="<s:url action="membersmenu_action" namespace="/"/>">Members</a></td> 
	</tr>
	<tr>
		<td> <a href="<s:url action="maintenance_action" namespace="/"/>">Maintenance</a></td> 
	</tr>
	<tr>
		<td> <a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Groups</a></td> 
	</tr>
	<tr>
		<td> <a href="<s:url action="administration_action" namespace="/"/>">Administration</a></td>  
	</tr>
	<tr>
		<td> <a href="<s:url action="help_action" namespace="/"/>">Help</a></td> 
	</tr> 
		
	</tr>
	</table>
</span>	
 -->
<ul> 
    <li><a href="<s:url action="home_action" namespace="/"/>">
Home</a></li> 
    <li><a href="#">Clients</a> 
      <ul> 
        <li><a href="<s:url action="clientview_action" namespace="/"/>">View All</a></li> 
        <li><a href="<s:url action="clientinput_action" namespace="/"/>">Client Input</a></li> 
      </ul> 
    </li> 
    <li><a href="#">Surveys</a> 
      <ul> 
        <li><a href="<s:url action="allsurveysview_action" namespace="/"/>">View All Surveys</a></li> 
        <li><a href="<s:url action="createsurveys_action" namespace="/"/>">Create Surveys</a></li> 
        <li><a href="<s:url action="mysurveysview_action" namespace="/"/>">My Jobs</a></li> 
        <li><a href="<s:url action="surveyauth_action" namespace="/"/>">Authosise Surveys</a></li> 
      </ul> 
    </li> 
    <li><a href="#">Reports</a> 
      <ul>  
        <li><a href="<s:url action="clientview_report_action" namespace="/"/>">All Clients Report</a></li> 
        <li><a href="<s:url action="surveyview_report_action" namespace="/"/>">Survey Reading Report</a></li> 
      </ul> 
    </li> 
   
    <%
if (request.isUserInRole("admin")) {%>  
    <!-- put admin-only information-->

    <li><a href="#">Administration</a> 
    <ul>
      <li><a href="<s:url action="rolesview_action" namespace="/"/>">View All Roles</a></li>
      <li><a href="<s:url action="roleinput_action" namespace="/"/>">Create New Roles</a></li>
      <li><a href="<s:url action="usersview_action" namespace="/"/>">View Users</a></li>
      <li><a href="<s:url action="userinput_action" namespace="/"/>">Input Users</a></li>
      <li><a href="<s:url action="messageEmail_action" namespace="/"/>">Email users</a></li>
      <li><a href="<s:url action="emailTextFile_action" namespace="/"/>">Email Text File</a></li>
    </ul>
    </li>
    <%}%>
    <li><a href="<s:url action="help_action" namespace="/"/>">Help</a> 
      
    </li>  
  </ul>

<!--  
<input type=hidden name=arav value="1*#*#*2"><ul id='nav'>
<li> <a href='#'>Members</a>
<ul>
<li style='background-color:#b0c4de;'><a href=>Add Members</a></li>
<li style='background-color:#bebebe;'><a href=>View All</a></li>
</ul>
</li>
</ul>
-->
