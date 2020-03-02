<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP header page - displays game title and user name, also displays button that allows user to save and exit game 
-->

<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<header>
	<h1>DEAL OR NO DEAL!</h1>
	<nav>
		<!--save and menu button-->
		<form action="Game" method="post">
			<input type="submit" name="HeaderOption" value="Main Menu"/>
			<input type="submit" name="HeaderOption" value="Save & Exit"/>
		</form>
	</nav>
	<hr>
	Welcome <%=game.getName()%>!<br>
</header>
