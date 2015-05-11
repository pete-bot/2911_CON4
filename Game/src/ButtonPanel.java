import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
	private JButton restartButton;
	private JButton exitButton;
	private TextPanel textPanel;
    private Board backendBoard;
    private GridBoard visualBoard;
	
	// constructor
	public ButtonPanel() {
		setLayout(new FlowLayout());
		
		restartButton = new JButton("Restart");
		exitButton = new JButton("Quit");
		
	    restartButton.addActionListener(this);
	    exitButton.addActionListener(this);
		
		// add buttons to the whole panel class
		add(restartButton);
		add(exitButton);
	}
	
	// made a new function to set text panel's text.
	public void setTextPanel(TextPanel textPanel) {
		this.textPanel = textPanel;
	}

    public void addBoards(Board backendBoard, GridBoard visualBoard) {
        this.backendBoard = backendBoard;
        this.visualBoard = visualBoard;
    }
	
	public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(restartButton)) {
        	System.out.println("Restart Requested..");
            backendBoard.resetBoard();
            visualBoard.resetBoard(backendBoard);
        } else {
            System.exit(0);
        }
	}
}
