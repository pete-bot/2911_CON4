import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * This is the main grid of tokens
 */
public class FrontEndBoard extends JPanel implements MouseListener,
        MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // our colours, should be updated to match the palette
    private Color gridColor = new Color(60, 58, 232, 255);

    Dimension gridSize = new Dimension(700, 700);
    private Token[] gameTokens = new Token[42];
    private BackendBoard backendBoard; // Should be 'backendBoard'
    private final int rows = 6;
    private final int cols = 7;
    private final int tilesOnBoard = 42;
    private Window mainWindow;
    private MechanicalTurk newTurk;
    private PlayArea playArea;
    private GridLayout frontEndBoardLayout = new GridLayout(rows, cols);

    private PauseButton pausePanel;

    Path assetsLocation;
    private ImageIcon blankTokenIcon;
    private ImageIcon glowingTokenIcon;
    private ImageIcon redTokenIcon;
    private ImageIcon yellowTokenIcon;
    private ImageIcon winTokenIcon;

    public FrontEndBoard(BackendBoard backendBoard, Window mainWindow) {
        super();
        setUpPaths();
        System.out.println("You are running this game from: " + System.getProperty("user.dir"));
        System.out.println("Asset location" + assetsLocation.toString());

        pausePanel = new PauseButton(mainWindow);
        pausePanel.setOpaque(false);

        // AIclass is a simple way of passing in which AI that the user may want
        int AIclass = 0;
        newTurk = new MechanicalTurk(AIclass);

        this.backendBoard = backendBoard;
        this.mainWindow = mainWindow;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
    	gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        // these values change the way the resizing modifies spacing distribution on teh tokens
        gbc.weightx = 0;
        gbc.weighty = 0;

        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.fill = GridBagConstraints.BOTH;


        setSize(gridSize);

        // Setup the clickable play area.
        //frontEndBoardLayout.setHgap(new Integer(2));
        //frontEndBoardLayout.setVgap(new Integer(2));
        playArea = new PlayArea(gridColor, gridSize);
        playArea.addMouseListener(this);
        playArea.addMouseMotionListener(this);
        playArea.setLayout(frontEndBoardLayout);
        add(playArea, gbc);

        int i = 0, currY = 0, currX = 0;
        for (currY = 0; currY < 6; currY++) {
            for(currX = 0; currX<7; currX++){
            	Token token = new Token(currX, currY, blankTokenIcon);
                token.setOpaque(false);
                Dimension iconSize = new Dimension(blankTokenIcon.getIconHeight(),
                        blankTokenIcon.getIconWidth());
                token.setPreferredSize(iconSize);
                playArea.add(token);
                gameTokens[i] = token;
                i++;
                gbc.gridx++;
            }
            gbc.gridy++;
        }
        
        gbc.gridy++;

        // disable opacity on the
        setOpaque(false);

        // adjust position of menu button
        gbc.gridx = 40;
        add(pausePanel, gbc);

    }

    private void setUpPaths() {
        String runningDir = System.getProperty("user.dir");
        assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        Path blankTokenPath = Paths.get(assetsLocation + "/circle101.png");
        Path glowingTokenPath = Paths.get(assetsLocation + "/glow.png");
        Path redTokenPath = Paths.get(assetsLocation + "/circle101_RED.png");
        Path yellowTokenPath = Paths.get(assetsLocation + "/circle101_YELLOW.png");
        Path winTokenPath = Paths.get(assetsLocation + "/win.png");

        blankTokenIcon = new ImageIcon(blankTokenPath.toString());
        glowingTokenIcon = new ImageIcon( glowingTokenPath.toString());
        redTokenIcon = new ImageIcon(redTokenPath.toString());
        yellowTokenIcon = new ImageIcon( yellowTokenPath.toString());
        winTokenIcon = new ImageIcon(winTokenPath.toString());
    }

    // this highlights the column
    public void highlightColumn(MouseEvent cursor) {
        // Change the icons of the tokens that are in the rows for the given
        // column
        for (int i = 0; i < gameTokens.length; i++) {
            Token token = gameTokens[i];

            // this uses the mouse location to determine which column to
            // highlight
            Point tokenLocation = token.getLocation();
            double beginRange = tokenLocation.getX();
            double endRange = tokenLocation.getX() + token.getWidth();
            boolean inRow = cursor.getX() > beginRange
                    && cursor.getX() < endRange;
            boolean isBlankIcon = (token.getPlayer() == 0);
            if (inRow && isBlankIcon) {
                token.setIcon(glowingTokenIcon);
            } else if (!inRow && isBlankIcon) {
                token.setIcon(blankTokenIcon);
            }
        }
    }

    // this highlights the win
    public void highlightWin(ArrayList<Point> winList) {
        Iterator<Point> winIterator = winList.iterator();
        while (winIterator.hasNext()) {
            Point winCoords = winIterator.next();
            System.out.println("winCoords = " + winCoords.toString());
            int indexToGet = (winCoords.x) * (cols) + (cols - winCoords.y);
            Token t = gameTokens[42 - indexToGet];
            t.setIcon(winTokenIcon);
        }
    }

    // TODO
    // implement player choice (go first or second)
    // this will be pulled from the intro menu
    // this is essentially the 'primary function through which the game is
    // played'
    public void getUserMove(Action newAction) {
        if (backendBoard.isLegal(newAction)) {

            updateBoardWithMove(newAction.getColumn());
            // Update terminal
            backendBoard.makeMove(newAction);
            backendBoard.showTerminalBoard();

            ArrayList<Point> winList = backendBoard.checkWinState(newAction);

            // Game win found?
            if (!winList.isEmpty()) {
                highlightWin(winList);
                if (backendBoard.getTurn() % 2 == 0) {
                    System.out.println("PLAYER_1, you WIN!");
                    JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
                } else {
                    System.out.println("PLAYER_2, you WIN!");
                    JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
                }
                return;
            }

            // Otherwise game continues
            if (backendBoard.getTurn() % 2 == 1) {
                System.out.println("PLAYER_1, please enter your move:");
            } else {
                System.out.println("PLAYER_2, please enter your move:");
            }

            backendBoard.IncrementTurn();

            // call AI here.
            turkMove(backendBoard);
        } else {
            System.out
                    .println("You have entered an invalid move, please try again.");
        }
    }

    // FIXME
    // AI code should not persist in this class.
    // Theoretically this should be a 'secondPlayer' or 'competitor' method.
    // Also, this method should be making calls through getUserMove();
    public void turkMove(BackendBoard backendBoard) {

        System.out.println("The Turk makes its move...");
        Action turkMove = newTurk.getTurkMove(backendBoard);

        backendBoard.makeMove(turkMove);
        backendBoard.showTerminalBoard();
        updateBoardWithMove(turkMove.getColumn());

        ArrayList<Point> winList = backendBoard.checkWinState(turkMove);
        
        if (!winList.isEmpty()) {
        	highlightWin(winList);
            if (backendBoard.getTurn() % 2 == 0) {
                System.out.println("PLAYER_1, you WIN!");
                JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
            } else {
                System.out.println("PLAYER_2, you WIN!");
                JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
            }
            mainWindow.resetWindow();
            return;
        }

        backendBoard.IncrementTurn();

        System.out.println("Control has returned to the player.");
    }

    // Updates the board with the next _legal_ move
    // xPos is the column, refactor later.
    public void updateBoardWithMove(int xPos) {
        for (int count = tilesOnBoard - (cols - xPos); count >= 0; count -= 7) {
            Token currentButton = gameTokens[count];
            if (currentButton.getPlayer() == 0) {
                if (backendBoard.getTurn() % 2 == 0) {
                    currentButton.setPlayer(1);
                    currentButton.setIcon(redTokenIcon);
                } else {
                    currentButton.setPlayer(1);
                    currentButton.setIcon(yellowTokenIcon);
                }
                break;
            }
        }
    }

    public void resetBoard() {
        backendBoard.resetBoard();

        for (Token gameToken : gameTokens) {
            gameToken.setIcon(blankTokenIcon);
            gameToken.setPlayer(0);
        }
    }

    // MOUSELISTENER AND MOUSEMOTIONLISTENER OVERRIDES
    @Override
    public void mouseClicked(MouseEvent e) {
        // FIXME Testing
        // Package the appropriate column the mouse is on into an Action
        int col = getColumn(e.getX());
        Action newMove = new Action(1, col);
        System.out.printf("Column %d chosen.\n", col);
        getUserMove(newMove);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // int col = getColumn(e.getX());
        // Action newMove = new Action(1, col);
        highlightColumn(e);
        moveGraphicToken(e);
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    private void moveGraphicToken(MouseEvent e) {

        return;
    }

    // Get a column from a given x coordinate
    // FIXME should throw an exception
    private int getColumn(int x) {
        int columnWidth = (int) gridSize.getWidth() / 7;
        int currentColBegin = 0;
        int currentColEnd = columnWidth;
        int currentCol = 0;
        while (currentColEnd <= gridSize.getWidth()) {
            if (currentColBegin <= x && x <= currentColEnd) {
                return currentCol;
            }
            currentColBegin += columnWidth;
            currentColEnd += columnWidth;
            currentCol++;
        }
        return -1; // No column found.
    }

    public void turnOff() {
        for (Token b : gameTokens) {
            b.setEnabled(false);
        }
        setVisible(false);
        setEnabled(false);
    }

    public void turnOn() {
        for (Token b : gameTokens) {
            b.setEnabled(true);
        }
        setVisible(true);
        setEnabled(true);
    }

}
