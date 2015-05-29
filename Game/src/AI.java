import java.util.PriorityQueue;

/**
 * 
 * @author WOBCON4
 * 		Interface class (?) for the AI.
 *
 */
public class AI extends Opponent {
    private BackendBoard backendBoard;
    private Difficulty difficulty;

    /**
     * 
     * @param difficulty
     * 		The difficulty setting for the AI. Represented with enum EASY, MEDIUM, HARD
     * @param backendBoard
     * 		representation of the gameboard - used to determine the current game state (player positions etc)
     */
    public AI(Difficulty difficulty, BackendBoard backendBoard) {
        this.difficulty = difficulty;
        this.backendBoard = backendBoard;
    }

    
    /**
     * Gets an action object from the AI - this represents the 'move' made by the AI.
     */
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

    /**
     * 
     * @param difficulty
     * 		The difficulty level of the AI. Represented with enum EASY, MEDIUM, HARD
     * @return
     * 		Returns an action object that represents the AI's choice of column for its next move.
     */
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
    
    /**
     * Returns whether or not there is a human player /AI.
     */
    @Override
    public boolean isAI() {
        return true;
    }
    
    
    /**
     * 
     * @return
     * 		returns a random move. (drunk AI).
     */
    private Action randomMove() {
        int randomColumn = (int) (10 * Math.random() % 7);
        Action move = new Action(randomColumn);
        return move;
    }
}
