<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>

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

<body bgcolor="${sessionScope.pickedBgCol}">
		
	<ul id="menu">
		<li><a href="colors">Background color chooser</a></li>
		<li hidden="true">
		<li><a href="trigonometric?a=0&b=90">Table of sin and cos functions values</a></li>
		<li hidden="true">
		<li><a href="stories/funny">Funny story</a></li>
		<li hidden="true">
		<li><a href="report">Report on OS usage</a></li>
		<li hidden="true">
		<li><a href="powers?a=1&b=100&n=3">Powers of some integers</a></li>
		<li hidden="true">
		<li><a href="appinfo">Time this application is running</a></li>
		<li hidden="true">
		<li><a href="glasanje">Vote for favorite band!!!</a></li>
	</ul>  
		
</body>
</html>