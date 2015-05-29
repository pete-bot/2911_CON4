import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 * Class that contains the game play grid and action listeners to facilitate the use thereof. 
 */
public class PlayArea extends JLabel {

	/**Standard Serialisation */
    private static final long serialVersionUID = 1L;
    /**Min size  for the gridboard */
    private Dimension minSize;
    
    /** Dimensions for the play area*/
    private Dimension size = new Dimension(750, 650);
    /** board dimensions*/
    private final int rows = 6;
    private final int cols = 7;
    /** token array*/
    private final Token[] gameTokens = new Token[42];
    
    /**Default colour */
    private Color defaultColor = new Color(255, 255, 235, 100);

    /**Assests variable */
    private GameAssets assets;

    /**Variables to control the proportionality of the game grid */
    private double propPercentage = 0.80;
    private Window mainWindow;

    /**
     * Constructor for the play area
     * @param boardSize
     * 		The board size preferred
     * @param assets
     * 		Assets list, used to populate token icons etc.
     * @param mainWindow
     * 		The main window instance.
     */
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

    /**
     * @returns 
     * 		Dimension for the minimum size
     */
    @Override
    public Dimension getMinimumSize() {
        return minSize;
    }

    /**
     * @returns 
     * 		Dimension for the preferred size
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
     * @return 
     * 		The current size of the board.
     */
    @Override
    public Dimension getSize() {
        return size;
    }

    
    /** 
     * get the token array
     * @return
     * 		The token array in the grid board.
     */
    public Token[] getTokens() {
        return gameTokens;
    }

    
    /**
     * This is a fix for an issue to do with the transparency of the panels.
     */
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * Populate the gridboard with tokens.
     */
    private void populateWithTokens() {
        int i = 0, currY = 0, currX = 0;
        for (currY = 0; currY < rows; currY++) {
            for (currX = 0; currX < cols; currX++) {
                Token token = new Token(currX, currY);
                token.setIcon(assets.getResizedAsset("sample_token.png",
                        minSize.width, minSize.height));
                token.setOpaque(false);
                Dimension iconSize = new Dimension(100, 100);
                token.setPreferredSize(iconSize);
                add(token, new Integer(2));
                gameTokens[i] = token;
                i++;
            }
        }
    }

	/**
	 * Debug methodt o write the current board state out to the terminal
	 * @param newBoard
	 */
    public void writeBoard(int[][] newBoard) {
        int i = 0;
        for (int row = rows - 1; row >= 0; row--) {
            for (int col = 0; col < cols; col++) {
                // Logic for determining the token icon
                if (newBoard[row][col] == 0) { // For Empty Tokens
                    gameTokens[i].setIcon(assets.getResizedAsset(
                            "sample_token.png", minSize.width, minSize.height));
                } else if (newBoard[row][col] == 1) { // For Player 1 Tokens
                    gameTokens[i].setIcon(assets.getResizedAsset(
                            "sample_token_red.png", minSize.width,
                            minSize.height));
                } else { // For Player 2 Tokens
                    gameTokens[i].setIcon(assets.getResizedAsset(
                            "sample_token_yellow.png", minSize.width,
                            minSize.height));
                }

                i++;
            }

        }
    }
}
