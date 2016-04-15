<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<table class="heading">
<tr><td colspan="2" align="left">Welcome to Cell Group Information Center. Select Option Of Action You Desire Below </td></tr>
</table>

<br></br>
<div id="center">
<table class="menu">
<tr><th align="left">Action</th><th align="left">Description</th></tr>
<tr><td><a href="<s:url action="cellgroupview_action" namespace="/"/>">View All</a></td><td>List Of All Cel Groups Entered in Database</td></tr>
<tr><td><a href="<s:url action="cellgroupinput_action" namespace="/"/>">Cell Group Input</a></td><td>Add Cell Groups to Registry</td></tr>
</table>
</div>