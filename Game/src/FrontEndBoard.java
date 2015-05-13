import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * This is the main grid of tokens
 */
public class FrontEndBoard extends JPanel 
	implements MouseListener, MouseMotionListener {
	
	// our colours 
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	private JPanel gridBoard;
    private GameButton[] buttonIcons; // XXX Why was this public??
    private GameButton inputButton;
    private BackendBoard backendBoard; //Should be 'backendBoard'
    private int rows = 6;
    private int cols = 7;
    private final int tilesOnBoard = 42;
    private Window mainWindow;
    private MechanicalTurk newTurk;
    private PlayArea playArea;
    private Path assetsPath; //Does this need to be a member? or can it just be computed at runtime?
    
    /*
     *                               GRAPHIC IMAGE FILES
     *  Uncomment:
     *      (1) if you're sanjay and windows is a piece of shit
     *      (2) everybody else
     */
    
    
    //     (1) SANJAY: WINDOWS ECLIPSE
    //     private static final String assLoc = "assets/";
            
    //     (2) EVERYONE ELSE: 
    private static final String assLoc = "../assets/"; 
    
    private static final String emptyButton = assLoc + "sketch_circle_empty.png";
    private static final String redToken = assLoc + "sketch_circle_red.jpg";
    private static final String yellowToken = assLoc + "sketch_circle_yellow.jpg";
    
    
    
	public FrontEndBoard(BackendBoard newGameBoard, Window mainWindow) {
		
		// temporary 
		// this is a work around, we will need to collect this data from the 
		// menu option when it is implemented
		int AIclass = 0; //What does AIclass do?
		newTurk = new MechanicalTurk(AIclass);
		
		backendBoard = newGameBoard;
		this.mainWindow = mainWindow; 
		
		assetsPath = FileSystems.getDefault().getPath("assets");
		System.out.println(assetsPath.toString());
		
		Dimension size = new Dimension(500,500);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(size);
		layeredPane.setOpaque(true);
		layeredPane.setVisible(true);
		layeredPane.setEnabled(true);
		this.add(layeredPane);
		
		//buttonIcons =  new GameButton[42];
		//setLayout(new GridLayout(rows, cols));
		//setSize(size);
		
		// this is to 'squash' the columns together - need to find better way
		// to do this
		//Setup the clickable play area.
		/*
		playArea = new PlayArea(new Color(60, 58, 232, 255), size); //Transparent play area
		playArea.addMouseListener(this);
		playArea.addMouseMotionListener(this);
		playArea.setLayout(new BoxLayout(playArea, BoxLayout.X_AXIS));
		add(playArea);
		*/
		JPanel playArea = new JPanel();
		playArea.setBackground(Color.YELLOW);
		layeredPane.add(playArea, JLayeredPane.DRAG_LAYER);
		
		// create our button array
		/*
		Insets margin = new Insets(-3,-3,-3,-3);
        for (int i = 0; i <  42; i++) {
            //XXX
        	int currX = i % 7;
        	int currY = 5 - ((int) Math.ceil( i/7 ));
        	final GameButton b = new GameButton(currX, currY); // why final?
        	
        	b.setIcon(new ImageIcon(assLoc + "sketch_circle_empty.png"));
        	b.setBorderPainted(false);
        	b.setMargin(margin);
        	setBorder(BorderFactory.createEmptyBorder());
            b.setRolloverEnabled(true);
        	b.setVisible(true);
        	playArea.add(b);
            buttonIcons[i] = b;
        }
		*/
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
			mainWindow.displayMenu();
			if(backendBoard.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
			}else{
				System.out.println("PLAYER_2, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
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

    // TODO XXX
    // AI code should not persist in this class. 
    // Theoretically this should be a 'secondPlayer' or 'competitor' method.
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
			}else{
				System.out.println("PLAYER_2, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
			}
			mainWindow.resetWindow();
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
    				currentButton.setIcon(new ImageIcon(assLoc +  "sketch_circle_red.jpg"));
    			} else{
    				currentButton.setPlayer(1);
    				currentButton.setIcon(new ImageIcon(assLoc + "sketch_circle_yellow.jpg"));
    			}
    			break;
    		}
    	}
    }

    public void resetBoard() {
        backendBoard.resetBoard();
		
        for (GameButton gameButton : buttonIcons) {
        	gameButton.setIcon(new ImageIcon(assLoc + "sketch_circle_empty.png"));
        	gameButton.setPlayer(0);
        }
    }

    //MouseListener and MouseMotionListener overrides
    @Override
    public void mouseEntered(MouseEvent event) {
        //highlightColumn(event.getLocationOnScreen());
    }
    
    @Override
    public void mouseExited(MouseEvent e) { 
    	doNothing();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) { 
    	//XXX Testing
    	int x=e.getX();
        int y=e.getY();
        Point point = e.getPoint();
        System.out.println(point);
        System.out.println("x:" + x + ", y:" + y); //these co-ords are relative to the component
    }
    
    @Override
    public void mousePressed(MouseEvent e) { 
    	doNothing();
    }
    
    @Override
    public void mouseReleased(MouseEvent e) { 
    	//System.out.println(e.getLocationOnScreen());
    	doNothing();
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
    	System.out.println(e.getLocationOnScreen());
		// TODO Auto-generated method stub
	}
    
    //Legibility function
    private void doNothing() {
    	return;
    }
    
    public void disable() {
    	setVisible(false);
    	for (GameButton b : buttonIcons) {
            b.setEnabled(false);    		
    	}
    }
    
    public void enable() {
    	setVisible(true);
    	for (GameButton b : buttonIcons) {
    		b.setEnabled(true);
    	}
    }


}


