import java.util.PriorityQueue;

public class AI extends Opponent {
    private BackendBoard backendBoard;
    private DIFFICULTY difficulty;

    public AI(DIFFICULTY difficulty, BackendBoard backendBoard) {
        this.difficulty = difficulty;
        this.backendBoard = backendBoard;
    }

    @Override
    public Action getMove() {
        switch (difficulty) {
            case EASY:
                return randomMove();
            case MEDIUM:
                return informedMove();
            case HARD:
                return informedMove();
            default:
                return informedMove();
        }
    }

    private Action informedMove() {
        PriorityQueue<State> q = new PriorityQueue<State>();

        State s = new State(backendBoard);

        for (int i = 0; i < 7; i++) {
            State newState = new State(i, s);
            q.add(newState);
        }

        s = q.poll();

        return s.getAction();
    }

    @Override
    public boolean isAI() {
        return true;
    }

    private Action randomMove() {
        // need a clever way of determining this once we have starting turn
        // choice
        int playerNumber = 2;

        // Math.random() returns a number between 0 and 1, mult by 10 and mod by
        // 7
        int column = (int) (10 * Math.random() % 7);
        Action move = new Action(playerNumber, column);

        while (!backendBoard.isLegal(move)) {
            column = (int) (10 * Math.random() % 7);
            move = new Action(playerNumber, column);
        }

        return move;
    }
}
