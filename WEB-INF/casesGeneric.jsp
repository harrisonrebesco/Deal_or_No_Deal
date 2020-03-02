<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP page that displays clickable cases in regular stages of game (during rounds, when a bank offer is not commencing)
-->

<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<div class="container"> 
	<%
	for (int i = 0; i < 12; i++)
	{
		if(game.getCase(i).isOpened())
		{	%>
			<!--display opened case, showing case contents-->
			<div class="opened">
				<%= game.getCase(i).getContents()%>
			</div>
	<%	} 
		else 
		{	%> 
			<!--display closed case, showing case number, clickable text-->
			<div>
				<a class="plain" href="Game?num=<%=i%>"><%= game.getCase(i).getNumber()%></a>
			</div>
	<%	}
	} 	%>
</div>