<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<table>
<tr>
<td align="center"><h2><i>Powered By</i></h2></td><td><img border="0" width="150" height="45" src="resources/images/clickatell.png" alt="Clickatell"/></td>
</tr>
<tr><p><b>Important:</b> Please note that a standard sms is 160 characters long.</p></tr>
</table>
<div id="incentreleft_scroll">

	
<form name="data" action="post">
<display:table name="members"  class="data" requestURI="individualSMS_action.action" id="memberdataIndividual" export="false">
<display:column media="html"><input type="checkbox" name="select" onClick="CheckClickMember(document.data.select)"/></display:column> 
		<display:column property="memberId" title="Member_ID" />
		<display:column property="name" title="Name" sortable="true" />
		<display:column property="surname" title="Surname" sortable="true" />
		<display:column property="birthday" title="Birthday" sortable="true" />
		<display:column property="sex" title="Sex" sortable="false" />
		<display:column property="maritalStatus" title="Marital Status" sortable="true" />
		<display:column property="cellphone" title="Cell Phone"/>
		<display:column property="cellgroup.groupName" title="CellGroup" sortable="true" />
		<display:column property="newMember" title="New Member?"/>
		<display:caption class="heading"><strong>F.I.F Parklands Members</strong></display:caption>
		 
		
</display:table>
</form>
</div>
<div id="incentreright">
	<s:form action = "sendSMS_action" method="POST" name="individual">
   <s:textarea label="Message" cols="40" rows="15" name="messageIndividual" required="true"  onkeyup="CheckMaxLength(this,'displayCount_individuals')"></s:textarea>
	
	<s:hidden name="memberIdToSms" value=""/>
	<s:hidden name="individualsSms" value="true"/>
	<s:hidden name="birthdaySms" value="false"/>
	<s:hidden name="groupSms" value="false"/>
	<s:submit value="Send Message" onclick="setMemberIdSubmitIndividual(document.data.select)" />
	
	</s:form>
	<div id="displayCount_individuals"></div>
        </div>