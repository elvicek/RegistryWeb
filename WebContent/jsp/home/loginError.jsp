<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
#global{
	margin: 10% auto;
	padding: 10px;
	width: 400px;
	height: 150px;
	background: #f9f9f9;
	border: 1px solid #ccc;
	text-align: left;
	
	}

.error{
 color:red;
}
</style>
<title>Login Error</title>
</head>
<body>
<div id="global">
<table>
<tr>
<td><span style="color: red">Authentication Failed,Wrong user name or password. Contact Administrator or </span><a href="<%=request.getContextPath() %>/hhiHome.jsp">Retry</a></td>
</tr>
</table>
</div>

</body>
</html>