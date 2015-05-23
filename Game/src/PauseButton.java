import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.sun.glass.events.KeyEvent;

public class PauseButton extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton pauseButton = new JButton("");
    private GameAssets assets = new GameAssets();
    private Window window;

    
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
            
			System.out.println("paused-esc");
			if(window.paused == false){
            	System.out.println("Pausing.");
            	window.pauseGame();
            }else if (window.paused == true){
            	System.out.println("un-Pausing.");
            	window.resumeGame();
            }
        } 
    };
    
    
    
    // constructor
    public PauseButton(Window mainWindow) {
    	
    	
        // init buttons w/ listeners
        pauseButton.addActionListener(this);
        // init key bindings
        getInputMap(IFW).put(escapeStroke, "escapeSequence");
        getActionMap().put( "escapeSequence", escapeAction );
        
        
        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        // this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        javax.swing.border.Border empty = BorderFactory.createEmptyBorder();

        ImageIcon menuIcon = assets.getAsset("menu_button.png");

        pauseButton.setIcon(menuIcon);
        pauseButton.setOpaque(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setBorder(empty);

        add(pauseButton, gbc);
        window = mainWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }
}
