<%@ page import="java.util.ArrayList" language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="true"%>

<html>

<body bgcolor="${sessionScope.pickedBgCol}">
	<p>Table of sinus and cosinus values for all requested values.</p>
	
	<table border="10">
	
	<thead>
		<tr><td>Angle[&alpha;(&deg;)]</td><td>sin(&alpha;)</td><td>cos(&alpha;)</td></tr>
	</thead>	
		
		<% for(int i = 0; i < (Integer)request.getAttribute("numOfElems"); i++) {%>
			<tr>
				<td><% out.print(((ArrayList<Integer>)request.getAttribute("angles")).get(i)); %></td>
				<td><% out.print(((ArrayList<Double>)request.getAttribute("sin")).get(i)); %></td>
				<td><% out.print(((ArrayList<Double>)request.getAttribute("cos")).get(i)); %></td>
			</tr>
		<% } %>		
	</table>
	
	<br>
	
	<a href="index">Home page</a>
	
</body>
</html>