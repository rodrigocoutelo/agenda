<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="br.com.coutelo.model.User"%>
<%@page import="br.com.coutelo.model.Phone"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
User userLogged = (User) session.getAttribute("user");
String errorMsg = (String) session.getAttribute("errorMsg");
Long userId = null;
String userName = "";
String userEmail = "";
User user = (User) request.getAttribute("user");

if(user != null) {
userId = user.getId();
userName = user.getName();
userEmail = user.getEmail();
}

if(errorMsg != null) { %>
	<script>alert("<%=errorMsg%>")</script>
	
<% 
session.setAttribute("errorMsg", null); }
%>

<main>
	<header>
		<p> Agenda </p>
	</header>
	
	<div class="container">
		<div class="register">
			<form action="SignUp" method="post">
					<p> Faça seu cadastro </p>
					<p><input id="userId" type="hidden" name="userId" required placeholder="Nome" value="<%=userId%>"></p>
					<p><input id="name" type="text" name="name" required placeholder="Nome" value="<%=userName%>"></p>
					<p><input id="email" type="text" name="email" required placeholder="E-mail" value="<%=userEmail%>"></p>
          <p><input id="passW" type="password" name="passW" required placeholder="Senha"></p>
					<p><input id="passWCheck" type="password" name="passWCheck" required placeholder="Confirme a senha"></p>
					<p><button id="btn-acessar" type="submit">Cadastrar</button></p>
				
			</form>
		</div>
		<div class="login">
		</div>
	
	
	</div>
	</main>
</body>
</html>