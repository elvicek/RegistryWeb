<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style type="text/css">
@import url(resources/css/main.css);
</style>
<script type="text/javascript" src="scripts/application.js">
</script>
<title>Administration</title>
</head>
<body onload="setFunctions()">
<div id="header">
<%@include file="global/mainHeader.jsp" %>
</div>
<div id="leftcol">
<%@include file="global/leftmenu.jsp"%>
</div>
<div id="content">

<c:choose>
<c:when test="${sessionScope.adminContent eq initParam.menu }">
<%@ include file="data/administrationmenu.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.success }">
<%@ include file="data/groupSuccessMsg.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'membership' }">
<%@ include file="data/memberByGroupview.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'sms' }">
<%@ include file="data/groupsSendSms.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent == 'email' }">
<%@ include file="data/groupsSendEmail.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.view }">
<%@ include file="data/groupsview.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.error }">
<%@ include file="global/error.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.edit }">
<%@ include file="data/groupsdataedit.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq initParam.input }">
<%@ include file="data/groupsdatainput.jsp" %>
</c:when>
<c:when test="${sessionScope.adminContent eq 'write' }">
<%@ include file="data/groupSuccessMsg.jsp" %>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>
</div>

<div id="footer">

<%@include file="global/mainFooter.jsp" %>
</div>
</body>
</html>