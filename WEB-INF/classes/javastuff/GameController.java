package javastuff;

/* SENG2050 - ASSIGNMENT 2
 * Author: Harrison Rebesco 
 * Student Number: 3237487  
 * Date: 9/5/19
 * Description: A control servlet that modifies game data and forwards user to different jsp files to ensure they stay within Deal or No Deal boundaries.
 */

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import javastuff.GameModel;

@WebServlet(urlPatterns = {"/Game"})
public class GameController extends HttpServlet
{
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		GameModel game = (GameModel)request.getSession().getAttribute("game");
		//dispatchers 
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game.jsp"); //sends user to game.jsp (displays game data)
		RequestDispatcher dispatchError = getServletContext().getRequestDispatcher("/error.jsp"); //sends user to error.jsp (displays error message)
		RequestDispatcher dispatchIndex = getServletContext().getRequestDispatcher("/index.jsp"); //sends user to index.jsp (displays main menu)
		RequestDispatcher dispatchLoad 
		= getServletContext().getRequestDispatcher("/loadError.jsp"); //sends user to loadError.jsp (displays load error message)
		RequestDispatcher dispatchSave = getServletContext().getRequestDispatcher("/saveSuccess.jsp"); //sends user to saveSuccess.jsp (confirms successful save)
		
		//check if game hasnt been initiated 
		if (game == null)
			game = new GameModel(); //initiate game 
		
		//check to see if user wants to exit game (save or main menu)
		if (request.getParameter("HeaderOption") != null)
		{
			if (request.getParameter("HeaderOption").equals("Save & Exit"))
			{
				String filepath="../webapps/c3237487_assignment2/"+game.getName()+".txt"; //create a file with username as file name 
				writeObjectToFile(filepath, game); //calls method to write to file 
				game.setName(null); //set name to null - this is how i check to see if there is an ongoing game (see below)
				session.setAttribute("game", game); //sets data
				dispatchSave.forward(request, response); //forward user to saveSuccess.jsp to confirm successful save
			}
			else
			{
				game.setName(null); //set name to null - this is how i check to see if there is an ongoing game (see below)
				session.setAttribute("game", game); //sets data
				dispatchIndex.forward(request, response); //forward user to index.jsp 
			}
		}

		//if game is not ongoing (i use name as a check to see if game is ongoing, so if name is null there is not currently a game underway)
		if (game.getName() == null) 
		{
			String option = request.getParameter("Start Option"); //indicates if user wants to start a new game or load a game 
			//null pointer error check 
			if (option != null) 
			{
				//user wants to start a new game
				if (option.equals("new"))  
				{
					game = new GameModel(); //instantiate new game 
					game.populateCases(); //populate cases - this numbers all cases, shuffles and prepares the cases for a new game 
					game.setName(request.getParameter("username")); //sets the name of user (this also indicates that a game will be ongoing)
					session.setAttribute("game", game); //sets data 
					dispatcher.forward(request, response); //forwards to game.jsp 
				}
				//user wants to load a game
				else  
				{	
					String filepath="../webapps/c3237487_assignment2/"+request.getParameter("username")+".txt"; //file named after user 
					File tempFile = new File(filepath); //create file 
					boolean exists = tempFile.exists(); //check to see if file actually exists 
					//if file does exist, read from file
					if (exists)  
					{
						game = (GameModel)readObjectFromFile(filepath); //read file 
						tempFile.delete(); // delete file after being read 
						session.setAttribute("game", game); //set session data 
						dispatcher.forward(request, response); //forward to game.jsp 
					}
					//if file doesnt exist, create new game (same logic as new game)
					else 
					{
						game = new GameModel(); 
						game.populateCases();
						game.setName(request.getParameter("username"));
						session.setAttribute("game", game);
						dispatchLoad.forward(request, response); //forward user to loadError.jsp (let them know file didnt exist)
					}
				}
			}
			//if option is null (i.e user is just refreshing main page) reload page 
			else 
				dispatchIndex.forward(request, response); //forward user to index 
		}
		//if game name is not null, will mean game is ongoing 
		else 
		{
			//check to see if game is still in progress 
			if (!game.isGameOver())
			{
				//if round 1 - means 4 cases will be opened 
				if (game.getRound() == 1)
				{
					//if it is not bank offer stage (end of roung)
					if (!game.isBankOffer()) 
					{
						//tracking open cases, making sure user doesnt open more than 3
						if (game.totalOpen() < 3)
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
									game.openCase(num); //opens case revealing contents to user 
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
						//lase case of round 
						else 
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
								{
									game.openCase(num); //opens case revealing contents to user 
									game.setOfferStatus(true);
								}
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
					}			
					//if it is bank offer stage (end of round)
					else 
					{
						//if parameter is null (user refreshes page) will reload page preventing null pointer 
						if (request.getParameter("dealDecision") == null)
							dispatcher.forward(request, response); //forward game.jsp (reload)
						//if user picks "NO DEAL" game continues 
						else if (request.getParameter("dealDecision").equals("NO DEAL"))
						{
							game.setOfferStatus(false); //end game offer stage
							game.nextRound(); //next round 
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward game.jsp 
						}
						//if user picks "DEAL" game ends 
						else
						{
							game.setGameEnd(true); //end game 
							game.setPrize(game.makeOffer()); //give user price (will be the amount offered)
							dispatcher.forward(request, response); //forward game.jsp
						}
					}
				}
				//if round 2 - means 3 cases will be opened 
				else if (game.getRound() == 2)
				{
					//if it is not bank offer stage (end of roung)
					if (!game.isBankOffer()) 
					{
						//tracking open cases, making sure user doesnt open more than 6
						if (game.totalOpen() < 6)
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
									game.openCase(num); //opens case revealing contents to user 
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
						//lase case of round 
						else 
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
								{
									game.openCase(num); //opens case revealing contents to user 
									game.setOfferStatus(true);
								}
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
					}			
					//if it is bank offer stage (end of round)
					else 
					{
						//if parameter is null (user refreshes page) will reload page preventing null pointer 
						if (request.getParameter("dealDecision") == null)
							dispatcher.forward(request, response); //forward game.jsp (reload)
						//if user picks "NO DEAL" game continues 
						else if (request.getParameter("dealDecision").equals("NO DEAL"))
						{
							game.setOfferStatus(false); //end game offer stage
							game.nextRound(); //next round 
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward game.jsp 
						}
						//if user picks "DEAL" game ends 
						else
						{
							game.setGameEnd(true); //end game 
							game.setPrize(game.makeOffer()); //give user price (will be the amount offered)
							dispatcher.forward(request, response); //forward game.jsp
						}
					}
				}
				//if round 3 - means 2 cases will be opened 
				else if (game.getRound() == 3)
				{
					//if it is not bank offer stage (end of roung)
					if (!game.isBankOffer()) 
					{
						//tracking open cases, making sure user doesnt open more than 6
						if (game.totalOpen() < 8)
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
									game.openCase(num); //opens case revealing contents to user 
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
						//lase case of round 
						else 
						{
							//null pointer error check 
							if (request.getParameter("num") != null)
							{
								String caseNumber = request.getParameter("num"); //retrieves case picked 
								int num = Integer.parseInt(caseNumber); //converts string to int 
								//if within range, and not already opened 
								if (rangeCheck(num) == 1 && !game.getCase(num).isOpened()) 
								{
									game.openCase(num); //opens case revealing contents to user 
									game.setOfferStatus(true);
								}
								//if out of bounds or already opened 
								else 
									dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
								session.setAttribute("game", game); //sets data 
								dispatcher.forward(request, response); //send user to game.jsp
							}
							//if parameter is null (i.e user is just refreshing main page) reload page 
							else 
								dispatcher.forward(request, response); //forward user to game.jsp (reload page)
						}
					}			
					//if it is bank offer stage (end of round)
					else 
					{
						//if parameter is null (user refreshes page) will reload page preventing null pointer 
						if (request.getParameter("dealDecision") == null)
							dispatcher.forward(request, response); //forward game.jsp (reload)
						//if user picks "NO DEAL" game continues 
						else if (request.getParameter("dealDecision").equals("NO DEAL"))
						{
							game.setOfferStatus(false); //end game offer stage
							game.nextRound(); //next round 
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward game.jsp 
						}
						//if user picks "DEAL" game ends 
						else
						{
							game.setGameEnd(true); //end game 
							game.setPrize(game.makeOffer()); //give user price (will be the amount offered)
							dispatcher.forward(request, response); //forward game.jsp
						}
					}
				}
				//if round 4 - means user will open 1 case 
				else if (game.getRound() == 4)
				{
					//if not in bank offer stage
					if (!game.isBankOffer()) 
					{
						//if parameter isnt null (null pointer check)
						if (request.getParameter("num") != null)
						{
							String caseNumber = request.getParameter("num"); //retrieve case number 
							int num = Integer.parseInt(caseNumber); //convert from string to int 
							//if within range, and not already opened 
							if (rangeCheck(num) == 1 && !game.getCase(num).isOpened())
							{
								game.openCase(num); //open case 
								game.setOfferStatus(true); //start bank offer stage 
							}
							//if out of bounds or already opened 
							else 
								dispatchError.forward(request, response); //send user to error.jsp (alerts they are trying to access out of bounds cases)
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward to game.jsp 
						}
						//if parameter is null (user refreses page)
						else 
							dispatcher.forward(request, response); //forward to game.jsp (reload)
					}			
					//if it is bank offer stage (end of round)
					else 
					{
						//if parameter is null (user refreshes page) will reload page preventing null pointer 
						if (request.getParameter("dealDecision") == null)
							dispatcher.forward(request, response); //forward game.jsp (reload)
						//if user picks "NO DEAL" game continues 
						else if (request.getParameter("dealDecision").equals("NO DEAL"))
						{
							game.setOfferStatus(false); //end game offer stage
							game.nextRound(); //next round 
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward game.jsp 
						}
						//if user picks "DEAL" game ends 
						else
						{
							game.setGameEnd(true); //end game 
							game.setPrize(game.makeOffer()); //give user price (will be the amount offered)
							dispatcher.forward(request, response); //forward game.jsp
						}
					}
				}			
				//if round 5 - final round - 1 case to open 
				if (game.getRound() == 5)
				{
					//if paramater isnt null (NPE check)
					if (request.getParameter("num") != null)
					{
						String caseNumber = request.getParameter("num"); //retrieve number 
						int num = Integer.parseInt(caseNumber); //convert from string to int 
						//if within range, and not already opened 
						if (rangeCheck(num) == 1 && !game.getCase(num).isOpened())
						{
							game.openCase(num); //open case 
							game.setGameEnd(true); //end the game 
							//loop through all cases to 
							for (int i = 0; i < 12; i++)
							{
								//check if case has been opened to find final case (will be user prize)
								if (!game.getCase(i).isOpened())
									game.setPrize(game.getCase(i).getContents()); //set user prize to last case remaining 
							}
							session.setAttribute("game", game); //set data 
							dispatcher.forward(request, response); //forward to game.jsp 
						}
						//if not within range, or already opened 
						else
							dispatchError.forward(request, response); //forward to error.jsp 
					}
					//if paramater is null (user reloads page)
					else 
						dispatcher.forward(request, response); //forward to game.jsp 
				}
			}
			//if game is over (user refreshes page) 
			else 
				dispatcher.forward(request, response); //forward to game.jsp (reload)
		}
	}
	
	//doGet is just a reimplementation of doPost 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException 
	{
		doPost(request, response);
	}
	
	//method to write a serialized object to file 
	//PRE: must have a valid file path and object 
	//POST: object is written to file 
	public void writeObjectToFile(String filepath, Object g) 
	{
        try 
		{
            FileOutputStream fileOut = new FileOutputStream(filepath); //file output 
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut); //object output
            objectOut.writeObject(g); //writes serialized object (game)
            System.out.println("Game succesfully written to a file"); //indicates successful export to console
			objectOut.close(); //close stream
        } 
		catch (Exception ex) 
		{
            ex.printStackTrace();
        }
    }
	
	//method to read a serialized object from file 
	//PRE: must have a valid file path and object 
	//POST: object is read from file 
    public Object readObjectFromFile(String filepath) 
	{
        try 				
		{
            FileInputStream fileIn = new FileInputStream(filepath); //file input
            ObjectInputStream objectIn = new ObjectInputStream(fileIn); //object input
            Object obj = objectIn.readObject(); //reads object
            System.out.println("Game has been read from the file"); //indicates successful import to console
			objectIn.close(); //close stream
            return obj; //
 
        }
		catch (Exception ex) 
		{
            ex.printStackTrace();
            return null;
        }
    }
	
	//range check method to ensure cases are within bounds 
	//PRE: n/a
	//POST: indicates if int is within range, returns 1 if true, 0 if false 
	public int rangeCheck(int i)
	{
		//if within 0 and 11 (game case array boundaries)
		if (i >= 0 && i < 12)
			return 1; 
		//if outside 0 and 11 (game case array boundaries)
		else 
			return 0; 
	}
}