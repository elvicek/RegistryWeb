<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 



<font class="heading">${sessionScope.title}</font>
<div id="center">
<table>
<tr>
<td><table><tr><td><s:form action="editmember_action" method="Post" name="edit"><s:hidden name="memberId" value=""/><input type="submit" id="edit" value="Edit" onClick="setMemberIdSubmit(document.data.select)"/></s:form></td><td><s:form action="memberdelete_action" method="Post" name="toDelete"><s:hidden name="memberIdToDelete" value=""/><input type="submit" id="delete" value="Delete" onClick="setMemberIdToDelete(document.data.select)"/></s:form></td><td></tr></table></td>
</tr>
</table>
<form name="data" action="post">
<table  class="data" id="memberdata">

<tr>
<th><input type="checkbox" name="selectAll"  onClick="CheckBoxState(document.data.select)"/></th>
<th>Member ID</th>
<th>Title</th>
<th>First Name</th>
<th>Surname</th>
<th>Address</th>
<th>Sex</th>
<th>Phone</th>
<th>Land Line</th>
<th>Work Phone</th>
<th>Water Baptised</th>
<th>email</th>
<th>Employment Status</th>
<th>Cell Group</th>
<th>Created By</th>
<th>Created Date</th>
</tr>

<c:forEach items="${membersBycell}" var="member">
<tr>
<td><input type="checkbox" name="select" onClick="CheckClick(document.data.select)"/></td>
<td>${member.memberId}</td>
<td>${member.title }</td>
<td>${member.name }</td>
<td>${member.surname }</td>
<td>${member.address.addressline1 } <br/>
${member.address.addressline2 } <br/>
${member.address.addressline3 } <br/>

</td>
<td>${member.sex }</td>
<td>${member.cellphone}</td>
<td>${member.phone }</td>
<td>${member.workphone }</td>
<td>${member.waterBaptised }</td>
<td>${member.email }</td>
<td>${member.employmentStatus}</td>
<td>${member.cellgroup.groupName }</td>
<td>${member.createdBy }</td>
<td>${member.createdDate }</td>
</tr>

</c:forEach>

</table>
</form> 
<a href="<s:url action="cellgroupview_action" namespace="/"/>">Back</a>


</div>