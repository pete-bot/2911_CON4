
/*
 * not sure if this is necessary - possibly useful to encapsulate the 'action' of a player
 * usfeful to check legality of move etc. 
 */

public class Action {

	private int player;
	private int move; //Column to supply a movement
	
	public Action(int newPlayer, int newColumnChoice){
		this.player = newPlayer;
		this.move = newColumnChoice;
	}
	
	// return the player number
	int getPlayer(){
		return this.player;
	}
	
	// return the player move
	int getColumn(){
		return this.move;
	}
	
	void showAction(){
		System.out.println("action_player:"+player);
		System.out.println("action_move:"+move);
		
	}

}
