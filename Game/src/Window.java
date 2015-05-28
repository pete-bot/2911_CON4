import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {

    private static final long serialVersionUID = 1L;

    /** */
    private FrontendBoard frontEndBoard;
    /** */
    private BackendBoard backendBoard;
    
    /** */
    private Dimension defaultSize = new Dimension(1024, 700);
    
    /** */
    private GridBagConstraints gbc;
    
    /** */
    private GameAssets assets = new GameAssets();
    
    /** */
    private MainMenuPanel menuPanel = new MainMenuPanel(this, this.assets);

    /** */
    private JPanel titlePane;

    /** */
    private BackgroundPanel background;

    /**
     * Method to provide 'esc' key support to exit to menu
     */
    public AbstractAction escapeAction = new AbstractAction() {
        private static final long serialVersionUID = 1L;
        private boolean paused = true;

        @Override
        public void actionPerformed(ActionEvent event) {
            paused = !paused;

            System.out.println("window-esc" + "pause state: " + paused);

            if (!paused) {
                // System.out.println("Pausing.");
                pauseGame();
            } else {
                // System.out.println("un-Pausing.");
                resumeGame();
            }
        }
    };
    
    /**
     * Set opponent type to human. 
     */
    public void setBoardToPVP() {
    	frontEndBoard.setOpponentToHuman();
    }
    
    /**
     * Class constructor - creates a new JFrame object
     * @param newBoard
     * 		The instance of the game representation back end. 
     */
    public Window(BackendBoard newBoard) {
        super("Brace yourself, Wobke is coming.");

        this.setMinimumSize(new Dimension(800, 820));
        this.setLocationRelativeTo(null);
        
        
        initFrontendBoard(newBoard);
        initWindow(newBoard);
        pack();
    }
    
    /**
     * Method to add the main menu  panel to the JFrame
     */
    public void addMenu() {
        menuPanel.addMainMenuItems();
    }

    /**
     * Method to display the main menu
     */
    public void displayMenu() {

        titlePane.setVisible(true);
        menuPanel.setVisible(true);
    }

    /**
     * Hide the game play area. Used when transitioning to another menu. etc
     */
    public void hideGameBoard() {
        frontEndBoard.turnOff();
    }

    /**
     * Hide the main menu. Used when transitioning to another menu. etc
     */
    private void hideMainMenu() {
        menuPanel.hideMainMenu();
        titlePane.setVisible(false);
    }

    /**
     * Initilaise a very attractive background
     */
    private void initBackground() {
        ImageIcon asset = assets.getAsset("sample_bg.png");
        Image img = asset == null ? null : asset.getImage();
        background = new BackgroundPanel(img, BackgroundPanel.SCALED, 0.50f,
                0.5f);
        setContentPane(background);
    }

    
    /**
     * Method that creates a new instance of the front end board.
     * @param newBoard
     * 		The instance of the backend board that is being used to populate the new front end board.
     */
    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontendBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
    }

    /**
     * Method to initialise the gridbaglayout constraints that are used in this window.
     */
    private void initLayout() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.weightx = 0.3;
        gbc.weighty = 0.7;

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
    }

    /**
     * Method to initilise title pane.
     */
    private void initTitle() {
        titlePane = new JPanel();

        titlePane.setSize(new Dimension(420, 200));
        ImageIcon titleIcon = assets.getAsset("sample_title.png");
        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(titleIcon);
        titlePane.add(gameTitle);

        titlePane.setBackground(new Color(255, 255, 235, 200));
    }

    /**
     * Initialise The main window layout and objects.
     * @param newBoard
     * 		instance of backend board being used to represent the game state.
     */
    private void initWindow(BackendBoard newBoard) {
        initBackground();
        initLayout();
        initTitle();

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titlePane, gbc);

        setPreferredSize(defaultSize);
        setSize(defaultSize);
        setResizable(true); // XXX Here for testing, remove later.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        gbc.gridy += 10;
        add(frontEndBoard, gbc);

        // gbc.gridx = 0;
        // gbc.gridy = 0;

        gbc.fill = GridBagConstraints.CENTER;

        add(menuPanel, gbc);

        menuPanel.setVisible(false);
        titlePane.setVisible(false);
        try {
            menuPanel.musicToggle();
        } catch (Exception e) {
            System.err.println("Cannot play music.");
        }
    }

    /**
     * Method to load the pause game menu.
     */
    public void pauseGame() {
        frontEndBoard.turnOff();
        menuPanel.showPauseMenu();
    }

    /**
     * Method to reset the backend board
     */
    public void resetBackEndBoard() {
        backendBoard.resetBoard();
    }

    /**
     * Method to reset the window and the game state.
     */
    public void resetWindow() {
        menuPanel.hideMainMenu();
        menuPanel.hidePauseMenu();
        titlePane.setVisible(false);
        frontEndBoard.turnOn();
        frontEndBoard.resetBoard();
    }

    /**
     * Method to un pause the game
     */
    public void resumeGame() {
        frontEndBoard.turnOn();
        menuPanel.hidePauseMenu();
    }

    /**
     * Method to render the select difficulty screen.
     */
    public void selectDifficulty() {
        hideMainMenu();
        titlePane.setVisible(false);
        menuPanel.showDifficultyPanel();
    }

    /**
     * Method to set the correct difficulty via the AI interface /abstract interface
     */
    public void setDifficulty(Difficulty difficulty) {
        frontEndBoard.setAI(difficulty);
    }

    /**
     * Method to kick off the game
     */
    public void startNewGame() {
        hideMainMenu();
        backendBoard.showTerminalBoard();
        frontEndBoard.turnOn();
    }

}