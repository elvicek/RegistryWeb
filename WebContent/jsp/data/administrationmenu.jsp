<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<table class="heading">
<tr>
<td colspan="2" align="left">Welcome to Administration Menu. Select Option Of Action You Desire Below </td>
</tr>
</table>
<br/>
<div id="center">
<table class="menu">
<tr><th align="left">Action</th><th align="left">Description</th></tr>
<tr><td><a href="<s:url action="rolesview_action" namespace="/"/>">View All Roles</a></td><td>List Of All Roles Created in Database</td></tr>
<tr><td><a href="<s:url action="userinput_action" namespace="/"/>">User Input</a></td><td>Add users to Application</td></tr>
<tr><td><a href="<s:url action="userSMS_action" namespace="/"/>">Send SMS to Users</a></td><td>Send SMS to Registry Members</td></tr>
<tr><td><a href="<s:url action="messageEmail_action" namespace="/"/>">Email users</a></td><td>Send Emails to Registry Members</td></tr>
<tr><td><a href="<s:url action="emailTextFile_action" namespace="/"/>">Email address Export</a></td><td>Generate Email Address Text File</td></tr>


</table>
</div>