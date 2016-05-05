<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<font class="heading">Survey Edit</font>
<div id="center">
	<s:form action="surveyupdatestatus_action" method="POST">
		<table>
			<tr>
				<td colspan="2"><s:textfield label="Survey Name"
						name="survey.surveyName" size="30"
						value="%{#session.survey.surveyName}" readonly="true" /></td>
				<td colspan="2"><s:textfield name="clientName" label="Client"
						readonly="true"
						value="%{#session.survey.client.clientName}" /></td>
				<td colspan="2"><s:textfield name="survey.surveyType"
						label="Survey Type" readonly="true"
						value="%{#session.survey.surveyType}" /></td>
				<td colspan="2"><s:textfield name="surveyRequestedDate"
						label="Required Date On Site (dd-MMM-yyyy)"
					    readonly="true"
						value="%{#session.survey.surveyRequestedDate}" /></td>
				<s:textarea name="survey.workSummary" label="Work Summary" cols="40"
					rows="10" value="%{#session.survey.workSummary}" readonly="true" />
				<s:hidden name="survey.status" value="%{#session.survey.status}"
					/>
				<s:hidden name="survey.surveyDate"
					value="%{#session.survey.surveyDate}"/>
				<c:choose>
					<c:when test="${sessionScope.survey.status eq 'ASSIGNED' && requestScope.mode eq 'changeStatus' }">
					<s:hidden name="mode" value="changeStatus" />
						<td align="right" colspan="2"><input type="submit"
						value="Accept" /></td>
					</c:when>
					<c:when test="${sessionScope.survey.status eq 'NEW' && requestScope.mode eq 'assign' }">
					<td colspan="2"><s:select name="userName" label="User" headerKey=" " headerValue="[Select a User]" list="userDetails" listKey="userName" listValue="displayName" required="true"/></td>
					<s:hidden name="mode" value="assign" />
						<td align="right" colspan="2"><input type="submit"
						value="Assign" /></td>
					</c:when>
					<c:otherwise>
					<s:hidden name="mode" value="changeStatus" />
					<td align="right" colspan="2"><input type="submit"
						value="Complete" /></td>
					</c:otherwise>
				</c:choose>	
			</tr>
		</table>

	</s:form>
</div>

