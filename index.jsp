<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP index that will allow user to either save or load a game - this acts as the main menu. 
-->

<!DOCTYPE html>
<html lang="en">
	<head>
		<title>A2 - C3237487</title>
		<link rel="stylesheet" href="style.css"/>
	</head>
	<body>
		<h1>DEAL OR NO DEAL!</h1>
		<h2>Start Game:</h2>
		Please enter your username to load or start a new game:<br>
		<form action="Game" method="post">
			<input type="text" required placeholder="Username" name="username"/> 
			<input type="submit" name="Start Option" value="new"/> <!--new game-->
			<input type="submit" name="Start Option" value="load"/> <!--load game-->
		</form>
	</body>
</html>
