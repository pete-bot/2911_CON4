import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayArea extends JLabel {

    private static final long serialVersionUID = 1L;
    
    /**Dimensions for game panel. Minimum stops resizing to un-supported sizes.*/
    private Dimension minSize = new Dimension(750, 650);
    private Dimension size = new Dimension(750, 650);
    
    /**Grid board dimensions (rows, cols) */
    private final int rows = 6;
    private final int cols = 7;
    private final Token[] gameTokens = new Token[42];
    
    /**Image icon for blank token */
    private ImageIcon blankTokenIcon;
    
    /**Default grid colour. */
    private Color defaultColor = new Color(255, 255, 235, 100);

    // how PROPORTIONATE the playArea will be
    // according to the height of the main window.
    // change to adjust proportionality.
    private double propPercentage = 0.80;
    private Window mainWindow;

    /**
     * Constructor for the playArea - the area where the game drid and tokens are displayed.
     * @param blankTokenIcon
     * 		The default token - representing an un-played slot.
     * @param mainWindow
     * 		The main window instance.
     */
    public PlayArea(ImageIcon blankTokenIcon, Window mainWindow) {

        // board transparency settings - may need to change depending on colour
        // prefs
        setOpaque(false);
        setBackground(defaultColor);

        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(rows, cols));
        this.blankTokenIcon = blankTokenIcon;
        this.mainWindow = mainWindow;
        populateWithTokens();
    }

    /**
     * Return the minimum dimension for the board.
     */
    @Override
    public Dimension getMinimumSize() {
        return minSize;
    }

    /**
     * Return the preferred dimension for the board.
    */
    @Override
    public Dimension getPreferredSize() {

        // scales with respect to height.
        int toReturnDim = (int) Math.round(mainWindow.getHeight()
                * propPercentage);

        // board is square, so both sides must be same length
        size = new Dimension(toReturnDim, toReturnDim);
        return size;
    }

    /**
     * Return the current size dimension.
     */
    @Override
    public Dimension getSize() {
        return size;
    }
    
    /**
     * Method to get the grid token array.
     * @return
     * 		return the token array.
     */
    public Token[] getTokens() {
        return gameTokens;
    }

    /**
     * Required override to fix issues with the transparency of certain JPanels. Prior to this
     * override, mousing over a transparent panel would re-render its transparent effect, over the top
     * of the previous effect. It was very visually unappealing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * A method to populate the grid board with tokens, their spacing and their icon representation.
     */
    private void populateWithTokens() {
        int i = 0, currY = 0, currX = 0;
        for (currY = 0; currY < rows; currY++) {
            for (currX = 0; currX < cols; currX++) {
                Token token = new Token(currX, currY, blankTokenIcon);
                token.setOpaque(false);
                Dimension iconSize = new Dimension(100, 100);
                token.setPreferredSize(iconSize);
                add(token, new Integer(2));
                gameTokens[i] = token;
                i++;
            }
        }
    }
}
