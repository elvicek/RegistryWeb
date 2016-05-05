<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>



<font class="heading">Survey Readings</font>
<c:if test="${not empty requestScope.memberMessage}">
				<table class="messages">
					<tr>
						<td><p>
								<font color="green"><c:out value="${requestScope.memberMessage}"></c:out></font>
							</p></td>
				</table>
				
</c:if>
<div id="center">

	<table width="100%">
		<tr>
			<td><table>
			<tr>
			<s:form action = "surveyreadingssave_action" method="POST">
	<input type="checkbox" name="validationRequest"
				value="validateRequest" checked="checked" style="display: none;" />
			
	
	<tr>
	
       <s:actionerror />
	<s:hidden name="surveyName" value="%{#session.survey.surveyName}"/>
	<s:label>Default calculation is for Dynamic pressure where Pd = 1/2pv2</s:label>
	<td colspan="2"><s:textfield label="Location" name="surveyReading.location" size="40" required="true"/></td>
	<td colspan="2"><s:textfield label="Description" name="surveyReading.description" size="40"/></td>
	<td colspan="2"><s:textfield label="Density (kg/m3) " name="surveyReading.density" size="15" required="true"/></td>
	<td colspan="2"><s:textfield label="Velocity (m/s)" name="surveyReading.velocity" size="15" required="true"/></td>
	<td colspan="2"><s:select name="surveyReading.readingType" label="Reading Type " headerKey=" " headerValue="Dynamic Pressure" list="readingTypes" listKey="description" listValue="description" required="true"/></td>
	<td colspan="2"><s:select name="surveyReading.unit" label="Unit" headerKey=" " headerValue="Pd" list="units" listKey="description" listValue="description" required="true"/></td>
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	
	
</s:form>
			
			</tr>
			<tr><td><hr/></td></tr>
					
<table>
<tr>
						<td><s:form action="readingedit_action" method="Post"
								name="edit">
								<s:hidden name="readingId" value="" />
								<input type="submit" id="edit" value="Edit"
									onClick="setReadingIdForEdit(document.data.select)" />
							</s:form>
						</td>
						<td>
						<s:form action="readingdelete_action" method="Post"
								name="toDelete">
								<s:hidden name="readingIdToDelete" value="" />
								<s:hidden name="surveyName" value="%{#session.survey.surveyName}"/>
								<input type="submit" id="delete" value="Delete"
									onClick="setReadingIdToDelete(document.data.select)" />
							</s:form></td>
					</tr>
					</table>
				</table></td>
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

		<display:table name="readings" pagesize="${pageSize}" class="data"
			requestURI="export_readingsview.action" id="readingsdata" export="true">
			<display:column media="html">
				<input type="checkbox" name="select"
					onClick="CheckClickReadings(document.data.select)" />
			</display:column>
			<display:column property="readingId" title="ID"/>
			<display:column property="location" title="Location" />
			<display:column property="description" title="Location" />
			<display:column property="velocity" title="Velocity" />
			<display:column property="density" title="Density" />
			
			<display:column property="dynamicPressure"
				title="Dynamic Pressure (Pd)" />


			<display:setProperty name="export.pdf" value="true" />
			<display:setProperty name="export.pdf.filename"
				value="ReadingDetails.pdf" />
			<display:setProperty name="export.excel.filename"
				value="ReadingDetails.xls" />
			<display:footer media="excel pdf">Sample footer</display:footer>
			<display:caption class="heading">
				<strong>Readings</strong>
			</display:caption>


		</display:table>
	</form>


</div>