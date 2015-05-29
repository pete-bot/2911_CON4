
/**
 * Class for statistics that contains all the details of a turn
 * 
 * @param turnNumber			Stores which turn of the game this state refers to
 * @param boardState			The integer representation of the current board state
 * @param player				Stores which player was deciding this turn
 * @param lastAction			Stores the last column a token was dropped
 */
public class TurnSummary {
	private int turnNumber;
	private int[][] boardState;
	private int player;
	private Action lastAction;
	
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
