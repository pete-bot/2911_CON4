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
		this.stateRank = generateBoard(column);
//		this.thisAction = generateAction(stateRank);
	}

	private BackendBoard getBoard() {
		return boardRep;
	}

	public int compareTo(Object o) {
		return getHValue();
	}

	private int getHValue() {
		return stateRank;
	}

	public Action getAction(){
		return thisAction;
	}

	private int generateBoard(int column){
		int score = 0;
		
		//Make placeholder move and extract the board array, create a co-ordinate
		this.boardRep.makeMove(new Action(2, column));
		if(!this.boardRep.checkWinState(new Action(2, column)).isEmpty()) {
			return Integer.MAX_VALUE;
		}
		int[][] b = this.boardRep.getBoard();
		int[] xy = new int[] {0, column};
		
		//Find co-ordinate of tile played
		for(int i = xy[0]; i < 6; i++) {
			if(b[i][column] == 2) {
				xy[0] = i;
				break;
			}
		}
		
		/*Scan surrounding tiles*/
		//Scan column, first determine deviation from edge
		int lowerBound = (xy[0] + 3) > 5 ? 5 : (xy[0] + 3);
		int counter = 0;
		for(int i = xy[0]; i < lowerBound; i++) {
			if(b[i][column] == 2) {
				counter++;
			} else if(b[i][column] == 1) {
				break;
			}
		}
		if(counter < 4 && xy[0] > 3) { //Check that there's room in the column to have a possible win, otherwise ignore
			counter = 0;
		}
		score += counter;
		
		//Scan row, first determine deviation from edge
		int leftBound = (column - 3) < 0 ? 0 : (column - 3);
		int rightBound = (column + 3) > 6 ? 6 : (column + 3);
		for(int i = column; i < rightBound; i++) {
			if(b[i][column] == 2) {
				counter++;
			} else if(b[i][column] == 1) {
				break;
			} else {
				if(b[i+1][column] == 0) {
					break;
				} else {
					counter++;
				}
			}
		}
		for(int i = column; i > leftBound; i--) {
			if(b[i][column] == 2) {
				counter++;
			} else if(b[i][column] == 1) {
				break;
			} else {
				if(b[i+1][column] == 0) {
					break;
				} else {
					counter++;
				}
			}
		}
		// MIGHT NEED TO CHECK IF THERES ROOM IN THE ROW TO WIN HERE!
		score += counter;
		
		//Scan leading diagonal
		
		//Scan leading diagonal
		return score;
	}
}
