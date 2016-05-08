<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1" /> 
<style type="text/css">
#global{
	margin: 10% auto;
	padding: 10px;
	width: 400px;
	height: 200px;
	background: #f9f9f9;
	border: 1px solid #ccc;
	text-align: center;
	font-family: Arial,san-sarif,Verdana;
	font-size: 12px;
	
	
	}
#welcome{
	float: left;
	background: #81d742;
	width: 100%;
	height: 20px;
	padding: 2px;

}
.error{
 color:red;
}
</style>
<title>Login</title>
</head>
<body>
<div id="global">
<div id="welcome">
<table>
<tr>
<td align="left"><font color="white"><b>Welcome</b></font>
</td>
</tr>
</table>
</div>
<br/>
<br/>
<table width="100%">
<tr>
<td align="left">Enter User Name and Password to Login to System</td>
</tr>
</table>
<br/>


<form action="j_security_check" method="POST">
<table width="100%">
<tr>
<td>User Name:</td><td><input type="text" name="j_username"/></td>
</tr>
<tr>
<td>Password:</td><td><input type="password" name="j_password"/></td>
</tr>
<tr>
<td><input type="submit" value="Login"></td><td><input type="reset"></td>
</tr>

</table>
</form>

</table>

</div>
</body>
</html>