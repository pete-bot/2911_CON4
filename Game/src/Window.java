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

    //This is for serialization; don't worry about it.
	private static final long serialVersionUID = 1L;
	private ButtonPanel buttonPanel;
    private TextPanel textPanel;
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private int turnCount; // Peter does not like this.

    
    /**
     * Class constructor
     */
    public Window(BackendBoard newBoard) {
    	super("Connect Java: Advanced Wobfighter");
        initWindow(newBoard);
        setVisible(true);
    }  

    private void initWindow(BackendBoard newBoard) {
		setLayout(new BorderLayout());
		///getContentPane().setBackground(Color.DARK_GRAY);
        setSize(700, 690);
        setLocationRelativeTo(null);
        setResizable(false);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Button must be made before gridBoard is made

        // set our button interface
        buttonPanel = new ButtonPanel(this);
        buttonPanel.setTextPanel(textPanel);

        // create new gridBoard
        
        backendBoard = newBoard;
        frontEndBoard = new FrontEndBoard(backendBoard, this);
        add(buttonPanel, BorderLayout.SOUTH);
		add(frontEndBoard, BorderLayout.NORTH);
		
		
		
		// this is only here temporarily
		// this is a bad design
		System.out.println("Welcome to WOBCON4. Enjoy the game.");
		System.out.println("Initial Game State:");
		backendBoard.showTerminalBoard();
		System.out.println("PLAYER_1, please enter your column choice:");
		
		setVisible(true);
		//pack(); //Autosizes to match components
    }
    
    // Modified initialization for window.
    // Resets the game and redisplays the new variant.
    public void resetWindow() {	
    	frontEndBoard.enable();
		frontEndBoard.resetBoard();

		//XXX
		System.out.println("Welcome to WOBCON4. Enjoy the game.");
		System.out.println("Initial Game State:");
		backendBoard.showTerminalBoard();
		System.out.println("PLAYER_1, please enter your column choice:");
    }
    
    public void displayMenu() {
    	frontEndBoard.disable();
    }
}
