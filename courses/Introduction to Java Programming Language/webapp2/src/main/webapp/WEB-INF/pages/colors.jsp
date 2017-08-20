<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<html>
	<head>
		<title>Choose color. (choose wisely)</title>
	</head>
<body bgcolor="${sessionScope.pickedBgCol}">
	<ul>
		<li><a href="setColor?color=white">WHITE</a></li>
		<li><a href="setColor?color=red">RED</a></li>
		<li><a href="setColor?color=green">GREEN</a></li>
		<li><a href="setColor?color=cyan">CYAN</a></li>
	</ul>
	
	<br>
	
	<a href="index">Home page</a>
	
</body>
</html>