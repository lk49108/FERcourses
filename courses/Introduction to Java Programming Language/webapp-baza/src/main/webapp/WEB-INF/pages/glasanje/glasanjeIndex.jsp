<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<body bgcolor="${sessionScope.pickedBgCol}">
	<h1>${descriptor.title}</h1>
	<p>${descriptor.message}</p>
	<ol>
		<c:forEach var="pollOption" items="${pollOptions}" >
			<li><a href="glasanje-glasaj?pollID=${pollOption.pollID}&id=${pollOption.id}">${pollOption.optionTitle}</a></li>
		</c:forEach>
	</ol>
	
	<br>
	
	<a href="index.html">Home page</a>
	
</body>
</html>