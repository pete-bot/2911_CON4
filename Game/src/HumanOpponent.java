
/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 *
 *	Class that represents the human player as an opponent
 *
 */
public class HumanOpponent extends Opponent {
	
	/**
	 * Returns a null move - this is to allow the human player an opportunity to play
	 */
    @Override
    public Action getMove() {
        return null;
    }

    /**
     * @return
     * 		Returns false to AI query,
     */
    @Override
    public boolean isAI() {
        return false;
    }

}
