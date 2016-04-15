<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<div id="center">


<table>

<s:form action="update_group" method="POST">
<input type="checkbox" name="validationRequest" value="validateRequest" checked="checked" style="display:none;" />
<table >

<tr>

<td><s:textfield  label="Group Name" labelposition="left" id ="groups.groupName" name="groups.groupName" required="true" size="30" value="%{#session.group.groupName}" readonly="true"/></td>

</tr>

<tr>


<td><s:textfield label="Group Description" labelposition="left" name="groups.groupDescription" size="40" required="true" value="%{#session.group.groupDescription}"/></td>
</tr>

<tr>
</table>
<td><input type="submit" value="Submit"/><input type="reset" value="Reset"/></td>
</tr>
<s:hidden name="groupsedit" value="edit"/>
<s:hidden  name="groups.groupId"  value="%{#session.group.groupId}"/>

</s:form>



</table>
</div>