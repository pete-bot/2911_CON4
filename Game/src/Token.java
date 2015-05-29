import javax.swing.JLabel;

/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 * Token class - stores information about the tokens, inc position and icon etc.
 *
 */
public class Token extends JLabel {
    /**Standard serialisation */
	private static final long serialVersionUID = 1L;
    /**X and Y pos of each token */
	private int xPos;
    private int yPos;
    /**Player who owns the token */
    private int player = 0; // Default player is no player

    /**
     * Token class constructor
     * @param x
     * 		The X pos on the board.
     * @param y
     * 		The Y pos on the board
     */
    public Token(int x, int y) {
        xPos = x;
        yPos = y;
        player = 0;
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * Method to get the owner of a token
     * @return
     * 		Returns the player number associated with each token,
     */
    public int getPlayer() {
        return player;
    }


    /**
     * Debug method 
     * returns the values associated with a specific token.
     */
    public void getValue() {
        System.out.println("mouseclick");
        System.out.println("Xpos: " + this.getXPos());
        System.out.println("Ypos: " + this.getYPos());
        System.out.println("Player: " + this.getPlayer());
    }

    /**
     * Method to get the xPos of token
     * @return
     * 		The Xpos of the current token.
     */
    public int getXPos() {
        return xPos;
    }
    
    /**
     * Method to get the yPos of token
     * @return
     * 		The Ypos of the current token.
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Set the player/owner of the current token
     * @param newPlayer
     * 		The player who will be assigned this token.
     */
    public void setPlayer(int newPlayer) {
        player = newPlayer;
    }

}
