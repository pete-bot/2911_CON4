public class Game {
	
	private BackendBoard newBoard;

	public static void main(String args[]){
		Game newGame = new Game();
		newGame.playGame();
	}

	public Game(){
		newBoard = new BackendBoard();
	}
	
	public BackendBoard getBoard(){
		return newBoard; 
	}
	
	/**
	 * at the moment, this will provide enjoyable connect 4 
	 * entertainment for up to two people at once. be advised: you must provide your
	 * own second player
	 */
	public void playGame(){
		System.out.println("Initiating WOBCON4. Standby for de-Wobbification.");
		@SuppressWarnings("unused")
        Window gameWindow = new Window(newBoard);
	}
}
