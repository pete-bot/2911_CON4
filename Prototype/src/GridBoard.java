import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GridBoard extends JPanel{
	private JPanel gridBoard;
	
	public GridBoard() {
		int rows = 6;
		int cols = 7;
		
		setLayout(new GridLayout(rows, cols));
		setSize(500,500);
		
		for (int y = 0; y < cols; y++) {
		 for (int x = 0; x < rows; x++) {
		  JLabel b1 = new JLabel();
		     try {
		   	  Image img = ImageIO.read(getClass().getResource("circle50.png"));
		   	  Color c = new Color(255, 0, 0);
		   	  b1.setBackground(c);
		   	  b1.setIcon(new ImageIcon(img));
		     } catch (IOException e) {
		   		  e.printStackTrace();
		   		  
		     }
		  add(b1);
		 }
		}
		
		setVisible(true);
	}
}
