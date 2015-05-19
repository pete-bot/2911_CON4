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

		
		
		
		
		return column;
	}
}
