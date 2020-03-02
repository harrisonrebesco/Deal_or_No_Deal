package javastuff;

/*  SENG2050 - ASSIGNMENT 2
 * Author: Harrison Rebesco 
 * Student Number: 3237487  
 * Date: 9/5/19
 * Description: GameModel class used as a bean to perform the functionality of "Deal or No Deal" game  
 */
 
import java.util.*;
import java.io.Serializable; //to write as file 
public class GameModel implements Serializable
{
	//these attributes are used to control game state
	private int round = 1; //each round has a specific amount of cases to open 
	private int openCases = 0; //tracking cases helps to determine which round it should be 
	private boolean bankOffer, gameEnd; //offer determines if a bank offer is expected, end determines if user has opened all cases, or has taken the "deal"
	
	//these attributes are used for game funtionality 
	private double prize; //the prize at the end 
	private CaseModel [] cases; //array of cases 
	private String user; //username 
	
	//method to set the name of user 
	//PRE: name is string 
	//POST: name is changed to represent user name 
	public void setName(String n)
	{
		user = n; 
	}

	//method to retrive the users name 
	//PRE: n/a
	//POST: name is returned
	public String getName()
	{
		return user;
	}
	
	//method to change offer (indicating if bank offer is commencing)
	//PRE: n/a
	//POST: bank offer status is changed
	public void setOfferStatus(boolean b)
	{
		bankOffer = b;
	}
	
	//returns offer status 
	//PRE: n/a
	//POST: bank offer status is returned 
	public boolean isBankOffer()
	{
		return bankOffer;
	}
	
	//method to change game status (indicating if game is over or not)
	//PRE: n/a
	//POST: game status is changed
	public void setGameEnd(boolean g)
	{
		gameEnd = g;
	}
	
	//returns game status indicating if game is over or not.
	//PRE: n/a 
	//POST: game status is returned 
	public boolean isGameOver()
	{
		return gameEnd;
	}
	
	//opens a case with corresponding array position 
	//PRE: the int passed must be within array range 
	//POST: the case position which corresponds with i is opened, and the total cases opened is incremented 
	public void openCase(int i)
	{
		cases[i].open();
		openCases++;
	}
	
	//returns the total number of cases opened to keep track of game progression
	//PRE: n/a 
	//POST: total openCases returned 
	public int totalOpen()
	{
		return openCases;
	}

	//increments round to move game state forward into next phase 
	//PRE: cannot be more than 5 rounds 
	//POST: round is incremented to a maximum of 5
	public void nextRound()
	{
		if (round <= 5)
			round++;
	}

	//assigns a prize for user 
	//PRE: must be a double 
	//POST: prize assigned 
	public void setPrize(double p)
	{
		prize = p;
	}
	
	//returns user prize 
	//PRE: n/a
	//POST: returns prize 
	public double getPrize()
	{
		return prize;
	}
	
	//returns case with corresponding array position
	//PRE: int i must be within array size 
	//POST: the case with corresponding position is returned 
	public CaseModel getCase(int i)
	{
		return cases[i];
	}
	
	//returns the current round
	//PRE: n/a
	//POST: round is returned 
	public int getRound()
	{
		return round;
	}
	
	//initiates cases, assigns an amount and shuffles the contents of each case 
	//PRE: n/a 
	//POST: cases are all assigned a number (1-12) and a shuffled value (0.5-50000) 
	public void populateCases()
	{
		cases = new CaseModel [12];
		//this will be the case contents 
		double [] amount = {0.5, 1, 10, 100, 200, 500, 1000, 2000, 5000, 10000, 20000, 50000};

		//"shuffle" cases by swapping index numbers
		shuffle(amount);
		
		//assign number and amount to cases
		for (int i = 0; i < 12; i++)
			cases[i] = new CaseModel(i+1, amount[i]);
	}
	
	//helper method used to shuffle the contents of an array 
	//PRE: array double must be passed 
	//POST: the contents of array are randomly shuffled 
	private void shuffle(double[] c)
	{
		int index; 
		double temp;
		Random random = new Random();
		for (int i = c.length - 1; i > 0; i--)
		{
			//generates random int within array range to 'pick' an index to swap with 
			index = random.nextInt(i + 1);
			temp = c[index];
			c[index] = c[i];
			c[i] = temp;
		}
	}
	
	//generates a bank offer used to tempt user between rounds by finding the average amount of remaining cases 
	//PRE: there must be cases left to average 
	//POST: average contents of all cases returned 
	public double makeOffer()
	{
		double amount = 0;
		for(int i = 0; i < 12; i ++) //traverse array 
		{
			if(!cases[i].isOpened()) //if case isnt opened 
				amount += cases[i].getContents(); //add up total 
		}
		amount = amount/(12-openCases); //divide by remaining cases 
		amount = (double)Math.round(amount * 100)/100; //round amount to 2 d.p
		return amount;
	}
}