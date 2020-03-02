<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Map.Entry" %>
<%@page import="beans.models.Game" %>

<!DOCTYPE html>
<jsp:useBean scope="session" id="game" class="beans.models.Game" />
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>Deal Or No Deal!</title>
	</head>
	<body>
		<h1>DEAL OR NO DEAL!</h1>
		
		
		<%
		game.populateCases();
		for (int i = 0; i < 12; i++)
		{
		%>
			<div>
				<%= game.getCaseNumber(i)%>
			</div>
		<%
		}
		%>
	</body>
</html>