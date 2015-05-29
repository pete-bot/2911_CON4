import java.util.PriorityQueue;

public class AI extends Opponent {
    private BackendBoard backendBoard;
    private Difficulty difficulty;

    public AI(Difficulty difficulty, BackendBoard backendBoard) {
        this.difficulty = difficulty;
        this.backendBoard = backendBoard;
    }

    @Override
    public Action getMove() {
        switch (difficulty) {
        case EASY:
            return randomMove();
        case MEDIUM:
            return informedMove(Difficulty.MEDIUM);
        case HARD:
            return informedMove(Difficulty.HARD);
        default:
            return informedMove(Difficulty.MEDIUM);
        }
    }

    private Action informedMove(Difficulty difficulty) {
        PriorityQueue<State> q = new PriorityQueue<State>();

        State firstState = new State(backendBoard);

        for (int i = 0; i < 7; i++) {
            State newState = new State(i, firstState, difficulty);
            q.add(newState);
        }

        State finalState = q.poll();

        return finalState.getAction();
    }

    @Override
    public boolean isAI() {
        return true;
    }

    private Action randomMove() {
        int randomColumn = (int) (10 * Math.random() % 7);
        Action move = new Action(randomColumn);
        return move;
    }
}
