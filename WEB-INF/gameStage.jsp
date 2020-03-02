<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="javastuff.GameModel" %>

<!--
SENG2050 - ASSIGNMENT 2 
Author: Harrison Rebesco 
Student Number: 3237487  
Date: 9/5/19
Description: JSP page that displays current stage of game (rounds 1-5, bank offers, end state)
-->

<jsp:useBean scope="session" id="game" class="javastuff.GameModel" />
<div class="bold">
<%	if(!game.isGameOver()) 
	{ 	%>
		<br>
		<!--if game is not over-->
			<%	if (game.getRound() == 1)
				{ 
					if (!game.isBankOffer())
					{ 
						int count = 4 - game.totalOpen(); 
						if (count == 1)
						{	%>
							<!--if 1st round and only one case to open-->
							1 case to open!
					<%	}
						else 
						{	%>
							<!--if 1st round and more than 1 case to open-->
							<%=count%> cases to open!
					<%	}
					}			
					else 
					{	%>
						<!--if end of round (bank offer)-->
						Bank offer: $<%=game.makeOffer()%>
					<%	if (game.isBankOffer())
						{ 	%>		
						<!--generate "deal" and "no deal" options-->
						<form action="Game" method="post">
							<input type="submit" class="deal" name="dealDecision" value="DEAL"/>
							<input type="submit" class="noDeal" name="dealDecision" value="NO DEAL"/>
						</form>
				<%		}
					}
				}
				else if (game.getRound() == 2)
				{ 	
					if (!game.isBankOffer())
					{ 
						int count = 7 - game.totalOpen(); 
						if (count == 1)
						{ 	%>
							<!--if 2nd round and only one case to open-->
							1 case to open!
					<%	}
						else 
						{	%>
							<!--if 2nd round and more than one case to open-->
							<%=count%> cases to open!
					<%	}
					}			
					else 
					{	%>
						<!--if end of round (bank offer)-->
						Bank offer: $<%=game.makeOffer()%>
					<%	if (game.isBankOffer())
						{ 	%>		
						<!--generate "deal" and "no deal" options-->
						<form action="Game" method="post">
							<input type="submit" class="deal" name="dealDecision" value="DEAL"/>
							<input type="submit" class="noDeal" name="dealDecision" value="NO DEAL"/>
						</form>
				<%		}
					}
				}
				else if (game.getRound() == 3)
				{ 	
					if (!game.isBankOffer())
					{ 
						int count = 9 - game.totalOpen(); 
						if (count == 1)
						{ 	%>
							<!--if 3rd round and only one case to open-->
							1 case to open!
					<%	}
						else 
						{	%>
							<!--if 3rd round and more than one case to open-->
							<%=count%> cases to open!
					<%	}
					}			
					else 
					{	%>
						<!--if end of round (bank offer)-->
						Bank offer: $<%=game.makeOffer()%>
					<%	if (game.isBankOffer())
						{ 	%>		
						<!--generate "deal" and "no deal" options-->
						<form action="Game" method="post">
							<input type="submit" class="deal" name="dealDecision" value="DEAL"/>
							<input type="submit" class="noDeal" name="dealDecision" value="NO DEAL"/>
						</form>
				<%		}
					}
				}
				else if (game.getRound() == 4)
				{ 				
					if (!game.isBankOffer())
					{ 
						int count = 10 - game.totalOpen(); 	%>
						<!--if 4th round (only one case to open)-->
						1 case to open!
				<%	}			
					else 
					{	%>
						<!--if 4th round and more than one case to open-->
						Bank offer: $<%=game.makeOffer()%>
					<%	if (game.isBankOffer())
						{ 	%>		
						<!--generate "deal" and "no deal" options-->
						<form action="Game" method="post">
							<input type="submit" class="deal" name="dealDecision" value="DEAL"/>
							<input type="submit" class="noDeal" name="dealDecision" value="NO DEAL"/>
						</form>
				<%		}
					}
				} 
				else 
				{ 	
					{ 	%>
						<!--if 5th round (only one case to open)-->
						Open the final case. Good luck!
				<%	}
				}	
	} 	 
	else 
	{ 	%>
		<!--if game is over-->
			<!--display winnings-->
			<br>Congratulations!<br>
			You have won $<%=game.getPrize()%> <%
	} %>
</div> 
	