<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<sx:head/>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
@import url(resources/css/main_aes.css);
</style>
<script type="text/javascript" src="scripts/application.js">
</script>
<title>Administration</title>
</head>
<body onload="setFunctions()">
<div id="header">
<%@include file="../../global/mainHeader.jsp" %>
</div>
<div id="leftcol">
<%@include file="../../global/leftmenu.jsp"%>
</div>
<div id="content">

<c:choose>
<c:when test="${sessionScope.adminContent eq initParam.menu }">
<%@ include file="administrationmenu.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.success }">
<%@ include file="roleSuccessMsg.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'membership' }">
<%@ include file="usersByRoleview.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'sms' }">
<%@ include file="groupsSendSms.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'email' }">
<%@ include file="groupsSendEmail.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'rolesview' }">
<%@ include file="rolesview.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.error }">
<%@ include file="../../global/error.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'roleedit' }">
<%@ include file="roledataedit.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.input }">
<%@ include file="roledatainput.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'user_input' }">
<%@ include file="userdatainput.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'user_edit' }">
<%@ include file="userdataedit.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'user_success' }">
<%@ include file="userSuccessMsg.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'write' }">
<%@ include file="roleSuccessMsg.jsp" %>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>
</div>

<div id="footer">

<%@include file="../../global/mainFooter.jsp" %>
</div>
</body>
</html>