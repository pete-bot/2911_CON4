/**
 * 
 * @author WOBCON4
 *	Main function container. 
 *
 */
public class Game {

    private BackendBoard newBoard = new BackendBoard();
    private Window gameWindow = new Window(newBoard);

	/**
	 * Main function
	 * @param args
	 * 		Command line arguments.
	 */
    public static void main(String args[]) {
        Game newGame = new Game();
        newGame.playGame();
    }

    /**
     * The function that initiates the game.
     */
    public void playGame() {
        System.out
                .println("Initializing WOBCON4. Standby for de-Wobbification.");
        gameWindow.displayMenu();
    }
}
