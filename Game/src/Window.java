import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame{

    //This is for serialization; don't worry about it.
	private static final long serialVersionUID = 1L;
	private ButtonPanel buttonPanel = new ButtonPanel(this);
    private FrontEndBoard frontEndBoard;
    private BackendBoard backendBoard;
    private Dimension defaultSize = new Dimension(1024,768);


    public Window(BackendBoard newBoard) {
    	super("Connect Java: Advanced Wobfighter");
    	initFrontendBoard(newBoard);
        initWindow(newBoard);
    }

    //TODO Perhaps the initial window should display a resolution that gets saved as a pref.
    private void initWindow(BackendBoard newBoard) {
		setLayout(new BorderLayout());
        setSize(defaultSize);
        //setLocationRelativeTo(null); //What's this for?
        setResizable(false); //Do not allow the screen to be resized.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initFrontendBoard(newBoard);
        add(frontEndBoard, BorderLayout.NORTH);
        setVisible(true);
        displayMenu();
		//pack(); //Autosizes to match components
    }

    private void initFrontendBoard(BackendBoard newBoard) {
        backendBoard = newBoard;
        Window mainWindow = this;
        frontEndBoard = new FrontEndBoard(backendBoard, mainWindow);
        frontEndBoard.setEnabled(false);
        frontEndBoard.setVisible(false);
    }

    public void startNewGame() {
        //We'll also need to disable the menu.
        hideMainMenu();
        showTerminalBoard();
        frontEndBoard.turnOn();
    }

    //Initialize the backend terminal board
    private void showTerminalBoard() {
		System.out.println("Welcome to WOBCON4. Enjoy the game.");
		System.out.println("Initial Game State:");
		backendBoard.showTerminalBoard();
		System.out.println("User, please enter your column choice:");
    }

    public void resetWindow() {
    	frontEndBoard.turnOn();
		frontEndBoard.resetBoard();
		showTerminalBoard();
    }

    public void displayMenu() {
        //turn off and clear the board.
    	frontEndBoard.turnOff();
        frontEndBoard.resetBoard();
        add(buttonPanel, BorderLayout.CENTER);
		buttonPanel.setVisible(true);
    }

    private void hideMainMenu() {
        buttonPanel.setEnabled(false);
        buttonPanel.setVisible(false);
    }
}
