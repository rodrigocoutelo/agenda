<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!DOCTYPE html>
<html>
<head>
<meta meta http-equiv=”Content-Type” content=”text/html; charset=utf-8″>
<title>CRUD</title>
</head>
<body>
<%
String errorMsg;
errorMsg = (String) request.getAttribute("errorMsg");
if (errorMsg == null) {
	errorMsg = "";
}
%>	
	<main>
	<header>
		<p> Agenda</p>
	</header>
	
	<div class="container">
		<div class="register">
			<form action="Login" method="post">
					<p> Login </p>
					<p><input id="email" type="text" name="email" required placeholder="E-mail"></p>
          <p><input id="passW" type="password" name="passW" required placeholder="Senha"></p>
          <p><button id="btn-acessar" type="submit">Login</button></p>
          <p class="error"><%= errorMsg %> &nbsp;</p>
          
         
			</form>
		</div>
		<div class="login">
		<a href="SignUp">Cadastre-se</a>
		<a href="ListUsers">Lista Usuários</a>
		</div>
	
	
	</div>
	
	
	</main>

</body>
</html>