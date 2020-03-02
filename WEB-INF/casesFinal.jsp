<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP page that displays cases in final stage of game (if user has opened all other cases, or has accepted bank offer)
-->

<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<div class="container"> 
	<%
	for (int i = 0; i < 12; i++)
	{
		if (!game.getCase(i).isOpened())
		{	%>
			<!--will display closed case, showing contents-->
			<div>
				<%=game.getCase(i).getContents()%>
			</div>
	<%	}
		else 
		{	%>
			<!--will display open case, showing contents-->
			<div class="opened">
				<%= game.getCase(i).getContents()%>
			</div>
	<%	} 
	} 	%>
</div>
