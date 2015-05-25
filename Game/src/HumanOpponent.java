public class HumanOpponent extends Opponent {
    private BackendBoard backendBoard;
    private FrontendBoard frontendBoard;

    public HumanOpponent(FrontendBoard frontendBoard, BackendBoard backendBoard) {
        this.backendBoard = backendBoard;
        this.frontendBoard = frontendBoard;
    }

    // As a human player in this current status is a 'pass and play', there is
    // no move to make. Therefore, control gets handed back over to frontend
    // board.
    @Override
    public Action getMove() {
        return null;
    }

}
