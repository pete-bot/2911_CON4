import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

/*
 * Handles the generation of the Statistics Panel component
 */
public class FrontEndStatistics extends JPanel implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private GridBagConstraints gbc;
	static final int rows = 6;
	static final int cols = 7;
	
	//List of game states
	private ArrayList<TurnSummary> gameSummary;
	
	private PlayArea turnPreview;
	private StatsPanel statsPanel = new StatsPanel(this);
    private Border emptyBorder = BorderFactory.createEmptyBorder();
    private Color defaultColor = new Color(255, 255, 235, 100);
	private Window parentWindow;
	private int visibleIndex;
	private int maximumIndex;
	private int turnNumber;
	
	//Buttons
	private JButton back = new JButton("Back");
	private JButton prev = new JButton("Previous Turn");
	private JButton next = new JButton("Next Turn");
	private PauseButton pause = new PauseButton(parentWindow); 
	
	/*
	 * Constructor for Statistics Panel of game Window
	 */
	public FrontEndStatistics(GameAssets assets, Window mainWindow){
		super();
		
		visibleIndex = 0;
		maximumIndex = -1;
		
		setBackground(defaultColor);
		
		//Assign parent window
		parentWindow = mainWindow;
		
		//Button Inits
		initButton(back);
		initButton(prev);
		initButton(next);
		
		prev.setBackground(defaultColor);
		next.setBackground(defaultColor);
		setBackground(defaultColor);
		
		
		back.addActionListener(this);
		prev.addActionListener(this);
		next.addActionListener(this);
		
		
		turnPreview = new PlayArea(new Dimension(500, 400), assets, parentWindow);	//Creates the preview board
		turnPreview.writeBoard(new int[rows][cols]);
		turnPreview.setBorder(BorderFactory.createLineBorder(Color.black));
		turnPreview.setBackground(defaultColor);
		
		
		
		//Set grid bag layout
		setLayout(new GridBagLayout());
		gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.anchor = GridBagConstraints.CENTER;
		
		//add(turnPreview, gbc);	//Add the board preview to the layout
		
		turnPreview.setVisible(true);
		
		add(turnPreview, gbc);
		
		
		
		//Layout for Prev button
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		add(prev, gbc);
		
		//Layout for Next butto
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		add(next, gbc);
		
		//Layout for Stats Panel
		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.gridheight = 3;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.BOTH;
		add(statsPanel, gbc);
		
		//Add the back button;
		gbc.gridx = 0;
		gbc.gridy++;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.anchor = GridBagConstraints.CENTER;
		//add(back, gbc);
		
		//Visibilities
		prev.setVisible(true);
		next.setVisible(true);
		statsPanel.setVisible(true);
		back.setVisible(true);
		
			
		
        gbc.fill = GridBagConstraints.REMAINDER;

        setOpaque(false);

        // adjust position of menu button
        gbc.gridx = 0; // Any non-zero value doesn't seem to affect this much
        gbc.weightx = 1.0;
        
        gbc.gridy++;
        add(pause, gbc);
		
		
		
		//Variable inits
		turnNumber = 0;
		gameSummary = new ArrayList<TurnSummary>();		//Create a new list of all turns
		
		//Create a special turn for node 1
		addTurn(new TurnSummary(0, new int[rows][cols], -1, new Action(-1)));
		
	}
	
	
	/*
	 * Method that creates a new turn and adds it to the list of all turns
	 */
	public void addTurn(TurnSummary turn){
		gameSummary.add(turn);
		maximumIndex++;
		updateAllStats(gameSummary.get(maximumIndex));
	}
	
	/*
	 * Method that updates the entire statistics component
	 */
	public void updateAllStats(TurnSummary turn){
		turnPreview.writeBoard(turn.getBoardState());	//Write the new board preview
		statsPanel.updateStats(turn);
		visibleIndex = maximumIndex;
	}
	
	/*
	 * For updating the statistics panel without adding a turn
	 */
	public void updateView(TurnSummary turn){
		turnPreview.writeBoard(turn.getBoardState());	//Write the new board preview
		statsPanel.viewStats(turn);
	}
	
    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
        b.setRolloverEnabled(false);
    }
    
	//Make the Statistics active
	public void turnOn(){
		back.setEnabled(true);
		back.setVisible(true);
		setEnabled(true);
		setVisible(true);
	}
	
	//Make the Statistics inactive
	public void turnOff(){
		back.setEnabled(false);
		back.setVisible(false);
		setEnabled(false);
		setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JButton buttonPressed = (JButton) e.getSource();
		
		if(buttonPressed.equals(prev)){
			if(visibleIndex > 0){
				System.out.println("yee prev");
				visibleIndex--;
				updateView(gameSummary.get(visibleIndex));
			}
		} else if (buttonPressed.equals(next)){
			if(visibleIndex < maximumIndex){
				System.out.println("yee next");
				visibleIndex++;
				updateView(gameSummary.get(visibleIndex));
			}
		}
		if(buttonPressed.equals(back)){
			parentWindow.hideStatistics();
		}
	}
}
