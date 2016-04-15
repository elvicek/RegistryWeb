<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<font class="heading">Administrative Groups Input</font>
<div id="center">


<table>

<s:form action="save_group" method="POST">
<input type="checkbox" name="validationRequest" value="validateRequest" checked="checked" style="display:none;" />
<table >

<tr>

<td><s:textfield  label="Group Name" labelposition="left" id ="groups.groupName" name="groups.groupName" size="30" required="true"/></td>

</tr>

<tr>


<td><s:textfield label="Group Description" labelposition="left" name="groups.groupDescription" size="40" required="true"/></td>
</tr>
<tr>
</table>
<td><input type="submit" value="Submit"/><input type="reset" value="Reset"/></td>
</tr>

</s:form>



</table>
</div>