<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<body bgcolor="${sessionScope.pickedBgCol}">
	<h1>Glasanje za omiljeni bend:</h1>
	<p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste 
	glasali!</p>
	<% int i = 1; %>
	<ol>
		<c:forEach var="band" items="${bands}" >
			<li><a href="glasanje-glasaj?id=<%=i%>">${band}</a></li>
			<% i++; %>
		</c:forEach>
	</ol>
	
	<br>
	
	<a href="index">Home page</a>
	
</body>
</html>