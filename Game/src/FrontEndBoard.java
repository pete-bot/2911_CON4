import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * This is the main grid of tokens
 */
public class FrontEndBoard extends JPanel implements MouseListener{
	
	// our colours 
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	private JPanel gridBoard;
    public GameButton[] buttonIcons;
    private GameButton inputButton;
    private BackendBoard backendBoard; //Should be 'backendBoard'
    private int rows = 6;
    private int cols = 7;
    private final int tilesOnBoard = 42;
    private Window mainWindow;
    private MechanicalTurk newTurk;
    
    
	public FrontEndBoard(BackendBoard newGameBoard, Window mainWindow) {
		
		// temporary 
		// this is a work around, we will need to collect this data from the 
		// menu option when it is implemented
		int AIclass = 0;
		newTurk = new MechanicalTurk(AIclass);
		
		//XXX
		backendBoard = newGameBoard;
		this.mainWindow = mainWindow; 
		
		buttonIcons =  new GameButton[42];
		setLayout(new GridLayout(rows, cols));
		setSize(500,500);
		
		// this is to 'squash' the columns together - need to find better way
		// to do this
		Insets margin = new Insets(-3,0,-3,0);
		
		
		
//		this.inputButton = new GameButton(0, 0);
//        this.inputButton.addActionListener(this);
//        this.add(inputButton);
//		
		
		// create our button array
        for (int i = 0; i <  42; i++) {
            //XXX
        	int currX = i%7;
        	int currY = 5-((int) Math.ceil(i/7));
        	final GameButton b = new GameButton(currX, currY); // why final?
        	
        	b.addActionListener(new ActionListener(){
        			@Override
        			public void actionPerformed(ActionEvent e){
	        			  getColumnInput(b);
	        		  }
        		  });
        	
        	b.setIcon(new ImageIcon("../assets/circle101.png"));
        	b.setBorderPainted(false);
        	b.setMargin(margin);
        	setBorder(BorderFactory.createEmptyBorder());
            b.setRolloverEnabled(true);
            b.addMouseListener(this);
            add(b);
            buttonIcons[i] = b;
        }

		
		setVisible(true);
	}

	
	// this highlights the column
	public void highlightColumn(Point cursor) {
        for (int i = 0; i < buttonIcons.length; i++) {
            JButton button = buttonIcons[i];
            
            // this uses the mouse location to determine which column to highlight
            Point buttonLocation = button.getLocationOnScreen();
            double west = buttonLocation.getX();
            double east = buttonLocation.getX() + button.getWidth();
            boolean inRow = cursor.getX() > west && cursor.getX() < east;
            button.setBackground(inRow ? new Color(0x6bb4e5) : null );

            }
    }
	
	
	// this is where the mouse position is determined and kept track of
    @Override
    public void mouseEntered(MouseEvent event) {
        highlightColumn(event.getLocationOnScreen());

    }
    
    
    // TODO
    // implement player choice (go first or second)
    // this will be pulled from the intro menu
    // this is essentially the 'primary function through which the game is played'
    public void getColumnInput(GameButton b){
  
       	Action newAction;
		if(backendBoard.getTurn()%2==0 ){
			// PLAYER1 input
			newAction = new Action(1, b.getXPos());
		}else{
			newAction = new Action(2, b.getXPos());
		}
	
		if(!backendBoard.isLegal(newAction)){
			// need some error indicator
			System.out.println("You have entered an invalid move, please try again.");
			return;
		}
		
		// update terminal rep
		backendBoard.makeMove(newAction);
		backendBoard.showTerminalBoard();
		
		// update SWING
		// need to have another method to colour correct button.
		updateBoardWithMove(b.getXPos());
	
		//XXX BEWARE, THERE BE WOBCKES HERE.
		if (backendBoard.checkWinState()){
			if(backendBoard.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
				mainWindow.resetWindow(); // at the moment, window resets at win
			}else{
				System.out.println("PLAYER_2, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
				mainWindow.resetWindow();
			}
			
			return;
		}
		
		if(backendBoard.getTurn()%2==1 ){
			System.out.println("PLAYER_1, please enter your move:");
		}else{
			System.out.println("PLAYER_2, please enter your move:");
		}
		
		backendBoard.IncrementTurn();		
    	
		// call AI here.
		turkMove(backendBoard);
		
		
    }

    // 
    public void turkMove(BackendBoard backendBoard){

		System.out.println("The Turk makes its move...");
    	Action turkMove = newTurk.getTurkMove(backendBoard);
		
		backendBoard.makeMove(turkMove);
		backendBoard.showTerminalBoard();
		updateBoardWithMove(turkMove.getColumn());
		
		if (backendBoard.checkWinState()){
			if(backendBoard.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
				mainWindow.resetWindow(); // at the moment, window resets at win
			}else{
				System.out.println("PLAYER_2, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
				mainWindow.resetWindow();
			}
			return;
		}
		
		backendBoard.IncrementTurn();
		
		System.out.println("Control has returned to the player.");
    }
    
    
    // Updates the board with the next _legal_ move
    public void updateBoardWithMove(int xPos){
    	int tilesOnBoard = 42;
    	for (int count = tilesOnBoard - (cols - xPos); count >= 0; count -= 7){
    		GameButton currentButton = buttonIcons[count];
    		if (currentButton.getPlayer() == 0){
    			if ( backendBoard.getTurn() % 2==0 ){
    				currentButton.setPlayer(1);
    				currentButton.setIcon(new ImageIcon("../assets/circle101_RED.png"));
    			} else{
    				currentButton.setPlayer(1);
    				currentButton.setIcon(new ImageIcon("../assets/circle101_YELLOW.png"));
    			}
    			break;
    		}
    	}
    }

    //This does not redisplay properly
    public void resetBoard() {
        backendBoard.resetBoard();
		
		// create our button array
		// this needs to simply repaint
		
        for (GameButton gameButton : buttonIcons) {
        	gameButton.setIcon(new ImageIcon("../assets/circle101.png"));
        	gameButton.setPlayer(0);
        }

		setVisible(true);
    }
    
    //XXX BEWARE, THESE GUYS DON'T DO ANYTHING BUT ARE NEEDED APPARENTLY
    @Override
    public void mouseExited(MouseEvent e) { 
    	doNothing();
    }
    public void mouseClicked(MouseEvent e) { 
    	doNothing();
    }
    @Override
    public void mousePressed(MouseEvent e) { 
    	doNothing();
    }
    @Override
    public void mouseReleased(MouseEvent e) { 
    	doNothing();
    }
    
    //Legibility function
    private void doNothing() {
    	return;
    }
}


