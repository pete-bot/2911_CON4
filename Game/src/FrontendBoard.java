import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLayeredPane;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 *
 * @author WOBCON4 Class that represents the front end of the game. This class
 *         primarily allows for the creation of the user interface, however, it
 *         is also used to reflect changes in the game state in that front end
 *         interface.
 */
public class FrontendBoard extends JLayeredPane implements MouseListener,
MouseMotionListener, ActionListener {

    private static final long serialVersionUID = 1L;

    /** Instance of the back end of the game */
    private BackendBoard backendBoard;
    /** values for the dimensions of the game board. */
    private final int rows = 6;
    private final int cols = 7;
    private final int tilesOnBoard = rows * cols;

    /** Create the token board */
    private Token[] gameTokens = new Token[tilesOnBoard];

    /** Instance of the JFrame used to contain the front end board */
    private Window mainWindow;
    /**
     * Instance of play area- this is where the game tokens are kept in a grid
     * layout
     */
    private PlayArea playArea;
    /** Pause button for in game menu - holds action listeners etc */
    private PauseButton pauseButton;
    /** Status indicator used to report game status to the user, win state etc. */
    private JButton statusIndicator = new JButton("");

    /** An empty border asset used multiple times in UI */
    private Border emptyBorder = BorderFactory.createEmptyBorder();
    /** Assets object - used to clean up paths etc. */
    private GameAssets assets = new GameAssets();

    /**
     * Winning moves stored in list - used to represent the winning play with
     * animated tokens.
     */
    private ArrayList<Point> winList = new ArrayList<Point>();
    /** Used in the win state animation */
    private int animationBeat = 0;
    Timer clock = new Timer(600, this);

    /** Image icon objects used in UI on the grid of tokens */
    private ImageIcon blankTokenIcon;
    private ImageIcon glowingTokenIcon;
    private ImageIcon redTokenIcon;
    private ImageIcon yellowTokenIcon;
    private ImageIcon winTokenIcon;

    /**
     * The Opponent object. May be an AI if the game mode is player vs AI, human
     * otherwise.
     */
    private Opponent opponent;

    /** status flags for game state - a player has won, the AI is 'thinking' */
    private boolean inWinState = false;
    private boolean aiThinking = false;

    /**
     *
     * @param backendBoard
     *            Representation of the game state/back-end. used to determine
     *            current game situation.
     * @param mainWindow
     *            The JFrame used to contain the game UI. Also contains some
     *            helpful functions used by other classes.
     */
    public FrontendBoard(BackendBoard backendBoard, Window mainWindow) {
        super();
        this.backendBoard = backendBoard;
        this.mainWindow = mainWindow;

        showMainMenu();
    }

    /**
     * method that generates an animation effect upon win state. Designed to
     * highlight the winning move by the winning player.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Iterator<Point> winIterator = winList.iterator();
        while (winIterator.hasNext()) {
            Point winCoords = winIterator.next();

            // converts 2-D (x,y) point to 1-D index in array.
            int indexToGet = winCoords.x * cols + cols - winCoords.y;
            Token t = gameTokens[42 - indexToGet];

            if (animationBeat % 2 == 0) {
                // beat = 0;
                t.setIcon(winTokenIcon);
            } else {
                // beat = 1;
                t.setIcon(backendBoard.getPlayer() == 1 ? redTokenIcon
                        : yellowTokenIcon);
            }
        }

        animationBeat++; // increment beat by 1.

        // resets the animation 'counter' to 0.
        if (animationBeat == 2) {
            animationBeat = 0;
        }

        // required to update icons on board
        revalidate();
    }

    /**
     * Method to check if the game has come to a draw state. (ie, the board is
     * full - no player can make a move legally)
     */
    private void checkDraw() {
        if (backendBoard.isFull()) {
            inWinState = true;
            updateStatusIndicator(GameState.DRAW);
        } else {
            return;
        }
    }

    /**
     * Method to check if last move by player is a winning move (ie, 4 have been
     * connected)
     * 
     * @param action
     *            action represents the column choice by a player - the last
     *            column choice will always determine who has won so it is
     *            enough to check it and its immediate neighbors.
     *
     */
    private void checkWin(Action action) {
        winList = backendBoard.checkWinState(action);

        // Game win found!
        if (!winList.isEmpty()) {
            inWinState = true;
            clock.restart();
            if (backendBoard.getPlayer() == 1) {
                updateStatusIndicator(GameState.WIN);
            } else if (backendBoard.getPlayer() == 2) {
                updateStatusIndicator(GameState.WIN);
            }
        }
    }

    // FIXME should throw an exception
    /**
     * A method to get the current game board grid column indicated by an x
     * position. This is used to determine which column is currently highlighted
     * by the user's mouse cursor.
     * 
     * @param x
     *            x position of user's mouse cursor
     * @return Return a column number representing the region on the grid board
     *         over which the mouse is hovering.
     */
    private int getColumn(int x) {
        Dimension gridSize = playArea.getSize();
        int columnWidth = (int) gridSize.getWidth() / 7;
        int currentColBegin = 0;
        int currentColEnd = columnWidth;
        int currentCol = 0;
        while (currentColEnd <= gridSize.getWidth()) {
            if (currentColBegin <= x && x <= currentColEnd)
                return currentCol;
            currentColBegin += columnWidth;
            currentColEnd += columnWidth;
            currentCol++;
        }
        return -1; // No column found.
    }

    /**
     * Method to take the next move and update the game board. Checks to make
     * sure that the move action is legal.
     * 
     * @param move
     *            Action that represents the players choice of column/move.
     */
    public void getNextMove(Action move) {
        if (!inWinState) {
            updateStatusIndicator(GameState.NO_WIN);
            if (backendBoard.isLegal(move)) {
                backendBoard.makeMove(move);
                backendBoard.showTerminalBoard();
                updateVisualBoard(move.getColumn());
                backendBoard.incremementTurn();
                // Update statistics
                mainWindow.updateStatistics(new TurnSummary(backendBoard
                        .getTurnNum(), backendBoard.getBoard().clone(),
                        backendBoard.getPlayer(), move));

                checkWin(move); // Wins come before draws.
                checkDraw();
                if (!inWinState)
                    backendBoard.switchPlayer();
                makeOpponentMove();
            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    /**
     * Method to hide the pause menu.
     */
    public void hidePause() {
        pauseButton.setVisible(false);
    }

    /**
     * Method to hide the pause button ("MENU" in game)
     */
    private void hidePauseButton() {
        pauseButton.setVisible(false);
    }

    /**
     * Method to provide highlighting of the column that the user's mouse cursor
     * is currently hovering over. Designed to make the column choice more clear
     * to the user.
     * 
     * @param cursor
     *            An event object - the mouse position over the game board.
     */
    public void highlightColumn(MouseEvent cursor) {
        // Change the icons of the tokens that are in the rows for the given
        // column
        for (Token token : gameTokens) {
            // this uses the mouse location to determine which column to
            // highlight
            Point tokenLocation = token.getLocation();
            double beginRange = tokenLocation.getX();
            double endRange = tokenLocation.getX() + token.getWidth();
            boolean inRow = cursor.getX() > beginRange
                    && cursor.getX() < endRange;
            boolean isBlankIcon = token.getPlayer() == 0;
            if (inRow && isBlankIcon) {
                token.setIcon(glowingTokenIcon);
            } else if (!inRow && isBlankIcon) {
                token.setIcon(blankTokenIcon);
            }
        }
    }

    /**
     * Initialise button properties. Transparency, opacity etc.
     * 
     * @param b
     *            The button object to be modified.
     */
    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
        b.setRolloverEnabled(false);
    }

    /**
     * A method to initialise the main graphical objects on the play-board. This
     * includes the grid board, the status indicator and the pause menu button.
     */
    private void initGraphics() {
        pauseButton = new PauseButton(mainWindow);
        pauseButton.setOpaque(false);

        initButton(statusIndicator);
        updateStatusIndicator(GameState.NO_WIN);
        add(statusIndicator);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        // gbc.insets = new Insets(2, 2, 2, 2);

        gbc.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // these values change the way the resizing modifies spacing
        // distribution on the tokens
        gbc.weightx = 0;
        gbc.weighty = 1;

        gbc.gridy++;

        playArea = new PlayArea(new Dimension(750, 650), assets, mainWindow);
        playArea.addMouseListener(this);
        playArea.addMouseMotionListener(this);
        gameTokens = playArea.getTokens();
        add(playArea, gbc);

        gbc.gridy++;
        gbc.gridy++;
        gbc.gridy++;
        gbc.fill = GridBagConstraints.REMAINDER;

        setOpaque(false);

        // adjust position of menu button
        gbc.gridx = 0; // Any non-zero value doesn't seem to affect this much
        gbc.weightx = 1.0;
        gbc.gridy = 2;
        add(pauseButton, gbc);
    }

    /**
     * Method to initialise icons with the correct visual assets. Red token with
     * red token icon etc.
     */
    private void initIcons() {
        blankTokenIcon = assets.getAsset("sample_token.png");
        glowingTokenIcon = assets.getAsset("sample_token_glow.png");
        redTokenIcon = assets.getAsset("sample_token_red.png");
        yellowTokenIcon = assets.getAsset("sample_token_yellow.png");
        winTokenIcon = assets.getAsset("sample_token_win.png");
    }

    /**
     * Key listener to facilitate the use of the 'esc' key to enter and exit the
     * pause menu.
     */
    private void initKeyListener() {
        int InFocusWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
        KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getInputMap(InFocusWindow).put(escapeStroke, "escapeSequence");
        getActionMap().put("escapeSequence", mainWindow.escapeAction);
    }

    /**
     * Method to take opponent move and update the game state accordingly. For
     * AI opponents, provides an AIThinking timer, to simulate the thought time
     * of an AI. In reality, the AI has already calculated exactly how to kill
     * everyone in the immediate proximity well before this timer expires, but
     * it gives the player some feedback about the AI involvement in the game to
     * foster a false sense of hope and security in the human player. For human
     * opponents, allows them to click and place a token.
     */
    public void makeOpponentMove() {
        if (opponent.isAI() && !inWinState) {
            updateStatusIndicator(GameState.NO_WIN);

            // timer for AI move
            for (Token token : gameTokens) {
                token.repaint(250);
            }
            statusIndicator.setIcon(assets.getAsset("ai_think.png"));
            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent event) {
                    Action opponentMove = opponent.getMove();
                    while (!backendBoard.isLegal(opponentMove)) {
                        opponentMove = opponent.getMove();
                    }
                    backendBoard.makeMove(opponentMove);
                    backendBoard.showTerminalBoard();
                    updateStatusIndicator(GameState.NO_WIN);
                    updateVisualBoard(opponentMove.getColumn());
                    backendBoard.incremementTurn();

                    // AI Updates statistics
                    mainWindow.updateStatistics(new TurnSummary(backendBoard
                            .getTurnNum(), backendBoard.getBoard().clone(),
                            backendBoard.getPlayer(), opponentMove));

                    checkWin(opponentMove);
                    checkDraw();
                    if (!inWinState)
                        backendBoard.switchPlayer();
                    aiThinking = false;
                }
            };
            Timer aiTimer = new Timer(250, listener);
            aiTimer.setRepeats(false);
            aiTimer.start();
            aiThinking = true;
        }
    }

    /**
     * The following Override functions provide input information and feedback
     * on the user mouse cursor location.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (inWinState && !aiThinking)
            return;
        int col = getColumn(e.getX());
        Action newMove = new Action(col);
        System.out.printf("Column %d chosen.\n", col);
        getNextMove(newMove);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (inWinState)
            return;
        highlightColumn(e);
        moveGraphicToken(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    private void moveGraphicToken(MouseEvent e) {
    }

    /**
     * Method to reset the board - including reseting the back end board. This
     * will completely restart the game.
     */
    public void resetBoard() {
        inWinState = false;
        backendBoard.resetBoard();
        winList.clear();
        clock.stop();
        statusIndicator.setIcon(assets.getAsset("player_one_turn.png"));
        for (Token gameToken : gameTokens) {
            gameToken.setIcon(blankTokenIcon);
            gameToken.setPlayer(0);
        }
        mainWindow.resetStatistics();
    }

    /**
     * Method that allows the user to choose and set their AI opponents
     * difficulty level.
     * 
     * @param difficulty
     */
    public void setAI(Difficulty difficulty) {
        opponent = new AI(difficulty, backendBoard);
    }

    /**
     * Set the opponent variable to human - used for PvP games.
     */
    public void setOpponentToHuman() {
        opponent = new HumanOpponent();
    }

    /**
     * Method to show the main menu - this initialises the necessary graphical
     * components and sets them to visible in the frontEnd JPanel.
     */
    public void showMainMenu() {
        initIcons();
        initKeyListener();
        initGraphics();
    }

    /**
     * Sets pause button ("MENU") to visible.
     */
    private void showPauseButton() {
        pauseButton.setVisible(true);
    }

    /**
     * Method to render the game board invisible (set to off and disable
     * interactivity).
     */
    public void turnOff() {
        setVisible(false);
        setEnabled(false);
        for (Token b : gameTokens) {
            b.setEnabled(false);
            b.setVisible(false);
        }
        hidePauseButton();
    }

    /**
     * Method to render the game board visible (set to on and enable
     * interactivity).
     */
    public void turnOn() {
        setVisible(true);
        setEnabled(true);
        for (Token b : gameTokens) {
            b.setEnabled(true);
            b.setVisible(true);
        }
        showPauseButton();
    }

    /**
     * Method to update the status indicator for the player - this relays to the
     * player whose turn it is as well as if the winning move has been played.
     * 
     * @param state
     *            State of game - enum: NO_WIN, WIN, DRAW
     */
    private void updateStatusIndicator(GameState state) {
        if (GameState.NO_WIN == state) {
            if (backendBoard.getPlayer() == 1) {
                statusIndicator.setIcon(assets.getAsset("player_one_turn.png"));
                System.out.println("Alright player one, you go...");
            } else {
                statusIndicator.setIcon(assets.getAsset("player_two_turn.png"));
                System.out.println("Alright player two, you go...");
            }
        } else if (GameState.WIN == state) {
            if (backendBoard.getPlayer() == 1) {
                statusIndicator.setIcon(assets.getAsset("player_one_win.png"));
                System.out.println("PLAYER_1, you WIN!");
            } else {
                statusIndicator.setIcon(assets.getAsset("player_two_win.png"));
                System.out.println("PLAYER_2, you WIN!");
            }
        } else if (GameState.DRAW == state) {
            // Show appropriate graphics
            // current player does not matter.
            System.out.println("It's a draw!");
        }
        // statusIndicator.setVisible(true);
    }


    /**
     * Method to update the visual board with the next move. Will set the token
     * colour of the lowest token slot on the game board.
     * 
     * @param xPos
     *            X position - used to determine which column should be updated
     *            with the players token piece.
     */
    public void updateVisualBoard(int xPos) {
        for (int count = tilesOnBoard - (cols - xPos); count >= 0; count -= 7) {
            Token currentButton = gameTokens[count];
            if (currentButton.getPlayer() == 0) { // Free spot
                if (backendBoard.getPlayer() == 1) {
                    currentButton.setPlayer(1);
                    currentButton.setIcon(redTokenIcon);
                } else if (backendBoard.getPlayer() == 2) {
                    currentButton.setPlayer(2);
                    currentButton.setIcon(yellowTokenIcon);
                }
                break;
            }
        }
    }

}
