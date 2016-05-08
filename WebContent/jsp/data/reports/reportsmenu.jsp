<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<table class="heading">
<tr>
<td colspan="2" align="left">Welcome to Clients Reports Center. Select Option Of Action You Desire Below </td>
</tr>
</table>
<br/>
<div id="center">
<table class="menu">
<tr><th align="left">Action</th><th align="left">Description</th></tr>
<tr><td><a href="<s:url action="clientview_report_action" namespace="/"/>">All Clients Report</a></td><td>Report Of All Clients Entered in Database</td></tr>
<tr><td><a href="<s:url action="surveyview_report_action" namespace="/"/>">Survey Reading Report</a></td><td>Report of survey readings added to Database</td></tr>

</table>
</div>