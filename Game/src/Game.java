/**
 * 
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
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
                .println("Initializing FOUR IN A ROW.");
        gameWindow.displayMenu();
    }
}
