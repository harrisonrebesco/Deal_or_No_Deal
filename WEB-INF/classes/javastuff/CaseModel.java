package javastuff;

/*  SENG2050 - ASSIGNMENT 2
 * Author: Harrison Rebesco 
 * Student Number: 3237487  
 * Date: 9/5/19
 * Description: CaseModel, stores a number and contains an amount (prize) that can be opened within GameModel 
 */
 
import java.util.*;
import java.io.Serializable;
public class CaseModel implements Serializable
{
	private int number; //case number 
	private double contents; //contents within case (prize)
	private boolean opened; //denotes if case has been opened 
	
	public CaseModel()
	{
		//default constructor.
	}
	
	//constructor with number and contents 
	public CaseModel(int n, double c)
	{
		number = n;
		contents = c;
		opened = false;
	}
	
	//sets case number 
	//PRE: n/a
	//POST: number is set 
	public void setNumber(int n)
	{
		number = n;
	}
	
	//sets case contents 
	//PRE: n/a
	//POST: contents are set 
	public void setContents(double c)
	{
		contents = c;
	}
	
	//sets case opened to true, indicating that is has been opened 
	//PRE: n/a
	//POST: opened value changed to true 
	public void open()
	{
		opened = true;
	}
	
	//returns case number
	//PRE: n/a
	//POST: number is returned 
	public int getNumber()
	{
		return number;
	}
	
	//returns case contents 
	//PRE: n/a
	//POST: contents are returned 
	public double getContents()
	{
		return contents;
	}

	//opened is returned, indicating if case has been opened 
	//PRE: n/a
	//POST: opened returned 
	public boolean isOpened()
	{
		return opened;
	}
}