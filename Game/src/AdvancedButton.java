import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AdvancedButton extends JButton {
	
	private int xPos;
	private int yPos;
	private int player;
	
	public AdvancedButton(int i, double d) {
		xPos = i;
		yPos = (int)d;
		player = 0;
	}

	
	public int getXPos(){
		return xPos;
	}
	
	public int getYPos(){
		return yPos;
	}
	
	public int getPlayer(){
		return player;
	}	
	
	public void setPlayer(int newPlayer){
		player = newPlayer;
	}
	
	public void getValue(){
    	System.out.println("mouseclick");
    	System.out.println("Xpos: "+this.getXPos());
    	System.out.println("Ypos: "+this.getYPos());
    	System.out.println("Player: "+this.getPlayer());
    }

	
}
