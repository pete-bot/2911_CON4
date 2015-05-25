// So this is now the dumb AI I think
public class MechanicalTurk {

    @SuppressWarnings("unused")
    private int AIclass; // TENTATIVE

    // mechanical turk constructor
    public MechanicalTurk(int AIclass) {
        this.AIclass = AIclass;
    }

    // if AIclass is class 0 (ie, training mode)
    @Override
    public Action getTurkMove(BackendBoard backendBoard) {
        // need a clever way of determining this once we have starting turn
        // choice
        int playerNumber = 2;

        // Math.random() returns a number between 0 and 1, mult by 10 and mod by
        // 7
        int turkColumn = (int) (10 * Math.random() % 7);
        Action turkMove = new Action(playerNumber, turkColumn);

        while (!backendBoard.isLegal(turkMove)) {
            turkColumn = (int) (10 * Math.random() % 7);
            turkMove = new Action(playerNumber, turkColumn);
        }

        return turkMove;
    }
}
