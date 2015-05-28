import java.awt.Point;
import java.util.ArrayList;

public class BackendBoard {

    /**The board - represented as a 2d array */
    private int[][] board;
    /**The current player number (1 or 2) */
    private int currentPlayer = 1;

    /**Dimensions of the board */
    private int ROWMAX = 6;
    private int COLMAX = 7;

    /**
     * Constructor of backendBoard
     */
    public BackendBoard() {
        board = new int[ROWMAX][COLMAX];
    }

    /**
     * Method to check ascending diagonal win states (diagonal right and up)
     * @param lastPlayer
     * 		The last player to make a move
     * @param lastColumn
     * 		The last modified column (last column with a token inserted into it).
     * @param lastRow
     * 		The last row with a token inserted into it.
     * @return
     * 		True or false depending on whether or not the added token is a winning token.
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
     * Method to check descending diagonal win states (diagonal right and down)
     * @param lastPlayer
     * 		The last player to make a move
     * @param lastColumn
     * 		The last modified column (last column with a token inserted into it).
     * @param lastRow
     * 		The last row with a token inserted into it.
     * @return
     * 		True or false depending on whether or not the added token is a winning token.
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
     * Method to check horizontal win states 
     * @param lastPlayer
     * 		The last player to make a move
     * @param lastColumn
     * 		The last modified column (last column with a token inserted into it).
     * @param lastRow
     * 		The last row with a token inserted into it.
     * @return
     * 		True or false depending on whether or not the added token is a winning token.
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
     * Method to check vertical win states 
     * @param lastPlayer
     * 		The last player to make a move
     * @param lastColumn
     * 		The last modified column (last column with a token inserted into it).
     * @param lastRow
     * 		The last row with a token inserted into it.
     * @return
     * 		True or false depending on whether or not the added token is a winning token.
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
     * Added by Sketch in "Updated Win Row Addition" Returns the win state using
     * sweet Al Gore rhythms.
     * 
     * Assumes it will be called after a succesful legal move has been issued.
     * @return 
     * 		Game win condition
     * @param 
     * 		lastColumn The column of the last player move
     * @param 
     * 		lastRow Returns the last Row of the player action.
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
     * Method to return the backend board board.
     * @return
     * 		The current board representation as a 2d array of integers.
     */
    public int[][] getBoard() {
        return board;
    }

    /**
     * Method to return the winning moves in a list
     * @return
     * 		List of the winning moves and their positions.
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
     * Method to return the winning moves in a list
     * @return
     * 		List of the winning moves and their positions.
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
     * Method to return the winning moves in a list
     * @return
     * 		List of the winning moves and their positions.
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
     * @return 
     * 		Row of the top token of any column within the bounds of the game
     * 
     * @precondition 
     * 		0 <= column <= 6
     */
    private int getLastRow(int column) {
        int i;
        for (i = ROWMAX - 1; i >= 0 && board[i][column] == 0; i--) {
            ; // Iterates through i until value returns a player
        }
        return i;
    }

    /**
     * Method to return the current player turn.
     * @return
     * 		Return the current player whose turn it is.
     */
    public int getPlayer() {
        return currentPlayer;
    }

    /**
     * Method to return player in position.
     * @param row
     * 		Target row.
     * @param col
     * 		Target col.
     * @return
     * 		Player or none who has taken that position.
     */
    public int getPosition(int row, int col) {
        return board[row][col];
    }

    /**
     * Method to return the winning moves in a list
     * @return
     * 		List of the winning moves and their positions.
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
     * Method to check if board is full
     * @return
     * 		Boolean: True if board has all places taken. False otherwise.
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
     * Method to check if a move is legal. Namely to prevent ut of bounds plays.
     * @param newAction
     * 		The action to be checked.
     * @return
     * 		Boolean: True if move is legal. False otherwise.
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
     * Method to update the backend board with a new move. 
     * @precondition
     * 		Move legality has already been checked. 
     * @param newAction
     * 		The action to be checked.
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
     * Method to reset the board array.
     */
    public void resetBoard() {
        int empty = 0;
        for (int i = 0; i < ROWMAX; i++) {
            for (int j = 0; j < COLMAX; j++) {
                board[i][j] = empty;
            }
        }
        showTerminalBoard();
        setPlayer(1);
    }

    /**
     * Method to set player value.
     * @param player
     * 		The player with which to set value.
     */
    public void setPlayer(int player) {
        currentPlayer = player;
    }

    /**
     * A method to show board representation in terminal. Useful for debugging and ASCII fans everywhere.
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
     * Method to switch the player value.
     */
    public void switchPlayer() {
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

}
