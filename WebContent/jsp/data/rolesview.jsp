<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<font class="heading">View All Administrative Groups</font>
<div id="center">
	<table>
		<tr>
			<td><table>
					<tr>
						<td><s:form action="editroles_action" method="Post"
								name="edit">
								<s:hidden name="roleId" value="" />
								<input type="submit" id="edit" value="Edit"
									onClick="setGroupNameSubmit(document.data.select)" />
							</s:form></td>
						<td><s:form action="rolesmembership_action" method="Post"
								name="membership">
								<s:hidden name="roleId" value="" />
								<input type="submit" id="membershipview" value="View Membership"
									onClick="setRoleNameSubmitMembership(document.data.select)" />
							</s:form></td>
						<td><s:form action="deleterole_action" method="Post"
								name="toDelete">
								<s:hidden name="roleToDelete" value="" />
								<input type="submit" id="delete" value="Delete"
									onClick="setRoleToDelete(document.data.select)" />
							</s:form></td>
						<td>
					</tr>
				</table></td>
		</tr>
	</table>
	<form name="data" action="post">
		<table class="data" id="rolesdata">
			<tr>
				<th><input type="checkbox" name="selectAll"
					onClick="CheckBoxState(document.data.select)" /></th>
				<th>Role Name</th>
				<th>Role Description</th>
				<th>Created By</th>
				<th>Created Date</th>
			</tr>
			<c:forEach items="${roles}" var="role">

				<tr>
					<td align="center"><input type="checkbox" name="select"
						onClick="CheckClick(document.data.select)" /></td>
					<td style="display: none;">${role.roleName}</td>
					<td>${role.roleName }</td>
					<td>${role.roleDescription }</td>
					<td>${role.createdBy }</td>
					<td>${role.createdDate }</td>
				</tr>
			</c:forEach>
		</table>
	</form>
</div>