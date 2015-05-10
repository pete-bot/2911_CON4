import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*
 * TODO
 * - create enums
 * - hook th ebackend to Sanjay/oliuvers
 */

public class GameTerminal {
	
	private Board newBoard;
	// 
	public static void main(String args[]){
		GameTerminal newGame = new GameTerminal();
		newGame.playGame();
	}

	
	// game constructor
	public GameTerminal(){
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
		System.out.println("Welcome to WOBCON4. Enjoy the game.");
		System.out.println("Initial Game State:");
		newBoard.showBoard();
		System.out.println("PLAYER_1, please enter your column choice:");
		
		int turnCount = 0;
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;
			
			while((input=br.readLine())!=null){
							
				Action newAction;
				if(turnCount%2==0 ){
					// PLAYER1 input
					newAction = new Action(1, Integer.parseInt(input));
				}else{
					newAction = new Action(2, Integer.parseInt(input));
				}
				//newAction.showAction();
				
				if(!newBoard.isLegal(newAction)){
					System.out.println("You have entered an invalid move, please try again.");
					continue; 
				}
				
				
				newBoard.makeMove(newAction);
				
				newBoard.showBoard();
				
				if(newBoard.checkWinState()){
					if(turnCount%2==0 ){
						System.out.println("PLAYER_1, you WIN!");
						
					}else{
						System.out.println("PLAYER_2, you WIN!");
					}
					break;
				}
				
				if(turnCount%2==1 ){
					System.out.println("PLAYER_1, please enter your move:");
				}else{
					System.out.println("PLAYER_2, please enter your move:");
				}
				
				turnCount++;	
			}
	 
		}catch(IOException io){
			io.printStackTrace();
		} 	
	}
}
