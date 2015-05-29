import java.awt.Point;
import java.util.ArrayList;

public class BackendBoard {

    /** information for board representation*/
    private int[][] board;
    private int currentPlayer = 1;
    private int turnNum = 0;

    /**Dimensions of the game board. */
    private int ROWMAX = 6;
    private int COLMAX = 7;

    /**Instance of the game state and backend board */
    public BackendBoard() {
        board = new int[ROWMAX][COLMAX];
    }

    /**
     * Method to increment the game turn
     */
    public void incremementTurn(){
    	turnNum++;
    }
    
    /**
     * Method to return the game turn
     * @return
     * 		The game turn
     */
    public int getTurnNum(){
    	return turnNum;
    }
    
    /**
     * Method to check the win state in the ascending Diag direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a win state for the ascending diagonal move for the AI
     */
    private boolean checkAscDiagonal(int lastPlayer, int lastColumn, int lastRow) {
        boolean win = false;

        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int leftRowIndex = lastRow - 1;

        int rightColIndex = lastColumn + 1;
        int rightRowIndex = lastRow + 1;

        // Left iteration from last node, checking if tile is owned by player
        while (leftColIndex >= 0 && leftRowIndex >= 0
                && board[leftRowIndex][leftColIndex] == lastPlayer) {
            adjacentTiles++;
            leftColIndex--;
            leftRowIndex--;
        }

        // If we haven't won already
        if (adjacentTiles < 4) {
            // Right iteration
            while (rightColIndex < COLMAX && rightRowIndex < ROWMAX
                    && board[rightRowIndex][rightColIndex] == lastPlayer) {
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex++;
            }
        }

        if (adjacentTiles >= 4) {
            win = true;
        }

        return win;
    }

    /**
     * Method to check the win state in the descending Diag direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a win state for the descending diagonal move for the AI
     */
    private boolean checkDescDiagonal(int lastPlayer, int lastColumn,
            int lastRow) {
        boolean win = false;
        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int leftRowIndex = lastRow + 1;

        int rightColIndex = lastColumn + 1;
        int rightRowIndex = lastRow - 1;

        // Left iteration from last node, checking if tile is owned by player
        while (leftColIndex >= 0 && leftRowIndex < ROWMAX
                && board[leftRowIndex][leftColIndex] == lastPlayer) {
            adjacentTiles++;
            leftColIndex--;
            leftRowIndex++;
        }

        // If we haven't won already
        if (adjacentTiles < 4) {
            // Right iteration
            while (rightColIndex < COLMAX && rightRowIndex >= 0
                    && board[rightRowIndex][rightColIndex] == lastPlayer) {
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex--;
            }
        }

        // adjacentTiles will never be > 4 if run every turn.
        if (adjacentTiles >= 4) {
            win = true;
        }
        return win;
    }

    /**
     * Method to check the win state in the horizontal direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a win state for the horizontal diagonal move for the AI
     */
    private boolean checkHorizontal(int lastPlayer, int lastColumn, int lastRow) {
        boolean win = false;
        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int rightColIndex = lastColumn + 1;

        // Iterate left if there are lefts to iterate to
        if (lastColumn >= 0) {

            // Left iteration from last placed node, checking if tile is owned
            // by player
            while (leftColIndex >= 0
                    && board[lastRow][leftColIndex] == lastPlayer) {
                adjacentTiles++;
                leftColIndex--;
            }

            // If we haven't won already
            if (adjacentTiles < 4) {
                // Right iteration
                while (rightColIndex < COLMAX
                        && board[lastRow][rightColIndex] == lastPlayer) {
                    adjacentTiles++;
                    rightColIndex++;
                }

            }

            if (adjacentTiles >= 4) {
                win = true;
            }
        }
        return win;
    }

    /**
     * Method to check the win state in the vertical direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a win state for the vertical move for the AI
     */
    private boolean checkVertical(int lastPlayer, int lastColumn, int lastRow) {
        boolean win = false;

        // Don't bother if not enough tokens
        if (lastRow >= 3) {
            win = true;
            lastRow--;

            // Iterate down rows until adjTiles = 4 or win = false
            for (int adjTiles = 1; adjTiles < 4 && win == true; adjTiles++, lastRow--) {
                if (board[lastRow][lastColumn] != lastPlayer) { // If the
                    // checked tile
                    // is not last
                    // player
                    win = false;
                }
            }
        }
        return win;
    }

    /**
     * Assumes it will be called after a successful legal move has been issued.
     * 
     * @return Game win condition
     * 
     * @param lastColumn The column of the last player move
     * 
     * @param lastRow Returns the last Row of the player action.
     */
    public ArrayList<Point> checkWinState(Action lastTurn) {

        boolean win = false;
        ArrayList<Point> winList = new ArrayList<Point>();

        int player = currentPlayer; // Player to check win for.
        int lastColumn = lastTurn.getColumn();
        int lastRow = getLastRow(lastColumn);

        // Check vertical wins
        win = checkVertical(player, lastColumn, lastRow);
        if (win)
            return getVerticalWin();

        // Check horizontal wins
        if (!win) {
            win = checkHorizontal(player, lastColumn, lastRow);
        }
        if (win)
            return getHorizontalWin();

        // Check ascending diagonal wins
        if (!win) {
            win = checkAscDiagonal(player, lastColumn, lastRow);
        }
        if (win)
            return getDiagonalRightWin();

        // check descending diagonal wins
        if (!win) {
            win = checkDescDiagonal(player, lastColumn, lastRow);
        }
        if (win)
            return getDiagonalLeftWin();

        winList.clear();
        return winList;
    }

    /**
     * Method to get an instance of the back end board
     * @return
     * 		an instance of the  backend board
     */
    public int[][] getBoard() {
        int[][] clonedBoard = board.clone();
        for(int i = 0; i < ROWMAX; i++){
        	clonedBoard[i] = clonedBoard[i].clone();
        }
        
        return clonedBoard;
    }

    /**
     * Method to check for a diagonal win
     * @return
     * 		arraylist of the diagonal win positions
     */
    private ArrayList<Point> getDiagonalLeftWin() {
        ArrayList<Point> winList = new ArrayList<Point>();

        for (int row = 0; row < 3; row++) {
            for (int col = 3; col < COLMAX; col++) {
                if (board[row][col] != 0
                        && board[row][col] == board[row + 1][col - 1]
                                && board[row][col] == board[row + 2][col - 2]
                                        && board[row][col] == board[row + 3][col - 3]) {

                    winList.add(new Point(row, col));
                    winList.add(new Point(row + 1, col - 1));
                    winList.add(new Point(row + 2, col - 2));
                    winList.add(new Point(row + 3, col - 3));

                    return winList;
                }
            }
        }

        return winList;
    }

    /**
     * Method to check for a diagonal win
     * @return
     * 		arraylist of the diagonal win positions
     */
    private ArrayList<Point> getDiagonalRightWin() {
        ArrayList<Point> winList = new ArrayList<Point>();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0
                        && board[row][col] == board[row + 1][col + 1]
                                && board[row][col] == board[row + 2][col + 2]
                                        && board[row][col] == board[row + 3][col + 3]) {
                    System.out.println("winState: diag_right");

                    winList.add(new Point(row, col));
                    winList.add(new Point(row + 1, col + 1));
                    winList.add(new Point(row + 2, col + 2));
                    winList.add(new Point(row + 3, col + 3));

                    return winList;
                }
            }
        }

        return winList;
    }

    /**
     * Method to check for a horizontal win
     * @return
     * 		arraylist of the horizontal win positions
     */
    private ArrayList<Point> getHorizontalWin() {
        ArrayList<Point> winList = new ArrayList<Point>();

        // find four horizontal pieces.
        for (int row = 0; row < ROWMAX; row++) {
            for (int col = 0; col < 4; col++) {
                if (board[row][col] != 0
                        && board[row][col] == board[row][col + 1]
                                && board[row][col] == board[row][col + 2]
                                        && board[row][col] == board[row][col + 3]) {
                    winList.add(new Point(row, col));
                    winList.add(new Point(row, col + 1));
                    winList.add(new Point(row, col + 2));
                    winList.add(new Point(row, col + 3));

                    return winList;
                }
            }
        }

        return winList;
    }

    /**
     * Method to get the last row - used to determine how many rows in a column have been used
     * @param column
     * 		The column to check the row count in
     * @return
     * 		Integer - number of rows used in the current column
     */
    private int getLastRow(int column) {
        int i;
        for (i = ROWMAX - 1; i >= 0 && board[i][column] == 0; i--) {
            ; // Iterates through i until value returns a player
        }
        return i;
    }

    /**
     * Method to get the current player
     * @return
     * 		The player value (1, 2)
     */
    public int getPlayer() {
        return currentPlayer;
    }
    /** 
     * Method to get the position of a token
     * @param row
     * 		The row number of the token
     * @param col
     * 		The column number fo the token
     * @return
     * 		integer representing the current player who 'owns' that token
     */
    public int getPosition(int row, int col) {
        return board[row][col];
    }

    
    /**
     * Method to check for a vertical win
     * @return
     * 		arraylist of the vertical win positions
     */
    private ArrayList<Point> getVerticalWin() {
        ArrayList<Point> winList = new ArrayList<Point>();

        // find four Vertical pieces.
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < COLMAX; col++) {
                if (board[row][col] != 0
                        && board[row][col] == board[row + 1][col]
                                && board[row][col] == board[row + 2][col]
                                        && board[row][col] == board[row + 3][col]) {
                    winList.add(new Point(row, col));
                    winList.add(new Point(row + 1, col));
                    winList.add(new Point(row + 2, col));
                    winList.add(new Point(row + 3, col));

                    return winList;
                }
            }
        }

        return winList;
    }

    /**
     * method to check if the game board is full yet.
     * @return
     * 		Boolean: is the game board full
     */
    public boolean isFull() {
        for (int row = 0; row < ROWMAX; row++) {
            for (int col = 0; col < COLMAX; col++) {
                if (board[row][col] == 0)
                    return false;
            }
        }
        return true;
    }

    /**
     * Method to check the legality of a move
     * @param newAction
     * 		The move to be made and checked
     * @return
     * 		Boolean: whether a move is legal or not
     */
    public boolean isLegal(Action newAction) {
        if (newAction != null) {
            // check if tile is out of bounds (pos < 0, pos > 6)
            if (newAction.getColumn() < 0 || newAction.getColumn() > 6)
                return false;

            // check if the tile will 'overflow' the board
            if (board[5][newAction.getColumn()] != 0)
                return false;

            return true;
        }
        return false;
    }

    /**
     * Method to make the move.
     * @precondition
     * 		The action has been checked for legality by isLegal()
     * @param newAction
     * 		The action that is to modify the game state
     */
    public void makeMove(Action newAction) {
        System.out.println("Player " + currentPlayer + " makes a move!");
        int col;
        for (col = 0; col < ROWMAX; col++) {
            if (board[col][newAction.getColumn()] == 0) {
                break;
            }
        }
        board[col][newAction.getColumn()] = currentPlayer;
    }

    /**
     * Method to reset the current board.
     */
    public void resetBoard() {
        int empty = 0;
        for (int i = 0; i < ROWMAX; i++) {
            for (int j = 0; j < COLMAX; j++) {
                board[i][j] = empty;
            }
        }
        turnNum = 0;
        showTerminalBoard();
        setPlayer(1);
    }

    /**
     * Method to set the current player
     * @param player
     * 		The player value to set the current player value with.
     */
    public void setPlayer(int player) {
        currentPlayer = player;
    }

    /**
     * Method to show the current game state in terminal.
     * used primarily for debugging purposes
     */
    public void showTerminalBoard() {
        for (int row = ROWMAX - 1; row >= 0; row--) {
            for (int col = 0; col < COLMAX; col++) {
                System.out.print("[" + getPosition(row, col) + "]");
            }
            System.out.println();
        }
    }

    /**
     * Method to switch the current player
     */
    public void switchPlayer() {
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

}
