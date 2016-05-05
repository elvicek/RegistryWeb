<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<font class="heading">My Jobs</font>
<div id="center">

	<table width="100%">
		<tr>
			<td><table>
					<tr>

						<td><s:form action="surveyattend_action" method="Post"
								name="edit">
								<s:hidden name="surveyName" value="" />
								<input type="submit" id="edit" value="Attend"
									onClick="setSurveyNameForEdit(document.data.select)" />
							</s:form></td>
							<td><s:form action="surveyeditresults_action" method="Post"
								name="readings">
								<s:hidden name="surveyName" value="" />
								<input type="submit" id="readings" value="View Readings"
									onClick="setSurveyNameForReadingsEdit(document.data.select)" />
							</s:form></td>
						<td><s:form action="surveydelete_action" method="Post"
								name="toDelete">
								<s:hidden name="surveyNameToDelete" value="" />
								<input type="submit" id="delete" value="Delete"
									onClick="setSurveyNameToDelete(document.data.select)" />
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

				<s:form action="filterSurveyResults_action" method="Post"
					name="apply">
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

		<display:table name="surveys" pagesize="${pageSize}" class="data"
			requestURI="export_surveysview.action" id="surveydata" export="true">
			<display:column media="html">
				<input type="checkbox" name="select"
					onClick="CheckClickSurvey(document.data.select)" />
			</display:column>
			<display:column property="surveyName" title="Survey Name" />
			<display:column property="client.clientName" title="Client Name" />
			<display:column property="surveyType" title="Survey Type" />
			<display:column property="surveyRequestedDate"
				title="Survey Requested Date" />
			<display:column property="status"
				title="Survey Status" />


			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.pdf.filename"
				value="UserDetails.pdf" />
			<display:setProperty name="export.excel.filename"
				value="UserDetails.xls" />
			<display:footer media="excel pdf">Sample footer</display:footer>
			<display:caption class="heading">
				<strong>AES Surveys</strong>
			</display:caption>


		</display:table>
	</form>


</div>