<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<table class="heading">
<tr>
<td colspan="2" align="left">Welcome to Membership Information Center. Select Option Of Action You Desire Below </td>
</tr>
</table>
<br/>
<div id="center">
<table class="menu">
<tr><th align="left">Action</th><th align="left">Description</th></tr>
<tr><td><a href="<s:url action="memberview_action" namespace="/"/>">View All</a></td><td>List Of All Members Entered in Database</td></tr>
<tr><td><a href="<s:url action="memberinput_action" namespace="/"/>">Member Input</a></td><td>Add members to Registry</td></tr>

</table>
</div>