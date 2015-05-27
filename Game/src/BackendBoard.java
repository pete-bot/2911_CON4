import java.awt.Point;
import java.util.ArrayList;

public class BackendBoard {

    // this is the board representation - very simple and light
    // we store the position of player_1 as 1, player_2 as 2
    private int[][] board;
    private int currentPlayer = 1;

    // need to make these ENUMS
    private int ROWMAX = 6;
    private int COLMAX = 7;

    public BackendBoard() {
        board = new int[ROWMAX][COLMAX];
    }

    /*
     * Check ascending diagonal wins Added by Sketch in
     * "Updated win condition patch"
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

    /*
     * Check descending diagonal wins Added by Sketch in
     * "Updated win condition patch"
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

    /*
     * Check horizontal wins Added by Sketch in "Updated win condition patch"
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

    /*
     * Check vertical wins Added by Sketch in "Updated Win Row Addition"
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

    /*
     * Added by Sketch in "Updated Win Row Addition" Returns the win state using
     * sweet Al Gore rhythms.
     * 
     * Assumes it will be called after a succesful legal move has been issued.
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

    public int[][] getBoard() {
        return board;
    }

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

    /*
     * Added by Sketch in "Updated win condition patch" Change to public if
     * required outside the scope, however I would advise creating another an
     * interface function unique to the caller to preserve abstraction.
     * 
     * @return Row of the top token of any column within the bounds of the game
     * 
     * @precondition 0 <= column <= 6
     */
    private int getLastRow(int column) {
        int i;
        for (i = ROWMAX - 1; i >= 0 && board[i][column] == 0; i--) {
            ; // Iterates through i until value returns a player
        }
        return i;
    }

    public int getPlayer() {
        return currentPlayer;
    }

    public int getPosition(int row, int col) {
        return board[row][col];
    }

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

    public boolean isFull() {
        for (int row = 0; row < ROWMAX; row++) {
            for (int col = 0; col < COLMAX; col++) {
                if (board[row][col] == 0)
                    return false;
            }
        }
        return true;
    }

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

    // this method assumes that the isLegal method has been called and has been
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

    // To be called from the menu
    public void setPlayer(int player) {
        currentPlayer = player;
    }

    // render the current board in terminal
    // Maybe 'showTerminalBoard' is better...
    public void showTerminalBoard() {
        for (int row = ROWMAX - 1; row >= 0; row--) {
            for (int col = 0; col < COLMAX; col++) {
                System.out.print("[" + getPosition(row, col) + "]");
            }
            System.out.println();
        }
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer == 1 ? 2 : 1;
    }

}
