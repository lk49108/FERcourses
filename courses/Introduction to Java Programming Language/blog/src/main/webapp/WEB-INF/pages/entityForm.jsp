<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<h1>${type} entity</h1>
		<br>
		
		<% if(request.getAttribute("type").equals("edit")){ %>
		
		<form action="<%=request.getContextPath()%>/servleti/author/<%=((BlogUser)request.getSession().getAttribute("current.user")).getNick()%>/${type}?id=${form.id}"  method="post">
			Title <textarea name="title" rows="3" cols="50">${form.title}</textarea>
			<c:if test="${form.hasMistake('title')}">
			<c:out value="${form.getMistake('title')}"></c:out>
			</c:if>
			<br>
			Text <textarea name="text" rows="20" cols="100">${form.text}</textarea>
			<c:if test="${form.hasMistake('text')}">
			<c:out value="${form.getMistake('text')}"></c:out>
			</c:if>
			<input type="submit" name="method" value="Submit">
		</form>

		<%  }  else  { %>
		
		<form action="<%=request.getContextPath()%>/servleti/author/<%=((BlogUser)request.getSession().getAttribute("current.user")).getNick()%>/${type}"  method="post">
			Title <textarea name="title" rows="3" cols="50">${form.title}</textarea>
			<c:if test="${form.hasMistake('title')}">
			<c:out value="${form.getMistake('title')}"></c:out>
			</c:if>
			<br>
			Text <textarea name="text" rows="20" cols="100">${form.text}</textarea>
			<c:if test="${form.hasMistake('text')}">
			<c:out value="${form.getMistake('text')}"></c:out>
			</c:if>
			<input type="submit" name="method" value="Submit">
		</form>
		
		<% } %>
		<br>
		<br>
					
		<a href="<%=request.getContextPath()%>/servleti/author/<%=((BlogUser)request.getSession().getAttribute("current.user")).getNick()%>">Main blog page</a>

		<br>
		<br>
		<a href="<%=request.getContextPath()%>/servleti/main">Home page</a>
	</body>
</html>