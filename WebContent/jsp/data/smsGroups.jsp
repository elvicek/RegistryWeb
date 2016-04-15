<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div id="top_borders">
<table>
<tr>
<td align="center"><h2><i>Powered By</i></h2></td><td><img border="0" width="150" height="45" src="resources/images/clickatell.png" alt="Clickatell"/></td>
</tr>
<tr><p><b>Important:</b> Please note that a standard sms is 160 characters long.</p></tr>
</table>
</div>
<div id="incentreleft">

	
	
<table>
<tr>
<td>

<s:select name="groupsSelect" id="groupsSelect" label="Available Groups"  list="options" listKey="groupId" headerValue="Select All" headerKey="all" listValue="groupName" multiple="true" size="10" cssStyle="width:175px"/>
</td>
<td align="right" colspan="3"><input type="button" id="edit" value="Add" onclick="moveOption('groupsSelect','groupsAllocated')" class="button"/>
	
<input type="button" id="edit" value="Remove" onclick="moveOption('groupsAllocated','groupsSelect')" class="button"/>
</td>
<td>
	<s:select name="groupsAllocated" id="groupsAllocated" label="Groups Chosen"  list="allocated" listKey="groupId" listValue="groupName" multiple="true" size="10" cssStyle="width:175px"/>
</td>
</tr>	
</table>	
</div>
<div id="incentreright">
<s:form action = "sendSMS_action" method="POST">
   <s:textarea label="Message" cols="40" rows="15" name="messageGroup" required="true" onkeyup="CheckMaxLength(this,'displayCount_group')"></s:textarea>
	<s:submit value="Send Message"/>
	<s:hidden name="individualsSms" value="false"/>
	<s:hidden name="birthdaySms" value="false"/>
	<s:hidden name="groupSms" value="true"/>
	<s:hidden name="groupsToSend" id="groupsToSend" value=" "></s:hidden>
</s:form>

<div id="displayCount_group"></div>
	</div>
	
	