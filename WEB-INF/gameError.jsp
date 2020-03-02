<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Map.Entry" %>
<%@page import="javastuff.GameModel" %>

<!DOCTYPE html>
<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<html lang="en">
	<head>
		<meta charset="UTF-8">
		<title>A2 - C3237487</title>
		<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
		<jsp:include page="WEB-INF/gameHeader.jsp"/>
		Error. Invalid case. (Must be within bounds, must not already be open!)
		<jsp:include page="WEB-INF/gameStage.jsp"/>
	<%	if(!game.gameOver())
		{
			if (!game.bankOffer())
			{	%>
				<jsp:include page="WEB-INF/casesGeneric.jsp"/>
		<%	}
			else 
			{ 	%>
				<jsp:include page="WEB-INF/casesBank.jsp"/>
		<%	}	
		} 
		else 
		{ 	%> 
			<jsp:include page="WEB-INF/casesFinal.jsp"/>
	<%	} 	%>
	</body>
</html>