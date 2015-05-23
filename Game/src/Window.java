import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

import java.awt.Color;
import java.awt.event.ActionEvent;

public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	
	
	
    
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(1024, 900);
    private GridBagConstraints gbc;
    private GameAssets assets = new GameAssets();
    private MainMenuPanel menuPanel = new MainMenuPanel(this, this.assets);
    private JPanel titlePane;
    private BackgroundPanel background;
    
    public boolean paused = false;

    public Window(BackendBoard newBoard) {
        super("wob wob wob wob wob wob wob wob - kee");
        initFrontendBoard(newBoard);
        initWindow(newBoard);
    }

    public void displayMenu() {
        titlePane.setVisible(true);
        menuPanel.setVisible(true);
    }

    private void hideMainMenu() {
        menuPanel.setEnabled(false);
        menuPanel.setVisible(false);
        titlePane.setVisible(false);
    }

    private void initBackground() {
        ImageIcon asset = assets.getAsset("sample_bg.png");
        Image img = asset == null ? null : asset.getImage();
        background = new BackgroundPanel(img, BackgroundPanel.SCALED, 0.50f,
                0.5f);
        setContentPane(background);
    }

    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontEndBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
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
        
        titlePane.setSize(new Dimension(420, 200));
        ImageIcon titleIcon = assets.getAsset("sample_title.png");
        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(titleIcon);
        titlePane.add(gameTitle);
        
        // white
        //titlePane.setBackground(new Color(255, 255, 235, 200));
        // gray
        titlePane.setBackground(new Color(127, 127, 127, 127));
    }

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

        //gbc.gridx = 0;
        //gbc.gridy = 0;

        //GridBagConstraints gbc_diff = new GridBagConstraints();
        gbc.fill = GridBagConstraints.CENTER;

        add(menuPanel, gbc);

        menuPanel.setVisible(false);
        titlePane.setVisible(false);
    }

    public void pauseGame() {
    	paused = true;
    	frontEndBoard.turnOff();
        titlePane.setVisible(true);
        menuPanel.showPauseMenu();
        
    }

    public void endGame(int winner){
    	// expect winner ==1, 2
    	frontEndBoard.hidePause();
    	
    	// display win message and offer restart and quit
    	// maybe difficulty select?
    }
    
    
    public void resetWindow() {
        titlePane.setVisible(false);
        menuPanel.setVisible(false);
        frontEndBoard.turnOn();
        frontEndBoard.resetBoard();
        showTerminalBoard();
    }

    public void resumeGame() {
    	paused = false;
    	frontEndBoard.turnOn();
        titlePane.setVisible(false);
        menuPanel.setVisible(false);
        
    }

    private void showTerminalBoard() {
        backendBoard.showTerminalBoard();
    }

    public void startNewGame() {
        hideMainMenu();
        showTerminalBoard();
        frontEndBoard.turnOn();
    }

    
    
    
    
}