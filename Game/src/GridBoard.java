import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
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
    public JButton[] buttons;
    
    
    
	public GridBoard() {
		buttons =  new JButton[42];
		
		int rows = 6;
		int cols = 7;
		
		setLayout(new GridLayout(rows, cols));
		setSize(500,500);
		
		// this is to 'squash' the columns together - need to find better way
		// to do this
		Insets margin = new Insets(-3,0,-3,0);
		
		// create our button array
    	this.setLayout(new GridLayout(6, 7));
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
            button.setBackground(inRow ? new Color(0x6bb4e5) : null);
        }
    }


	// this is where the mouse position is determined and kept track of
    @Override
    public void mouseEntered(MouseEvent event) {
        highlightColumn(event.getLocationOnScreen());
    }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseClicked(MouseEvent e) {
    	//System.out.println("pointCursor: "+e.getLocationOnScreen());
    	
    }

    
    // use these for click and dragging
    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    
    public int getColumnInput(AdvancedButton b){
    	  //System.out.println("Hi sanjay - this is a mouseclick :)");
    	  b.getValue();
    	  return b.getXPos();
    }
    
    
    

}


