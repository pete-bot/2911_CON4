import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * TODO
 * - fix button scaling
 * - fill in buttons/borders properly
 * - generally improve visuals
 */

public class Window extends JFrame{

	// no idea what this is. Sanjay, help!??!?!
	private static final long serialVersionUID = 1L;
	private ButtonPanel buttonPanel;
    private TextPanel textPanel;
    private GridBoard gridBoard;
    private Board game;

    
    /**
     * Class constructor
     */
    public Window(Board newGame) {
    	super("Connect Java: Advanced Wobfighter");
		this.setLayout(new BorderLayout());
		///getContentPane().setBackground(Color.DARK_GRAY);
        this.setSize(700, 690);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game = newGame;
        
        // set our button interface
        buttonPanel = new ButtonPanel();
        buttonPanel.setTextPanel(textPanel);
        
        // create new gruidBoard
        gridBoard = new GridBoard();
        
		add(buttonPanel, BorderLayout.SOUTH);
		add(gridBoard, BorderLayout.NORTH);
		this.setVisible(true);
		
		
    }  
}