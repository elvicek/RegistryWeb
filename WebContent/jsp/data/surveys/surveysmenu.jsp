<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<table class="heading">
<tr>
<td colspan="2" align="left">Welcome to Surveys Information Center. Select Option Of Action You Desire Below </td>
</tr>
</table>
<br/>
<div id="center">
<table class="menu">
<tr><th align="left">Action</th><th align="left">Description</th></tr>
<tr><td><a href="<s:url action="allsurveysview_action" namespace="/"/>">View All Surveys</a></td><td>List Of All Survey requests Entered in Database</td></tr>
<tr><td><a href="<s:url action="createsurveys_action" namespace="/"/>">Create New Survey</a></td><td>Add surveys to Registry</td></tr>
<tr><td><a href="<s:url action="mysurveysview_action" namespace="/"/>">My Jobs</a></td><td>List Of All Jobs Allocate To Me</td></tr>
<tr><td><a href="<s:url action="surveyauth_action" namespace="/"/>">Authorise Surveys</a></td><td>Authorise Surveys</td></tr>

</table>
</div>