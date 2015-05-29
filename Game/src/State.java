/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 *	The State class used by the AI to make the best decision about which move to make next.
 */
public class State implements Comparable<State> {

	/**Rank for the state - make it easier to select the best state */
    private int stateRank;
    /**The Action associated with this stae. */
    private Action thisAction;
    /**The backend board representation associated with this state. */
    private BackendBoard boardRep;
    /**AI difficulty associated with this state. */
    private Difficulty difficulty;

    
    /**
     * Starting state constructor
     * @param backendBoard
     * 		Representation of the current backend board,
     */
    public State(BackendBoard backendBoard) {
        boardRep = backendBoard;
    }

    /**
     * Choice state constructor.
     * @param column
     * 		The column associated with this state.
     * @param prevState
     * 		The parent/predecessor state.
     * @param difficulty
     * 		The difficulty of the AI.
     */
    public State(int column, State prevState, Difficulty difficulty) {
        boardRep = prevState.getBoard();
        this.difficulty = difficulty;
        stateRank = generateScore(column);
        thisAction = generateAction(column);

    }

    /**
     * Method to check the AI score in the ascending Diag direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a score for the ascending diagonal move for the AI
     */
    private int checkAscDiagonal(int lastPlayer, int lastColumn, int[][] board) {

        // check if slots are full (illegal move)
        if (board[5][lastColumn] == 1 || board[5][lastColumn] == 2)
            return -100;

        int lastRow = 5;
        // find lastRow
        while (lastRow >= 0) {
            if (board[lastRow][lastColumn] == 1
                    || board[lastRow][lastColumn] == 2) {
                break;
            }
            lastRow--;
        }

        // increment to the spot above the first tile
        lastRow++;

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
            while (rightColIndex < 7 && rightRowIndex < 6
                    && board[rightRowIndex][rightColIndex] == lastPlayer) {
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex++;
            }
        }

        return adjacentTiles;
    }

    /*
     * Check descending diagonal wins Added by Sketch in
     * "Updated win condition patch"
     */
    
    /**
     * Method to check the AI score in the descending Diag direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a score for the descending diagonal move for the AI
     */
    private int checkDescDiagonal(int lastPlayer, int lastColumn, int[][] board) {
        // check if slots are full (illegal move)

        if (board[5][lastColumn] == 1 || board[5][lastColumn] == 2)
            return -100;

        int lastRow = 5;
        // find lastRow
        while (lastRow >= 0) {
            if (board[lastRow][lastColumn] == 1
                    || board[lastRow][lastColumn] == 2) {
                break;
            }
            lastRow--;
        }

        // increment to the spot above the first tile
        lastRow++;

        int adjacentTiles = 1;
        int leftColIndex = lastColumn - 1;
        int leftRowIndex = lastRow + 1;

        int rightColIndex = lastColumn + 1;
        int rightRowIndex = lastRow - 1;

        // Left iteration from last node, checking if tile is owned by player
        while (leftColIndex >= 0 && leftRowIndex < 6
                && board[leftRowIndex][leftColIndex] == lastPlayer) {
            adjacentTiles++;
            leftColIndex--;
            leftRowIndex++;
        }

        // If we haven't won already
        if (adjacentTiles < 4) {
            // Right iteration
            while (rightColIndex < 7 && rightRowIndex >= 0
                    && board[rightRowIndex][rightColIndex] == lastPlayer) {
                adjacentTiles++;
                rightColIndex++;
                rightRowIndex--;
            }
        }
        return adjacentTiles;
    }
    
    /**
     * Method to check the AI score in the horizontal direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a score for the horizontal move for the AI
     */
    private int checkHorizontal(int[][] board, int column, int lastPlayer) {

        int leftadjacentTiles = 1;
        int rightadjacentTiles = 1;

        int leftColIndex = column - 1;
        int rightColIndex = column + 1;

        int lastRow = 5;

        // check if slots are full (illegal move)
        if (board[5][column] == 1 || board[5][column] == 2)
            return -100;

        while (lastRow >= 0) {
            if (board[lastRow][column] == 1 || board[lastRow][column] == 2) {
                break;
            }
            lastRow--;
        }

        // increment to the spot above the first tile
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
        return leftadjacentTiles > rightadjacentTiles ? leftadjacentTiles
                : rightadjacentTiles;
    }

    /**
     * Method to check the AI score in the vertical direction
     * @param lastPlayer
     * 		The last player to make a move.
     * @param lastColumn
     * 		The last column to take a token.
     * @param board
     * 		The backend board representation
     * @return
     * 		Return a score for the vertical move for the AI
     */
    private int checkVertical(int[][] board, int column) {
        int x = 0;
        int row = 5;

        if (board[5][column] == 1 || board[5][column] == 2)
            return -100;

        int player = 0;

        while (row >= 0) {
            if (board[row][column] != 0) {
                player = board[row][column];
                break;
            }
            row--;
        }

        while (row >= 0) {
            if (board[row][column] == player) {
                x++;
            } else if (board[row][column] != player) {
                break;
            }
            row--;
        }

        return x;
    }

    /**
     * Override of compare to method. THis will compare the H value of each state
     */
    @Override
    public int compareTo(State o) {
        return o.getHValue() - getHValue();
    }

    /**
     * Method to generate an action for the next state.
     * @param column
     * 		Column choice for next action.
     * @return
     * 		Returns an action for the next state.
     */
    private Action generateAction(int column) {
        Action a = new Action(column);
        return a;
    }

    /**
     * Method that considers the diagonal and vertical, horizontal scores to generate a total H score.
     * @param column
     * 		The column that is currently being considered.
     * @return
     * 		Returns a total H score.
     */
    public int generateScore(int column) {
        int[][] board = boardRep.getBoard();

        int vertScore = checkVertical(board, column);
        int hortScore1 = checkHorizontal(board, column, 1);

        if (Difficulty.MEDIUM == this.difficulty) {
            checkHorizontal(board, column, 2);
            return vertScore > hortScore1 ? vertScore : hortScore1;

        } else { // Else Difficulty.HARD
            int diagUp = checkDescDiagonal(1, column, board);
            int diagDown = checkAscDiagonal(1, column, board);

            // find best score
            int maxDiag = (diagUp > diagDown) ? diagUp : diagDown;
            int verHorScore = (vertScore > hortScore1) ? vertScore : hortScore1;
            // System.out.println("diagUP:"+diagUp);
            // System.out.println("diagDOWN:"+diagDown);
            // return best score
            return maxDiag > verHorScore ? maxDiag : verHorScore;
        }
    }

    /**
     * Return the action for the current state
     */
    public Action getAction() {
        return thisAction;
    }

    /**
     * Get the board rep of the current state.
     * @return
     * 		The board rep of the current state
     */
    private BackendBoard getBoard() {
        return boardRep;
    }
    /**
     * Get the current state H value.
     * @return
     * 		Return the current states H value.
     */
    private int getHValue() {
        return stateRank;
    }
}
