<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<font class="heading">Client Capture</font>
<div id="center">
<s:form action = "surveysave_action" method="POST">
	<input type="checkbox" name="validationRequest"
				value="validateRequest" checked="checked" style="display: none;" />
			<table>
	<table>
	<tr>
	
       <s:actionerror />
	
	<td colspan="2"><s:textfield label="Survey Name" name="survey.surveyName" size="30" required="true"/></td>
	<td colspan="2"><s:select name="clientName" label="Client" headerKey=" " headerValue="[Select a Client]" list="clients" listKey="clientName" listValue="clientName" required="true"/></td>
	<td colspan="2"><s:select name="survey.surveyType" label="Survey Type" headerKey=" " headerValue="[Select Option]" list="surveyTypes" listKey="description" listValue="description" required="true"/></td>
	<s:datetimepicker name="surveyRequestedDate" label="Required Date On Site (dd-MMM-yyyy)" displayFormat="dd-MMM-yyyy" required="true" formatLength="30"/>
	<s:textarea name="survey.workSummary" label="Work Summary"  cols="40" rows="10"/>
	<td align="right" colspan="2"><input  type="reset" value="Reset"/><input type="submit" value="Submit"/></td>
	</tr>
	</table>
	

	
	

</s:form>


</div>