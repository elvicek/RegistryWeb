<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">Message</font>
<div id="center">

<c:choose>
<c:when test="${sessionScope.cellgroupMessage == 'cellgroupDeleteError'}">
<div id="operror">
</c:when>
<c:otherwise>
<div id="savedrecord">
</c:otherwise>
</c:choose>

<c:choose>
<c:when test="${sessionScope.cellgroupMessage == 'saveRecord'}">
<table class="messages">
<tr><td><p>New Cell Group Record Saved Successfully<br/>
        To Add Another Record Click on link to Add another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="cellgroupinput_action" namespace="/"/>">Add Another Record</a></td><td><a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Groups Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.cellgroupMessage == 'updateRecord'}">
<table class="messages">
<tr><td><p>Cell Group Record Has been Updated Successfully<br/>
        To Add Another Record Click on link to select another Record to Delete below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="cellgroupview_action" namespace="/"/>">Update Another Record</a></td><td><a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Group Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:when test="${sessionScope.cellgroupMessage == 'cellgroupDeleteError'}">
<table class="messages">
<tr><td><p><span style="color:red">Cell Group Record(s) Cannot be Deleted<br/>
        While Members are allocated to it. <br/>
        Fist Unallocate members to the cellgroup(s).Then Proceed</span></p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Group Menu</a></td>

</tr>




</table>
</span>
</c:when>
<c:otherwise>
<table class="messages">
<tr><td><p>Cell Groups Record(s) Has been Deleted Successfully<br/>
        To Delete Another Record Click on link to Delete another Record below<br/>
        For Other Options Use the Menu Navigation</p></td>

</table>


</div>
<span class="internal">
<table>
<tr>

<td><a href="<s:url action="cellgroupview_action" namespace="/"/>">Delete Another Record</a></td><td><a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Groups Menu</a></td>

</tr>




</table>
</span>
</c:otherwise>
</c:choose>



</div>