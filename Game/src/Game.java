import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * TODO
 * - create enums
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
		System.out.println("Initiating WOBCON4. Standby for de-Wobbification.");
		
		// most of gameplay happens here - this is not how i want the design to be
		// this is mostly because I could not get the button stuff to do what i needed it 
		//to do. we can fic this though
		Window gameWindow = new Window(newBoard);
        
	}
}
