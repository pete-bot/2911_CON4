import java.awt.Point;
import java.util.ArrayList;


public class State implements Comparable {

	private int stateRank;
	private Action thisAction;
	private BackendBoard boardRep;
	
	//starting state constructor
	public State(BackendBoard backendBoard) {
		this.stateRank = 0;
		this.thisAction = null;
		this.boardRep = backendBoard;	
	}

	//choice constructor
	public State(int column, State prevState) {
		this.boardRep = prevState.getBoard();
		this.stateRank = generateScore(column, 2);
		this.thisAction = generateAction(column);
	}

	private Action generateAction(int column) {
		Action a = new Action(2, column);
		return a;
	}

	private BackendBoard getBoard() {
		return boardRep;
	}

	public int compareTo(Object o) {
		return getHValue()-((State) o).getHValue();
	}

	private int getHValue() {
		return stateRank;
	}

	public Action getAction(){
		return thisAction;
	}
	
	public int generateScore(int player, int column){
		int[][] board = this.boardRep.getBoard();
//		int highestScore = 0;
		
		int vertScore = 0;
		int horzScore = 0;
//		int diagScore = 0;
		
		
		if (player == 1){
			vertScore = checkVertical(board, column, 1);
			horzScore = checkHorizontal(board, column, 1);
			
			
		} else if (player == 2){
			vertScore = checkVertical(board, column, 2);
			horzScore = checkHorizontal(board, column, 2);
			
		}
		
		
		return (vertScore > horzScore) ? vertScore : horzScore;
	}

	private int checkHorizontal(int[][] board, int column, int player) {
		
        int rightadjacentTiles = 1;
        int leftadjacentTiles = 1;
        int leftColIndex = column - 1;
        int rightColIndex = column + 1;
        int lastRow = 6;
        
        //find the spot above the last piece played
        while (lastRow != 0){
        	if (board[lastRow][column] == 1 || board[lastRow][column] == 2){
        		break;
        	}
        }
        
        lastRow++;
        
        //check if move fits return 0 if it does not
        if (lastRow > 6){
        	return 0;
        }
        

        // Iterate left if there are lefts to iterate to
        if (column >= 0) {

            // Left iteration from last placed node, checking if tile is owned
            // by player
            while (leftColIndex >= 0
                    && board[lastRow][leftColIndex] == player) {
                leftadjacentTiles++;
                leftColIndex--;
            }

            // If we haven't won already
            if (leftadjacentTiles < 4) {
                // Right iteration
                while (rightColIndex < 7
                        && board[lastRow][rightColIndex] == player) {
                    rightadjacentTiles++;
                    rightColIndex++;
                }

            }

        }
        return (leftadjacentTiles > rightadjacentTiles) ? leftadjacentTiles : rightadjacentTiles;

	}

	private int checkVertical(int[][] board, int column, int player) {
		int x = 0;
		int row = 6;
		
		while (row != 0){
			if (board[row][column] == player){
				x++;
			} else if (board[row][column] == '0'){
				//do nothing
			} else if (board[row][column] != player){
				break;
			}
			row--;
		}		
		return x;
	}
}
