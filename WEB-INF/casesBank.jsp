<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP page that displays non-clickable cases in bank offer stages of game (end of rounds, cases not clickable so user cant open during offer)
-->

<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<div class="container"> 
	<%
	for (int i = 0; i < 12; i++)
	{
		if(game.getCase(i).isOpened() != false)
		{	%>
			<!--display opened case, showing contents-->
			<div class="opened">
				<%= game.getCase(i).getContents()%>
			</div>
	<%	} 
		else 
		{	%> 
			<!--display closed case, showing case number-->
			<div>
				<%= game.getCase(i).getNumber()%>
			</div>
	<%	}
	} 	%>
</div>