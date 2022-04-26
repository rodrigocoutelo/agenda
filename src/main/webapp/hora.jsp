<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import = "java.io.*,java.util.*, javax.servlet.*, java.util.concurrent.TimeUnit" %>

<%
String titulo_da_pagina = "Estudando Java";
String texto_inicial = "Que dia e hora são?";
String alinhamento = "center";
String tag = "h2";
String nome_usuario = ""; 
nome_usuario = request.getParameter("name");
%>




<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=titulo_da_pagina%></title>
</head>


<body>
		
         <h1>Olá, <%=nome_usuario%> !</h1>
         
         <<%=tag%> style="text-align:<%=alinhamento%>"><%=texto_inicial%></<%=tag%>>

      
      <%
        for(int x = 0; x < 5; x++) {
        		Date date = new Date();
            out.print( "<h2 align = \"center\">" +date.toString()+"</h2>");
         
            
        }
      	
      %>
</body>
</html>

