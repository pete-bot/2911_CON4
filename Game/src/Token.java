import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Token extends JLabel {
    private static final long serialVersionUID = 1L;
    
    /**Position of token. */
    private int xPos;
    private int yPos;
    
    /**Player whome th etoken belongs to (0 - unowned, 1, 2) */
    private int player = 0; // Default player is no player

    /**
     * Constructor
     * @param x
     * 		X position in grid.
     * @param y
     * 		Y position in grid.
     * @param image
     * 		The icon image used. Blank is default.
     */
    public Token(int x, int y, ImageIcon image) {
        xPos = x;
        yPos = y;
        player = 0;
        setIcon(image);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    /**
     * 
     * @return
     * 		X position of token.
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * 	
     * @return
     * 		Y position of token.
     */
    public int getYPos() {
        return yPos;
    }
    
    /**
     * 
     * @return
     * 		The player to whom the token belongs
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Method to set token ownership
     * @param newPlayer
     * 		This is the value of the player who owns the token.
     */
    public void setPlayer(int newPlayer) {
        player = newPlayer;
    }


    /**
     * Method used for debugging - print information about token to terminal.
     */
    public void getValue() {
        System.out.println("mouseclick");
        System.out.println("Xpos: " + this.getXPos());
        System.out.println("Ypos: " + this.getYPos());
        System.out.println("Player: " + this.getPlayer());
    }

}
