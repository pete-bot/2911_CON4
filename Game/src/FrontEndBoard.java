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
import javax.swing.JPanel;

/*
 * This is the main grid of tokens
 */
public class FrontEndBoard extends JPanel implements MouseListener{
	
	// our colours 
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	private JPanel gridBoard;
    public GameButton[] buttons;
    private BackendBoard backendBoard; //Should be 'backendBoard'
    private int rows = 6;
    private int cols = 7;
    
    
	public FrontEndBoard(BackendBoard newGameBoard) {
		
		// I DO NOT LIKE HAVING THIS HERE - FIX AFTER SPRINT
		//XXX
		backendBoard = newGameBoard;
		
		
		buttons =  new GameButton[42];
		
		setLayout(new GridLayout(rows, cols));
		setSize(500,500);
		
		// this is to 'squash' the columns together - need to find better way
		// to do this
		Insets margin = new Insets(-3,0,-3,0);
		
		// create our button array
        for (int i = 0; i <  42; i++) {
        	
        	// this is dodgy - please change
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
        	
        	b.setIcon(new ImageIcon("circle101.png"));
        	b.setBorderPainted(false);
        	b.setMargin(margin);
        	setBorder(BorderFactory.createEmptyBorder());
            b.setRolloverEnabled(true);
            b.addMouseListener(this);
            this.add(b);
            buttons[i] = b;
        }

		
		setVisible(true);
	}

	// this highlights the column
	public void highlightColumn(Point cursor) {
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            
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

    
    // this is way too much for this function to be doing
    // we need to redesign the interface with buttons that are useful
    // so we dont have to do this
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
			updateBoardWithMove(b);
		
		
		// EXCEPTION HERE AT CHECK GOAL STATE. NEED TO CHECK!
		if(backendBoard.checkWinState()){
			if(backendBoard.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
			}else{
				System.out.println("PLAYER_2, you WIN!");
			}
			
			// need some win state indicator for front end
			// another method would fix this, but for now, we must fix the basic design
			
			return;
		}
		
		if(backendBoard.getTurn()%2==1 ){
			System.out.println("PLAYER_1, please enter your move:");
		}else{
			System.out.println("PLAYER_2, please enter your move:");
		}
		
		backendBoard.IncrementTurn();		
    	
    }

    
    // Updates the board with the next _legal_ move
    public void updateBoardWithMove(GameButton b){
    	int tilesOnBoard = 42;
    	for (int count = tilesOnBoard - (cols - b.getXPos()); count > 0; count -= 7){
    		GameButton currentButton = buttons[count];
    		if (currentButton.getPlayer() == 0){
    			if ( backendBoard.getTurn() % 2==0 ){
    				currentButton.setPlayer(1);
    				currentButton.setIcon(new ImageIcon("circle101_RED.png"));
    			} else{
    				currentButton.setPlayer(1);
    				currentButton.setIcon(new ImageIcon("circle101_YELLOW.png"));
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
		
        for (GameButton gameButton : buttons) {
        	gameButton.setIcon(new ImageIcon("circle101.png"));
        	gameButton.setPlayer(0);
            //gameButton.repaint();
        }
        

		setVisible(true);
    }
    
    //XXX BEWARE, THESE GUYS DON'T DO ANYTHING BUT ARE NEEDED APPARENTLY
    
    @Override
    public void mouseExited(MouseEvent e) { 
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    	//System.out.println("pointCursor: "+e.getLocationOnScreen());	
    }
    
    // use these for click and dragging
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }
    

}


