
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton resume = new JButton("Resume");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("Quit");
    
    private JButton playervsPlayerGameButton = new JButton("Player vs Player Game (NULL)");
    
    private JButton optionsButton = new JButton("Options");
    private JButton genericButton_1 = new JButton("Generic Button_1 ");
    private JButton genericButton_2 = new JButton("Generic Button_2 ");
    
    
    private Window window;
    

    // constructor
    public PauseMenuPanel(Window mainWindow) {
    	
    	// init buttons w/ listeners
        resume.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        //TODO: change button icons here to suit aesthetic. 
        
        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        
        //init bridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        add(resume, gbc);
        gbc.gridy++;
        
        add(optionsButton, gbc);
        gbc.gridy++;
        
        add(restartButton, gbc);
        gbc.gridy++;

        add(exitButton, gbc);
        
        
        // the fill instruction determines how to deal with the buttons if the area the buttons 
        // need to fill are larger than the buttons need. Ie, spread buttons out, or whatever
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        //setBounds(500,500,500,500);

        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if ( buttonPressed.equals(resume)) {
            window.resumeGame();
        } else if (buttonPressed.equals(restartButton)){
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton) ){
            System.exit(0);
        }
    }
}
