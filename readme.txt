SENG 2050 - ASSIGNMENT 2
Harrison Rebesco C3237487

http://localhost:8080/c3237487_assignment2/Game

1. The application’s structure, i.e. relationships among objects etc. 
2. What is the purpose of each of your objects? 

For this assignment I have two beans, Game and Case:
-  Case bean sets/gets case number, contents and identifies if case 
   is open or not.
-  Game bean performs all game functionality, by manipulating cases
   (such as shuffling, opening, etc), while also tracking game state.
   I have used an int to track rounds, and booleans to track bank offers
   /if the game is over (user opens all cases, or user accepts bank deal)

I have the one servlet, GameController that forwards the game to various 
jsps, depending on state of the game. (bank offers, error pages, table generation)

3. How did you implement session tracking? 

Apart from the utilization of beans, I have implemented session tracking by
using various game states as mentioned above, depending on the amount of cases
opened, the game dynamically determines which stage it is in. If a stage is 
at its end, BankOffer will become true, forcing the user to accept or decline
the deal. Tracking rounds and cases opened force the player to the correct
stage. At the end of the game, gameEnd becomes true, forcing player to either
go to main menu or save game. Once user decides to leave page, username is 
set to null, which is what ultimately determines if a user has left the game
page.

4. How did you implement game saving? 

I have implemented serialization for both case and game beans, if user wants 
to save game they click a button and the "Save & Exit" parameter is passed
to controller. A filepath is created based on username and the object is 
written to file. The games username is set to null, which alerts controller
that user has ended their game session, and user dispatched to main menu.