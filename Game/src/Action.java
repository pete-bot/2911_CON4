/*
 * not sure if this is necessary - possibly useful to encapsulate the 'action'
 * of a player useful to check legality of move etc. This function did much more
 * than it does not. As refactoring as happened, this got reduced to purely
 * being a column. This is a pointless class now, but is wired up too thoroughly
 * at this stage to do clean and easy refactoring.
 */
/**
 * 
 * @author WOBCON
 * 	class to represent a player move. Used by both human and AI players. 
 *
 */
public class Action {
	
	/** An integer value representing the column choice of the player. [0,6]  */
    private int move; // Column to supply a movement

    /**
     * 
     * @param newColumnChoice
     * 		the integer representation of the player column choice. in range [0,6]
     */
    public Action(int newColumnChoice) {
        this.move = newColumnChoice;
    }

    /**
     * 
     * @return
     * 		returns the player move for this action.
     */
    public int getColumn() {
        return this.move;
    }

    /**
     * 
     * @param newCol
     * 		sets the column choice for an action.
     */
    public void setColumn(int newCol) {
        move = newCol;
    }

    /**
     * Prints to stdout the move for an action. Used for debugging.
     */
    public void showAction() {
        System.out.println("action_move:" + move);
    }

}
