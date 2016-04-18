<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<font class="heading">${sessionScope.title}</font>
<div id="center">
	<table>
		<tr>
			<td><table>
					<tr>
						<td><s:form action="editmember_action" method="Post"
								name="edit">
								<s:hidden name="memberId" value="" />
								<input type="submit" id="edit" value="Edit"
									onClick="setMemberIdSubmit(document.data.select)" />
							</s:form></td>
						<td><s:form action="memberdelete_action" method="Post"
								name="toDelete">
								<s:hidden name="memberIdToDelete" value="" />
								<input type="submit" id="delete" value="Delete"
									onClick="setMemberIdToDelete(document.data.select)" />
							</s:form></td>
						<td>
					</tr>
				</table></td>
		</tr>
	</table>
	<form name="data" action="post">
		<table class="data" id="memberdata">

			<tr>
				<th><input type="checkbox" name="selectAll"
					onClick="CheckBoxState(document.data.select)" /></th>
				<th>User Name</th>
				<th>Title</th>
				<th>First Name</th>
				<th>Surname</th>
				<th>Address</th>
				<th>Sex</th>
				<th>Phone</th>
				<th>email</th>
				<th>Created By</th>
				<th>Created Date</th>
			</tr>

			<c:forEach items="${usersBygroup}" var="user">
				<tr>
					<td><input type="checkbox" name="select"
						onClick="CheckClick(document.data.select)" /></td>
					<td>${user.username}</td>
					<td>${user.person.title }</td>
					<td>${user.person.name }</td>
					<td>${user.person.surname }</td>
					<td>${user.person.address.addressline1 }<br />
						${user.person.address.addressline2 } <br />
						${user.person.address.addressline3 } <br />

					</td>
					<td>${user.person.sex }</td>
					<td>${user.person.cellphone}</td>
					<td>${user.person.email }</td>
					<td>${user.createdBy }</td>
					<td>${user.createdDate }</td>
				</tr>

			</c:forEach>

		</table>
	</form>
	<a href="<s:url action="rolesview_action" namespace="/"/>">Back</a>


</div>