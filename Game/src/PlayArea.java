import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PlayArea extends JLabel {

    private static final long serialVersionUID = 1L;
    private Dimension minSize;
    private final int rows = 6;
    private final int cols = 7;
    private final Token[] gameTokens = new Token[42];
    private ImageIcon blankTokenIcon;
    private Color defaultColor = new Color(255, 255, 235, 100);
    
    
    // how PROPORTIONATE the playArea will be
    // according to the height of the main window.
    // change to adjust proportionality.
    private double propPercentage = 0.80;
    private Window mainWindow;
    
    public PlayArea(Dimension dimension, ImageIcon blankTokenIcon, Window mainWindow) {

        // board transparency settings - may need to change depending on colour
        // prefs
        setOpaque(false);
        setBackground(defaultColor);

        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(rows, cols));
        minSize = dimension;
        this.blankTokenIcon = blankTokenIcon;
        this.mainWindow = mainWindow;
        populateWithTokens();
    }

    @Override
    public Dimension getMinimumSize() {
        return minSize;
    }

    @Override
    public Dimension getPreferredSize() {
    	
    	// scales with respect to height.
    	int toReturnDim = (int) Math.round(mainWindow.getHeight() * propPercentage);
    	
    	//board is square, so both sides must be same length
    	
    	return new Dimension(toReturnDim,toReturnDim);
    }

    public Token[] getTokens() {
        return gameTokens;
    }

    // fix tranparent panel issue
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

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
