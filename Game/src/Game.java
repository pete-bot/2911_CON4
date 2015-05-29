public class Game {
	
	/**
	 * 
	 * @param args
	 * 		Command line arguments. Not used (should remove?)
	 */
    public static void main(String args[]) {
        Game newGame = new Game();
        newGame.playGame();
    }
    
    /**an instance of the back end of the game - used to store the game state */
    private BackendBoard newBoard = new BackendBoard();
    /** an instance of the game JFrame - used to render the front end.*/
    private Window gameWindow = new Window(newBoard);

    /**
     * function to initiate the game.
     */
    public void playGame() {
        System.out
                .println("Initializing WOBCON4. Connection of up to four tokens is supported in this version.");
        gameWindow.displayMenu();
    }
}
