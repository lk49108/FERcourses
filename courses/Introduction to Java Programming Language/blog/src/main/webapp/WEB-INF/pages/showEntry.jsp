<%@ page import="hr.fer.zemris.java.tecaj_13.model.BlogUser" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<title>${entry.title}</title>
<body>

	<jsp:include page="header.jsp" />
	
	<br>
	<br>
	<br>
	
	<h1>${entry.title}
		<c:if test="${sameUser}">
		| <a
				href="<%=request.getContextPath()%>/servleti/author/${user.nick}/edit?id=${entry.id}">Edit
				blog entry</a>
		</c:if>
	</h1>

	<div style="font-size: medium; width: 300px; font-style: italic;">
		<p style="position: relative; word-wrap: break-word">
			<c:out value="${entry.text}"></c:out>
		</p>
	</div>

	<c:choose>
		<c:when test="${comments.isEmpty()}">
			<h4>There's no currently available comments for this post.</h4>
		</c:when>
		<c:otherwise>
			<ol>
				<c:forEach var="c" items="${comments}">
					<li><div style="font-weight: bold">
							[User=${c.usersEMail}] ${c.postedOn}</div>
						<div style="padding-left: 10px;">${c.message}</div></li>
				</c:forEach>
			</ol>
		</c:otherwise>
	</c:choose>
	
	<h3>
		Add new comment
	</h3>
	
	<br>
		
	<% if( request.getSession().getAttribute("current.user") == null) { %>
			
	<form
		action="<%=request.getContextPath()%>/servleti/author/${user.nick}/${entry.id}" method="post">

		<table>
			<tr>
				<td><div>E-mail</div></td>
				<td><input type="text" name="usersEMail"
					value='<c:out value="${form.usersEMail}"/>' size="30" /></td>
					<td><c:if test="${form.hasMistake('usersEMail')}">
					<c:out value="${form.getMistake('usersEMail')}"></c:out>
					</c:if>
					</td>
			</tr>

			<tr>
				<td><div>Comment</div></td>
				<td><textarea name="message" rows="15" cols="70"><c:out
							value="${form.message}" /></textarea></td>
				<td><c:if test="${form.hasMistake('message')}">
					<c:out value="${form.getMistake('message')}"></c:out>
					</c:if>
				</td>
			</tr>

			<tr>
				<td><input type="submit" name="metoda" value="Objavi"></td>
				<td></td>
			</tr>
		</table>
	</form>

	<% } else { %>
	
		<form
		action="<%=request.getContextPath()%>/servleti/author/${user.nick}/${entry.id}" method="post">

		<table>
			<tr>
				<td><input type="hidden" name="usersEMail"
					value='<%=((BlogUser)request.getSession().getAttribute("current.user")).getEmail()%>' size="30" />
				</td>
			</tr>

			<tr>
				<td><div>Comment</div></td>
				<td><textarea name="message" rows="15" cols="70"><c:out
							value="${form.message}" /></textarea></td>
				<td><c:if test="${form.hasMistake('message')}">
					<c:out value="${form.getMistake('message')}"></c:out>
					</c:if>
				</td>
			</tr>

			<tr>
				<td><input type="submit" name="metoda" value="Objavi"></td>
				<td></td>
			</tr>
		</table>
	</form>
		
	
	<% } %>
	
		<br>
		<br>
					
		<a href="<%=request.getContextPath()%>/servleti/author/${user.nick}">Main blog page</a>
			
		<br>
		<br>
		<a href="<%=request.getContextPath()%>/servleti/main">Home page</a>
</body>
</html>