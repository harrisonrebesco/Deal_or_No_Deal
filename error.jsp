<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP page that shows game logic and presents error to user 
(only displayed if they try to access a case that is out of bounds, or a case that has already been opened). 
-->

<!DOCTYPE html>
<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>A2 - C3237487</title>
		<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
		<!--Header include-->
		<jsp:include page="WEB-INF/gameHeader.jsp"/><br>
		<!--Display error-->
		<span class="error">Error: Attempt to access invalid case. (Case must be within bounds, case cannot already be open!)</wspan><br>
		<!--Stage include (will display cases left, bank offer, etc)-->
		<jsp:include page="WEB-INF/gameStage.jsp"/>
	<%	if(!game.isGameOver()) 
		{
			if (!game.isBankOffer())
			{	%>
				<!--If game is not over, and it is not a bank offer, display the case table (clickable text, contents hidden)-->
				<jsp:include page="WEB-INF/casesGeneric.jsp"/>
		<%	}
			else 
			{ 	%>
				<!--If game is not over, and it is a bank offer, display the case table (non-clickable text, contents hidden)-->
				<jsp:include page="WEB-INF/casesBank.jsp"/>
		<%	}	
		} 
		else 
		{ 	%> 
			<!--If game is over, display the case table (non-clickable text, contents shown)-->
			<jsp:include page="WEB-INF/casesFinal.jsp"/>
	<%	} 	%>
	</body>
</html>