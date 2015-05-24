import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class PlayArea extends JLabel {

    private static final long serialVersionUID = 1L;
    private Dimension minSize;
    private final int rows = 6;
    private final int cols = 7;
    private final Token[] gameTokens = new Token[42];
    private ImageIcon blankTokenIcon;
    private Color defaultColor = new Color(127, 127, 127, 127);

    
    
    
    public PlayArea(Color color, Dimension dimension, ImageIcon blankTokenIcon) {
    	
    	// board transparency settings - may need to change depending on colour prefs
    	setOpaque(false);
    	setBackground( new Color(127, 127, 127, 127) );
    	
    	setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(rows,cols));
        minSize = dimension;
        this.blankTokenIcon = blankTokenIcon;
        populateWithTokens();
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }

    public Token[] getTokens() {
        return gameTokens;
    }

    private void populateWithTokens() {
        int i = 0, currY = 0, currX = 0;
        for (currY = 0; currY < rows; currY++) {
            for(currX = 0; currX < cols; currX++){
                Token token = new Token(currX, currY, blankTokenIcon);
                token.setOpaque(false);
                Dimension iconSize = new Dimension(100,100);
                token.setPreferredSize(iconSize);
                add(token, new Integer(2));
                gameTokens[i] = token;
                i++;
            }
        }
    }

    
    // fix tranparent panel issue
    protected void paintComponent(Graphics g){
    	g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
