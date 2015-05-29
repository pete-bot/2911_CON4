/*
 * not sure if this is necessary - possibly useful to encapsulate the 'action'
 * of a player useful to check legality of move etc. This function did much more
 * than it does not. As refactoring as happened, this got reduced to purely
 * being a column. This is a pointless class now, but is wired up too thoroughly
 * at this stage to do clean and easy refactoring.
 */

public class Action {

    private int move; // Column to supply a movement

    public Action(int newColumnChoice) {
        this.move = newColumnChoice;
    }

    // return the player move
    public int getColumn() {
        return this.move;
    }

    public void setColumn(int newCol) {
        move = newCol;
    }

    public void showAction() {
        System.out.println("action_move:" + move);
    }
    
    @Override
    public Action clone(){
    	return new Action(move);
    }

}
