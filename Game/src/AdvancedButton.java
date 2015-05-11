import java.awt.Color;

import javax.swing.JButton;


public class AdvancedButton extends JButton{
	
	private int xPos;
	private int yPos;
	private int player;
	
	public AdvancedButton(int i, double d) {
		//setBackground(new Color(0x212121));
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
	
	
	// this is for the listener
	public void getValue(){
    	System.out.println("mouseclick");
    	System.out.println("Xpos: "+this.getXPos());
    	System.out.println("Ypos: "+this.getYPos());
    	System.out.println("Player: "+this.getPlayer());
    }

	
}
