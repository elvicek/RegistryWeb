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

	
<form name="dataBirth" action="post">
<display:table name="birthdayCalendar"  class="data" requestURI="birthdaySMS_action.action" id="memberdatabirthday" export="true">
<display:column media="html"><input type="checkbox" name="selectBirthday" /></display:column> 
		<display:column property="memberId" title="Member_ID" />
		<display:column property="member.name" title="Name" sortable="trur" />
		<display:column property="member.surname" title="Surname" sortable="true" />
		<display:column property="day" title="Birthday" sortable="true" />
		<display:column property="compositeDay" title="Date" sortable="true" />
		<display:column property="age" title="Age" sortable="true" />
		<display:column property="member.cellphone" title="Cell Phone"/>
		<display:caption class="heading"><strong>F.I.F Parklands Upcoming BirthDays</strong></display:caption>
		 
		
</display:table>
</form>
</div>
<div id="incentreright">
	<s:form action = "sendSMS_action" method="POST" name="birthday">
   <s:textarea label="Message" cols="40" rows="15" name="messageBirthday" required="true"  onkeyup="CheckMaxLength(this,'displayCount_birthdays')"></s:textarea>
	
	<s:hidden name="memberIdToSms" value=""/>
	<s:hidden name="individualsSms" value="false"/>
	<s:hidden name="birthdaySms" value="true"/>
	<s:hidden name="groupSms" value="false"/>
	<s:submit value="Send Message" onclick="setMemberIdSubmitBirthday(document.dataBirth.selectBirthday)" />
	
	</s:form>
	<div id="displayCount_birthdays"></div>
        </div>