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

/*
 * Handles the generation of the Statistics Panel component
 */
public class FrontEndStatistics extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;
    static final int rows = 6;
    static final int cols = 7;
    private GridBagConstraints gbc;

    // List of game states
    private ArrayList<TurnSummary> gameSummary;

    private PlayArea turnPreview;
    private StatsPanel statsPanel = new StatsPanel(this);

    private Window parentWindow;
    private int visibleIndex;
    private int maximumIndex;

    // Buttons
    private JButton back;
    private JButton prev;
    private JButton next;
    
    GameAssets assets;

    /*
     * Constructor for Statistics Panel of game Window
     */
    public FrontEndStatistics(GameAssets assets, Window mainWindow) {
        super();

        visibleIndex = 0;
        maximumIndex = -1;

        // Assign parent window
        parentWindow = mainWindow;
        
        this.assets = assets; 

        // Button Inits
        initButtons();
        initButton(back);
        initButton(prev);
        initButton(next);

        back.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);

        turnPreview = new PlayArea(new Dimension(500, 400), assets,
                parentWindow); // Creates the preview board
        turnPreview.writeBoard(new int[rows][cols]);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Set grid bag layout
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        add(turnPreview, gbc); // Add the board preview to the layout

        // Layout for Prev button
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        add(prev, gbc);

        // Layout for Next butto
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        add(next, gbc);

        // Layout for Stats Panel
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridheight = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        add(statsPanel, gbc);

        // Add the back button;
        //gbc.gridx = 0;
        //gbc.gridy = 3;
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        add(back, gbc);

        // Visibilities
        turnPreview.setVisible(true);
        prev.setVisible(true);
        next.setVisible(true);
        statsPanel.setVisible(true);
        back.setVisible(true);

        // Variable inits
        gameSummary = new ArrayList<TurnSummary>(); // Create a new list of all
                                                    // turns

        // Create a special turn for node 1
        addTurn(new TurnSummary(0, new int[rows][cols], -1, new Action(-1)));

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(prev)) {
            if (visibleIndex > 0) {
                visibleIndex--;
                updateView(gameSummary.get(visibleIndex));
            }
        } else if (buttonPressed.equals(next)) {
            if (visibleIndex < maximumIndex) {
                visibleIndex++;
                updateView(gameSummary.get(visibleIndex));
            }
        }
        if (buttonPressed.equals(back)) {
            parentWindow.hideStatistics();
        }
    }

    /*
     * Method that creates a new turn and adds it to the list of all turns
     */
    public void addTurn(TurnSummary turn) {
        gameSummary.add(turn);
        maximumIndex++;
        updateAllStats(gameSummary.get(maximumIndex));
    }

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder());
        b.setRolloverEnabled(false);
    }

    /*
     * Method that initializes the buttons
     */
    public void initButtons() {
        back = new JButton();
        next = new JButton();
        prev = new JButton();
        back.setIcon(assets.getAsset("back_stats.png"));
        next.setIcon(assets.getAsset("right_arrow.png"));
        prev.setIcon(assets.getAsset("left_arrow.png"));
    }

    // Make the Statistics inactive
    public void turnOff() {
        back.setEnabled(false);
        back.setVisible(false);
        setEnabled(false);
        setVisible(false);
    }

    // Make the Statistics active
    public void turnOn() {
        back.setEnabled(true);
        back.setVisible(true);
        setEnabled(true);
        setVisible(true);
    }

    /*
     * Method that updates the entire statistics component
     */
    public void updateAllStats(TurnSummary turn) {
        turnPreview.writeBoard(turn.getBoardState()); // Write the new board
                                                      // preview
        statsPanel.updateStats(turn);
        visibleIndex = maximumIndex;
    }

    /*
     * For updating the statistics panel without adding a turn
     */
    public void updateView(TurnSummary turn) {
        turnPreview.writeBoard(turn.getBoardState()); // Write the new board
                                                      // preview
        statsPanel.viewStats(turn);
    }
}
