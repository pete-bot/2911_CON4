import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
//import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.border.Border;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;




import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JButton pvCPUButton = new JButton();
    private JButton restartButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton pvpButton = new JButton();
    private JButton resumeButton = new JButton();
    private JButton optionsButton = new JButton();
    private JButton spacer = new JButton();
    private Border emptyBorder = BorderFactory.createEmptyBorder();
    private ImageIcon pvCPUIcon;
    private ImageIcon pvpIcon;
    private ImageIcon optionsIcon;
    private ImageIcon spacerIcon;
    private ImageIcon quitIcon;
    private ImageIcon resumeIcon;
    private ImageIcon restartIcon;
    private ImageIcon exitIcon;
    private GameAssets assets;
    private Window mainWindow;
    private Color defaultColor = new Color(127, 127, 127, 127);
    
    // music controller
    private boolean music = false;
    String soundFile = "../assets/taylor_swift-shake_it_off.mid";
    InputStream in;
    AudioStream audioStream;
    
    // KEY BINDING
    // MAY NOT BE THE BEST PLACE FOR THIS
    // for key binding
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final KeyStroke escapeStroke = 
    	    KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    
    // for escape sequence (esc loads menu)
    public AbstractAction escapeAction = new AbstractAction() { 
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent event) { 
            System.out.println("mainMenu-esc");
			if(mainWindow.paused == false){
            	System.out.println("Pausing.");
            	mainWindow.pauseGame();
            }else if (mainWindow.paused == true){
            	System.out.println("un-Pausing.");
            	mainWindow.resumeGame();
            }
        } 
    };

    
    public MainMenuPanel(Window mainWindow, GameAssets assets) {
        this.assets = assets;
        this.mainWindow = mainWindow;
        setLayout(new GridBagLayout());
        
    	setOpaque(false);
    	setBackground( new Color(127, 127, 127, 127) );
    	
        
        setSize(new Dimension(100,100));
        initIcons();
        addMainMenuItems();
        
        
        // init key bindings
        setFocusable(true);
        getInputMap(IFW).put(escapeStroke, "escapeSequence");
        getActionMap().put( "escapeSequence", escapeAction );
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pvCPUButton)) {
            mainWindow.startNewGame();
        } else if (buttonPressed.equals(resumeButton)) {
            mainWindow.resumeGame();
        } else if (buttonPressed.equals(restartButton)) {
            mainWindow.resetWindow();
        }else if (buttonPressed.equals(optionsButton)){
        	try {
				this.musicToggle();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        } else if (buttonPressed.equals(exitButton)) {
            System.exit(0);
        }
    }

    private void addMainMenuItems() {
        GridBagConstraints gbc = new GridBagConstraints();
        // init GridBagConstraints and inset (default spacing on buttons)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(10, 10, 10, 10);

        initIcons();
        //spacer.setIcon(spacerIcon);
        pvCPUButton.setIcon(pvCPUIcon);
        pvpButton.setIcon(pvpIcon);
        optionsButton.setIcon(optionsIcon);
        exitButton.setIcon(quitIcon);

        //initButton(spacer);
        initButton(pvCPUButton);
        initButton(pvpButton);
        initButton(optionsButton);
        initButton(exitButton);

        pvCPUButton.addActionListener(this);
        pvpButton.addActionListener(this);
        optionsButton.addActionListener(this);
        exitButton.addActionListener(this);

        //add(spacer, gbc);
        //gbc.gridy++;

        // TODO: change button icons here to suit aesthetic.

        add(pvCPUButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(pvpButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(optionsButton, gbc);
        gbc.gridy++;
        gbc.gridy++;

        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
    }

    private void initButton(JButton b) {
        b.setOpaque(false);
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setBorder(emptyBorder);
        b.setRolloverEnabled(false);
    }

    // This must be called before buttons can add icons
    private void initIcons() {
        //spacerIcon = assets.getAsset("spacer.png"); //Thought: why do we need a transparent image?
        pvCPUIcon = assets.getAsset("new_game_button.png");
        pvpIcon = assets.getAsset("passnplay_button.png");
        optionsIcon = assets.getAsset("options_button.png");
        quitIcon = assets.getAsset("quit_button.png");
        resumeIcon = assets.getAsset("resume_game_button.png");
        restartIcon = assets.getAsset("restart_button.png");
    }

    private void removeMainMenuItems() {
        remove(pvCPUButton);
        remove(pvpButton);
        remove(optionsButton);
        remove(exitButton);
    }

    public void showWinMessage(){
        removeMainMenuItems();
    	
    	// set glassy pane behind win message
    	setOpaque(false);
    	setBackground( new Color(127, 127, 127, 127) );
    	
    	
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);
        
        initButton(restartButton);
        restartButton.setIcon(restartIcon);
        add(restartButton, gbc);
        gbc.gridy++;

        initButton(exitButton);
        exitButton.setIcon(quitIcon);
        add(exitButton, gbc);
        
    	restartButton.addActionListener(this);
        exitButton.addActionListener(this);
        
        
        requestFocus();
        setVisible(true);
        
    }
    
    public void musicToggle() throws Exception{
    	if(in ==null && audioStream == null ){
    		in = new FileInputStream(soundFile);
    		audioStream = new AudioStream(in);
    	}
    	
    	if(music == false){
    		System.out.println("playin' tunes!");
    		AudioPlayer.player.start(audioStream);
            music = true;
    	}else{
    		System.out.println("stoppin' tunes :(");
    		AudioPlayer.player.stop(audioStream);
    		music = false;
    	}
    }
    
    
    public void showPauseMenu() {
        // Clear the panel of older items
        removeMainMenuItems();
        
        resumeButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        initButton(resumeButton);
        resumeButton.setIcon(resumeIcon);
        add(resumeButton, gbc);
        gbc.gridy++;

        add(optionsButton, gbc);
        gbc.gridy++;

        initButton(restartButton);
        restartButton.setIcon(restartIcon);
        add(restartButton, gbc);
        gbc.gridy++;

        initButton(exitButton);
        exitButton.setIcon(quitIcon);
        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        setVisible(true);
    }
    
    // fix tranparent panel issue
    protected void paintComponent(Graphics g){
    	g.setColor( getBackground() );
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

}
