import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.InputStream;

//import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    JPanel difficultyPanel = new JPanel();

    private JButton pvCPUButton = new JButton();
    private JButton restartButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton pvpButton = new JButton();
    private JButton resumeButton = new JButton();
    private JButton optionsButton = new JButton();
    @SuppressWarnings("unused")
    private JButton spacer = new JButton();
    private JButton easyButton = new JButton();
    private JButton mediumButton = new JButton();
    private JButton hardButton = new JButton();
    private JButton backButton = new JButton();

    private Border emptyBorder = BorderFactory.createEmptyBorder();

    private ImageIcon pvCPUIcon;
    private ImageIcon pvpIcon;
    private ImageIcon optionsIcon;
    @SuppressWarnings("unused")
    private ImageIcon spacerIcon;
    private ImageIcon quitIcon;
    private ImageIcon resumeIcon;
    private ImageIcon restartIcon;

    private ImageIcon easyIcon;
    private ImageIcon mediumIcon;
    private ImageIcon hardIcon;
    private ImageIcon backIcon;
    private GameAssets assets;

    private Window mainWindow;

    private Color defaultColor = new Color(255, 255, 235, 200);
    private boolean music = false;
    String runningDir = System.getProperty("user.dir");
    String soundFilePath = runningDir.matches("src") ? runningDir
            + "../assets/toto-africa.mid" : "assets/toto-africa.mid";

    InputStream in;
    AudioStream audioStream;

    public MainMenuPanel(Window mainWindow, GameAssets assets) {
        this.assets = assets;
        this.mainWindow = mainWindow;
        setLayout(new GridBagLayout());

        setOpaque(false);
        setBackground(defaultColor);

        setSize(new Dimension(100, 100));
        initIcons();
        addMainMenuItems();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pvCPUButton)) {
            mainWindow.selectDifficulty();
        } else if (buttonPressed.equals(pvpButton)) {
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(resumeButton)) {
            mainWindow.resumeGame();
        } else if (buttonPressed.equals(restartButton)) {
            mainWindow.resetWindow();
        } else if (buttonPressed.equals(optionsButton)) {
            try {
                this.musicToggle();
            } catch (Exception e1) {
                System.out.println("Music not loading, check your paths.");
            }
        }

        if (buttonPressed.equals(easyButton)) {
            System.out.println("Setting Easy AI");
            mainWindow.setDifficulty(Difficulty.EASY);
            hideDifficultypanel();
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(mediumButton)) {
            System.out.println("Setting medium AI");
            hideDifficultypanel();
            mainWindow.setDifficulty(Difficulty.MEDIUM);
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(hardButton)) {
            System.out.println("Setting Hard AI");
            hideDifficultypanel();
            mainWindow.setDifficulty(Difficulty.HARD);
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(backButton)) {
            mainWindow.resetWindow();
            mainWindow.hideGameBoard();
            hideDifficultypanel();
            removePauseMenuItems();
            System.out.println("Going back to menu");
            mainWindow.addMenu();
            mainWindow.displayMenu();
        } else if (buttonPressed.equals(exitButton)) {
            System.exit(0);
        }
    }

    public void addMainMenuItems() {
        GridBagConstraints gbc = new GridBagConstraints();
        // init GridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        initIcons();
        // spacer.setIcon(spacerIcon);
        pvCPUButton.setIcon(pvCPUIcon);
        pvpButton.setIcon(pvpIcon);
        optionsButton.setIcon(optionsIcon);
        exitButton.setIcon(quitIcon);

        // initButton(spacer);
        initButton(pvCPUButton);
        initButton(pvpButton);
        initButton(optionsButton);
        initButton(exitButton);

        pvCPUButton.addActionListener(this);
        pvpButton.addActionListener(this);
        optionsButton.addActionListener(this);
        exitButton.addActionListener(this);

        // add(spacer, gbc);
        // gbc.gridy++;

        add(pvCPUButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(pvpButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(optionsButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
    }

    public void hideDifficultypanel() {
        removeDifficultyItems();
        remove(difficultyPanel);
    }

    public void hideMainMenu() {
        removeMainMenuItems();
    }

    public void hidePauseMenu() {
        removePauseMenuItems();
    }

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
        b.setRolloverEnabled(false);
    }

    // This must be called before buttons can add icons
    private void initIcons() {
        // spacerIcon = assets.getAsset("spacer.png"); //Thought: why do we need
        // a transparent image?
        pvCPUIcon = assets.getAsset("new_game_button.png");
        pvpIcon = assets.getAsset("passnplay_button.png");
        optionsIcon = assets.getAsset("music_on.png");
        quitIcon = assets.getAsset("quit_button.png");
        resumeIcon = assets.getAsset("resume_game_button.png");
        restartIcon = assets.getAsset("restart_button.png");

        easyIcon = assets.getAsset("easy.png");
        mediumIcon = assets.getAsset("medium.png");
        hardIcon = assets.getAsset("hard.png");
        backIcon = assets.getAsset("back.png");

    }

    public void musicToggle() throws Exception {
        if (in == null && audioStream == null) {
            in = new FileInputStream(soundFilePath);
            audioStream = new AudioStream(in);
        }

        if (music == false) {
            System.out.println("playin' tunes!");
            AudioPlayer.player.start(audioStream);
            optionsButton.setIcon(assets.getAsset("music_off.png"));
            music = true;
        } else {
            System.out.println("stoppin' tunes :(");
            optionsButton.setIcon(assets.getAsset("music_on.png"));
            AudioPlayer.player.stop(audioStream);
            music = false;
        }
    }

    // fix tranparent panel issue
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    private void removeDifficultyItems() {
        remove(easyButton);
        remove(mediumButton);
        remove(hardButton);
        remove(backButton);
    }

    private void removeMainMenuItems() {
        remove(pvCPUButton);
        remove(pvpButton);
        remove(optionsButton);
        remove(exitButton);
    }

    private void removePauseMenuItems() {
        remove(resumeButton);
        remove(optionsButton);
        remove(restartButton);
        remove(backButton);
        remove(exitButton);
    }

    public void showDifficultyPanel() {

        difficultyPanel.setBorder(emptyBorder);
        difficultyPanel.setBackground(defaultColor);
        difficultyPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        // init GridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        initIcons();

        easyButton.setIcon(easyIcon);
        mediumButton.setIcon(mediumIcon);
        hardButton.setIcon(hardIcon);
        backButton.setIcon(backIcon);

        initButton(easyButton);
        initButton(mediumButton);
        initButton(hardButton);
        initButton(backButton);

        easyButton.addActionListener(this);
        mediumButton.addActionListener(this);
        hardButton.addActionListener(this);
        backButton.addActionListener(this);

        // add(spacer, gbc);
        // gbc.gridy++;

        difficultyPanel.add(easyButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(mediumButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(hardButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(backButton, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        difficultyPanel.setVisible(true);
        add(difficultyPanel, gbc);

    }

    public void showPauseMenu() {
        // Clear the panel of older items
        removeMainMenuItems();

        resumeButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        initButton(resumeButton);
        resumeButton.setIcon(resumeIcon);
        add(resumeButton, gbc);
        gbc.gridy++;

        add(optionsButton, gbc);
        gbc.gridy++;

        initButton(restartButton);
        restartButton.setIcon(restartIcon);
        add(restartButton, gbc);
        gbc.gridy++;

        initButton(backButton);
        backButton.setIcon(backIcon);
        add(backButton, gbc);
        gbc.gridy++;

        initButton(exitButton);
        exitButton.setIcon(quitIcon);
        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        // init key bindings
        final int InFocusWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
        final KeyStroke escapeStroke = KeyStroke.getKeyStroke(
                KeyEvent.VK_ESCAPE, 0);
        getInputMap(InFocusWindow).put(escapeStroke, "escapeSequence");
        getActionMap().put("escapeSequence", mainWindow.escapeAction);

        setVisible(true);
    }

}
