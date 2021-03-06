import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Token extends JLabel {
    private static final long serialVersionUID = 1L;
    private int xPos;
    private int yPos;
    private int player = 0; // Default player is no player

    public Token(int x, int y, ImageIcon image) {
        xPos = x;
        yPos = y;
        player = 0;
        setIcon(image);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getPlayer() {
        return player;
    }

    public void setPlayer(int newPlayer) {
        player = newPlayer;
    }

    // this is for the listener
    // XXX it should also be mentioned that this is for debugging.
    public void getValue() {
        System.out.println("mouseclick");
        System.out.println("Xpos: " + this.getXPos());
        System.out.println("Ypos: " + this.getYPos());
        System.out.println("Player: " + this.getPlayer());
    }

}
