<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<font class="heading">View All Administrative Groups</font>
<div id="center">
<table>
<tr>
<td><table><tr><td><s:form action="editgroups_action" method="Post" name="edit"><s:hidden name="groupId" value=""/><input type="submit" id="edit" value="Edit" onClick="setGroupNameSubmit(document.data.select)"/></s:form></td><td><s:form action="groupmembership_action" method="Post" name="membership"><s:hidden name="groupIdMembers" value=""/><input type="submit" id="membershipview" value="View Membership" onClick="setGroupNameSubmitMembership(document.data.select)"/></s:form></td><td><s:form action="deletegroup_action" method="Post" name="toDelete"><s:hidden name="groupToDelete" value=""/><input type="submit" id="delete" value="Delete" onClick="setGroupToDelete(document.data.select)"/></s:form></td><td></tr></table></td>
</tr>
</table>
<form name="data" action="post">
<table class="data" id="groupsdata">
<tr>
<th><input type="checkbox" name="selectAll" onClick="CheckBoxState(document.data.select)"/></th>
<th>Group Name</th>
<th>Group Description</th>
<th>Created By</th>
<th>Created Date</th>
</tr>
<c:forEach items="${groups}" var="group">

<tr>
<td align="center"><input type="checkbox" name="select" onClick="CheckClick(document.data.select)"/></td>
<td style="display:none;">${group.groupId}</td>
<td>${group.groupName }</td>
<td>${group.groupDescription }</td> 
<td>${group.createdBy }</td>
<td>${group.createdDate }</td>
</tr>
</c:forEach>
</table>
</form>
</div>