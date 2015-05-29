
/**
 * 
 * @author WOBCON4
 *	
 *	Abstract class for the Opponent. This allows for the 'plug and play' of AI or human player
 *
 */
public abstract class Opponent {
    public abstract Action getMove();

    public abstract boolean isAI();
}