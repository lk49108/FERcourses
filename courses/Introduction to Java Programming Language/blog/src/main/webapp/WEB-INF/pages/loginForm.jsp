<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<h1>Login form</h1>
	
		<form action="<%=request.getContextPath()%>/servleti/login"  method="post">
			Nick <input type="text" name="nick" value='<c:out value="${formular.nick}"/>' size="30"></div>
			<c:if test="${formular.hasMistake('nick')}">
			<c:out value="${formular.getMistake('nick')}"></c:out>
			</c:if>
			<br>
			Password <input type="password" name="password" value='' size="30"></div>
			<c:if test="${formular.hasMistake('password')}">
			<c:out value="${formular.getMistake('password')}"></c:out>
			</c:if>
			<br>
			<input type="submit" name="method" value="Login">
		</form>



	</body>
</html>