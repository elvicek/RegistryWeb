<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<font class="heading">Available Users</font>
<div id="center">

	<table width="100%">
		<tr>
			<td><table>
					<tr>

						<td><s:form action="edituser_action" method="Post"
								name="edit">
								<s:hidden name="userId" value="" />
								<input type="submit" id="edit" value="Edit"
									onClick="setUserIdSubmit(document.data.select)" />
							</s:form></td>
						<td><s:form action="userdelete_action" method="Post"
								name="toDelete">
								<s:hidden name="userIdToDelete" value="" />
								<input type="submit" id="delete" value="Delete"
									onClick="setUserIdToDelete(document.data.select)" />
							</s:form></td>
						<td><a
							href="<s:url action="usersview_action" namespace="/"/>">View
								All</a></td>
					</tr>
				</table></td>
			<td align="right">


				<table class="search">

					<tr>
						<td><s:form action="usersearch_action.action" method="Post"
								name="search">
								<s:hidden name="stringToSearch" value="" />
								<input type="text" id="searchText" width="40" />
								<input type="submit" id="edit" value="Search"
									onClick="setSearchString(document.search.searchText.value)" />
							</s:form></td>

					</tr>
				</table>

			</td>
		</tr>
	</table>
	<br></br>

	<div id="displayOption">
		<table>

			<tr>

				<s:form action="filterUserResults_action" method="Post" name="apply">
					<tr>
						<td align="left" colspan="2">Display Options</td>
					</tr>
					<td><select id="pagerSize" name="pagerSize">
							<option>${pageSize}</option>
							<c:forEach var="pageType" items="${applicationScope.pagerParams}">
								<c:if test="${pageType ne pageSize}">
									<option>${pageType}</option>
								</c:if>
							</c:forEach>
					</select></td>
					<td align="left"><input type="submit" id="apply" value="Apply"
						onClick="refreshPager('pagerSize')" /></td>

					<s:hidden name="pageSizeOption" value="" />
				</s:form>

			</tr>

		</table>
	</div>
	<br />

	<form name="data" action="post">

		<display:table name="users" pagesize="${pageSize}" class="data"
			requestURI="export_userview.action" id="memberdata" export="true">
			<display:column media="html">
				<input type="checkbox" name="select"
					onClick="CheckClickMember(document.data.select)" />
			</display:column>
			<display:column property="username" title="User Name" />
			<display:column property="person.name" title="Name" sortable="true" />
			<display:column property="person.surname" title="Surname" sortable="true" />
			<display:column property="person.birthday" title="Birthday" sortable="true" />
			<display:column property="person.sex" title="Sex" sortable="true" />
			<display:column property="person.phone" title="Phone" />
			<display:column property="person.cellphone" title="Cell Phone" />
			<display:column property="person.workphone" title="Work Phone" />
			<display:column property="person.address.addressline1"
				title="Address Line 1" />
			<display:column property="person.address.addressline2"
				title="Address Line 2" />
			<display:column property="person.address.addressline3"
				title="Address Line 3" />
			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.pdf.filename"
				value="UserDetails.pdf" />
			<display:setProperty name="export.excel.filename"
				value="UserDetails.xls" />
			<display:footer media="excel pdf">Sample footer</display:footer>
			<display:caption class="heading">
				<strong>AES User Details</strong>
			</display:caption>


		</display:table>
	</form>


</div>