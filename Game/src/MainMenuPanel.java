
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.scene.layout.Border;

import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton plavervsAIGameButton = new JButton("");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("");

    private JButton playervsPlayerGameButton = new JButton("");

    private JButton optionsButton = new JButton("");
    private JButton spacer = new JButton("");

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

        
        javax.swing.border.Border empty = BorderFactory.createEmptyBorder();
        
        String runningDir = System.getProperty("user.dir");
        Path assetsLocation = Paths.get( runningDir.matches(".*src") ? runningDir.replaceFirst("src", "") + "assets/" : runningDir + "/assets");
        Path spacerIcon = Paths.get(assetsLocation + "/spacer.png");
        ImageIcon space_Icon = new ImageIcon(spacerIcon.toString());
        
        spacer.setIcon(space_Icon);
        spacer.setOpaque(false);
        spacer.setContentAreaFilled(false);
        spacer.setBorderPainted(false);
        spacer.setBorder(empty);
        
        add(spacer, gbc);
        gbc.gridy++;
        
        
        
        // set menu border colour and opacity
        setBackground(new Color(119, 136, 153, 0));

    	// init buttons w/ listeners
        plavervsAIGameButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);
        

        
        Path playerVsAIIcon = Paths.get(assetsLocation + "/player_AI.png");
        ImageIcon playerAI = new ImageIcon(playerVsAIIcon.toString());
        
        Path playerVsPlayerIcon = Paths.get(assetsLocation + "/player_player.png");
        ImageIcon playerPlayer = new ImageIcon(playerVsPlayerIcon.toString());
        
        Path optionsIcon = Paths.get(assetsLocation + "/options.png");
        ImageIcon options = new ImageIcon(optionsIcon.toString());
        
        Path quitIcon = Paths.get(assetsLocation + "/quit.png");        
        ImageIcon quit = new ImageIcon(quitIcon.toString());
        
        
        plavervsAIGameButton.setIcon(playerAI);
        playervsPlayerGameButton.setIcon(playerPlayer);
        optionsButton.setIcon(options);
        exitButton.setIcon(quit);
        
        
        // set opacity of each button
        plavervsAIGameButton.setOpaque(false);
        plavervsAIGameButton.setContentAreaFilled(false);
        plavervsAIGameButton.setBorderPainted(false);
        plavervsAIGameButton.setBorder(empty);
        
        playervsPlayerGameButton.setOpaque(false);
        playervsPlayerGameButton.setContentAreaFilled(false);
        playervsPlayerGameButton.setBorderPainted(false);
        playervsPlayerGameButton.setBorder(empty);
        
        
        optionsButton.setOpaque(false);
        optionsButton.setContentAreaFilled(false);
        optionsButton.setBorderPainted(false);
        optionsButton.setBorder(empty);
        
        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setBorder(empty);
        
        
        //TODO: change button icons here to suit aesthetic.

        add(plavervsAIGameButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(playervsPlayerGameButton, gbc);
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
