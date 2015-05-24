import java.awt.Point;
import java.util.ArrayList;


public class State implements Comparable<State> {

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
		this.stateRank = generateScore(column);
		this.thisAction = generateAction(column);
		System.out.println("COLUMN:"+thisAction.getColumn());
		System.out.println("RANK:"+stateRank);
	}

	private Action generateAction(int column) {
		Action a = new Action(2, column);
		return a;
	}

	private BackendBoard getBoard() {
		return boardRep;
	}

	public int compareTo(State o) {
		return getHValue()-o.getHValue();
	}

	private int getHValue() {
		return stateRank;
	}

	public Action getAction(){
		return thisAction;
	}
	
	public int generateScore(int column){
		int[][] board = this.boardRep.getBoard();
				
		//get p1 scores
		int horz1 = checkHorizontal(board, column, 1);
		int vertScore1 = checkVertical(board, column, 1);
		
		//get ai's scores
		int horz2 = checkHorizontal(board, column, 2);
		int vertScore2 = checkVertical(board, column, 2);
		
		//tally them
		int maxp1 = horz2 + vertScore2;		
		int maxp2 = horz1 + vertScore1;
		
		//return win move for AI
		if (maxp2 >= 4){
			return maxp2;
		}
		
		//else return a blocking move~?
		return (maxp1 > maxp2) ? maxp1 : maxp2;
		
	}

	private int checkHorizontal(int[][] board, int column, int lastPlayer) {
		
        int leftadjacentTiles = 1;
        int rightadjacentTiles = 1;
        
        int leftColIndex = column - 1;
        int rightColIndex = column + 1;
        
        int lastRow = 5;
        while (lastRow != 0){
        	if (board[lastRow][column] == 1 || board[lastRow][column] == 2){
        		break;
        	}
        	lastRow--;
        }
        
        //return if row is full
        if (lastRow == 5){
        	return 0;
        }
        
        //increment to the spot above the first tile
        lastRow++;
        

        // Iterate left if there are lefts to iterate to
        if (column >= 0) {

            // Left iteration from last placed node, checking if tile is owned
            // by player
            while (leftColIndex >= 0
                    && board[lastRow][leftColIndex] == lastPlayer) {
                leftadjacentTiles++;
                leftColIndex--;
            }

            // If we haven't won already
            if (leftadjacentTiles < 4) {
                // Right iteration
                while (rightColIndex < 6
                        && board[lastRow][rightColIndex] == lastPlayer) {
                    rightadjacentTiles++;
                    rightColIndex++;
                }

            }

        }
        return (leftadjacentTiles > rightadjacentTiles) ? leftadjacentTiles : rightadjacentTiles;
	}

	private int checkVertical(int[][] board, int column, int player) {
		int x = 0;
		int row = 5;
		
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
