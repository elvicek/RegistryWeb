<%@ taglib prefix="s" uri="/struts-tags" %>

<br/>
<br/>
<br/>
<table class="mainHeader" width="100%">
<tr>
	<td align="center">
	<!-- 
		<font class="headerTitle">
			
		    HHI Capetown Registry
			
		</font>
	 -->
	 &nbsp;&nbsp;
	</td>
</tr>
</table>
<span class="outer">
<table width="100%">
<tr><td>&nbsp;</td></tr>
<tr>
<td>
<span class="headerleft">
<b><font color="grey">Logged In: ${pageContext.request.remoteUser}</font> |<a href="<s:url action="signout_action" namespace="/"/>"> Sign out</a></b>
</span>
</td>

<td align="right">
	<table class="mainmenu" >
	<tr>
		<td align="right"><a href="<s:url action="home_action" namespace="/"/>">
Home</a>
		|
		<a href="<s:url action="membersmenu_action" namespace="/"/>">Members</a>
		| 
		<a href="#">Maintenance</a>
		|
		<a href="<s:url action="cellgroupmenu_action" namespace="/"/>">Cell Groups</a>
		|
		<a href="<s:url action="administrationmenu_action" namespace="/"/>">Administration</a> 
		|
		<a href="<s:url action="help_action" namespace="/"/>">Help</a>
		</td> 
		
	</tr>
	</table>
</td>

</tr>
</table>
</span>









