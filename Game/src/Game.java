public class Game {

    public static void main(String args[]) {
        Game newGame = new Game();
        newGame.playGame();
    }

    private BackendBoard newBoard = new BackendBoard();
    private Window gameWindow = new Window(newBoard);

    public BackendBoard getBoard() {
        return newBoard;
    }

    public void playGame() {
        System.out.println("Initiating WOBCON4. Standby for de-Wobbification.");
        gameWindow.displayMenu();
    }
}
