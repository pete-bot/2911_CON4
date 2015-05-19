import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton newGameButton = new JButton("New Game");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("Quit");
    private Window window;

    // constructor
    public ButtonPanel(Window mainWindow) {
    	
    	// init buttons w/ listeners
        newGameButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);
    	
        //TODO: change button icons here to suit aesthetic. 
        
        // create GridBagLayout et al
    	setLayout(new GridBagLayout());
        //this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        
        //init bridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        add(newGameButton, gbc);
        gbc.gridx++;

        add(restartButton, gbc);
        gbc.gridx++;
        
        add(exitButton, gbc);

        // the fill instruction determines how to deal with the buttons if teh area the buttons 
        // need to filla re larger than the buttons need. Ie, spread buttons out, or whatever
        // we are spreading horizontally for now. can change
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        // not used with gridbaglayout
        //setBounds(500, 500, 500, 500);

        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(newGameButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(restartButton)) {
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton)) {
            System.exit(0);
        }
    }
}
