import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ButtonPanel extends JPanel implements ActionListener {
	private JButton helloButton;
	private JButton exitButton;
	private TextPanel textPanel;
	
	// constructor
	public ButtonPanel() {
		setLayout(new FlowLayout());
		
		helloButton = new JButton("Hi");
		exitButton = new JButton("Bye");
		
	    helloButton.addActionListener(this);
	    exitButton.addActionListener(this);
		
		// add buttons to the whole panel class
		add(helloButton);
		add(exitButton);
	}
	
	// made a new function to set text panel's text.
	public void setTextPanel(TextPanel textPanel) {
		this.textPanel = textPanel;
	}
	
	public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(helloButton)) {
        	System.out.println("AYY");
        } else {
            System.exit(0);
        }
	}
}
