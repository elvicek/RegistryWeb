<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">Email Centre</font>
<div id="center">
<table>
<tr>
<td align="center"><h2><i>Powered By</i></h2></td><td><img border="0" width="76" height="36" src="resources/images/gmail_logo.png" alt="Google Mail"/></td>
</tr>
</table>
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
<s:form action = "sendEmail_action" method="POST">
   <s:textfield  label="Subject" labelposition="left" id ="subject" name="subject" size="79" required="true"/>
   <s:textarea label="Body" cols="60" rows="15" name="message" required="true"></s:textarea>
	<s:submit value="Send Message"/>
	<s:hidden name="groupsToSend" id="groupsToSend" value=" "></s:hidden>
	
	<s:hidden name="individualsSms" value="false"/>
	<s:hidden name="birthdaySms" value="false"/>
	<s:hidden name="groupSms" value="false"/>
</s:form>
	</div>


</div>

