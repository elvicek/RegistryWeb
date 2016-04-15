<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<br/>
<br/>
<font class="heading">Select Groups That Member Should be added to</font>
<br/>
<br/>
<s:form action = "allocategroups_action" method="POST">
	
	<table>
	
	<tr>
	<td><s:select name="groupsSelect" id="groupsSelect" label="Available Groups"  list="options" listKey="groupId" listValue="groupName" multiple="true" size="10" cssStyle="width:200px"/></td>
	</tr>
	<tr>
	<td colspan="2" align="center"><input type="button" id="edit" value="Add Group" onclick="moveOption('groupsSelect','groupsAllocated')" class="button"/></td>
	</tr>
	<td colspan="2" align="center"><input type="button" id="edit" value="Remove Group" onclick="moveOption('groupsAllocated','groupsSelect')" class="button"/></td>
	<tr>
	<td><s:select name="groupsAllocated" id="groupsAllocated" label="Groups Chosen"  list="allocated" listKey="groupId" listValue="groupName" multiple="true" size="10" cssStyle="width:200px"/></td>
	</tr>
	</table>
	<s:hidden name="memberId" value="%{#session.Member.memberId}"/>
	

	<tr>
	<td colspan="2"><input type="submit" id="save" value="Save" onClick="selectAll('groupsAllocated')"/></td>
	</tr>
	

</s:form>




