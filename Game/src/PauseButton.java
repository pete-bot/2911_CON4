import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseButton extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton pauseButton = new JButton("Menu");
    
    private Window window;
    
    // constructor
    public PauseButton(Window mainWindow) {
    	
    	//TODO: set pause button icon here
    	
    	
    	// init buttons w/ listeners
        pauseButton.addActionListener(this);
        
        // create GridBagLayout et al
    	setLayout(new GridBagLayout());
        //this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        add(pauseButton, gbc);
        
        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }
}
