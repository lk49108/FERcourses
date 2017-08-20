<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<h1>Error message: ${error}</h1>		
		
		<br>
		<br>
		<a href="<%=request.getContextPath()%>/servleti/main">Home page</a>
	</body>
</html>