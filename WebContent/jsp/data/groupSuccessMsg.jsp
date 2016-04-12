<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<font class="heading">Message</font>
<div id="center">

<c:choose>
<c:when test="${sessionScope.groupMessage == 'cellgroupDeleteError'}">
<div id="operror">
</c:when>
<c:otherwise>
<div id="savedrecord">
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${sessionScope.groupMessage == 'saveRecord'}">
<table class="messages">
<tr><td><p>New Group Record Saved Successfully<br/>
        To Add Another Record Click on link to Add another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="groupsinput_action" namespace="/"/>">Add Another Record</a></td><td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Groups Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.groupMessage == 'smsDeliverySuccess'}">
<table class="messages">
<tr><td><p>Messages Delivered Successfully<br/>
        </p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Groups Menu</a></td>

</tr>




</table>
</span>

</c:when>
<c:when test="${sessionScope.adminContent == 'write'}">
<table class="messages">
<tr><td><p>Email Addresses Written Successfully<br/>
        </p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Groups Menu</a></td>

</tr>




</table>
</span>
</c:when>

<c:when test="${sessionScope.groupMessage == 'emailDeliverySuccess'}">
<table class="messages">
<tr><td><p>Emails Delivered Successfully<br/>
        </p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Groups Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.groupMessage == 'updateRecord'}">
<table class="messages">
<tr><td><p>Group Record Has been Updated Successfully<br/>
        To Edit Another Record Click on link to select another Record to Delete below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="groupsview_action" namespace="/"/>">Update Another Record</a></td><td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Cell Group Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.memberMessage == 'updateRecord'}">
<table class="messages">
<tr><td><p>Member Record Has been Updated Successfully<br/>
        To Add Another Record Click on link to select another Record to Delete below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="memberview_action" namespace="/"/>">Update Another Record</a></td><td><a href="<s:url action="membersmenu_action" namespace="/"/>">Members Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.groupMessage == 'groupDeleteError'}">
<table class="messages">
<tr><td><p><span style="color:red">Group Record(s) Cannot be Deleted<br/>
        While Members are allocated to it. <br/>
        Fist Unallocate members to the group(s).Then Proceed</span></p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="administrationmenu_action" namespace="/"/>">Group Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.groupMessage == 'deleteRecord'}">
<table class="messages">
<tr><td><p>Groups Record(s)  Deleted Successfully<br/>
        To Delete Another Record Click on link to Delete another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="groupsview_action" namespace="/"/>">Delete Another Record</a></td><td><a href="<s:url action="memberdeletesuccess_action" namespace="/"/>">Group Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>



</div>