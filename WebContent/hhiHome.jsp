<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Landing Page</title>
</head>
<body>
 <c:set var="browser" value="${header['User-Agent']}" scope="session"/> 
 <c:choose>
 <c:when test="${fn:contains(browser,'Mobile')}">
 <jsp:forward page="/jsp/welcome_mobile.jsp"></jsp:forward>
 </c:when>
 <c:otherwise>
 <jsp:forward page="/jsp/welcome.jsp"></jsp:forward>
 </c:otherwise>
 </c:choose>



</body>
</html>