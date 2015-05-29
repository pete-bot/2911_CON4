
/*
 * Class for statistics that contains all the details of a turn
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
