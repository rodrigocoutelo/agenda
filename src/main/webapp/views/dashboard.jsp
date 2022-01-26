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
<title>iGenda</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<style>
/* remover setas do campo de telefone
/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* Firefox */
input[type=number] {
	-moz-appearance: textfield;
}
</style>
</head>
<body>
	<%
	final String DDD_BRASIL = "11,12,13,14,15,16,17,18,19,21,22,24,27,28,31,32,33,34,35,37,38,41,42,43,44,45,46,47,48,49,51,53,54,"
			+ "55,61,62,63,64,65,66,67,68,69,71,73,74,75,77,79,81,82,83,84,85,86,87,88,89,91,92,93,94,95,96,97,98,99";

	String[] ddd = DDD_BRASIL.split(",");
	User userLogged = (User) session.getAttribute("user");
	List<User> usersList = new ArrayList<User>();
	String errorMsg = (String) request.getAttribute("errorMsg");
	ArrayList<String> initials = new ArrayList<String>(Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"));

	User.Role role = null;

	if (request.getAttribute("usersList") != null) {
		usersList = (ArrayList<User>) request.getAttribute("usersList");
	}

	if (errorMsg != null && !errorMsg.equals("")) {
	%>
	<script>alert("<%=errorMsg%>")</script>
	<%
	errorMsg = "";
	}
	%>

	<div
		style="margin-bottom: 100px; padding-right: 30px; text-align: right">
		Olá,
		<%=userLogged.getName()%>
		<a href="#" onClick="document.getElementById('editUser').submit()">Editar</a>
		- <a href="Logout">Sair</a> !

		<form id="editUser" action="Update" method="post">
			<input type="hidden" name="userId" id="userId"
				value="<%=userLogged.getId()%>" />
			<div id="myPhones">
				<h3>Meus Telefones</h3>
				<ul>
					<%
					for (Phone phone : userLogged.getPhones()) {
						role = userLogged.getRole();
					%>
					<li style="list-style: none;"><%=phone.getDdd()%> - <%=phone.getNumber()%>
						- <%=phone.getType().getLabel()%> <a href="#" onclick="removePhone(<%=phone.getId()%>)">
							Excluir </a> - <a href="#"
						onclick="loadPhoneToUpdate(<%=userLogged.getId()%>,<%=phone.getId()%>, <%=phone.getDdd()%>, <%=phone.getNumber()%>, '<%=phone.getType().getValue()%>')">
							Alterar </a> <%

 %></li>
					<%}%>

				</ul>


			</div>
		</form>
		
		<div id="formAddPhone">
		<form id="formPhone" action="AddPhone" method="post">
			<h2>Cadastre seus telefone</h2>
			<input id="userId" name="userId" hidden="true"
				value="<%=userLogged.getId()%>" /> <input id="phoneId"
				name="phoneId" hidden="true" value="" /> <select id="ddd"
				name="ddd" style="width: 50px">
				<option value="">DDD</option>
				<%
				for (String codDDD : ddd) {
				%>
				<option value="<%=codDDD%>"><%=codDDD%></option>
				<%
				}
				%>
			</select> <input type="text" pattern="\d*" maxlength="9" id="number"
				name="number" placeholder="número" size=12 /> <select
				id="phoneType" name="phoneType" style="padding: 0 5px">
				<%
				for (Phone.PhoneType pt : Phone.PhoneType.values()) {
				%>
				<option value="<%=pt.getValue()%>"><%=pt.getLabel()%></option>
				<%
				}
				%>
			</select>

			<button id="btn-addPhone" name="btn-addPhone" type="submit">Cadastrar</button>
			<button id="btn-updatePhone" name="btn-updatePhone" type="button"
				hidden onclick="updatePhone()">Atualizar</button>
			<button id="btn-cancelUpdate" name="btn-cancelUpdate" type="button"
				hidden onclick="cancelUpdatePhone()">Cancelar</button>
		</form>


	</div>
	
	</div>

	

	<div id="initial">
		<ul>
			<%
			for (String initial : initials) {
			%>
			<li style="display: inline-block; list-style: none; padding: 0;"><a
				href="Dashboard?initial=<%=initial%>"> <%=initial%>
			</a></li>
			<%}%>
		</ul>
	</div>
	<table>
		<tr>
			<th>Nome</th>
			<th>E-mail</th>
			<th>Telefones</th>
		</tr>

		<%
		for (User user : usersList) {

			if (user.getId() == userLogged.getId()) {
				continue;
			}
		%>
		<tr>
			<td><%=user.getName()%></td>
			<td><%=user.getEmail()%></td>
			<td>
				<ul>
					<%
					for (Phone phone : user.getPhones()) {
						role = user.getRole();
					%>
					<li><%=phone.getDdd()%> - <%=phone.getNumber()%> - <%=phone.getType().getLabel()%>
						<%
						if (user.getId() == userLogged.getId() || userLogged.getRole() == User.Role.ADMINISTRATOR) {
						%> <a href="#" onclick="removePhone(<%=phone.getId()%>)">
							Excluir </a> - <a href="#"
						onclick="loadPhoneToUpdate(<%=user.getId()%>,<%=phone.getId()%>, <%=phone.getDdd()%>, <%=phone.getNumber()%>, '<%=phone.getType().getValue()%>')">
							Alterar </a> <%
 }
 %></li>
					<%}%>

				</ul>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<script type="text/javascript">


function removePhone(phoneId) {
	fetch("AddPhone?phoneId=" + phoneId , {method: 'delete'})
    .then(function (response) {
        window.location.replace("/desafio/Dashboard");
    });
}

function loadPhoneToUpdate(userId, phoneId, ddd, number, phoneType) {
	
	document.getElementById('btn-addPhone').hidden = true
	document.getElementById('btn-updatePhone').hidden = false
	document.getElementById('btn-cancelUpdate').hidden = false

	document.getElementById('userId').value  = userId
	document.getElementById('phoneId').value = phoneId
	
	document.getElementById('ddd').value = ddd
	document.getElementById('number').value = number
	document.getElementById('phoneType').value = phoneType
	
}

function updatePhone() {
	var userId = document.getElementById('userId').value 
	var phoneId = document.getElementById('phoneId').value 

	var ddd = document.getElementById('ddd').value
	var number = document.getElementById('number').value 
	var phoneType = document.getElementById('phoneType').value 

	fetch("AddPhone?userId=" + userId + "&ddd="+ ddd + "&number="+number + "&phoneType=" + phoneType + "&phoneId=" + phoneId, {method: 'put'})
    .then(function (response) {
        window.location.replace("/desafio/Dashboard");
    });
}

function cancelUpdatePhone() {
	document.getElementById('btn-addPhone').hidden = false
	document.getElementById('btn-updatePhone').hidden = true
	document.getElementById('btn-cancelUpdate').hidden = true

	document.getElementById('userId').value  = "<%=userLogged.getId()%>"
	document.getElementById('phoneId').value = ""
	
	document.getElementById('ddd').value = ""
	document.getElementById('number').value = ""
	document.getElementById('phoneType').value = ""
}

</script>
</body>