import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;


/*
 * Class responsible for rendering the lower statistics panel to the window
 */
public class StatsPanel extends JPanel{
	private JList moveList;
	private DefaultListModel model;	//Contents of the list
	private JScrollPane turnTracker;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	private FrontEndStatistics parentWindow;
	
	//Labels
	private JLabel turnNum;
	private JLabel currPlayer;
	private JLabel lastMove;
	
	//Layout
	GridBagConstraints gbc;

	
	/*
	 *  Constructor for the lower stats Panel
	 */
	public StatsPanel(FrontEndStatistics parent){
		parentWindow = parent;
		setBorder(BorderFactory.createLineBorder(Color.red));
		setLayout(new GridLayout());
		leftPanel = initLeftPanel();
		rightPanel = initRightPanel();

		add(leftPanel);
		add(rightPanel);
			
		turnTracker.setVisible(true);	
	}
	
	public JPanel initRightPanel(){
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
	
	/*
	 * Method that updates both the left and the right panel
	 */
	public void updateStats(TurnSummary turn){
		updateLeftPanel(turn.getTurnNumber(), turn.getCurrPlayer(), turn.getLastMove());
		updateRightPanel(turn.getTurnNumber(), turn.getCurrPlayer(), turn.getLastMove());
	}
	
	public void viewStats(TurnSummary turn){
		moveList.setSelectedIndex(turn.getTurnNumber());
		updateRightPanel(turn.getTurnNumber(), turn.getCurrPlayer(), turn.getLastMove());
	}
	
	/*
	 * Updates the left panel
	 */
	private void updateLeftPanel(int turnNum, int player, Action prevMove){
		model.addElement("Turn " + String.valueOf(turnNum) + ": [P=" + String.valueOf(player) + "][M=" + String.valueOf(prevMove.getColumn()) + "]");
		moveList.setSelectedIndex(turnNum);
	}
	
	/*
	 * Method that updates the values of the right panel
	 */
	private void updateRightPanel(int turn, int player, Action prevMove){
		turnNum.setText("Turn: " + String.valueOf(turn));
		currPlayer.setText("Player: " + String.valueOf(player));
		lastMove.setText("Last Move: " + String.valueOf(prevMove.getColumn()));
	}
	
	public JPanel initLeftPanel(){
		
		JPanel leftStats = new JPanel();
		
		//Initialise the list of moves
		model = new DefaultListModel();
		moveList = new JList(model);
		
		turnTracker = new JScrollPane(moveList);
		turnTracker.setMinimumSize(new Dimension(100, 100));
		leftStats.add(turnTracker);
		return leftStats;
	}
	
	//Add turn to list of elements
	public void addTurn(Action action, int player){
		model.addElement(player + "[" + action.getColumn() + "]");
	}
	
	@Override
	public Dimension getPreferredSize(){
		return new Dimension(150, 150);
	}
	
}
