
public class Board {

	// this is the board representation - very simple and light
	// we store the position of player_1 as 1, player_2 as 2 
	private int[][] board;
	private int turnNumber;
	
	// need to make these ENUMS
	private int ROWMAX;
	private int COLMAX;
	
	public Board(){
		board = new int[6][7];
		ROWMAX = 6;
		COLMAX = 7;
		turnNumber = 0;
	}
	
	// set the turn number
	public void IncrementTurn(){
		turnNumber++;	
	}
	
	// return teh turn number
	public int getTurn(){
		return turnNumber;	
	}
	
	public int getPosition(int row, int col){
		return board[row][col];
	}
	
	// this method assumes that the isLegal method has been called and has been 
	public void makeMove(Action newAction){
		int col;
		for(col = 0; col < ROWMAX; col++){
			if(board[col][newAction.getColumn()]==0){
				break;
			}
		}
		board[col][newAction.getColumn()] = newAction.getPlayer(); 	
	}
	
	public boolean isLegal(Action newAction){
		
		// check if tile is out of bounds (pos < 0, pos > 6)
		if( (newAction.getColumn() < 0) || (newAction.getColumn()> 6)){
			return false;
		}
		
		// check if the tile will 'overflow' the board
		if((board[5][newAction.getColumn()] != 0)){
			return false;
		}
		
		return true;
	}
	
	
	// render the current board in terminal
	public void showBoard(){
		for(int row = ROWMAX-1; row >= 0; row--){
			for(int col = 0; col < COLMAX; col++){
				System.out.print("["+this.getPosition(row, col)+"]");
			}
			System.out.println();
		}
	}
	
	
	// terrible implementation - this works for now but is vastly inefficient.
	// need to fix
	public boolean checkWinState(){
		
		// find four horizontal pieces.
		for(int row = 0; row<ROWMAX; row++){
			for(int col = 0; col<4; col++){
				if( (board[row][col] != 0) && (board[row][col]==board[row][col+1]) && (board[row][col]==board[row][col+2]) && (board[row][col]==board[row][col+3])){
					System.out.println("winState: hor");
					return true;
				}
			}
		}
		
		// find four Vertical pieces.
		for(int row = 0; row<3; row++){
			for(int col = 0; col<COLMAX; col++){
				if((board[row][col] != 0) && (board[row][col]==board[row+1][col]) && (board[row][col]==board[row+2][col]) && (board[row][col]==board[row+3][col])){
					System.out.println("winState: vert");
					return true;
				}
			}
		}
		// diagonal right
		// find four Vertical pieces.
		for(int row = 0; row<3; row++){
			for(int col = 0; col<4; col++){
				if((board[row][col] != 0) && (board[row][col]==board[row+1][col+1]) && (board[row][col]==board[row+2][col+2]) && (board[row][col]==board[row+3][col+3])){
					System.out.println("winState: diag_right");
					return true;
				}
			}
		}
		
		// diagonal left
		// find four Vertical pieces.
		for(int row = 0; row<ROWMAX; row++){
			for(int col = 3; col<COLMAX; col++){
				if((board[row][col] != 0) && (board[row][col]==board[row+1][col-1]) && (board[row][col]==board[row+2][col-2]) && (board[row][col]==board[row+3][col-3]) ){
					System.out.println("winState: diag_left");
					return true;
				}
			}
		}
		
		return false;
		
	}

    public void resetBoard() {
		board = new int[6][7];
		ROWMAX = 6;
		COLMAX = 7;
		turnNumber = 0;

        showBoard();
    }
	
}
