<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<font class="heading">SMS Centre</font>
<div class="Tabview" id="Tabview" style="background: #fff;">
    <div class="Tabs" style="width: 350px;"> <a>Groups</a> <a>Individuals</a> <a>Birthdays</a> </div>
    <div class="Pages" style="width: 100%; height: 600px;">
      <div class="Page">
        <div class="Pad">

 <%@ include file="smsGroups.jsp" %>



</div>






</div>
      
      <div class="Page">
        <div class="Pad">
          <%@ include file="smsIndividuals.jsp" %>
          
      </div>
      </div>
      
      <div class="Page">
        <div class="Pad">
         <%@ include file="smsbirthdaymsg.jsp" %>
          
      </div>
      </div>
      </div>
      </div>
 <table width="80%">
<tr>
<td align="center"><img src="resources/images/CellC.gif" alt="Cell C"/><img src="resources/images/MTN.gif" alt="MTN"/><img border="0" width="35" height="32" src="resources/images/Vodacom.png" alt="Vodacom"/><img src="resources/images/Virgin.gif" alt="Virgin"/><img border="0" width="35" height="40" src="resources/images/8ta.png" alt="8.ta"/></td>
</tr>
</table>
<script type="text/javascript">
    tabview_initialize('Tabview','${tab}');
</script>