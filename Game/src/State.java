public class State implements Comparable<State> {

    private int stateRank;
    private Action thisAction;
    private BackendBoard boardRep;
    private Difficulty difficulty;

    /**
     * Constructor for starting state
     * @param backendBoard
     * 		Instance of backend board - represents game state.
     */
    public State(BackendBoard backendBoard) {
        boardRep = backendBoard;
    }

    /**
     * Method to create a choice state
     * @param column
     * 		Column under scrutiny
     * @param prevState
     * 		Previous state (predecessor)
     * @param difficulty
     * 		Difficulty level - determines the thoroughness of search 
     */
    public State(int column, State prevState, Difficulty difficulty) {
        boardRep = prevState.getBoard();
        this.difficulty = difficulty;
        stateRank = generateScore(column);
        thisAction = generateAction(column);

    }

    /**
     * Method to check AI criteria for determining the best course of action
     * @param lastPlayer
     * 		Player who last took turn
     * @param lastColumn
     * 		the last col tobe modified
     * @param board
     * 		The game board
     * @return
     * 		return the 'score' of this position
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


    /**
     * Method to check AI criteria for determining the best course of action
     * @param lastPlayer
     * 		Player who last took turn
     * @param lastColumn
     * 		the last col tobe modified
     * @param board
     * 		The game board
     * @return
     * 		return the 'score' of this position
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
     * Method to check AI criteria for determining the best course of action
     * @param lastPlayer
     * 		Player who last took turn
     * @param lastColumn
     * 		the last col tobe modified
     * @param board
     * 		The game board
     * @return
     * 		return the 'score' of this position
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
     * Method to check AI criteria for determining the best course of action
     * @param lastPlayer
     * 		Player who last took turn
     * @param lastColumn
     * 		the last col tobe modified
     * @param board
     * 		The game board
     * @return
     * 		return the 'score' of this position
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
     * Method to provide a comparison for AI calcs.
     */
    @Override
    public int compareTo(State o) {
        return o.getHValue() - getHValue();
    }

    /**
     * Method to create an action based on input
     * @param column
     * 		Action column.
     * @return
     * 		Action object
     */
    private Action generateAction(int column) {
        Action a = new Action(column);
        return a;
    }
    
    /**
     * Method to generate score - this allows AI to determine the best move
     * @param column
     * 		Column to be scrutinised.
     * @return
     * 		The score calculation
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
     * Method t return action of this state
     * @return
     * 		The action of this state
     */
    public Action getAction() {
        return thisAction;
    }

    /**
     * Method to return the backend board
     * @return
     * 		The backend board. Gah, stop asking!
     */
    private BackendBoard getBoard() {
        return boardRep;
    }

    /**
     * Get heuristic value
     * @return
     * 		The heuristic value fo the AI calculation.
     */
    private int getHValue() {
        return stateRank;
    }
}
