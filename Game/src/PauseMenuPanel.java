import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton resume = new JButton("");
    private JButton restartButton = new JButton("");
    private JButton exitButton = new JButton("");

    private JButton playervsPlayerGameButton = new JButton("");

    private JButton optionsButton = new JButton("");

    private Window window;

    // constructor
    public PauseMenuPanel(Window mainWindow) {

        // init buttons w/ listeners
        resume.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        javax.swing.border.Border empty = BorderFactory.createEmptyBorder();

        String runningDir = System.getProperty("user.dir");
        Path assetsLocation = Paths
                .get(runningDir.matches(".*src") ? runningDir.replaceFirst(
                        "src", "") + "assets/" : runningDir + "/assets");

        // TODO: change button icons here to suit aesthetic.

        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();

        // init bridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        // set menu border colour and opacity
        setBackground(new Color(119, 136, 153, 0));

        Path resumePath = Paths.get(assetsLocation + "/resume.png");
        ImageIcon resumeIcon = new ImageIcon(resumePath.toString());

        resume.setIcon(resumeIcon);
        resume.setOpaque(false);
        resume.setContentAreaFilled(false);
        resume.setBorderPainted(false);
        resume.setBorder(empty);
        add(resume, gbc);
        gbc.gridy++;

        Path optionsPath = Paths.get(assetsLocation + "/options.png");
        ImageIcon optionsIcon = new ImageIcon(optionsPath.toString());

        optionsButton.setIcon(optionsIcon);
        optionsButton.setOpaque(false);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setBorderPainted(false);
        optionsButton.setBorder(empty);
        add(optionsButton, gbc);
        gbc.gridy++;

        Path restartPath = Paths.get(assetsLocation + "/restart.png");
        ImageIcon restartIcon = new ImageIcon(restartPath.toString());

        restartButton.setIcon(restartIcon);
        restartButton.setOpaque(false);
        restartButton.setContentAreaFilled(false);
        restartButton.setBorderPainted(false);
        restartButton.setBorder(empty);
        add(restartButton, gbc);
        gbc.gridy++;

        Path exitPath = Paths.get(assetsLocation + "/quit.png");
        ImageIcon exitIcon = new ImageIcon(exitPath.toString());

        exitButton.setIcon(exitIcon);
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setBorder(empty);

        add(exitButton, gbc);

        // the fill instruction determines how to deal with the buttons if the
        // area the buttons
        // need to fill are larger than the buttons need. Ie, spread buttons
        // out, or whatever
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        // setBounds(500,500,500,500);

        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(resume)) {
            window.resumeGame();
        } else if (buttonPressed.equals(restartButton)) {
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton)) {
            System.exit(0);
        }
    }
}
