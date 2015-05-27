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

public class FrontendBoard extends JLayeredPane implements MouseListener,
MouseMotionListener, ActionListener {

    private static final long serialVersionUID = 1L;

    // TODO our colours, should be updated to match the palette
    private BackendBoard backendBoard; // Should be 'backendBoard'
    private final int rows = 6;
    private final int cols = 7;
    private final int tilesOnBoard = rows * cols;
    private Token[] gameTokens = new Token[tilesOnBoard];
    private Window mainWindow;
    private PlayArea playArea;
    private PauseButton pauseButton;
    private JButton statusIndicator = new JButton("");

    private Border emptyBorder = BorderFactory.createEmptyBorder();
    private GameAssets assets = new GameAssets();

    private ArrayList<Point> winList = new ArrayList<Point>();
    private int animationBeat = 0;
    Timer clock = new Timer(600, this);

    private ImageIcon blankTokenIcon;
    private ImageIcon glowingTokenIcon;
    private ImageIcon redTokenIcon;
    private ImageIcon yellowTokenIcon;
    private ImageIcon winTokenIcon;

    private Opponent opponent;

    private boolean inWinState = false;
    private boolean aiThinking = false;

    public FrontendBoard(BackendBoard backendBoard, Window mainWindow) {
        super();
        this.backendBoard = backendBoard;
        this.mainWindow = mainWindow;

        showMainMenu();
    }

    @Override
    // (replaces 'highlightWin' function)
    public void actionPerformed(ActionEvent e) {
        Iterator<Point> winIterator = winList.iterator();
        while (winIterator.hasNext()) {
            Point winCoords = winIterator.next();

            // converts 2-D (x,y) point to 1-D index in array.
            int indexToGet = winCoords.x * cols + cols - winCoords.y;
            Token t = gameTokens[42 - indexToGet];

            // based off 'beats'
            // -> we are checking the field 'animationBeat' in this class. it
            // can either be: 0 or 1.
            // at every iteration the 'actionPerformed' is called, animation
            // beat is incremented 1
            // but quickly reset to 0 if it beat == 2.
            // so animationBeat = 0,1,0,1,0,1...
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

    private void checkDraw() {
        if (backendBoard.isFull()) {
            inWinState = true;
            updateStatusIndicator(GameState.DRAW);
        } else {
            return;
        }
    }

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
            // clock.stop();
            // winList.clear();
            // SHOULD PAUSE AT THIS POINT.
        }
    }

    // need to fix design of this
    public void endGame(int winner) {
        mainWindow.showEndGame(winner);
    }

    // Get a column from a given x coordinate
    // FIXME should throw an exception
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

    public void getNextMove(Action move) {
        if (!inWinState) {
            updateStatusIndicator(GameState.NO_WIN);
            if (backendBoard.isLegal(move)) {
                backendBoard.makeMove(move);
                backendBoard.showTerminalBoard();
                updateVisualBoard(move.getColumn());
                checkWin(move); // Wins come before draws.
                checkDraw();
                backendBoard.switchPlayer();
                makeOpponentMove();
            } else {
                System.out.println("Invalid move.");
            }
        }
    }

    public void hidePause() {
        pauseButton.setVisible(false);
    }

    private void hidePauseButton() {
        pauseButton.setVisible(false);
    }

    // this highlights the column
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

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
        b.setRolloverEnabled(false);
    }

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

        playArea = new PlayArea(blankTokenIcon, mainWindow);
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

    private void initIcons() {
        blankTokenIcon = assets.getAsset("sample_token.png");
        glowingTokenIcon = assets.getAsset("sample_token_glow.png");
        redTokenIcon = assets.getAsset("sample_token_red.png");
        yellowTokenIcon = assets.getAsset("sample_token_yellow.png");
        winTokenIcon = assets.getAsset("sample_token_win.png");
    }

    private void initKeyListener() {
        int InFocusWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
        KeyStroke escapeStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        getInputMap(InFocusWindow).put(escapeStroke, "escapeSequence");
        getActionMap().put("escapeSequence", mainWindow.escapeAction);
    }

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
                    System.out.println("Dun thinkin!");
                    Action opponentMove = opponent.getMove();
                    while (!backendBoard.isLegal(opponentMove)) {
                        opponentMove = opponent.getMove();
                    }
                    backendBoard.makeMove(opponentMove);
                    backendBoard.showTerminalBoard();
                    updateStatusIndicator(GameState.NO_WIN);
                    updateVisualBoard(opponentMove.getColumn());
                    checkWin(opponentMove);
                    checkDraw();
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

    // MOUSELISTENER AND MOUSEMOTIONLISTENER OVERRIDES
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
    }

    public void setAI(DIFFICULTY difficulty) {
        opponent = new AI(difficulty, backendBoard);
     
    }

    public void showMainMenu() {
        initIcons();
        initKeyListener();
        initGraphics();
    }

    private void showPauseButton() {
        pauseButton.setVisible(true);
    }

    public void turnOff() {
        setVisible(false);
        setEnabled(false);
        for (Token b : gameTokens) {
            b.setEnabled(false);
            b.setVisible(false);
        }
        hidePauseButton();
    }

    public void turnOn() {
        setVisible(true);
        setEnabled(true);
        for (Token b : gameTokens) {
            b.setEnabled(true);
            b.setVisible(true);
        }
        showPauseButton();
    }

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

    // Updates the board with the next _legal_ move
    // xPos is the column, refactor later.
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
