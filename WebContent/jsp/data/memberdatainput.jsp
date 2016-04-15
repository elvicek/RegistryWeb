<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">Members Input</font>
<div id="center">
<s:form action = "membersave_action" method="POST">
	
	<table>
	<tr>
	<td colspan="2"><s:textfield label="First Name" name="member.name" size="30" required="true"/></td>
	<td colspan="2"><s:textfield label="Last Name" name="member.surname" size="30" required="true"/></td>
	<td colspan="2"><s:select name="member.title" label="Title" headerKey=" " headerValue="[Select a Title]" list="titles" listKey="description" listValue="description" required="true"/></td>
	<s:datetimepicker name="birthday" label="BirthDay (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30"/>
	
    <s:datetimepicker name="convertedDate" label="Converted Date (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true"/>
	<td colspan="2"><s:select name="member.sex" label="Sex" headerKey="0" list="sexes" listKey="description" listValue="description" required="true"/></td>
	<td colspan="2"><s:select name="member.maritalStatus" label="Marital Status" headerKey=" " headerValue="[Select a Status]" list="maritalStatus" listKey="description" listValue="description" required="true"/></td>
	<td colspan="2"><s:textfield label="Address Line 1" name="address.addressline1" size="30" required="true"/></td>
	<td colspan="2"><s:textfield label="Address Line 2" name="address.addressline2" size="30" required="true"/></td>
	<td colspan="2"><s:textfield label="Address Line 3" name="address.addressline3" size="30"/></td>
	<td colspan="2"><s:textfield label="Phone (Home)" name="member.phone" size="15"/></td>
	<td colspan="2"><s:textfield label="Cell Phone" name="member.cellphone" size="15" required="true"/></td>
	<td colspan="2"><s:textfield label="Phone (Work)" name="member.workphone" size="15"/></td>
	<td colspan="2"><s:textfield label="Email" name="member.email" size="30"/></td>
	<td colspan="2"><s:select name="member.employmentStatus" label="Employment Status" headerKey=" " headerValue="[Select a Status]" list="status" listKey="description" listValue="description" required="true"/></td>
	<td colspan="2"><s:select name="waterBaptised" label="Water Baptised?" headerKey="0"  list="baptism" listKey="description" listValue="description"/></td>
	<td colspan="2"><s:select name="cellgroup.groupName" label="Cell Group" headerKey=" " headerValue="[Allocate a Cell Group]" list="cellgroups" listKey="groupName" listValue="groupName" required="true"/></td>
	<td colspan="2"><s:checkbox name="member.newMember" fieldValue="true" label="New Member?"/></td>
	
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
	

	
	

</s:form>

</div>