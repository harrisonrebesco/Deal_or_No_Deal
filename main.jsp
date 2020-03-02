<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<title>DEAL OR NO DEAL!</title>
	<link rel="stylesheet" href="style.css"/>
</head>
<body>
	<h1>Deal Or No Deal!</h1>
	<h2>New Game:</h2>
	<form action="Game" method="post">
		<input type="text" placeholder="Username" name="username"/>
		<input type="submit" value="Start"/>
	</form>
	<h2>Load Game:</h2>
	<form action="Game" method="post">
		<input type="text" placeholder="Username" name="username"/>
		<input type="submit" value="Start"/>
	</form>
</body>
</html>
