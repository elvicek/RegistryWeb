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
    <li><a href="#">Members</a> 
      <ul> 
        <li><a href="<s:url action="memberview_action" namespace="/"/>">View All</a></li> 
        <li><a href="<s:url action="memberinput_action" namespace="/"/>">Member Input</a></li> 
      </ul> 
    </li> 
    <li><a href="#">Maintenance</a> 
    </li>
    <li><a href="#">Cell Groups</a> 
      <ul> 
        <li><a href="<s:url action="cellgroupview_action" namespace="/"/>">View All</a></li> 
        <li><a href="<s:url action="cellgroupinput_action" namespace="/"/>">Cell Group Input</a></li> 
      </ul> 
    </li> 
    <li><a href="#">Administration</a> 
    <ul>
      <li><a href="<s:url action="rolesview_action" namespace="/"/>">View All Roles</a></li>
      <li><a href="<s:url action="roleinput_action" namespace="/"/>">Create New Roles</a></li>
      <li><a href="<s:url action="userinput_action" namespace="/"/>">users Input</a></li>
      <li><a href="<s:url action="groupSMS_action" namespace="/"/>">Send SMS to Users</a></li>
      <li><a href="<s:url action="messageEmail_action" namespace="/"/>">Email users</a></li>
      <li><a href="<s:url action="emailTextFile_action" namespace="/"/>">Email Text File</a></li>
    </ul>
    </li>
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
