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

    private FrontendBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(1024, 700);
    private GridBagConstraints gbc;
    private GameAssets assets = new GameAssets();
    private MainMenuPanel menuPanel = new MainMenuPanel(this, this.assets);

    private JPanel titlePane;

    private BackgroundPanel background;

    // Provides escape behavior
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
    
    public void setBoardToPVP() {
    	frontEndBoard.setOpponentToHuman();
    }

    public Window(BackendBoard newBoard) {
        super("Prepare yourself, Wobke is coming.");

        this.setMinimumSize(new Dimension(800, 820));
        this.setLocationRelativeTo(null);
        
        
        initFrontendBoard(newBoard);
        initWindow(newBoard);
        pack();
    }

    public void addMenu() {
        menuPanel.addMainMenuItems();
    }

    public void displayMenu() {

        titlePane.setVisible(true);
        menuPanel.setVisible(true);
    }

    public void hideGameBoard() {
        frontEndBoard.turnOff();
    }

    private void hideMainMenu() {
        menuPanel.hideMainMenu();
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
        frontEndBoard = new FrontendBoard(backendBoard, mainWindow);
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

        titlePane.setBackground(new Color(255, 255, 235, 200));
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

    public void pauseGame() {
        frontEndBoard.turnOff();
        menuPanel.showPauseMenu();
    }

    public void resetBackEndBoard() {
        backendBoard.resetBoard();
    }

    public void resetWindow() {
        menuPanel.hideMainMenu();
        menuPanel.hidePauseMenu();
        titlePane.setVisible(false);
        frontEndBoard.turnOn();
        frontEndBoard.resetBoard();
    }

    public void resumeGame() {
        frontEndBoard.turnOn();
        menuPanel.hidePauseMenu();
    }

    public void selectDifficulty() {
        hideMainMenu();
        titlePane.setVisible(false);
        menuPanel.showDifficultyPanel();
    }

    public void setDifficulty(Difficulty difficulty) {
        frontEndBoard.setAI(difficulty);
    }

    public void startNewGame() {
        hideMainMenu();
        backendBoard.showTerminalBoard();
        frontEndBoard.turnOn();
    }

}