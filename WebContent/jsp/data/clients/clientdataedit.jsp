<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<font class="heading">User Edit</font>
<div id="center">
<s:form action="clientupdate_action" method="POST">
	<table>
	<tr>
	<td colspan="2"><s:textfield label="Client Name" name="client.clientName" size="30" value="%{#session.client.clientName}" readonly="true"/></td>
	<td colspan="3"><s:textfield label="Client Description" name="client.description" size="30" required="true" value="%{#session.client.description}"/></td>
	<td colspan="2"><s:textfield label="Address Line 1" name="clientAddress.addressline1" size="30" required="true" value="%{#session.client.address.addressline1}"/></td>
	<td colspan="2"><s:textfield label="Address Line 2" name="clientAddress.addressline2" size="30" required="true" value="%{#session.client.address.addressline2}"/></td>
	<td colspan="2"><s:textfield label="Address Line 3" name="clientAddress.addressline3" size="30" value="%{#session.client.address.addressline3}"/></td>
	<td colspan="3"><hr/></td>
	<td colspan="3">&nbsp;</td>

	<br/>
	<td colspan="3"><s:label value="Contact Person Details" cssClass="subheading"></s:label></td>
	<td colspan="2"><s:textfield label="First Name" name="person.name" size="30" required="true" value="%{#session.client.person.name}"/></td>
	<td colspan="2"><s:textfield label="Last Name" name="person.surname" size="30" required="true" value="%{#session.client.person.surname}"/></td>
	<td colspan="2"><s:select name="person.title" label="Title" headerKey="%{#session.client.person.title}" headerValue="%{#session.client.person.title}" list="titles" listKey="description" listValue="description" required="true" value="%{#session.client.person.title}"/></td>
	<s:datetimepicker name="birthday" label="BirthDay (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30" value="%%{#session.client.person.birthday}"/>
	<td colspan="2"><s:select name="person.sex" label="Sex" headerKey="0" list="sexes" listKey="description" listValue="description" required="true" value="%{#session.client.person.sex}"/></td>
	<td colspan="2"><s:textfield label="Address Line 1" name="address.addressline1" size="30" required="true" value="%{#session.client.person.address.addressline1}"/></td>
	<td colspan="2"><s:textfield label="Address Line 2" name="address.addressline2" size="30" required="true" value="%{#session.client.person.address.addressline2}"/></td>
	<td colspan="2"><s:textfield label="Address Line 3" name="address.addressline3" size="30" value="%{#session.client.person.address.addressline3}"/></td>
	<td colspan="2"><s:textfield label="Phone (Home)" name="person.phone" size="15" value="%{#session.client.person.phone}"/></td>
	<td colspan="2"><s:textfield label="Cell Phone" name="person.cellphone" size="15" required="true" value="%{#session.client.person.cellphone}"/></td>
	<td colspan="2"><s:textfield label="Phone (Work)" name="person.workphone" size="15" value="%{#session.client.clientName}"/></td>
	<td colspan="2"><s:textfield label="Email" name="person.email" size="30" required="true" value="%{#session.client.person.email}"/></td>
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
	
</s:form>
</div>

