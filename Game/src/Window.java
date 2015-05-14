import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;

public class Window extends JFrame{

    //This is for serialization; don't worry about it.
	private static final long serialVersionUID = 1L;
	private ButtonPanel buttonPanel;
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(50,50);

    
    public Window(BackendBoard newBoard) {
    	super("Connect Java: Advanced Wobfighter");
        initWindow(newBoard);
        setVisible(true);
    }  

    //TODO Perhaps the initial window should display a resolution that gets saved as a pref.
    private void initWindow(BackendBoard newBoard) {
		setLayout(new BorderLayout());
        setSize(defaultSize); 
        //setLocationRelativeTo(null); //What's this for?
        setResizable(false); //Do not allow the screen to be resized.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // BUTTON MUST BE MADE BEFORE GRIDBOARD IS MADE
        buttonPanel = new ButtonPanel(this);
        backendBoard = newBoard;
        frontEndBoard = new FrontEndBoard(backendBoard, this);
        add(buttonPanel, BorderLayout.SOUTH);
		add(frontEndBoard, BorderLayout.NORTH);
		setVisible(true);
		initTerminal();
		//pack(); //Autosizes to match components
    }
    
    //Initialize the backend terminal board
    private void initTerminal() {
		System.out.println("Welcome to WOBCON4. Enjoy the game.");
		System.out.println("Initial Game State:");
		backendBoard.showTerminalBoard();
		System.out.println("User, please enter your column choice:");
    }
    
    public void resetWindow() {	
    	frontEndBoard.turnOn();
		frontEndBoard.resetBoard();
		initTerminal();
    }
    
    public void displayMenu() {
    	//frontEndBoard.turnOff();
        frontEndBoard.resetBoard();
        //Use a 'glassPane'? XXX
    }
}
