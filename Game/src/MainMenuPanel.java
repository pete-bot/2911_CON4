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

    private static final int InFocusWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final KeyStroke escapeStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_ESCAPE, 0);

    JPanel difficultyPanel = new JPanel();

    private JButton pvCPUButton = new JButton();
    private JButton restartButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton pvpButton = new JButton();
    private JButton resumeButton = new JButton();
    private JButton optionsButton = new JButton();
    @SuppressWarnings("unused")
    private JButton spacer = new JButton();
    private JButton easy = new JButton();
    private JButton medium = new JButton();
    private JButton hard = new JButton();
    private JButton back = new JButton();

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
    // music controller
    private boolean music = false;
    String soundFile = "../assets/toto-africa.mid";

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

            // DIFFICULTY CHOICE
        } else if (buttonPressed.equals(easy)) {
            System.out.println("Setting Easy AI");
            mainWindow.setDifficulty(1);
            hideDifficultypanel();
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(medium)) {
            System.out.println("Setting medium AI");
            hideDifficultypanel();
            mainWindow.setDifficulty(1);
            mainWindow.startNewGame();

        } else if (buttonPressed.equals(hard)) {
            System.out.println("Setting Hard AI");
            hideDifficultypanel();
            mainWindow.setDifficulty(1);
            mainWindow.startNewGame();

        } else if (buttonPressed.equals(back)) {
            mainWindow.resetWindow();
            mainWindow.hideGameBoard();
            hideDifficultypanel();
            removePauseMenuItems();
            System.out.println("Going back to menu");

            // this guy needs to redraw the menu
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
            in = new FileInputStream(soundFile);
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
        remove(back);
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

        easy.setIcon(easyIcon);
        easy.addActionListener(this);
        medium.setIcon(mediumIcon);
        medium.addActionListener(this);
        hard.setIcon(hardIcon);
        hard.addActionListener(this);
        back.setIcon(backIcon);
        back.addActionListener(this);

        // initButton(spacer);
        initButton(easy);
        initButton(medium);
        initButton(hard);
        initButton(back);

        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        back.addActionListener(this);

        // add(spacer, gbc);
        // gbc.gridy++;

        difficultyPanel.add(easy, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(medium, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(hard, gbc);
        gbc.gridy++;
        gbc.gridy++;

        difficultyPanel.add(back, gbc);

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

        initButton(back);
        back.setIcon(backIcon);
        add(back, gbc);
        gbc.gridy++;

        initButton(exitButton);
        exitButton.setIcon(quitIcon);
        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        // init key bindings
        getInputMap(InFocusWindow).put(escapeStroke, "escapeSequence");
        getActionMap().put("escapeSequence", mainWindow.escapeAction);

        setVisible(true);
    }

    public void showWinMessage() {
        removeMainMenuItems();

        // glass.setVisible(true);
        // glass.setLayout(new GridBagLayout());
        //
        // // set glassy pane behind win message
        // glass.setOpaque(false);
        // glass.setBackground(defaultColor);
        //
        // GridBagConstraints gbc = new GridBagConstraints();
        // gbc.gridx = 0;
        // gbc.gridy = 0;
        // gbc.insets = new Insets(2, 2, 2, 2);
        //
        // initButton(restartButton);
        // restartButton.setIcon(restartIcon);
        // glass.add(restartButton, gbc);
        // gbc.gridy++;
        //
        // initButton(exitButton);
        // exitButton.setIcon(quitIcon);
        // glass.add(exitButton, gbc);
        //
        // restartButton.addActionListener(this);
        // exitButton.addActionListener(this);
        //
        // glass.requestFocus();
        // glass.setVisible(true);
        // setVisible(true);

    }

}
