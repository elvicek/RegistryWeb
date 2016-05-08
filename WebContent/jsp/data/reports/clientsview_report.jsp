<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<font class="heading">Available Clients</font>
<div id="center">

	<table width="100%">
		<tr>
			<td><table>
					<tr>

						<td><s:form action="clientsreport_action" method="Post"
								name="view_report">
			
								<input type="submit" id="edit" value="View Report"/>
							</s:form></td>
						
					</tr>
				</table></td>
			<td align="right">


			</td>
		</tr>
	</table>
	<br></br>

	<div id="displayOption">
		<table>

			<tr>

				<s:form action="filterClientResults_action" method="Post" name="apply">
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

		<display:table name="clients" pagesize="${pageSize}" class="data"
			requestURI="export_clientsview.action" id="clientdata" export="true">
			<display:column property="clientName" title="Client Name" />
			<display:column property="address.addressline1"
				title="Client Address Line 1" />
			<display:column property="address.addressline2"
				title="Client Address Line 2" />
			<display:column property="address.addressline3"
				title="Client Address Line 3" />
			<display:column property="person.name" title="Contact Name" sortable="true" />
			<display:column property="person.surname" title="Contact Surname" sortable="true" />
			<display:column property="person.cellphone" title="Cell Phone" />
			<display:column property="person.workphone" title="Work Phone" />
			<display:column property="person.address.addressline1"
				title="Contact Address Line 1" />
			<display:column property="person.address.addressline2"
				title="Contact Address Line 2" />
			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.pdf.filename"
				value="UserDetails.pdf" />
			<display:setProperty name="export.excel.filename"
				value="UserDetails.xls" />
			<display:footer media="excel pdf">Sample footer</display:footer>
			<display:caption class="heading">
				<strong>AES Client Details</strong>
			</display:caption>


		</display:table>
	</form>


</div>