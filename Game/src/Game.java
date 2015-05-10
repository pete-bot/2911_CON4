import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * TODO
 * - create enums
 * - hook th ebackend to Sanjay/oliuvers
 */

public class Game {
	
	private Board newBoard;
	// 
	public static void main(String args[]){
		Game newGame = new Game();
		newGame.playGame();
	}

	
	// game constructor
	public Game(){
		newBoard = new Board();
	}
	
	public Board getBoard(){
		return newBoard; 
	}
	
	// main game play class
	// at the moment, this will provide enjoyable connect 4 
	// entertainment for up to two people at once. be advised: you must provide your
	// own second player
	public void playGame(){
		// need to reconfigure this section so that we can integrate the UI
		// temporary set up for testing/debugging
		System.out.println("Initiating WOBCON4. Standby for de-Wobbification.");
		
		Window gameWindow = new Window(newBoard);
		
		
	}
}
