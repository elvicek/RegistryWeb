<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<font class="heading">Message</font>
<div id="center">

	<c:choose>
		<c:when test="${sessionScope.roleMessage == 'roleDeleteError'}">
			<div id="operror">
		</c:when>
		<c:otherwise>
			<div id="savedrecord">
		</c:otherwise>
	</c:choose>

	<c:choose>
		<c:when test="${sessionScope.roleMessage == 'saveRecord'}">
			<table class="messages">
				<tr>
					<td><p>
							New Role Record Saved Successfully<br /> To Add Another Record
							Click on link to Add another Record below<br /> For Other Options
							Use the Menu Navigation
						</p></td>
			</table>
</div>
<span class="internal">
	<table>
		<tr>

			<td><a href="<s:url action="roleinput_action" namespace="/"/>">Add
					Another Record</a></td>
			<td><a
				href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
					Menu</a></td>

		</tr>




	</table>
</span>
</c:when>
<c:when test="${sessionScope.roleMessage == 'smsDeliverySuccess'}">
	<table class="messages">
		<tr>
			<td><p>
					Messages Delivered Successfully<br />
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>

</c:when>
<c:when test="${sessionScope.adminContent == 'write'}">
	<table class="messages">
		<tr>
			<td><p>
					Email Addresses Written Successfully<br />
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>

<c:when test="${sessionScope.roleMessage == 'emailDeliverySuccess'}">
	<table class="messages">
		<tr>
			<td><p>
					Emails Delivered Successfully<br />
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>
<c:when test="${sessionScope.roleMessage == 'updateRecord'}">
	<table class="messages">
		<tr>
			<td><p>
					Role Record Has been Updated Successfully<br /> To Edit Another
					Record Click on link to select another Record to Delete below<br />
					For Other Options Use the Menu Navigation
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a href="<s:url action="rolesview_action" namespace="/"/>">Update
						Another Record</a></td>
				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>
<c:when test="${sessionScope.userMessage == 'updateRecord'}">
	<table class="messages">
		<tr>
			<td><p>
					User Record Has been Updated Successfully<br /> To Add Another
					Record Click on link to select another Record to Delete below<br />
					For Other Options Use the Menu Navigation
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a href="<s:url action="userview_action" namespace="/"/>">Update
						Another Record</a></td>
				<td><a
					href="<s:url action="membersmenu_action" namespace="/"/>">Members
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>
<c:when test="${sessionScope.roleMessage == 'roleDeleteError'}">
	<table class="messages">
		<tr>
			<td><p>
					<span style="color: red">Role Record(s) Cannot be Deleted<br />
						While Users are allocated to it. <br /> Fist Unallocate members from
						the role(s).Then Proceed
					</span>
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>
<c:when test="${sessionScope.roleMessage == 'deleteRecord'}">
	<table class="messages">
		<tr>
			<td><p>
					Role Record(s) Deleted Successfully<br /> To Delete Another Record
					Click on link to Delete another Record below<br /> For Other
					Options Use the Menu Navigation
				</p></td>
	</table>


	</div>
	<span class="internal">
		<table>
			<tr>

				<td><a href="<s:url action="rolesview_action" namespace="/"/>">Delete
						Another Record</a></td>
				<td><a
					href="<s:url action="administrationmenu_action" namespace="/"/>">Admin
						Menu</a></td>

			</tr>




		</table>
	</span>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>



</div>