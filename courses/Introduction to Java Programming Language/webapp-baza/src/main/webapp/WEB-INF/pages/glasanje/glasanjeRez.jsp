<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<html>
<head>
	<style type="text/css">
		table.rez td {text-align: center;}
	</style>
</head>
<body>
	<h2>Rezultati glasanja</h2>
	
	<p>Ovo su rezultati glasanja.</p>
	
	<table border="1" cellspacing="5" class="rez">
	
	<thead><tr><th>Bend</th><th>Broj glasova</th></tr></thead>
	
	<tbody>
		<c:forEach var="resultElem" items="${resultList}" >
				<tr><td>${resultElem.optionTitle}</td><td>${resultElem.votesCount}</td><td><a href="${resultElem.optionLink}">Song</a></td></tr>
		</c:forEach>	
	</tbody>
	
	</table>
		
	<br>
	
	<h2>Grafički prikaz rezultata:</h2>
	<br>
		<img alt="Pie-chart" src="glasanje-grafika?pollID=${resultList[0].pollID}" width="400" height="400" />
		
	<br>
	
	<h2>Rezultati u XLS formatu:</h2>
		<p>Rezultati u XLS formatu dostupni su <a href="glasanje-xls?pollID=${resultList[0].pollID}">ovdje</a>.</p>

	<br>
	
	<a href="index.html">Home page</a>
		
</body>
</html>