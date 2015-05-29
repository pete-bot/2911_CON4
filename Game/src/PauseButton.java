import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Class that contains the pause button and its action listeners
 * @author pedro
 *
 */
public class PauseButton extends JPanel implements ActionListener {
    /**Variable for serialisation */
	private static final long serialVersionUID = 1L;
	/**The pause button Jbutton */
	private JButton pauseButton = new JButton("");
	/**Game Assets instance */
	private GameAssets assets = new GameAssets();
    /**The current game window. */
	private Window window;
	/** default colour scheme*/
    private Color defaultColor = new Color(255, 255, 235, 100);

    /**
     * Constructor for pause button.
     * @param mainWindow
     * 		The main window JFrame.
     */
    public PauseButton(Window mainWindow) {

        // init buttons w/ listeners
        pauseButton.addActionListener(this);

        // board transparency settings - may need to change depending on colour
        // prefs
        setOpaque(false);
        setBackground(defaultColor);

        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        // this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1;
        gbc.insets = new Insets(2, 2, 2, 2);

        javax.swing.border.Border empty = BorderFactory.createEmptyBorder();

        ImageIcon menuIcon = assets.getAsset("menu_button.png");

        pauseButton.setIcon(menuIcon);
        pauseButton.setOpaque(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setBorder(empty);
        
        gbc.gridy++;
        add(pauseButton, gbc);
        window = mainWindow;
    }

    /**
     * Action for the pause button. This will create an instance of the pasue menu and display it.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }

    /**
     * Fix for issue with drawing transparent windows.
     */
    @Override
    protected void paintComponent(Graphics g) {
        //g.setColor(getBackground());
        //g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
