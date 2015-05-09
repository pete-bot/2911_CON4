import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class TokenBag extends JPanel {

	private JLabel piece;
	private Image img;
	private Color colour;	
	
	
	public TokenBag() {

		
		setLayout(new GridLayout(11,2));
		
		for (int i = 0; i < 22; i++) {
			piece = new JLabel();
			try {
				img = ImageIO.read(getClass().getResource("token.png"));
			   	piece.setIcon(new ImageIcon(img));
			} catch (IOException e) {
				e.printStackTrace();
			}
			add(piece);
		}
		


		
		
		setVisible(true);
	}
	
}
