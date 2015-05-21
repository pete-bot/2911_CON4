import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

//import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton pvCPUButton = new JButton();
    private JButton restartButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton pvpButton = new JButton();
    private JButton resumeButton = new JButton();
    private JButton optionsButton = new JButton();
    private JButton spacer = new JButton();
    private Border emptyBorder = BorderFactory.createEmptyBorder();
    private Path assetsLocation;
    private ImageIcon pvCPUIcon;
    private ImageIcon pvpIcon;
    private ImageIcon optionsIcon;
    private ImageIcon spacerIcon;
    private ImageIcon quitIcon;
    private ImageIcon resumeIcon;
    private ImageIcon restartIcon;
    private ImageIcon exitIcon;
    private Window window;
    private Color defaultColor = new Color(119, 136, 153, 0);

    public MainMenuPanel(Window mainWindow) {
        setLayout(new GridBagLayout());
        setBackground(defaultColor);
        addMainMenuItems();
        window = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pvCPUButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(resumeButton)) {
            window.resumeGame();
        } else if (buttonPressed.equals(restartButton)) {
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton)) {
            System.exit(0);
        }
    }

    private void addMainMenuItems() {
        GridBagConstraints gbc = new GridBagConstraints();
        // init GridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        initIcons();
        spacer.setIcon(spacerIcon);
        pvCPUButton.setIcon(pvCPUIcon);
        pvpButton.setIcon(pvpIcon);
        optionsButton.setIcon(optionsIcon);
        exitButton.setIcon(quitIcon);

        initButton(spacer);
        initButton(pvCPUButton);
        initButton(pvpButton);
        initButton(optionsButton);
        initButton(exitButton);

        pvCPUButton.addActionListener(this);
        pvpButton.addActionListener(this);
        optionsButton.addActionListener(this);
        exitButton.addActionListener(this);

        add(spacer, gbc);
        gbc.gridy++;

        // TODO: change button icons here to suit aesthetic.

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

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
    }

    // This must be called before buttons can add icons
    private void initIcons() {
        // Get the running directory
        String runningDir = System.getProperty("user.dir");
        assetsLocation = Paths.get(runningDir.matches(".*src") ? runningDir
                .replaceFirst("src", "") + "assets/" : runningDir + "/assets");

        // Setup paths
        Path spacerIconPath = Paths.get(assetsLocation + "/spacer.png");
        Path pvCPUIconPath = Paths.get(assetsLocation + "/player_AI.png");
        Path pvpIconPath = Paths.get(assetsLocation + "/player_player.png");
        Path optionsIconPath = Paths.get(assetsLocation + "/options.png");
        Path quitIconPath = Paths.get(assetsLocation + "/quit.png");
        Path resumePath = Paths.get(assetsLocation + "/resume.png");
        Path restartPath = Paths.get(assetsLocation + "/restart.png");
        Path exitPath = Paths.get(assetsLocation + "/quit.png");

        // Setup icons
        spacerIcon = new ImageIcon(spacerIconPath.toString());
        pvCPUIcon = new ImageIcon(pvCPUIconPath.toString());
        pvpIcon = new ImageIcon(pvpIconPath.toString());
        optionsIcon = new ImageIcon(optionsIconPath.toString());
        quitIcon = new ImageIcon(quitIconPath.toString());
        resumeIcon = new ImageIcon(resumePath.toString());
        restartIcon = new ImageIcon(restartPath.toString());
        exitIcon = new ImageIcon(exitPath.toString());
    }

    private void removeMainMenuItems() {
        remove(pvCPUButton);
        remove(pvpButton);
        remove(optionsButton);
        remove(exitButton);
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

        Path optionsPath = Paths.get(assetsLocation + "/options.png");
        ImageIcon optionsIcon = new ImageIcon(optionsPath.toString());

        add(optionsButton, gbc);
        gbc.gridy++;

        initButton(restartButton);
        restartButton.setIcon(restartIcon);
        add(restartButton, gbc);
        gbc.gridy++;

        initButton(exitButton);
        exitButton.setIcon(exitIcon);
        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        setVisible(true);
    }

}
