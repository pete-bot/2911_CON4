import java.awt.FlowLayout;
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
		setLayout(new FlowLayout());

		newGameButton.addActionListener(this);
	    restartButton.addActionListener(this);
	    exitButton.addActionListener(this);

	    add(newGameButton);
		add(restartButton);
		add(exitButton);

        window = mainWindow;
	}

	public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if ( buttonPressed.equals(newGameButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(restartButton)){
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton) ){
            System.exit(0);
        }
	}
}
