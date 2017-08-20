<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style type="text/css">
 .topcorner{
    position:fixed;
    top:10px;
    right: 10px;
 }
</style>

<style type="text/css">
 .leftcorner{
    position:fixed;
    top:10px;
    left: 10px;
 }
</style>

<html>
	<body>
		
		<%
    	if (session.getAttribute("current.user") != null) {
		%>
		
		<div class="topcorner"><a href="<%=request.getContextPath()%>/servleti/logout">Logout</a></div>
		<br>
		<br>
		<div class="leftcorner">User: <%  out.println(((BlogUser)session.getAttribute("current.user")).getNick()); %></div>
		
		
		<%
    	} else {
    	%>
    
		<div class="leftcorner">Not logged in</div>
    
    	<%		
    	}
		%>

	</body>
</html>