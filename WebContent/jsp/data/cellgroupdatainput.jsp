<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">Cell Group Input</font>
<div id="center">


<table>

<s:form action="save_cellgroup" method="POST">
<input type="checkbox" name="validationRequest" value="validateRequest" checked="checked" style="display:none;" />
<table >

<tr>

<td><s:textfield  label="Cell Group Name" labelposition="left" id ="cellgroup.groupName" name="cellgroup.groupName" size="30" required="true"/></td>

</tr>

<tr>


<td><s:textfield label="Cell Group Location" labelposition="left" name="cellgroup.groupLocation" size="40" required="true"/></td>
</tr>
<tr>

<td><s:select name="member" label="Cell Group Leader" labelposition="left" headerKey="0" headerValue="[Select a Member]" list="members" listKey="memberId" listValue="name"/></td>
</tr>
<tr>
</table>
<td><input type="submit" value="Submit"/><input type="reset" value="Reset"/></td>
</tr>

</s:form>



</table>
</div>