
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton plavervsAIGameButton = new JButton("Player vs AI Game");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("Quit");

    private JButton playervsPlayerGameButton = new JButton("Player vs Player Game");

    private JButton optionsButton = new JButton("Options");
    private JButton genericButton_1 = new JButton("Generic Button_1 ");
    private JButton genericButton_2 = new JButton("Generic Button_2 ");



    private Window window;


    // constructor
    public MainMenuPanel(Window mainWindow) {

        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        //this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();

        //init bridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        String runningDir = System.getProperty("user.dir");
        Path assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");

        //bg.toString()

        // set menu border colour and opacity
        setBackground(new Color(119, 136, 153, 220));

    	// init buttons w/ listeners
        plavervsAIGameButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        //TODO: change button icons here to suit aesthetic.



        add(plavervsAIGameButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(playervsPlayerGameButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(optionsButton, gbc);
        gbc.gridy++;


        gbc.gridy+=2;

        add(exitButton, gbc);

        // the fill instruction determines how to deal with the buttons if the area the buttons
        // need to fill are larger than the buttons need. ie, spread buttons out, or whatever
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 1;

        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if ( buttonPressed.equals(plavervsAIGameButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(restartButton)){
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton) ){
            System.exit(0);
        }
    }


}
