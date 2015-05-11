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
public class GridBoard extends JPanel implements MouseListener{
	
	// our colours 
	private Color hoverBackgroundColor;
    private Color pressedBackgroundColor;
	
	private JPanel gridBoard;
    public AdvancedButton[] buttons;
    private Board board;
    
    
	public GridBoard(Board newGameBoard) {
		
		// I DO NOT LIKE HAVING THIS HERE - FIX AFTER SPRINT
		// DESIGN IS FUCKED GAAAAHHHH
		board = newGameBoard;
		
		
		buttons =  new AdvancedButton[42];
		
		int rows = 6;
		int cols = 7;
		
		setLayout(new GridLayout(rows, cols));
		setSize(500,500);
		
		// this is to 'squash' the columns together - need to find better way
		// to do this
		Insets margin = new Insets(-3,0,-3,0);
		
		// create our button array
    	this.setLayout(new GridLayout(6, 7));
    	this.setBackground(new Color(0x212121));
        for (int i = 0; i <  42; i++) {
        	
        	// this is dodgy - please change
        	final AdvancedButton b = new AdvancedButton((i%7),5-(Math.ceil(i/7)));
        	
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
            button.setRolloverIcon(inRow ? new ImageIcon("glow.png") : button.getIcon());
            //button.setBackground(bg);
            button.setBackground(inRow ? new Color(0xd9d9d9) : null);
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
    public void getColumnInput(AdvancedButton b){
  
	       	Action newAction;
			if(board.getTurn()%2==0 ){
				// PLAYER1 input
				newAction = new Action(1, b.getXPos());
			}else{
				newAction = new Action(2, b.getXPos());
			}
			
			if(!board.isLegal(newAction)){
				// need some error indicator
				System.out.println("You have entered an invalid move, please try again.");
				return;
			}
			
			// update terminal rep
			board.makeMove(newAction);
			board.showBoard();
			
			// update SWING
			// need to have another method to colour correct button.
			updateBoard(b);
//		if(board.getTurn()%2==0 ){
//			b.setIcon(new ImageIcon("circle101_RED_mkII.png"));
//		}else{
//			b.setIcon(new ImageIcon("circle101_YELLOW.png"));
//		}
		
		
		// EXCEPTION HERE AT CHECK GOAL STATE. NEED TO CHECK!
		if(board.checkWinState()){
			if(board.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
			}else{
				System.out.println("PLAYER_2, you WIN!");
			}
			
			// need some win state indicator for front end
			// another method would fix this, but for now, we must fix the basic design
			
			return;
		}
		
		if(board.getTurn()%2==1 ){
			System.out.println("PLAYER_1, please enter your move:");
		}else{
			System.out.println("PLAYER_2, please enter your move:");
		}
		
		board.IncrementTurn();		
    	
    }

    
    // this will update the JButton board with the appropriate tile
    public void updateBoard(AdvancedButton b){
    	for (int count = 42-(7-b.getXPos()); count < 42; count-=7){
    		if(buttons[count].getPlayer()==0){
    			if(board.getTurn()%2==0 ){
    				buttons[count].setPlayer(1);
    				buttons[count].setIcon(new ImageIcon("circle101_RED.png"));
    			}else{
    				buttons[count].setPlayer(1);
    				buttons[count].setIcon(new ImageIcon("circle101_YELLOW.png"));
    			}
    			break;
    		}
    	}
    }
    
    
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


