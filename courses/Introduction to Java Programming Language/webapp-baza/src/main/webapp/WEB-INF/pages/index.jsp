<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>

<head>
<style>
ul#menu {
    padding: 3;
}

ul#menu li {
    display: list-item;
}

ul#menu li a {
    background-color: black;
    color: white;
    padding: 9px 9px;
    text-decoration: none;
    border-radius: 2px 2px 0 0;
}

ul#menu li a:hover {
    background-color: orange;
}
</style>
</head>

<body>
	
	<h1>Available polls</h1>
	
	<ul id="menu">
	<c:forEach items="${pollOptions}" var="pollOption">
		<li><a href="glasanje?pollID=${pollOption.id}">${pollOption.title}</a></li>
		<li hidden="true">
	</c:forEach>
	</ul>  
		
</body>
</html>