<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<h1>Register form</h1>
	
		<form action="<%=request.getContextPath()%>/servleti/register"  method="post">
			First Name <input type="text" name="firstName" value='<c:out value="${formular.firstName}"/>' size="50"></div>
			<c:if test="${formular.hasMistake('firstName')}">
			<c:out value="${formular.getMistake('firstName')}"></c:out>
			</c:if>
			<br>
			Last Name <input type="text" name="lastName" value='<c:out value="${formular.lastName}"/>' size="60"></div>
			<c:if test="${formular.hasMistake('lastName')}">
			<c:out value="${formular.getMistake('lastName')}"></c:out>
			</c:if>
			<br>
			Nick <input type="text" name="nick" value='<c:out value="${formular.nick}"/>' size="30"></div>
			<c:if test="${formular.hasMistake('nick')}">
			<c:out value="${formular.getMistake('nick')}"></c:out>
			</c:if>
			<br>
			Password <input type="password" name="password" value='<c:out value="${formular.password}"/>' size="30"></div>
			<c:if test="${formular.hasMistake('password')}">
			<c:out value="${formular.getMistake('password')}"></c:out>
			</c:if>
			<br>
			Email <input type="text" name="email" value='<c:out value="${formular.email}"/>' size="40"></div>
			<c:if test="${formular.hasMistake('email')}">
			<c:out value="${formular.getMistake('email')}"></c:out>
			</c:if>
			<input type="submit" name="method" value="Register">
		</form>
		
		<br>
		<br>
		<a href="<%=request.getContextPath()%>/servleti/main">Home page</a>
	</body>
</html>