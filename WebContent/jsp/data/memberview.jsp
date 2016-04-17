<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<font class="heading">Available Members</font>
<div id="center">

	<table width="100%">
		<tr>
			<td><table>
					<tr>

						<td><s:form action="editmember_action" method="Post"
								name="edit">
								<s:hidden name="memberId" value="" />
								<input type="submit" id="edit" value="Edit"
									onClick="setMemberIdSubmit(document.data.select)" />
							</s:form></td>
						<td><s:form action="memberdelete_action" method="Post"
								name="toDelete">
								<s:hidden name="memberIdToDelete" value="" />
								<input type="submit" id="delete" value="Delete"
									onClick="setMemberIdToDelete(document.data.select)" />
							</s:form></td>
						<td><a
							href="<s:url action="memberview_action" namespace="/"/>">View
								All</a></td>
					</tr>
				</table></td>
			<td align="right">


				<table class="search">

					<tr>
						<td><s:form action="membersearch_action.action" method="Post"
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

				<s:form action="filterResults_action" method="Post" name="apply">
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

		<display:table name="members" pagesize="${pageSize}" class="data"
			requestURI="export_memberview.action" id="memberdata" export="true">
			<display:column media="html">
				<input type="checkbox" name="select"
					onClick="CheckClickMember(document.data.select)" />
			</display:column>
			<display:column property="memberId" title="Member_ID" />
			<display:column property="name" title="Name" sortable="true" />
			<display:column property="surname" title="Surname" sortable="true" />
			<display:column property="birthday" title="Birthday" sortable="true" />
			<display:column property="sex" title="Sex" sortable="true" />
			<display:column property="maritalStatus" title="Marital Status"
				sortable="true" />
			<display:column property="phone" title="Phone" />
			<display:column property="cellphone" title="Cell Phone" />
			<display:column property="workphone" title="Work Phone" />
			<display:column property="address.addressline1"
				title="Address Line 1" />
			<display:column property="address.addressline2"
				title="Address Line 2" />
			<display:column property="address.addressline3"
				title="Address Line 3" />
			<display:column property="waterBaptised" title="Baptised?" />
			<display:column property="employmentStatus" title="Employment"
				sortable="true" />
			<display:column property="cellgroup.groupName" title="CellGroup"
				sortable="true" />
			<display:column property="newMember" title="New Member?"
				sortable="true" />
			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.pdf.filename"
				value="MemberDetails.pdf" />
			<display:setProperty name="export.excel.filename"
				value="MemberDetails.xls" />
			<display:footer media="excel pdf">Sample footer</display:footer>
			<display:caption class="heading">
				<strong>F.I.F Parklands Member Details</strong>
			</display:caption>


		</display:table>
	</form>


</div>