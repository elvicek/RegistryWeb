<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<font class="heading">User Edit</font>
<div id="center">
<s:form action="userupdate_action" method="POST">
	<table>
	<tr>
	<td colspan="2"><s:textfield label="User Name" name="user.username" size="30" required="true" value="%{#session.user.username}"/></td>
	<td colspan="3"><s:password label="Password" name="user.password" size="30" required="true" value="%{#session.user.password}"/></td>
	<td colspan="2"><s:password label="Confirm Password" name="confirm" size="30" required="true" value="%{#session.user.password}"/></td>
	<td colspan="2"><s:select name="roleId" label="Role" headerKey="roleId" headerValue="roleId" list="roles" listKey="roleName" listValue="roleName" required="true" value="roleId"/></td>
	<td colspan="3"><hr/></td>
	<td colspan="2"><s:textfield label="First Name" name="person.name" size="30" required="true" value="%{#session.person.name}"/></td>
	<td colspan="2"><s:textfield label="Last Name" name="person.surname" size="30" required="true" value="%{#session.person.surname}"/></td>
	<td colspan="2"><s:select name="person.title" label="Title" headerKey="%{#session.person.title" headerValue="%{#session.person.title" list="titles" listKey="description" listValue="description" required="true" value="%{#session.person.title}"/></td>
	<s:datetimepicker name="birthday" label="BirthDay (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30" value="%{#session.person.birthday}"/>
	<td colspan="2"><s:select name="person.sex" label="Sex" headerKey="0" list="sexes" listKey="description" listValue="description" required="true" value="%{#session.person.sex}"/></td>
	<td colspan="2"><s:textfield label="Address Line 1" name="address.addressline1" size="30" required="true" value="%{#session.address.addressline1}"/></td>
	<td colspan="2"><s:textfield label="Address Line 2" name="address.addressline2" size="30" required="true" value="%{#session.address.addressline2}"/></td>
	<td colspan="2"><s:textfield label="Address Line 3" name="address.addressline3" size="30" value="%{#address.addressline3}"/></td>
	<td colspan="2"><s:textfield label="Phone (Home)" name="person.phone" size="15" value="%{#session.person.phone}"/></td>
	<td colspan="2"><s:textfield label="Cell Phone" name="person.cellphone" size="15" required="true" value="%{#session.person.cellphone}"/></td>
	<td colspan="2"><s:textfield label="Phone (Work)" name="person.workphone" size="15" value="%{#session.person.workphone}"/></td>
	<td colspan="2"><s:textfield label="Email" name="person.email" size="30" required="true" value="%{#session.person.email}"/></td>
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
</s:form>
</div>

