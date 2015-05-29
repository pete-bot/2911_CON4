
/**
 * 
 * @author WOBCON4
 * Class that represents the user/opponent (both human and AI) move. 
 *
 */
public class Action {

	/**The integer value of the column where the move is made. */
    private int move; // Column to supply a movement

    /**
     * Constructor for action object.
     * @param newColumnChoice
     * 		An int that represents the column choice made by the user/AI
     */
    public Action(int newColumnChoice) {
        this.move = newColumnChoice;
    }

    /**
     * 
     * @return
     * 		Returns the player move.
     */
    public int getColumn() {
        return this.move;
    }

    /**
     * Sets the move for an action object.
     * @param newCol
     * 		The integer choice of column
     */
    public void setColumn(int newCol) {
        move = newCol;
    }

    /**
     * Debug method to print the action to the terminal.
     */
    public void showAction() {
        System.out.println("action_move:" + move);
    }
    
    /**
     * Clone method override for action object.
     */
    @Override
    public Action clone(){
    	return new Action(move);
    }

}
