
public class BackendBoard {

    // this is the board representation - very simple and light
    // we store the position of player_1 as 1, player_2 as 2 
    private int[][] board;
    private int turnNumber = 0;
    
    // need to make these ENUMS
    private int ROWMAX = 6;
    private int COLMAX = 7;
    
    public BackendBoard(){
        board = new int[ROWMAX][COLMAX];
    }
    
    // set the turn number
    public void IncrementTurn(){
        turnNumber++;   
    }
    
    // Return the turn number
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
    // Maybe 'showTerminalBoard' is better...
    public void showTerminalBoard(){
        for(int row = ROWMAX-1; row >= 0; row--){
            for(int col = 0; col < COLMAX; col++){
                System.out.print("["+this.getPosition(row, col)+"]");
            }
            System.out.println();
        }
    }
    
    /*
     * Added by Sketch in "Updated Win Row Addition"
     * Returns the win state using sweet Al Gore rhythms.
     * 
     *  @return                 Game win condition
     *  @param lastColumn       The column of the last player move
     *  @param lastRow          Returns the last Row of the player action.
     */
    public boolean checkWinState(Action lastTurn){
        boolean win = false;
        
        int lastPlayer = lastTurn.getPlayer();
        int lastColumn = lastTurn.getColumn();
        int lastRow = getLastRow(lastColumn);   //Calculate the last row
        
        //Check vertical wins
        win = checkVertical(lastPlayer, lastColumn, lastRow);
        
        //Check horizontal wins
        if (win != true) {
            win = checkHorizontal(lastPlayer, lastColumn, lastRow);
        }
        
        //Check ascending diagonal wins
        if (win != true) {
            win = checkAscDiagonal(lastPlayer, lastColumn, lastRow);
        }
        
        //check descending diagonal wins
        if (win != true) {
            win = checkDescDiagonal(lastPlayer, lastColumn, lastRow);
        }
        
        return win;
    }
    
    /*
     * Check vertical wins
     * Added by Sketch in "Updated Win Row Addition"
     */
    private boolean checkVertical(int lastPlayer, int lastColumn, int lastRow) {
        boolean win = false;
        
        //Don't bother if not enough tokens
        if (lastRow >= 3 ){
            win = true;
            lastRow--;
                    
            //Iterate down rows until adjTiles = 4 or win = false
            for(int adjTiles = 1; adjTiles < 4  && win == true; adjTiles++, lastRow--) {
                if(board[lastRow][lastColumn] != lastPlayer){   //If the checked tile is not last player
                    win = false;
                }
            }
        }
        
        return win;
    }
    
    /*
     * Check horizontal wins 
     * Added by Sketch in "Updated win condition patch"
     */
    private boolean checkHorizontal(int lastPlayer, int lastColumn, int lastRow) {
        boolean win = false;
        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int rightColIndex = lastColumn + 1;
        
        //Iterate left if there are lefts to iterate to
        if( lastColumn >= 0 ){
            
            //Left iteration from last placed node, checking if tile is owned by player
            while( leftColIndex >= 0 && board[lastRow][leftColIndex] == lastPlayer ){
                adjacentTiles++;
                leftColIndex--;
            }
        
            //If we haven't won already
            if(adjacentTiles < 4){
                //Right iteration
                while( rightColIndex < COLMAX && board[lastRow][rightColIndex] == lastPlayer ){
                    adjacentTiles++;
                    rightColIndex++;
                }

            }
            
            if(adjacentTiles >= 4){
                win = true;
            }
        }
        return win;
    }
    
    /*
     * Check ascending diagonal wins
     * Added by Sketch in "Updated win condition patch"
     */
    private boolean checkAscDiagonal(int lastPlayer, int lastColumn, int lastRow){
        boolean win = false;

        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int leftRowIndex = lastRow - 1;
        
        int rightColIndex = lastColumn + 1;
        int rightRowIndex = lastRow + 1;
        
        //Left iteration from last node, checking if tile is owned by player
        while( leftColIndex >= 0 && leftRowIndex >= 0  &&
                board[leftRowIndex][leftColIndex] == lastPlayer){
            adjacentTiles++;
            leftColIndex--;
            leftRowIndex--;
        }

        //If we haven't won already
        if(adjacentTiles < 4){
            //Right iteration
            while( rightColIndex < COLMAX && rightRowIndex < ROWMAX &&
                    board[rightRowIndex][rightColIndex] == lastPlayer ){
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex++;
            }
        }
        
        if(adjacentTiles >= 4){
            win = true;
        }

        return win;
    }

    /*
     * Check descending diagonal wins
     * Added by Sketch in "Updated win condition patch"
     */
    private boolean checkDescDiagonal(int lastPlayer, int lastColumn, int lastRow){
        boolean win = false;
        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int leftRowIndex = lastRow + 1;
        
        int rightColIndex = lastColumn + 1;
        int rightRowIndex = lastRow - 1;
        
        //Left iteration from last node, checking if tile is owned by player
        while( leftColIndex >= 0 && leftRowIndex < ROWMAX &&
                board[leftRowIndex][leftColIndex] == lastPlayer){
            adjacentTiles++;
            leftColIndex--;
            leftRowIndex++;
        }

        //If we haven't won already
        if(adjacentTiles < 4){
            //Right iteration
            while( rightColIndex < COLMAX && rightRowIndex >= 0 &&
                    board[rightRowIndex][rightColIndex] == lastPlayer ){
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex--;
            }
        }
        
        // adjacentTiles will never be > 4 if run every turn.
        if(adjacentTiles >= 4){
            win = true;
        }
        return win;
    }
    
    /*
     * Added by Sketch in "Updated win condition patch"
     * Change to public if required outside the scope, however I would advise creating
     * another an interface function unique to the caller to preserve abstraction.
     * 
     *  @return         Row of the top token of any column within the bounds of the game
     *  @precondition   0 <= column <= 6
     */
    private int getLastRow(int column){
        int i;
        for( i = ROWMAX - 1; board[i][column] == 0; i--);       //Iterates through i until value returns a player
        return i;   
    }
    
    public void resetBoard() {
        int empty = 0;
        for (int i = 0; i < ROWMAX; i++) {
            for (int j = 0; j < COLMAX; j++) {
                board[i][j] = empty;
            }
        }
        turnNumber = 0;
        showTerminalBoard(); 
    }
    
}
