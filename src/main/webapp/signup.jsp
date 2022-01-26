<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="models.User"%>
<%@page import="models.Phone"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<!DOCTYPE html>
<html>
<head>
<meta meta http-equiv=”Content-Type” content=”text/html; charset=utf-8″>
<title>Insert title here</title>
</head>
<body>
	<%
User userLogged = (User) session.getAttribute("user");
String errorMsg = (String) request.getAttribute("errorMsg");
Integer userId = null;
String userFirstName = "";
String userLastName = "";
String userEmail = "";
User user = (User) request.getAttribute("user");

if(user != null) {
userId = user.getId();
userFirstName = user.getFirstName();
userLastName = user.getLastName();
userEmail = user.getEmail();
} else if (userLogged != null) {
	userId = userLogged.getId();
	userFirstName = userLogged.getFirstName();
	userLastName = userLogged.getLastName();
	userEmail = userLogged.getEmail();
}


%>

<main>
	<header>
		<p> Agenda </p>
	</header>
	
	<div class="container">
		<div class="register">
			<form action="SignUp" method="post">
					<%if (userId == null) { %>
					<p> Faça seu cadastro </p>
					<%} else { %>
					<p> Atualize seu cadastro </p>
					<% } %>
					<p><input id="userId" type="hidden" name="userId" value="<%=userId%>"></p>
					<p><input id="firstname" type="text" name="firstname"  placeholder="Nome" value="<%=userFirstName%>"></p>
					<p><input id="lastname" type="text" name="lastname"  placeholder="Sobrenome" value="<%=userLastName%>"></p>
					<p><input id="email" type="text" name="email"  placeholder="E-mail" value="<%=userEmail%>"></p>
          <p><input id="passW" type="password" name="passW"  <%if (userId == null) { %>   <%}%> placeholder="Senha"></p>
					<p><input id="passWCheck" type="password" <%if (userId == null) { %>   <%}%> name="passWCheck"  placeholder="Confirme a senha"></p>
					<p><button id="btn-acessar" type="submit">Cadastrar</button>  <button  id="btn-acessar" type="button" onclick="location.href='Dashboard';">Voltar</button></p>
				
			</form>
			
			<%if(errorMsg != null && !errorMsg.equals("")) {%>
				<div style='font-size:11px; color:red'><%=errorMsg%> </div>	
			<% errorMsg = "";} %>
			
		</div>
		<div class="login">
		</div>
	
	
	</div>
	</main>
</body>
</html>