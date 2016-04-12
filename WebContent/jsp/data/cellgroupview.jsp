<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<font class="heading">Available Cell Groups</font>

<div id="center">
<table>
<tr>
<td><table><tr><td><s:form action="editcellgroup_action" method="Post" name="edit"><s:hidden name="cellgroupName" value=""/><input type="submit" id="edit" value="Edit" onClick="setCellgroupNameSubmit(document.data.select)"/></s:form></td><td><s:form action="cellgroupmembership_action" method="Post" name="membership"><s:hidden name="cellgroupNameMembers" value=""/><input type="submit" id="membershipview" value="View Membership" onClick="setCellgroupNameSubmitMembership(document.data.select)"/></s:form></td><td><s:form action="deletecellgroup_action" method="Post" name="toDelete"><s:hidden name="cellgroupToDelete" value=""/><input type="submit" id="delete" value="Delete" onClick="setCellgroupToDelete(document.data.select)"/></s:form></td><td></tr></table></td>
</tr>
</table>
<form name="data" action="post">
<table class="data" id="cellgroupdata">
<tr>
<th><input type="checkbox" name="selectAll" onClick="CheckBoxState(document.data.select)"/></th>
<th>Cell Group Name</th>
<th>Cell group Location</th>
<th>Cell Group Leader</th>
<th>Created By</th>
<th>Created Date</th>
</tr>
<c:forEach items="${cellGroups}" var="cell">

<tr>
<td align="center"><input type="checkbox" name="select" onClick="CheckClick(document.data.select)"/></td><td>${cell.groupName}</td>
<td>${cell.groupLocation }</td>
<td>${memberMap[cell.memberId] }</td> 
<td>${cell.createdBy }</td>
<td>${cell.createdDate }</td>
</tr>
</c:forEach>
</table>
</form>
</div>