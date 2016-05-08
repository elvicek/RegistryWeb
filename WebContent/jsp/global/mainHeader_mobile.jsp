<%@ taglib prefix="s" uri="/struts-tags" %>

<br/>
<br/>
<br/>
<table class="mainHeader" width="100%">
<tr>
	<td align="center">
	
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

</tr>
</table>
</span>









