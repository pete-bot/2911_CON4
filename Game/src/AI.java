import java.util.PriorityQueue;
/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 *	Class to store and control the AI
 */
public class AI extends Opponent {
    /**Representation of the backend board */
	private BackendBoard backendBoard;
	/**The difficulty value for the AI */
    private Difficulty difficulty;

    /**
     * The class constructor
     * @param difficulty
     * 		the required difficulty value.
     * @param backendBoard
     * 		The backend board representation.
     */
    public AI(Difficulty difficulty, BackendBoard backendBoard) {
        this.difficulty = difficulty;
        this.backendBoard = backendBoard;
    }

    /**
     * Gets the AI move and returns an action.
     * @return
     * 		The action that the AI has chosen to make.
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
     * The method that selects the best method for the current board state. 
     * @param difficulty
     * 		The difficulty value for the current AI
     * @return
     * 		The return action - selected as the best choice for the current game state.
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
     * Method to return if the AI is an AI.
     */
    @Override
    public boolean isAI() {
        return true;
    }
    
    /**
     * Method to return a random (drunk move)
     * @return
     */
    private Action randomMove() {
        int randomColumn = (int) (10 * Math.random() % 7);
        Action move = new Action(randomColumn);
        return move;
    }
}
