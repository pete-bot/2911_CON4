
/**
 * 
 * @author WOBCON4
 * 	Class to contain the turn summary for each turn
 *
 */
 
public class TurnSummary {
	/**Turn number of current turn */
	private int turnNumber;
	/**Board state */
	private int[][] boardState;
	/** Player number of current turn (who made this turn?)*/
	private int player;
	/** Last action made*/
	private Action lastAction;
	
	/**
	 * Class for statistics that contains all the details of a turn
	 * 
	 * @param turnNum 		Stores which turn of the game this state refers to
	 * @param currBoard 	The integer representation of the current board state
	 * @param currPlayer	Stores which player was deciding this turn
	 * @param lastMove		Stores the last column a token was dropped
	 * */
	public TurnSummary(int turnNum, int[][] currBoard, int currPlayer, Action lastMove){
		turnNumber = turnNum;
		boardState = currBoard.clone();
		player = currPlayer;
		lastAction = lastMove;
	}
	
	
	public int getTurnNumber(){
		return turnNumber;
	}
	
	public int[][] getBoardState(){
		return boardState;
	}
	
	public int getCurrPlayer(){
		return player;
	}
	
	public Action getLastMove(){
		return lastAction;
	}
}
