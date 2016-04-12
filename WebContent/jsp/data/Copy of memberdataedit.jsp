<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<font class="heading">Members Edit</font>
<div id="center">
<s:form action = "memberupdate_action" method="POST">
	
	<table>
	<tr>
	<td colspan="2"><s:textfield label="Memebr Id" name="member.memberId" size="10" readonly="true" value="%{#session.Member.memberId}"/></td>
	<td colspan="2"><s:textfield label="First Name" name="member.name" size="30" required="true" value="%{#session.Member.name}"/></td>
	<td colspan="2"><s:textfield label="Last Name" name="member.surname" size="30" required="true" value="%{#session.Member.surname}"/></td>
	<td colspan="2"><s:select name="member.title" label="Title" headerKey="%{#session.Member.title}" headerValue="%{#session.Member.title}" list="titles" listKey="description" listValue="description" required="true" value="%{#session.Member.title}"/></td>
	<s:datetimepicker name="birthday" label="BirthDay (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30" value="%{#session.editdata.birthday}"/>
	
    <s:datetimepicker name="convertedDate" label="Converted Date (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" value="%{#session.editdata.convertedDate}"/>
	<td colspan="2"><s:select name="member.sex" label="Sex" headerKey="0" list="sexes" listKey="description" listValue="description" required="true" value="%{#session.Member.sex}"/></td>
	<td colspan="2"><s:textfield label="Address Line 1" name="address.addressline1" size="30" required="true" value="%{#session.Member.address.addressline1}"/></td>
	<td colspan="2"><s:textfield label="Address Line 2" name="address.addressline2" size="30" required="true" value="%{#session.Member.address.addressline2}"/></td>
	<td colspan="2"><s:textfield label="Address Line 3" name="address.addressline3" size="30" value="%{#session.Member.address.addressline3}"/></td>
	<td colspan="2"><s:textfield label="Phone (Home)" name="member.phone" size="15" value="%{#session.Member.phone}"/></td>
	<td colspan="2"><s:textfield label="Cell Phone" name="member.cellphone" size="15" required="true" value="%{#session.Member.cellphone}"/></td>
	<td colspan="2"><s:textfield label="Phone (Work)" name="member.workphone" size="15" value="%{#session.Member.workphone}"/></td>
	<td colspan="2"><s:textfield label="Email" name="member.email" size="30" value="%{#session.Member.email}"/></td>
	<td colspan="2"><s:select name="member.employmentStatus" label="Employment Status" headerKey="%{#session.Member.employmentStatus}" headerValue="%{#session.Member.employmentStatus}" list="status" listKey="description" listValue="description" required="true" value="%{#session.Member.employmentStatus}"/></td>
	<td colspan="2"><s:select name="waterBaptised" label="Water Baptised?" headerKey="%{#session.editdata.baptised}"  list="baptism"  headerValue="%{#session.editdata.baptised}" listKey="description" listValue="description"/></td>
	<td colspan="2"><s:select name="cellgroup.groupName" label="Cell Group" headerKey="%{#session.Member.cellgroup.groupName}" headerValue="%{#session.Member.cellgroup.groupName}" list="cellgroups" listKey="groupName" listValue="groupName" required="true" value="%{#session.Member.cellgroup.groupName}"/></td>
	
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
	

	
	

</s:form>

<%@ include file="membergroupmembership.jsp" %>

</div>