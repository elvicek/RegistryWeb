<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<font class="heading">Survey Edit</font>
<div id="center">
<s:form action="surveyupdate_action" method="POST">
	<table>
	<tr>
	<td colspan="2"><s:textfield label="Survey Name" name="survey.surveyName" size="30" value="%{#session.survey.surveyName}" readonly="true"/></td>
	<td colspan="2"><s:select name="clientName" label="Client" headerKey="%{#session.survey.client.clientName}" headerValue="%{#session.survey.client.clientName}" list="clients" listKey="clientName" listValue="clientName" required="true" value="%{#session.survey.client.clientName}"/></td>
	<td colspan="2"><s:select name="survey.surveyType" label="Survey Type" headerKey="%{#session.survey.surveyType}" headerValue="%{#session.survey.surveyType}" list="surveyTypes" listKey="description" listValue="description" required="true" value="%{#session.survey.surveyType}"/></td>
	<s:datetimepicker name="surveyRequestedDate" label="Required Date On Site (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30" value="%{#session.survey.surveyRequestedDate}"/>
	<s:textarea name="survey.workSummary" label="Work Summary"  cols="40" rows="10" value="%{#session.survey.workSummary}"/>
	<s:hidden name="survey.status" value="%{#session.survey.status}" />
	<s:hidden name="survey.surveyDate" value="%{#session.survey.surveyDate}" />
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
	
</s:form>
</div>

