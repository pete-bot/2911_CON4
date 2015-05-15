import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * This is the main grid of tokens
 */
public class FrontEndBoard extends JPanel
	implements MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    // our colours, should be updated to match the palette
    private Color gridColor = new Color(60, 58, 232, 255);

    Dimension gridSize = new Dimension(700,700);
    private Token[] gameTokens = new Token[42];
    private BackendBoard backendBoard; //Should be 'backendBoard'
    private final int rows = 6;
    private final int cols = 7;
    private final int tilesOnBoard = 42;
    private Window mainWindow;
    private MechanicalTurk newTurk;
    private PlayArea playArea;
    private GridLayout frontEndBoardLayout = new GridLayout(rows, cols);

    //private static final String assLoc = "../assets/";
    //Paths accepts windows or *nix filepath structures for its argument and converts accordingly
    Path assLoc = Paths.get("../assets/");
    Path blankTokenPath = Paths.get(assLoc + "/circle100.png");
    Path glowingTokenPath = Paths.get(assLoc + "/glow.png");
    Path redTokenPath = Paths.get(assLoc + "/sketch_circle_red.jpg");
    Path yellowTokenPath = Paths.get(assLoc + "/sketch_circle_yellow.jpg");
    Path winTokenPath = Paths.get(assLoc + "/win.png");

    private ImageIcon blankTokenIcon = new ImageIcon(blankTokenPath.toString());
    private ImageIcon glowingTokenIcon = new ImageIcon(glowingTokenPath.toString());
    private ImageIcon redTokenIcon = new ImageIcon(redTokenPath.toString());
    private ImageIcon yellowTokenIcon = new ImageIcon(yellowTokenPath.toString());
    private ImageIcon winTokenIcon = new ImageIcon(winTokenPath.toString());

	public FrontEndBoard(BackendBoard backendBoard, Window mainWindow) {
		super();
		System.out.println(assLoc);

		// temporary
		// this is a work around, we will need to collect this data from the
		// menu option when it is implemented
		int AIclass = 0; //What does AIclass do?
		newTurk = new MechanicalTurk(AIclass);

		this.backendBoard = backendBoard;
		this.mainWindow = mainWindow;

		FlowLayout panelLayout = new FlowLayout(FlowLayout.CENTER);
		panelLayout.setVgap(20); //FIXME This needs to be replaced with empty objects above and below.
		setLayout(panelLayout);

		//Dimension parentSize = mainWindow.getSize();
		//int margin = 100;
		//TODO although this is a smarter sizing behavior, we will need to add checks for our min size.
		//So far, with the current tokens, 650,600 seem the smallest sensible grid size. (needs to be slightly off since it's 6x7)
		//Dimension gridSize = new Dimension((int) parentSize.getHeight() - margin, (int) parentSize.getHeight() - margin);
		//System.out.println(gridSize.toString());
		setSize(gridSize);

		//Setup the clickable play area.
		frontEndBoardLayout.setHgap(new Integer(2));
		frontEndBoardLayout.setVgap(new Integer(2));
		playArea = new PlayArea(gridColor, gridSize);
		playArea.addMouseListener(this);
		playArea.addMouseMotionListener(this);
		playArea.setLayout(frontEndBoardLayout);
		add(playArea);

		for (int i = 0; i < 42; i++) {
        	int currX = i % 7;
        	int currY = 5 - ((int) Math.ceil( i/7 ));
		    Token token = new Token(currX, currY, blankTokenIcon);
		    Dimension iconSize = new Dimension(blankTokenIcon.getIconHeight(), blankTokenIcon.getIconWidth());
		    token.setPreferredSize(iconSize);
		    playArea.add(token);
		    gameTokens[i] = token;
		}
	}


	// this highlights the column
	public void highlightColumn(MouseEvent cursor) {
	    //Change the icons of the tokens that are in the rows for the given column
        for (int i = 0; i < gameTokens.length; i++) {
            Token token = gameTokens[i];

            // this uses the mouse location to determine which column to highlight
            Point tokenLocation = token.getLocation();
            double beginRange = tokenLocation.getX();
            double endRange = tokenLocation.getX() + token.getWidth();
            boolean inRow = cursor.getX() > beginRange && cursor.getX() < endRange;
            boolean isBlankIcon = (token.getPlayer() == 0);
            if ( inRow && isBlankIcon ) {
                token.setIcon(glowingTokenIcon);
            } else if ( !inRow && isBlankIcon ) {
                token.setIcon(blankTokenIcon);
            }
        }
    }

	// this highlights the win
	public void highlightWin(ArrayList<Point> winList) {
		Iterator<Point> winIterator = winList.iterator();
		while (winIterator.hasNext()) {
			Point winCoords = winIterator.next();
			System.out.println("winCoords = " + winCoords.toString());
			int indexToGet = (winCoords.x) * (cols) + (cols-winCoords.y);
			Token t = gameTokens[42 - indexToGet];
			t.setIcon(winTokenIcon);
		}
    }

    // TODO
    // implement player choice (go first or second)
    // this will be pulled from the intro menu
    // this is essentially the 'primary function through which the game is played'
    public void getUserMove(Action newAction){
		if( backendBoard.isLegal(newAction) ){

		    updateBoardWithMove(newAction.getColumn());
		    // Update terminal
		    backendBoard.makeMove(newAction);
		    backendBoard.showTerminalBoard();

		    ArrayList<Point> winList = backendBoard.checkWinState(newAction);

		    // Game win found?
		    if (!winList.isEmpty()){
		        highlightWin(winList);
		        if(backendBoard.getTurn()%2==0 ){
		            System.out.println("PLAYER_1, you WIN!");
		            JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
		        }else{
		            System.out.println("PLAYER_2, you WIN!");
		            JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
		        }
		        return;
		    }

		    //Otherwise game continues
		    if (backendBoard.getTurn() % 2==1 ){
		        System.out.println("PLAYER_1, please enter your move:");
		    }else{
		        System.out.println("PLAYER_2, please enter your move:");
		    }

		    backendBoard.IncrementTurn();

		    // call AI here.
		    turkMove(backendBoard);
		} else{
		    System.out.println("You have entered an invalid move, please try again.");
		}
    }

    // FIXME
    // AI code should not persist in this class.
    // Theoretically this should be a 'secondPlayer' or 'competitor' method.
    // Also, this method should be making calls through getUserMove();
    public void turkMove(BackendBoard backendBoard){

		System.out.println("The Turk makes its move...");
    	Action turkMove = newTurk.getTurkMove(backendBoard);

		backendBoard.makeMove(turkMove);
		backendBoard.showTerminalBoard();
		updateBoardWithMove(turkMove.getColumn());



		if (!backendBoard.checkWinState(turkMove).isEmpty()){
			if(backendBoard.getTurn()%2==0 ){
				System.out.println("PLAYER_1, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 1, you WIN!");
			}else{
				System.out.println("PLAYER_2, you WIN!");
				JOptionPane.showMessageDialog(null, "PLAYER 2, you WIN!");
			}
			mainWindow.resetWindow();
			return;
		}

		backendBoard.IncrementTurn();

		System.out.println("Control has returned to the player.");
    }

    // Updates the board with the next _legal_ move
    // xPos is the column, refactor later.
    public void updateBoardWithMove(int xPos){
    	for (int count = tilesOnBoard - (cols - xPos); count >= 0; count -= 7){
    		Token currentButton = gameTokens[count];
    		if (currentButton.getPlayer() == 0){
    			if ( backendBoard.getTurn() % 2==0 ){
    				currentButton.setPlayer(1);
    				currentButton.setIcon(redTokenIcon);
    			} else{
    				currentButton.setPlayer(1);
    				currentButton.setIcon(yellowTokenIcon);
    			}
    			break;
    		}
    	}
    }

    public void resetBoard() {
        backendBoard.resetBoard();

        for (Token gameToken : gameTokens) {
        	gameToken.setIcon(blankTokenIcon);
        	gameToken.setPlayer(0);
        }
    }

    //MOUSELISTENER AND MOUSEMOTIONLISTENER OVERRIDES
    @Override
    public void mouseClicked(MouseEvent e) {
    	//FIXME Testing
        //Package the appropriate column the mouse is on into an Action
    	int col = getColumn(e.getX());
        Action newMove = new Action(1, col);
        System.out.printf("Column %d chosen.\n", col);
        getUserMove(newMove);
    }

	@Override
	public void mouseMoved(MouseEvent e) {
    	//int col = getColumn(e.getX());
        //Action newMove = new Action(1, col);
        highlightColumn(e);
        moveGraphicToken(e);
	}

    @Override
    public void mouseEntered(MouseEvent event) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) { }

	private void moveGraphicToken(MouseEvent e) {

	    return;
	}

    // Get a column from a given x coordinate
	// FIXME should throw an exception
    private int getColumn(int x) {
        int columnWidth = (int) gridSize.getWidth() / 7;
        int currentColBegin = 0;
        int currentColEnd = columnWidth;
        int currentCol = 0;
        while ( currentColEnd <= gridSize.getWidth() ) {
            if ( currentColBegin <= x && x <= currentColEnd ) {
                return currentCol;
            }
            currentColBegin += columnWidth;
            currentColEnd += columnWidth;
            currentCol++;
        }
        return -1; // No column found.
    }

    public void turnOff() {
    	for (Token b : gameTokens) {
            b.setEnabled(false);
    	}
    	setVisible(false);
    	setEnabled(false);
    }

    public void turnOn() {
    	for (Token b : gameTokens) {
    		b.setEnabled(true);
    	}
    	setVisible(true);
    	setEnabled(true);
    }

}


