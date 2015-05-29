import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Class responsible for rendering the lower statistics panel to the window
 *
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 */
public class StatsPanel extends JPanel {
    
	private static final long serialVersionUID = 1L;
    /**The move list for the right stats panel */
	private JList<String> moveList;
	private DefaultListModel<String> model; // Contents of the list
    /**The scroll window to show the move list */
	private JScrollPane turnTracker;
	/**Left and right panels. */
    private JPanel leftPanel;
    private JPanel rightPanel;

    // JLabels for the turn statistics
    private JLabel turnNum;
    private JLabel currPlayer;
    private JLabel lastMove;

    // Layout
    GridBagConstraints gbc;

	 /**
	  * method to construct the stats panel.
	  * @param parent
	  * 		The parent frontendstatistics panel
	  */
    public StatsPanel(FrontEndStatistics parent) {
        setBorder(BorderFactory.createLineBorder(Color.red));
        setLayout(new GridLayout());
        leftPanel = initLeftPanel();
        rightPanel = initRightPanel();

        add(leftPanel);
        add(rightPanel);

        turnTracker.setVisible(true);
    }

    /**
     * Adds the turn information to the scrolling list of turns made this game
     *
     * @param action
     *            The action (column) the player of this turn has chosen.
     * @param player
     *            The integer value of which player made this choice
     */
    public void addTurn(Action action, int player) {
        model.addElement(player + "[" + action.getColumn() + "]");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 150);
    }

    /**
     * Method that initialises the left panel
     *
     * @return 
     * 		Jpanel that stores the move list for this game.
     */
    public JPanel initLeftPanel() {

        JPanel leftStats = new JPanel();

        // Initialise the list of moves
        model = new DefaultListModel<String>();
        // Give JList the DefaultList form.
        moveList = new JList<String>(model);

        turnTracker = new JScrollPane(moveList);
        turnTracker.setMinimumSize(new Dimension(100, 100));
        leftStats.add(turnTracker);
        return leftStats;
    }

    /**
     * Method that initialsises the right panel for the game. This includes turn count etc.
     * @return
     * 		Jpanel for right side of game board.
     */
    public JPanel initRightPanel() {
        JPanel rightStats = new JPanel();

        rightStats.setLayout(new GridLayout(8, 1));
        turnNum = new JLabel();
        currPlayer = new JLabel();
        lastMove = new JLabel();

        rightStats.add(new JLabel(" "));
        rightStats.add(turnNum);
        rightStats.add(new JLabel(" "));
        rightStats.add(currPlayer);
        rightStats.add(new JLabel(" "));
        rightStats.add(lastMove);
        rightStats.add(new JLabel(" "));
        rightStats.add(new JLabel(" "));

        return rightStats;
    }

    /**
     * method to reset the stats panel.
     */
    public void resetStatsPanel() {
        model.clear();
    }

    /**
     * Method that updates the game move list.
     * @param turnNum
     * 		The trun number of the currently displayed move.
     * @param player
     * 		The current player for the displayed move.
     * @param prevMove
     * 		The previous move made by the other player.
     */
    private void updateLeftPanel(int turnNum, int player, Action prevMove) {
        model.addElement("Turn " + String.valueOf(turnNum) + ": [P="
                + String.valueOf(player) + "][M="
                + String.valueOf(prevMove.getColumn()) + "]");
        moveList.setSelectedIndex(turnNum);
    }
    
    
    /**
     * Method that updates the game move list.
     * @param turnNum
     * 		The trun number of the currently displayed move.
     * @param player
     * 		The current player for the displayed move.
     * @param prevMove
     * 		The previous move made by the other player.
     */
    private void updateRightPanel(int turn, int player, Action prevMove) {
        // Cap turn and player to avoid going to -1
        turn = (turn < 0) ? 0 : turn;
        player = (player < 0) ? 0 : player;
        int previousMove = (prevMove.getColumn() < 0) ? 0 : prevMove
                .getColumn();
        turnNum.setText("Turn: " + String.valueOf(turn));
        currPlayer.setText("Player: " + String.valueOf(player));
        lastMove.setText("Last Move: " + String.valueOf(previousMove));
    }

    /**
     * Updates the lower stats panel
     *
     * @param turn
     *            Representation of the turn with its panel values to update the
     *            lower GUI for statistics
     */
    public void updateStats(TurnSummary turn) {
        updateLeftPanel(turn.getTurnNumber(), turn.getCurrPlayer(),
                turn.getLastMove());
        updateRightPanel(turn.getTurnNumber(), turn.getCurrPlayer(),
                turn.getLastMove());
    }

    /**
     * Method to update the main statistics panel.
     * @param turn
     * 		The current turn number
     */
    public void viewStats(TurnSummary turn) {
        moveList.setSelectedIndex(turn.getTurnNumber());
        updateRightPanel(turn.getTurnNumber(), turn.getCurrPlayer(),
                turn.getLastMove());
    }


}
