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
<c:when test="${sessionScope.surveyContent eq initParam.menu }">
<%@ include file="surveysmenu.jsp" %>
</c:when>

<c:when test="${sessionScope.surveyContent eq initParam.error }">
<%@ include file="../../global/error.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq initParam.input }">
<%@ include file="surveysdatainput.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq initParam.success }">
<%@ include file="surveysSuccessMsg.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq initParam.view }">
<%@ include file="surveysview.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq 'viewJobs' }">
<%@ include file="viewmyjobs.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq initParam.edit }">
<%@ include file="surveydataedit.jsp" %>
</c:when>
<c:when test="${sessionScope.surveyContent eq 'attend' }">
<%@ include file="surveydataattend.jsp" %>
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