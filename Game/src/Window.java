import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private ButtonPanel buttonPanel = new ButtonPanel(this);
    private MainMenuPanel menuPanel = new MainMenuPanel(this);
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(1024, 900);
    private GridBagConstraints gbc;
    private PauseMenuPanel pauseMenu = new PauseMenuPanel(this);
    private JPanel titlePane;
    private BackgroundPanel background;
    private Path assetsLocation;
    private Path bgPath;

    public Window(BackendBoard newBoard) {
        super("Generic tile-themed sequence pattern based fun simulator.");
        initFrontendBoard(newBoard);
        initWindow(newBoard);
    }

    private void initWindow(BackendBoard newBoard) {
		initPaths();
		initImages();
		initLayout();
        initTitle();
	    add(titlePane, gbc);

        setPreferredSize(defaultSize);
        setResizable(true); // XXX Here for testing, remove later.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

	    gbc.gridy+=10;
        add(frontEndBoard, gbc);

        gbc.gridy++;
        add(pauseMenu, gbc);

        //So what is pauseMenu vs menuPanel?
    	gbc.gridx = 0;
        gbc.gridy = 0;

        add(menuPanel, gbc);

        pauseMenu.setVisible(false);
        //pack(); //Autosizes to match components
    }

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

    private void initTitle() {
	    titlePane = new JPanel();
	    titlePane.setPreferredSize(new Dimension(400,300));
	    titlePane.setOpaque(false);
		Path titlePath = Paths.get(assetsLocation + "/game_title.png");
		ImageIcon icon = new ImageIcon(titlePath.toString());
	    JLabel gameTitle= new JLabel();
	    gameTitle.setIcon(icon);
	    titlePane.add(gameTitle);
    }

    private void initPaths() {
        String runningDir = System.getProperty("user.dir");
        assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        bgPath = Paths.get(assetsLocation + "/bg_pattern_2.jpg");
    }

    private void initImages() {
    	BufferedImage img = null;
		try {
		     File f = new File(bgPath.toString());
		     img = ImageIO.read(f);
		     System.out.println("File " + f.toString());
		} catch (Exception e) {
		     System.out.println("Cannot read file: " + e);
		}

		background = new BackgroundPanel(img, BackgroundPanel.SCALED, 0.50f, 0.5f);
		setContentPane(background);
    }

    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontEndBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
    }

    public void startNewGame() {
        hideMainMenu();
        showTerminalBoard();
        frontEndBoard.turnOn();
        pack();
    }

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

        pack(); //Why the hell does it not work without this?
        buttonPanel.setVisible(true);
    }

    private void hideMainMenu() {
        menuPanel.setEnabled(false);
        menuPanel.setVisible(false);

        titlePane.setVisible(false);
    }

    public void pauseGame(){
    	frontEndBoard.turnOff();
    	titlePane.setVisible(true);
    	pauseMenu.setVisible(true);
    }

    public void resumeGame(){
    	frontEndBoard.turnOn();
    	titlePane.setVisible(false);
    	pauseMenu.setVisible(false);
    }

}