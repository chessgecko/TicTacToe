package mpackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JPanel;



public class Display extends JPanel{

	int menustate;
	
	boolean cmove; //computer to move (within the round)
	
	int upX; // co-ordinates of the upper left hand corner
	int upY; // of the tictactoe board
	int boxsize;  //width of box
	int linewidth; // width of the grid lines
	
	Font title; //font of the title
	Font text;  // font of other stuff
	Font XO; //font of the X's and O's
	String playerString;
	String compString;
	
	int[] squares; //boxes in the tictactoe board
	boolean isfinished; //is the current game over
	
	JApplet myApplet; //needed to draw the background
	private Image background = null;
	
	public int difficulty; // 0 = easy, 1 = hard
	public int day; // number of games played +1
	public boolean playerStarts; //starts the round
	public boolean compWon;
	public boolean playerWon;
	
	private Timer moveTimer = new Timer();
	private boolean moveTimerinit = false;
	
	
	private int firetimes;
	double xspeed;
	double yspeed;
	Random Random = new Random();
					  	
	public Display(JApplet app){
		myApplet = app;
		init();
	}
	//reloadTimer2.schedule(new reloadGun2(), 750);
	/*
	 *  class reloadGun2 extends TimerTask{
		 public void run(){
			 
			 if(towersToBeReloaded2.size() >= 1)
				towersToBeReloaded2.get(0).isShooting = false;
			 	towersToBeReloaded2.remove(0);
		 }
	 }
	 */
	
	public void init(){
		
		addMouseListener(new ClickChecker());
		compWon = false; // computer has won a round
		playerWon = false; // player has lived for 10 rounds
		menustate = 0; //story screen
		boxsize = 50; //tictactoe box
		linewidth = 12; // width of tictactoe lines
		playerStarts = false; //this is for rounds not within a game
		day = 0; //current round
		
		upX = 768/2 - (boxsize * 3/2) -linewidth; //centered
		upY = 600/2 - (boxsize *3/2) - linewidth + 60; //slightly below center for text
		

		
		
		title = new Font("Times New Roman", Font.BOLD, 36); // title font
		text = new Font("Times New Roman", Font.BOLD, 24); // name and story font
		XO = new Font("Times New Roman", Font.BOLD, 48); //x's and o's
		
		playerString = "X"; //only for the first round
		compString = "O"; //also only for the first round
		
		cmove = false; //first round only
		squares = new int[9]; // new set of squares
							  // player squares are always == 2
							  // comp squares are always == 1
		
		isfinished = false; // a single round is finished
		
		if(cmove) //make the initial move here
			squares[setUpTheRecursiveCalculation(squares)] = 1;
		
		background = myApplet.getImage(myApplet.getCodeBase(), "resource/tictactoebackground_1.jpg"); //load background picture

	}
	
	
	public void paintComponent(Graphics page){
		super.paintComponent(page);
		
		//paint the background
		page.drawImage(background, 0, 0, null);
		
		//title
		page.setColor(Color.YELLOW);
		page.setFont(title);
		page.drawString("CASTAWAY", 20, 40);
				
		//name
		page.setFont(text);
		page.drawString("by David Silin", 20, 70);				
		
		switch (menustate){ //different screens (I wasn't sure initially how many I was going to have so I made a switch
							//but it ended up being 2 so it wasn't really useful
			case 0: 
				
				page.setColor(new Color(0,0,220));
				
				int xStr= 70; //string x locations
				int yStr= 300; // y locs
				int yDist = 30; // delta Y
				page.drawString("You are the Captain of a small whaling vessel in the Arctic and,", xStr, yStr);
				page.drawString("all of a sudden, your ship is blown off course. Drifting out ", xStr, yStr + yDist);
				page.drawString("of control it crashes into the shore of a small snow covered island.", xStr, yStr + 2*yDist);
				page.drawString("You are able to salvage most of your supplies and radio for help,",xStr, yStr + 3*yDist);
				page.drawString("but you will need to survive for ten days before assistance arrives.", xStr, yStr + 4*yDist);
				page.drawString("In order to live you will need to survive rounds of tic-tac-toe against", xStr, yStr + 5*yDist);
				page.drawString("Mother Nature. Good luck Sailor",xStr, yStr + 6*yDist);
				
				//easy mode button
				page.setColor(Color.YELLOW);
				page.fillRect(550, 30, 150, 40);
				page.setColor(Color.black);
				page.drawString("Easy mode", 570, 55);
				
				//hard mode button
				page.setColor(Color.YELLOW);
				page.fillRect(550, 90, 150, 40);
				page.setColor(Color.BLACK);
				page.drawString("Hard mode", 570, 115);
				
				break;
			
			
			case 1:
				
				page.setColor(Color.BLACK);
				//vertical tictactoe box rectangles
				page.fillRect(upX + boxsize, upY, linewidth, 3*boxsize + 2*linewidth);
				page.fillRect(upX + 2*boxsize + linewidth, upY, linewidth, 3*boxsize + 2*linewidth);
				
				//horizontal tictactoe box rectangles
				page.fillRect(upX, upY + boxsize, 3*boxsize + 2*linewidth, linewidth);
				page.fillRect(upX, upY + 2*boxsize + linewidth, 3*boxsize + 2*linewidth, linewidth);

				//draw the X's and O's
				page.setFont(XO);
				for(int i = 0; i<9; i++){
					
					if(squares[i] == 2){
						page.drawString(playerString, (i%3) * linewidth + boxsize * (i%3) + upX + 8, (i/3) * linewidth + (i/3) * boxsize + upY + 40);
					}
					if(squares[i] == 1){
						page.drawString(compString, (i%3) * linewidth + boxsize * (i%3) + upX + 8, (i/3) * linewidth + (i/3) * boxsize + upY + 40);
					}
					
				}
				
				page.setFont(text);
				
				//Main menu button
				page.setColor(Color.YELLOW);
				page.fillRect(550, 30, 150, 40);
				page.setColor(Color.black);
				page.drawString("Main Menu", 570, 55);
				
				//Next Day button
				page.setColor(Color.YELLOW);
				page.fillRect(550, 90, 150, 40);
				page.setColor(Color.BLACK);
				page.drawString("Next Day", 570, 115);
				
				//draws the message win, day #, or loss
				if(compWon){
					page.setColor(Color.RED);
					page.setFont(XO);
					page.drawString("YOU LOSE", 270, 60);
				}else if(playerWon){
					page.setColor(Color.GREEN);
					page.setFont(XO);
					page.drawString("YOU WIN", 270, 60);
				}				
				else
				{
					page.setFont(XO);
					page.setColor(Color.CYAN);
					page.drawString("Day " + day, 335, 50);
				}
				break;
		
		}
		repaint();

			
	}
	
	public void drawStory(Graphics page){
		page.setColor(new Color(0,0,220));
		
		int xStr= 70; //string x locations
		int yStr= 300; // y locs
		int yDist = 30; // delta Y
		page.drawString("You are the Captain of a small whaling vessel in the Arctic and,", xStr, yStr);
		page.drawString("all of a sudden, your ship is blown off course. Drifting out ", xStr, yStr + yDist);
		page.drawString("of control it crashes into the shore of a small snow covered island.", xStr, yStr + 2*yDist);
		page.drawString("You are able to salvage most of your supplies and radio for help,",xStr, yStr + 3*yDist);
		page.drawString("but you will need to survive for ten days before assistance arrives.", xStr, yStr + 4*yDist);
		page.drawString("In order to live you will need to survive rounds of tic-tac-toe against", xStr, yStr + 5*yDist);
		page.drawString("Mother Nature. Good luck Sailor",xStr, yStr + 6*yDist);
	}
	

	private class ClickChecker implements MouseListener{

		@Override
		public void mouseReleased(MouseEvent m) {

			
		}
		@Override
		public void mouseClicked(MouseEvent arg0) {}
		@Override
		public void mouseEntered(MouseEvent arg0) {}
		@Override
		public void mouseExited(MouseEvent arg0) {}
		@Override
		public void mousePressed(MouseEvent m) {
			
			if(!cmove && menustate == 1){
				
				for(int i = 0; i<9; i++){
					
					int xadjust = (i%3) * linewidth + boxsize * (i%3) + upX; // distance from the left of a given box
					int yadjust = (i/3) * linewidth + (i/3) * boxsize + upY; // distance from the top of a given X
					
					if(!isfinished && squares[i] == 0 && m.getX() >= xadjust && m.getX() <= xadjust + boxsize 
							&& m.getY() >= yadjust && m.getY() <= yadjust + boxsize ){ // the click is within a given box
						
						squares[i] = 2;//player moves here
					    cmove = true; //computer's move

						boolean isfull = true; // is the board full
						for(int j = 0; j<9; j++){ // if one square isnt full then the variable is false
							if(squares[j] == 0)
								isfull = false;
						}
						
						if(isfull){ //then the current game is finished
							isfinished = true;
						}
						
						if(!isfinished) // prevents the computer from moving after the game has ended					    
							squares[setUpTheRecursiveCalculation(squares)] = 1; //pick move
					    
						isfull = true; // checking again after comp moves
						for(int j = 0; j<9; j++){ 
							if(squares[j] == 0)
								isfull = false;
						}
						
						if(isfull){ //then the current game is finished
							isfinished = true;
						}
						
						if(checkwin(squares, 1)){ //if the computer wins end the game
							isfinished = true;
							compWon = true;
						}
					}
				}
			}
			
			if(menustate == 1){
	
				//main menu
				if(m.getX() > 550 && m.getX()< 700 && m.getY() > 30 && m.getY() < 70){
					menustate = 0;
					day = 0;
					compWon = false;
					playerWon = false;
					playerStarts = false;
					upX = 768/2 - (boxsize * 3/2) -linewidth; //centered
					upY = 600/2 - (boxsize *3/2) - linewidth + 60; //slightly below center for text
				}
				
				//next day
				if(m.getX() > 550 && m.getX()< 700 && m.getY() > 90 && m.getY() < 130 && isfinished && !compWon && !playerWon){
					newDay();
				}
				
			}else//menu buttons
			if(menustate == 0){
				
				//easy mode button
				if(m.getX() > 550 && m.getX()< 700 && m.getY() > 30 && m.getY() < 70){
					

					upX = 768/2 - (boxsize * 3/2) -linewidth; //centered
					upY = 600/2 - (boxsize *3/2) - linewidth + 60; //slightly below center for text
					
					menustate = 1;
					difficulty = 0;
					newDay();
				}
				
				//hard mode button
				if(m.getX() > 550 && m.getX()< 700 && m.getY() > 90 && m.getY() < 130){

					upX = 768/2 - (boxsize * 3/2) -linewidth; //centered
					upY = 600/2 - (boxsize *3/2) - linewidth + 60; //slightly below center for text
					
					menustate = 1;
					difficulty = 1;
					newDay();
					if(!moveTimerinit){
						moveTimer.schedule(new Mover(), 20);
						moveTimerinit = true;
					}
				}
			}
			
		}
	}
	
	
	public int setUpTheRecursiveCalculation(int[] tictactoeBoard){ // initializes the recursive search
		
		ArrayList<Integer> ArrayOfEmptySquaresOnTheBoard = new ArrayList<Integer>(); //emptyLocs
		
		int movesMadeInTotal = 0; //first move
		
		for(int PositionOnTheBoard = 0; PositionOnTheBoard<9; PositionOnTheBoard++){
			if(tictactoeBoard[PositionOnTheBoard] == 0)
				ArrayOfEmptySquaresOnTheBoard.add(PositionOnTheBoard);
			
				movesMadeInTotal+= tictactoeBoard[PositionOnTheBoard];
		}
		//make a random "good" first move
		if(movesMadeInTotal == 0){
			cmove = false; // no longer computer's move
			int n = Random.nextInt(8);
			if(n%2 == 0){
				return 4;
			}else{
				if(n == 1){
					return 0;
				}
				if(n == 3){
					return 2;
				}
				if(n == 5){
					return 6;
				}else{
					return 8;
				}
			}	
		}
		//makes a random "good" third move
		if(movesMadeInTotal == 3 && tictactoeBoard[4] == 2){
			
			int moveToMakeOnTheGui = makeGoodThirdMove(tictactoeBoard);//returns a good move less than 9 or 20 if the algorithm should be used instead
			if(moveToMakeOnTheGui < 9)
				return moveToMakeOnTheGui;
			
		}
		//make one of two potential 3rd moves for a specific case
		int x = (int) recursiveCalculation(tictactoeBoard.clone(), ArrayOfEmptySquaresOnTheBoard, 0);
		cmove = false; // no longer computer's move
		return x;
	}
	public int makeGoodThirdMove(int[] tictactoeBoard){//decides whether to make a good third move or generate it with the algorithm
		cmove = false; // no longer computer's move
		if(tictactoeBoard[0] == 1 && Random.nextInt(2) == 0) //r.nextInt(2) decides whether or not to use the algorithm
			return 8;
		if(tictactoeBoard[2] == 1 && Random.nextInt(2) == 0){
			return 6;
		}
		if(tictactoeBoard[6] == 1 && Random.nextInt(2) == 0)
			return 2;
		if(tictactoeBoard[8] == 1 && Random.nextInt(2) == 0){
			return 0;
		}
		
		return 20;
		
	}
	
	//squares is the board at a given level of the search
	//emptyLocs are the empty locations in squares, I could calculate them every time but it was easier to do it this way
	//depth allows me to know whose move it is and it needs to return a location instead of a positional value
	public double recursiveCalculation(int[] tictactoeBoard, ArrayList<Integer> arrayOfEmptySquaresOnTheBoard, int depthInTheRecursiveTree){
		//base case
		if(arrayOfEmptySquaresOnTheBoard.size() == 0){
			return 0;
		}		
		if(depthInTheRecursiveTree%2 == 1){ // player's turn to move
			return playerToMove(arrayOfEmptySquaresOnTheBoard, depthInTheRecursiveTree, tictactoeBoard);
		}
		return computerToMove(arrayOfEmptySquaresOnTheBoard, depthInTheRecursiveTree, tictactoeBoard);// computer to move
	}
	
	public double setupNextRecurse (int[] tictactoeBoardtoBePassedDowntheTree, int depthInTheRecursiveTree){
		ArrayList<Integer> p = new ArrayList<Integer>(); //new emptyLocs
		for(int j = 0; j<9; j++){
			if(tictactoeBoardtoBePassedDowntheTree[j] == 0){
				p.add(j);
			}
		}
		return recursiveCalculation(tictactoeBoardtoBePassedDowntheTree,p, depthInTheRecursiveTree+1); //find value of the position
	}
	
	public double playerToMove(ArrayList<Integer> arrayOfEmptySquaresOnTheBoard, int depthInTheRecursiveTree, int[] ticTacToeBoard){
		double SumOfTheHeuristicValuesOfThePlayersMoves = 0; //sum of the values of a position
		
		for(int emptyPosition : arrayOfEmptySquaresOnTheBoard){ // for each empty location
			
			int[] ticTacToeBoardToBePassedDownTheTree = ticTacToeBoard.clone(); //avoid child / parent value mix-ups
			ticTacToeBoardToBePassedDownTheTree[emptyPosition] = 2; // move to square i
			
			if(checkwin(ticTacToeBoardToBePassedDownTheTree, 2)) //if its a loss
				return -1; // don't make previous move
			
			double HeuristicValueOfTheChildNode = setupNextRecurse(ticTacToeBoardToBePassedDownTheTree, depthInTheRecursiveTree); //child value
			
			if(HeuristicValueOfTheChildNode == -1) //if there is a child that will necessarily lose
				return -1; // dont make parent move
			
			SumOfTheHeuristicValuesOfThePlayersMoves += HeuristicValueOfTheChildNode; //otherwise add to the value of this position
		}
			return SumOfTheHeuristicValuesOfThePlayersMoves/arrayOfEmptySquaresOnTheBoard.size();	//return the value of the parent move (ie this position)
	}
	
	public double computerToMove(ArrayList<Integer> arrayOfEmptySquaresOnTheBoard, int depthInTheRecursiveTree, int[] tictactoeBoard){

		double highestHeuristicValueOfSomePosition = -2; //-1<=x<=1
		int LocationOfTheHighestValue = 0; //only used if depth == 0
		
		for(int emptyposition: arrayOfEmptySquaresOnTheBoard){
							
			int[] tictactoeboardToBePassedDownTheTree = tictactoeBoard.clone(); // this step avoids issues where children will change the values in the parent
			tictactoeboardToBePassedDownTheTree[emptyposition] = 1; // move to square i
			
			if(checkwin(tictactoeboardToBePassedDownTheTree,1)){ //if you win
				if(depthInTheRecursiveTree == 0)
					return emptyposition;
				return 1;
			}
			
			double heuristicValueOfTheChildNode = setupNextRecurse(tictactoeboardToBePassedDownTheTree, depthInTheRecursiveTree); //value of child position

			if(heuristicValueOfTheChildNode > highestHeuristicValueOfSomePosition){ // pick the best move
				LocationOfTheHighestValue = emptyposition;
				highestHeuristicValueOfSomePosition = heuristicValueOfTheChildNode;
			}
		}
		if(depthInTheRecursiveTree == 0)
			return LocationOfTheHighestValue;
		
		return highestHeuristicValueOfSomePosition;
	}
	
	//checks if squares contains a win for player number toWin
	public boolean checkwin(int[] ticTacToeBoard, int IdOfPlayerWeAreCheckingForAVictory){
		for(int i = 0; i<3; i++){ 
			if( ticTacToeBoard[i] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[i+3] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[i+6] == IdOfPlayerWeAreCheckingForAVictory){ //vertical
				return true;
			}
			if(ticTacToeBoard[i*3] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[i*3 +1] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[i*3 + 2] == IdOfPlayerWeAreCheckingForAVictory){ //horizontal
				return true;
			}
		}
		if(ticTacToeBoard[0] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[4] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[8] == IdOfPlayerWeAreCheckingForAVictory){//diagonal
			return true;
		}
		if(ticTacToeBoard[2] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[4] == IdOfPlayerWeAreCheckingForAVictory && ticTacToeBoard[6] == IdOfPlayerWeAreCheckingForAVictory){//diagonal
			return true;
		}
		return false; //no win		
	}
	
	//initializes a new day
	public void newDay(){
		day+= 1;
		
		
		playerStarts = !playerStarts; //switches who makes first move
		squares = new int[9]; //clears the board
		isfinished = false; //makes it possible for player to move
		
		//sets up other variables
		if(playerStarts){
			cmove = false; //used within the round as opposed to between
			playerString = "X";
			compString = "O";
		}else{
			cmove = true;
			playerString = "O";
			compString = "X";
			squares[setUpTheRecursiveCalculation(squares)] = 1; //make the computer's move

		}
		
		if(day > 10){
			playerWon = true;
			isfinished = true;
		}
		
	}
	
	class Mover extends TimerTask{
		 public void run(){
			 
			
			if(firetimes == 0 && !compWon && !playerWon){ //when the amount of times it should move in the same direction run out
				xspeed = Random.nextInt(5)-2; // change the speed
				yspeed = Random.nextInt(5)-2;
				firetimes = Random.nextInt(40) + 30; //add more fire times
				
			}else if(!compWon && !playerWon){ //otherwise
				firetimes-=1; // de-increment the fire times
				upX += xspeed; //move the box
				upY += yspeed;
			}
			
			//if it would move off the screen re-adjust it
			if(upX <= 10 || upX >= 580){ 
				xspeed *= -1;
				upX += 3*xspeed;
			}
			if(upY <= 180 || upY >= 420){
				yspeed*=-1;
				upY+=3*yspeed;
			}
			
			//fire the timer again
			if(difficulty == 1)		
				moveTimer.schedule(new Mover(), (int) (14-day));
			else
				 moveTimerinit = false;
		 }
	 }

}
