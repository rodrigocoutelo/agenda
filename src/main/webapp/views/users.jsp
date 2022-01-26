<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	
<%@page import="models.User" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<meta meta http-equiv=”Content-Type” content=”text/html; charset=utf-8″>
<title>Usuários</title>
</head>
<body>
<p> Usuários </p>
<%
List<User> users = (List<User>) request.getAttribute("users");
if (users.isEmpty()) { %>
	<p>Nenhum usuário cadastrado</p>
<%}
for (User user: users) { %>
<p><%= user.getName()%> - 	<%= user.getEmail()%> - <%= user.getPass() %> </p>
<% } %>

</body>
</html>