<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@page import="br.com.coutelo.model.User" %>
<%@page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<p> Usuários </p>
<%
List<User> users = (List<User>) request.getAttribute("users");
for (User user: users) { %>
<p><%= user.getName()%> - 	<%= user.getEmail()%> - <%= user.getPassword() %> </p>
<% } %>

</body>
</html>