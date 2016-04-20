<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">Message</font>
<div id="center">


<div id="savedrecord">
<c:choose>
<c:when test="${sessionScope.memberMessage == 'saveRecord'}">
<table class="messages">
<tr><td><p>New User Record Saved Successfully<br/>
        To Add Another Record Click on link to Add another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="userinput_action" namespace="/"/>">Add Another Record</a></td><td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Members Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.memberMessage == 'updateRecord'}">
<table class="messages">
<tr><td><p>User Record Has been Updated Successfully<br/>
        To Add Another Record Click on link to select another Record to Delete below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="usersview_action" namespace="/"/>">Update Another Record</a></td><td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Members Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:otherwise>
<table class="messages">
<tr><td><p>User Record(s) Has been Deleted Successfully<br/>
        To Delete Another Record Click on link to Delete another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="usersview_action" namespace="/"/>">Delete Another Record</a></td><td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Members Menu</a></td>

</tr>




</table>
</span>
</c:otherwise>
</c:choose>



</div>