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

/**
 * 
 * @author WOBCON4
 *
 *	Window class for the game. This creates and contains all the neccessary components and information for the main window frame. 
 */
public class Window extends JFrame {

	/**Serialisation for threading */
    private static final long serialVersionUID = 1L;
    
    /**Main front end board object-contains all of the frontend board info */
    private FrontendBoard frontEndBoard;
    /**Front end statistics panel container */
    private FrontEndStatistics frontEndStatistics;
    /**Back end board representation */
    private BackendBoard backendBoard;
    /**Default dimension */
    private Dimension defaultSize = new Dimension(900, 730);
    /**Constraints for layout */
    private GridBagConstraints gbc;
    /**Assets populator */
    private GameAssets assets = new GameAssets();
    /**Menu panel container */
    private MainMenuPanel menuPanel = new MainMenuPanel(this, this.assets);

    /**Container for Title image */
    private JPanel titlePane;
    /**BackGround image panel - used to store the background image */
    private BackgroundImagePanel background;
    
    /**Game state flags */
    private boolean paused = false;
    private boolean inStats = false;

    
    /**
     * Esc key action handler - allows the esc key to pause the game.
     */
    public AbstractAction escapeAction = new AbstractAction() {
        private static final long serialVersionUID = 1L;

        // private boolean paused = true;

        @Override
        public void actionPerformed(ActionEvent event) {

            System.out.println("window-esc" + "pause state: " + paused);

            if (!paused && !inStats) {
                paused = true;
                // System.out.println("Pausing.");
                pauseGame();
            } else if (paused || inStats) {
                // System.out.println("un-Pausing.");
                resumeGame();
            }
        }
    };

    /**
     * Class constructor 
     * @param newBoard
     * 		The backend board representation
     */
    public Window(BackendBoard newBoard) {
        super("Prepare yourself, Wobke is coming.");

        this.setMinimumSize(new Dimension(900, 730));
        this.setLocationRelativeTo(null);

        initFrontendBoard(newBoard);
        initFrontendStatistics();
        initWindow(newBoard);
        pack();
    }

    /**
     * Method to add the main menu to the window
     */
    public void addMenu() {
        menuPanel.addMainMenuItems();
    }

    /**
     * Method to display the main menu and title iamge.
     */
    public void displayMenu() {

        titlePane.setVisible(true);
        menuPanel.setVisible(true);
    }
    /**
     * Method to hide the game board. Used to display the pause menu etc.
     */
    public void hideGameBoard() {
        frontEndBoard.turnOff();
    }

    /**
     * Method to hide the main menu - used when intialising the new game board.
     */
    private void hideMainMenu() {
        menuPanel.hideMainMenu();
        titlePane.setVisible(false);
    }

    /**
     * Method used to hide the statistics panel
     */
    public void hideStatistics() {
        menuPanel.showPauseMenu();
        frontEndStatistics.turnOff();
    }

    /**
     * Method to initialise the background image panel. This is persistent for the duration of the Window object.
     */
    private void initBackground() {
        ImageIcon asset = assets.getAsset("sample_bg.png");
        Image img = asset == null ? null : asset.getImage();
        background = new BackgroundImagePanel(img);
        setContentPane(background);
    }

    /**
     * Method to initialise the front end board. This is where the game board is displayed and updated.
     * @param newBoard
     * 		The backend board representation of the current game.
     */
    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontendBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
    }

    /*
     * Added by sketch cause statistics are gang Initialises the statistics
     * component that I spent half a year on. legit.
     */

    /**
     * Method to initialise the statistics panel
     */
    private void initFrontendStatistics() {
        frontEndStatistics = new FrontEndStatistics(assets, this);
        frontEndStatistics.setEnabled(false);
        frontEndStatistics.setVisible(false);
    }

    /**
     * Method to initialise the layout type for the window.
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
     * Method to initialise the Title tile. 
     */
    private void initTitle() {
        titlePane = new JPanel();

        titlePane.setSize(new Dimension(420, 200));
        ImageIcon titleIcon = assets.getAsset("sample_title_new.png");
        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(titleIcon);
        titlePane.add(gameTitle);

        titlePane.setBackground(new Color(255, 255, 235, 200));
    }
    
    /**
     * Method to initialise the window container and place all the needed components inside. 
     * @param newBoard
     * 		instance of the backend game state.
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
        add(frontEndStatistics, gbc);
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
     * Method to pause the game and show the pause menu.
     */
    public void pauseGame() {
        frontEndBoard.turnOff();
        menuPanel.showPauseMenu();
        frontEndStatistics.turnOff();
    }

    
    public void resetBackEndBoard() {
        backendBoard.resetBoard();
    }

    /*
     * Method to reset statistics
     */
    public void resetStatistics() {
        frontEndStatistics.resetStatistics();
    }

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
        paused = false;
        inStats = false;
        frontEndBoard.turnOn();
        menuPanel.hidePauseMenu();
        frontEndStatistics.turnOff();
    }

    /**
     * Method to allow player to choose the difficulty level of the AI
     */
    public void selectDifficulty() {
        hideMainMenu();
        titlePane.setVisible(false);
        menuPanel.showDifficultyPanel();
    }

    /** 
     * Set the game to Player vs Player mode. This sets the opponent to human type.
     */
    public void setBoardToPVP() {
        frontEndBoard.setOpponentToHuman();
    }

    /**
     * Set the opponent difficulty.
     * @param difficulty
     * 		The opponent difficulty.
     */
    public void setDifficulty(Difficulty difficulty) {
        frontEndBoard.setAI(difficulty);
    }

    /**
     *Called when the statistics need to be shown (from the button) 
     */
    public void showStatistics() {
        inStats = true;
        menuPanel.hidePauseMenu();
        frontEndStatistics.turnOn();
    }

   
    /**
     * method to start a new game
     */
    public void startNewGame() {
        hideMainMenu();
        backendBoard.showTerminalBoard();
        frontEndBoard.turnOn();
    }

    
    /**
     * Method to update the statistics panel  
     */
    public void updateStatistics(TurnSummary turn) {
        frontEndStatistics.addTurn(turn);
    }

}