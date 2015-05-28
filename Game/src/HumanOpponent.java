public class HumanOpponent extends Opponent {

    
	/**
	 * As a human player in this current status is a 'pass and play', there is\
	 * no move to make. Therefore, control gets handed back over to frontend
     * board.
	 */
	@Override
    public Action getMove() {
        return null;
    }

	/**
	 * Return that the current opposition is in fact a human. A gross, smelly, filthy probably lying human.
	 */
    @Override
    public boolean isAI() {
        return false;
    }

}
