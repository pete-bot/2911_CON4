
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javafx.scene.layout.Border;


import javax.swing.JButton;
import javax.swing.JPanel;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton pvCPUButton = new JButton("");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("");
    private JButton pvpButton = new JButton("");
    private JButton optionsButton = new JButton("");
    private JButton spacer = new JButton("");
    private Border emptyBorder = BorderFactory.createEmptyBorder();

    private Path assetsLocation;
    private ImageIcon pvCPUIcon;
    private ImageIcon pvpIcon;
    private ImageIcon optionsIcon;
    private ImageIcon spacerIcon;
    private ImageIcon quitIcon;

    private Window window;

    // constructor
    public MainMenuPanel(Window mainWindow) {


        setLayout(new GridBagLayout());
        setBackground(new Color(119, 136, 153, 0));
        GridBagConstraints gbc = new GridBagConstraints();

        //init GridBagConstraints and inset (default spacing on buttons)
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

        //TODO: change button icons here to suit aesthetic.

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

        // the fill instruction determines how to deal with the buttons if the area the buttons
        // need to fill are larger than the buttons need. ie, spread buttons out, or whatever
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;

        window = mainWindow;
    }

    //This must be called before buttons can add icons
    private void initIcons() {
        //Get the running directory
        String runningDir = System.getProperty("user.dir");
        assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");

        //Setup paths
        Path spacerIconPath = Paths.get(assetsLocation + "/spacer.png");
        Path pvCPUIconPath = Paths.get(assetsLocation + "/player_AI.png");
        Path pvpIconPath = Paths.get(assetsLocation + "/player_player.png");
        Path optionsIconPath = Paths.get(assetsLocation + "/options.png");
        Path quitIconPath = Paths.get(assetsLocation + "/quit.png");

        //Setup icons
        spacerIcon = new ImageIcon(spacerIconPath.toString());
        pvCPUIcon = new ImageIcon(pvCPUIconPath.toString());
        pvpIcon = new ImageIcon(pvpIconPath.toString());
        optionsIcon = new ImageIcon(optionsIconPath.toString());
        quitIcon = new ImageIcon(quitIconPath.toString());
    }

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if ( buttonPressed.equals(pvCPUButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(restartButton)){
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton) ){
            System.exit(0);
        }
    }


}
