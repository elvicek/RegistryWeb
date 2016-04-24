<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<font class="heading">Administrative Roles Edit</font>
<div id="center">


	<table>

		<s:form action="update_role" method="POST">
			<input type="checkbox" name="validationRequest"
				value="validateRequest" checked="checked" style="display: none;" />
			<table>

				<tr>

					<td><s:textfield label="Role Name" labelposition="left"
							id="role.roleName" name="role.roleName" 
							size="30" value="%{#session.role.roleName}" readonly="true" /></td>

				</tr>

				<tr>


					<td><s:textfield label="Role Description"
							labelposition="left" name="role.roleDescription" size="40"
							required="true" value="%{#session.role.roleDescription}" /></td>
				</tr>

				<tr>
			</table>
			<td><input type="submit" value="Submit" /><input type="reset"
				value="Reset" /></td>
			</tr>
			<s:hidden name="roleedit" value="edit" />
			<!--  s:hidden name="role.roleName" value="%{#session.role.roleName}" /-->

		</s:form>



	</table>
</div>