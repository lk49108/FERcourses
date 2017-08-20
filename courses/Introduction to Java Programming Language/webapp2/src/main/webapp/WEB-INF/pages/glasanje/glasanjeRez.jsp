<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<html>
<head>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
</head>
<body bgcolor="${sessionScope.pickedBgCol}">
	<h2>Rezultati glasanja</h2>
	
	<p>Ovo su rezultati glasanja.</p>
	
	<table border="1" cellspacing="5" class="rez">
	
	<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	
	<tbody>
		<c:forEach var="band" items="${bands}" >
				<tr><td>${band.name}</td><td>${band.numOfVotes}</td><td><a href="${band.songRepr}">Song</a></td></tr>
		</c:forEach>	
	</tbody>
	
	</table>
		
	<br>
	
	<h2>Grafički prikaz rezultata:</h2>
	<br>
		<img alt="Pie-chart" src="glasanje-grafika" width="400" height="400" />
		
	<br>
	
	<h2>Rezultati u XLS formatu:</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls">ovdje</a>.</p>

	<br>
	
	<a href="index">Home page</a>
		
</body>
</html>