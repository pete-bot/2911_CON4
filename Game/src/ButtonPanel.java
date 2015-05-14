import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton restartButton;
	private JButton exitButton;
    private Window gameWindow; //Needed to refresh for restarts
	
	// constructor
	public ButtonPanel(Window gameWindow) {
		setLayout(new FlowLayout());
		
		restartButton = new JButton("Restart");
		exitButton = new JButton("Quit");
		
	    restartButton.addActionListener(this);
	    exitButton.addActionListener(this);
		
		// add buttons to the whole panel class
		add(restartButton);
		add(exitButton);

        this.gameWindow = gameWindow;
	}
	
	public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(restartButton)) {
        	System.out.println("Restart Requested...");
            gameWindow.resetWindow();
        } else {
            System.exit(0);
        }
	}
}
