public class State implements Comparable<State> {

    private int stateRank;
    private Action thisAction;
    private BackendBoard boardRep;

    // starting state constructor
    public State(BackendBoard backendBoard) {
        this.stateRank = 0;
        this.thisAction = null;
        this.boardRep = backendBoard;
    }

    // choice constructor
    public State(int column, State prevState) {
        this.boardRep = prevState.getBoard();
        this.stateRank = generateScore(column);
        this.thisAction = generateAction(column);
        System.out.println("COLUMN:" + thisAction.getColumn());
        System.out.println("RANK:" + stateRank);
    }

    private int checkHorizontal(int[][] board, int column, int lastPlayer) {

        int leftadjacentTiles = 1;
        int rightadjacentTiles = 1;

        int leftColIndex = column - 1;
        int rightColIndex = column + 1;

        int lastRow = 5;

        // check if slots are full (illegal move)
        if (board[5][column] == 1 || board[5][column] == 2) {
            return -100;
        }

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

    private int checkVertical(int[][] board, int column) {
        int x = 0;
        int row = 5;

        if (board[5][column] == 1 || board[5][column] == 2) {
            return -100;
        }

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

    @Override
    public int compareTo(State o) {
        return o.getHValue() - getHValue();
    }

    private Action generateAction(int column) {
        Action a = new Action(2, column);
        return a;
    }

    public int generateScore(int column) {
        int[][] board = this.boardRep.getBoard();

        int vertScore = checkVertical(board, column);
        int hortScore1 = checkHorizontal(board, column, 1);
        int hortScore2 = checkHorizontal(board, column, 2);

        // Max horz in da house?
        @SuppressWarnings("unused")
        int maxhorz = hortScore1 > hortScore2 ? hortScore1 : hortScore2;
        return vertScore > hortScore1 ? vertScore : hortScore1;
    }

    public Action getAction() {
        return thisAction;
    }

    private BackendBoard getBoard() {
        return boardRep;
    }

    private int getHValue() {
        return stateRank;
    }
}
