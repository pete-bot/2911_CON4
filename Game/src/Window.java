import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Window extends JFrame {

    // This is for serialization; don't worry about it.
    private static final long serialVersionUID = 1L;

    // should we initialise these in the constructor? does that have any practical effect on the program?
    private ButtonPanel buttonPanel = new ButtonPanel(this);
    private MainMenuPanel menuPanel = new MainMenuPanel(this);
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(1024, 800);
    private GridBagConstraints gbc;
    private PauseMenuPanel pauseMenu = new PauseMenuPanel(this);
    // used as the main title bar
    private JPanel titlePane;

    private Path assetsLocation;


    // may need to change - stolen shamelessly for testing, need our own implementation
    private BackgroundPanel bg_pattern;

    public Window(BackendBoard newBoard) {
        super("Generic tile-themed sequence pattern based fun simulator.");
        initFrontendBoard(newBoard);
        initWindow(newBoard);

    }

    // TODO Perhaps the initial window should display a resolution that gets
    // saved as a pref.
    private void initWindow(BackendBoard newBoard) {

    	// this can be cleaned up - needs to be sorted out ryan style
    	// TODO: fix rollen's shit

        String runningDir = System.getProperty("user.dir");
        assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        Path bgPath = Paths.get(assetsLocation + "/bg_pattern_2.jpg");

    	BufferedImage img = null;
		try {
		     File f = new File(bgPath.toString());
		     img = ImageIO.read(f);
		     System.out.println("File " + f.toString());
		} catch (Exception e) {
		     System.out.println("Cannot read file: " + e);
		}

		BackgroundPanel background = new BackgroundPanel(img, BackgroundPanel.SCALED, 0.50f, 0.5f);
		setContentPane(background);


    	// create GridBagLayout et al
    	setLayout(new GridBagLayout());
    	// init gridbagConstraints
        gbc = new GridBagConstraints();
    	gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        gbc.weightx = 0.3;
        gbc.weighty = 0.7;

        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;



        // Title
	    titlePane = new JPanel();
	    titlePane.setPreferredSize(new Dimension(400,300));
	    titlePane.setOpaque(false);
		Path titlePath = Paths.get(assetsLocation + "/game_title.png");
		ImageIcon icon = new ImageIcon(titlePath.toString());
	    JLabel gameTitle= new JLabel();
	    gameTitle.setIcon(icon);
	    titlePane.add(gameTitle);
	    add(titlePane, gbc);

	    gbc.gridy+=10;


    	//this.setSize(new Dimension(1024, 768));

    	//setSize(defaultSize);
        // this should change the frame size consistently- across all panels
        setPreferredSize(defaultSize);


        // sets the JFrame window pos on screen
        //setLocationRelativeTo(null);
        setResizable(true); // allow the screen to be resized.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initFrontendBoard(newBoard);
        add(frontEndBoard, gbc);
        gbc.gridy++;
        displayMenu();
        setVisible(true);

        add(pauseMenu, gbc);
        pauseMenu.setVisible(false);


        //pack(); //Autosizes to match components
    }

    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontEndBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
    }

    public void startNewGame() {
        // We'll also need to disable the menu.
        hideMainMenu();
        showTerminalBoard();
        frontEndBoard.turnOn();
        pack();
    }

    // Initialize the backend terminal board
    private void showTerminalBoard() {
        System.out.println("Welcome to WOBCON4. Enjoy the game.");
        System.out.println("Initial Game State:");
        backendBoard.showTerminalBoard();
        System.out.println("User, please enter your column choice:");
    }

    public void resetWindow() {
    	titlePane.setVisible(false);
    	pauseMenu.setVisible(false);
    	frontEndBoard.turnOn();
        frontEndBoard.resetBoard();
        showTerminalBoard();
    }

    public void displayMenu() {
        // turn off and clear the board.

    	gbc = new GridBagConstraints();
    	gbc.gridx = 0;
        gbc.gridy = 0;

        add(menuPanel, gbc);
        pack();
        buttonPanel.setVisible(true);
    }

    // hide our main menu
    private void hideMainMenu() {
        menuPanel.setEnabled(false);
        menuPanel.setVisible(false);

        titlePane.setVisible(false);
    }

    public void pauseGame(){
    	frontEndBoard.turnOff();
    	titlePane.setVisible(true);
    	pauseMenu.setVisible(true);
    	// show pause menu;
    }

    public void resumeGame(){
    	frontEndBoard.turnOn();
    	titlePane.setVisible(false);
    	pauseMenu.setVisible(false);
    	// hide pause menu
    }

}












/*
 BufferedImage img = null;
        try {
             File f = new File("/home/petey/workspace/2911_CON4/Game/assets/bg_pattern.jpg");
             img = ImageIO.read(f);
             System.out.println("File " + f.toString());
        } catch (Exception e) {
            System.out.println("Cannot read file: " + e);
        }

        BackgroundPanel background = new BackgroundPanel(img, BackgroundPanel.TILED, 0.50f, 0.5f);

        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 100);
        frame.setVisible(true);




 */
