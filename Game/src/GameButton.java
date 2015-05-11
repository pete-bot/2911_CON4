import java.awt.Color;

import javax.swing.JButton;

/**
 * A game button.
 * @author z5028934
 *
 */
public class GameButton extends JButton{
	
	private int xPos;
	private int yPos;
	private int player;
	
	//TODO
	public GameButton(int x, int y) { 
		//setBackground(new Color(0x212121));
		xPos = x;
		yPos = y;
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
	
	// this is for the listener
	public void getValue(){
    	System.out.println("mouseclick");
    	System.out.println("Xpos: "+this.getXPos());
    	System.out.println("Ypos: "+this.getYPos());
    	System.out.println("Player: "+this.getPlayer());
    }

	
}
