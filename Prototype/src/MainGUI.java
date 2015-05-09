import javax.swing.JFrame;

import java.awt.BorderLayout;

public class MainGUI extends JFrame {

	private ButtonPanel buttonPanel;
	private TextPanel textPanel;
	private GridBoard gridBoard;
	private TokenBag leftBag;
	private TokenBag rightBag;
	
	public MainGUI() {
		super("CONNECT FOUR");
		setLayout(new BorderLayout());
		setSize(1024,768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		buttonPanel = new ButtonPanel();
		gridBoard = new GridBoard();
		leftBag = new TokenBag();
		rightBag = new TokenBag();

		buttonPanel.setTextPanel(textPanel);
		
		add(buttonPanel, BorderLayout.NORTH);
		add(gridBoard, BorderLayout.CENTER);
		add(leftBag, BorderLayout.WEST);
		add(rightBag, BorderLayout.EAST);
		
		setVisible(true);
	}
}
