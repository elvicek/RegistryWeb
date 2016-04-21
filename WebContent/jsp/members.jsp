<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<s:head theme="simple" />
<style type="text/css">

@import url(resources/css/main_aes.css);
</style>
<script type="text/javascript" src="scripts/application.js">
</script>

<title>Members</title>
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
<c:when test="${sessionScope.memberContent eq initParam.menu }">
<%@ include file="data/clients/clientmenu.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.success }">
<%@ include file="data/memberSuccessMsg.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.input }">
<%@ include file="data/memberdatainput.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.view }">
<%@ include file="data/memberview.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq 'userview' }">
<%@ include file="data/admin/userview.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.error }">
<%@ include file="global/error.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.edit }">
<%@ include file="data/memberdataedit.jsp" %>
</c:when>
<c:when test="${sessionScope.memberContent eq initParam.leader }">

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