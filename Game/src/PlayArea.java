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
    private Dimension size = new Dimension(750, 650);
    private final int rows = 6;
    private final int cols = 7;
    private final Token[] gameTokens = new Token[42];
    private ImageIcon blankTokenIcon;
    private Color defaultColor = new Color(255, 255, 235, 100);

    private GameAssets assets;
    
    // how PROPORTIONATE the playArea will be
    // according to the height of the main window.
    // change to adjust proportionality.
    private double propPercentage = 0.80;
    private Window mainWindow;

    public PlayArea(Dimension boardSize, GameAssets assets, Window mainWindow) {

        // board transparency settings - may need to change depending on colour
        // prefs
        setOpaque(false);
        setBackground(defaultColor);

        setBorder(BorderFactory.createLineBorder(Color.black));
        setLayout(new GridLayout(rows, cols));
        minSize = boardSize;
        this.assets = assets;
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
        int toReturnDim = (int) Math.round(mainWindow.getHeight()
                * propPercentage);

        // board is square, so both sides must be same length
        size = new Dimension(toReturnDim, toReturnDim);
        return size;
    }

    @Override
    public Dimension getSize() {
        return size;
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
                Token token = new Token(currX, currY);
                token.setIcon(assets.getResizedAsset("sample_token.png", minSize.width, minSize.height));
                token.setOpaque(false);
                Dimension iconSize = new Dimension(100, 100);
                token.setPreferredSize(iconSize);
                add(token, new Integer(2));
                gameTokens[i] = token;
                i++;
            }
        }
    }
    
    /*
     * Method that updates the Token icons based on a new board
     * Beware, this method is currently broken, in the sense that it colors the tokens in
     * a flipped geometry board. 
     * 
     * @precondition		newBoard is a int[rows][cols] array
     */
//    public void writeBoard( int[][] newBoard ){
//    	int i = 0;
//		int currY = 0;
//		int currX = 0;
//		for(currY = 0; currY < rows; currY++){
//			for(currX = 0; currX < cols; currX++){
//				//Logic for determining the token icon
//				if(newBoard[currY][currX] == 0){			//For Empty Tokens
//					gameTokens[i].setIcon(assets.getResizedAsset("sample_token.png", minSize.width, minSize.height));
//				} else if(newBoard[currY][currX] == 1){		//For Player 1 Tokens
//					gameTokens[i].setIcon(assets.getResizedAsset("sample_token_red.png", minSize.width, minSize.height));
//				} else {									//For Player 2 Tokens
//					gameTokens[i].setIcon(assets.getResizedAsset("sample_token_yellow.png", minSize.width, minSize.height));
//				}
//				
//				i++;
//			}
//		}
//    }
    
    // render the current board in terminal
    // Maybe 'showTerminalBoard' is better...
    public void writeBoard(int[][] newBoard) {
    	int i = 0;
        for (int row = rows - 1; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
				//Logic for determining the token icon
				if(newBoard[row][col] == 0){			//For Empty Tokens
					gameTokens[i].setIcon(assets.getResizedAsset("sample_token.png", minSize.width, minSize.height));
				} else if(newBoard[row][col] == 1){		//For Player 1 Tokens
					gameTokens[i].setIcon(assets.getResizedAsset("sample_token_red.png", minSize.width, minSize.height));
				} else {									//For Player 2 Tokens
					gameTokens[i].setIcon(assets.getResizedAsset("sample_token_yellow.png", minSize.width, minSize.height));
				}
				
				i++;
            }
            
        }
    }
}
