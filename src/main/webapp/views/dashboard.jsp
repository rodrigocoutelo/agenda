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
<title>iGenda</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
	<%
User userLogged = (User) session.getAttribute("user");
List<User> usersList = (List<User>) request.getAttribute("usersList");
String errorMsg = (String) session.getAttribute("errorMsg");
ArrayList<String> initials = new ArrayList<String>(Arrays. asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")); 
User.Role role = null;
if(errorMsg != null) { %>
	<script>alert("<%=errorMsg%>")</script>
	
<% 
session.setAttribute("errorMsg", null); }
%>

	<div style="margin-bottom:100px; padding-right:30px; text-align:right">
		Olá,
		<%= userLogged.getName() %> <a href="SignUp?userId=<%=userLogged.getId().toString()%>">Editar</a> - <a href="Logout">Sair</a>
		!
	</div>
	
	<div id="formAddPhone"> 
		<form id="formPhone" action="AddPhone" method="post">
		
		<input id="userId" name="userId" hidden="true" value="<%= userLogged.getId() %>"/>
		<input id="oldDDD" name="oldDDD" hidden="true" value=""/>		
		<input id="oldNumber" name="oldNumber" hidden="true" value=""/>	
		<input id="oldPhoneType" name="oldPhoneType" hidden="true" value=""/>		
		
		<input id="ddd" name="ddd" placeholder="DDD"/>
		<input id="number" name ="number" placeholder="número" type="number"/>
		<select id="phoneType" name="phoneType">
		<option value="<%=Phone.PhoneType.CELULAR.toString() %>"><%=Phone.PhoneType.CELULAR.getLabel() %></option>
		<option value="<%=Phone.PhoneType.RESIDENCIAL.toString() %>"><%=Phone.PhoneType.RESIDENCIAL.getLabel() %></option>
		<option value="<%=Phone.PhoneType.COMERCIAL.toString() %>"><%=Phone.PhoneType.COMERCIAL.getLabel() %></option>
		</select>
		<button id="btn-addPhone" name="btn-addPhone" type="submit">Cadastrar</button>
		<button id="btn-updatePhone" name="btn-updatePhone" type="button" hidden onclick="updatePhone()">Atualizar</button>
		<button id="btn-cancelUpdate" name="btn-cancelUpdate" type="button" hidden onclick="cancelUpdatePhone()">Cancelar</button>
		</form>
		
	
	</div>

<div id="initial" >
	<ul>
		<%for (String initial:initials) { %>
		<li style="display: inline-block; list-style: none;padding:0;"><a href="Dashboard?initial=<%=initial%>"> <%=initial%> </a> </li>
		<%}%>
	</ul>
</div>
	<table>
	<tr>
	<th>Nome</th>
	<th>E-mail</th>
	<th>Telefones</th>
	</tr>
	
	<% for (User user:usersList) { %>
		<tr>
			<td><%=user.getName()%></td>
			<td><%=user.getEmail()%></td>
			<td>
				<ul>
					<%
						for (Phone phone : user.getPhones()) {
							role = user.getRole();
					%>
					<li><%=phone.getDdd()%> - <%=phone.getNumber()%> - <%=phone.getPhonetype().getLabel()%>
						<%
							if (user.getId() == userLogged.getId() || userLogged.getRole() == User.Role.ADMINISTRATOR) {
						%>	
						<a href="#" onclick="removePhone(<%=user.getId()%>,<%=phone.getDdd()%>, <%=phone.getNumber()%>, '<%=phone.getPhonetype().toString()%>')"> Excluir </a> - 
						<a href="#" onclick="loadPhoneToUpdate(<%=user.getId()%>,<%=phone.getDdd()%>, <%=phone.getNumber()%>, '<%=phone.getPhonetype().toString()%>')"> Alterar </a> <% } %>
					</li>
						<%}%>
					
				</ul>
			</td>
		</tr>
		<% } %>
	</table>
	
	<script type="text/javascript">


function removePhone(userId, ddd, number, phoneType) {
	fetch("AddPhone?userId=" + userId + "&ddd="+ ddd + "&number="+number + "&phoneType="+phoneType , {method: 'delete'})
    .then(function (response) {
        window.location.replace("/crud/Dashboard");
    });
}

function loadPhoneToUpdate(userId, ddd, number, phoneType) {
	
	document.getElementById('btn-addPhone').hidden = true
	document.getElementById('btn-updatePhone').hidden = false
	document.getElementById('btn-cancelUpdate').hidden = false

	document.getElementById('userId').value  = userId
	document.getElementById('oldDDD').value = ddd
	document.getElementById('oldNumber').value = number
	document.getElementById('oldPhoneType').value = phoneType
	
	document.getElementById('ddd').value = ddd
	document.getElementById('number').value = number
	document.getElementById('phoneType').value = phoneType
	
}

function updatePhone() {
	var userId = document.getElementById('userId').value 
	var oldDDD = document.getElementById('oldDDD').value 
	var oldNumber = document.getElementById('oldNumber').value 
	var oldPhoneType = document.getElementById('oldPhoneType').value

	var ddd = document.getElementById('ddd').value
	var number = document.getElementById('number').value 
	var phoneType = document.getElementById('phoneType').value 

	fetch("AddPhone?userId=" + userId + "&ddd="+ ddd + "&number="+number + "&phoneType=" + phoneType + "&oldDDD=" + oldDDD + "&oldNumber=" + oldNumber + "&oldPhoneType=" + oldPhoneType, {method: 'put'})
    .then(function (response) {
        window.location.replace("/crud/Dashboard");
    });
}

function cancelUpdatePhone() {
	document.getElementById('btn-addPhone').hidden = false
	document.getElementById('btn-updatePhone').hidden = true
	document.getElementById('btn-cancelUpdate').hidden = true

	document.getElementById('userId').value  = "<%=userLogged.getId()%>"
	document.getElementById('oldDDD').value = ""
	document.getElementById('oldNumber').value = ""
	document.getElementById('oldPhoneType').value = ""
	
	document.getElementById('ddd').value = ""
	document.getElementById('number').value = ""
	document.getElementById('phoneType').value = ""
}

</script>
	</body>