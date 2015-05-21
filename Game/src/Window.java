import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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

    public Window(BackendBoard newBoard) {
        super("Generic tile-themed sequence pattern based fun simulator.");
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

        ImageIcon asset = assets.getAsset("bg_pattern_2.jpg");
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
        titlePane.setPreferredSize(new Dimension(400, 300));
        titlePane.setOpaque(false);
        ImageIcon titleIcon = assets.getAsset("game_title.png");
        JLabel gameTitle = new JLabel();
        gameTitle.setIcon(titleIcon);
        titlePane.add(gameTitle);
    }

    private void initWindow(BackendBoard newBoard) {
        initBackground();
        initLayout();
        initTitle();

        add(titlePane, gbc);

        setPreferredSize(defaultSize);
        setSize(defaultSize);
        setResizable(true); // XXX Here for testing, remove later.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        gbc.gridy += 10;
        add(frontEndBoard, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;

        add(menuPanel, gbc);

        menuPanel.setVisible(false);
        titlePane.setVisible(false);
        // pack(); //Autosizes to match components
    }

    public void pauseGame() {
        frontEndBoard.turnOff();
        titlePane.setVisible(true);
        menuPanel.showPauseMenu();
    }

    public void resetWindow() {
        titlePane.setVisible(false);
        menuPanel.setVisible(false);
        frontEndBoard.turnOn();
        frontEndBoard.resetBoard();
        showTerminalBoard();
    }

    public void resumeGame() {
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
        pack();
    }

}