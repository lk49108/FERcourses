<%@ page import="java.util.List,hr.fer.zemris.java.tecaj_13.model.BlogEntry,hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		
		<jsp:include page="header.jsp" />
		
		<br>
		<br>
	
		<h3> Blog author -> First name: ${author.firstName}
			<br>   &#160&#160&#160&#160&#160&#160&#160&#160
			&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160
			Last name: ${author.lastName}
			<br>   &#160&#160&#160&#160&#160&#160&#160&#160
			&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160&#160       
			Nick: ${author.nick}
		</h3>
		
		<% if(((List<BlogEntry>)request.getAttribute("blogEntries")) == null){ %>
				<h3>There are no blog entries for this user."</h3>
		<% } else { %>
				<h2>Entries:</h2>
				<br>
			<c:forEach var="entry" items="${blogEntries}">
				<br> <h2><a href="<%=request.getContextPath()%>/servleti/author/${author.nick}/${entry.id}">${entry.title}</a></h2>
			</c:forEach>
		<% } %>	
		
		<br>		<br>		<br>		<br>
		
		<% if( request.getSession().getAttribute("current.user") != null && ((BlogUser)request.getAttribute("author")).getId().equals(((BlogUser)request.getSession().getAttribute("current.user")).getId())) { %>
			<a href="<%=request.getContextPath()%>/servleti/author/${author.nick}/new">New blog entry</a>
		<% } %>
		
		<br>
		<br>
		<a href="<%=request.getContextPath()%>/servleti/main">Home page</a>
	</body>
</html>