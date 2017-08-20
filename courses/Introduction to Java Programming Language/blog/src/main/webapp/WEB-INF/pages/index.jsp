<%@ page import="java.util.List,hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		
		<jsp:include page="header.jsp" />
		
		<br>
		<br>
		
		<%
    	if (session.getAttribute("current.user") == null) {
		%>
		
		<jsp:include page="loginForm.jsp" />
		
		<br>
		<br>
		
		<p>Do not have account?</p><a href="<%=request.getContextPath()%>/servleti/register">Register</a>
		
		<br>
		<br>
		
		
		<% } %>
		
		
		<% if((List<BlogUser>)request.getAttribute("users") == null || ((List<BlogUser>)request.getAttribute("users")).isEmpty()) {%> 
		<h2>No currently registered users.</h2>
		<% } else { %>
		<h2>List of registered authors:</h2>
		
		<table>
		
		<thead>
		<tr><td><b>First name</b></td><td><b>Last name</b></td><td><b>Nick</b></td></tr>
		</thead>
		
		<c:forEach var="user" items="${users}">
				<tr><td><b>${user.firstName}</b></td><td><b>${user.lastName}</b></td><td><b>${user.nick}</b></td>
				<td><a href="<%=request.getContextPath()%>/servleti/author/${user.nick}">BLOG</a></td></tr>
		</c:forEach>
		
		</table>
		<% } %>
		
	</body>
</html>