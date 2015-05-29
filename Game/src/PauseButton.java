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

public class PauseButton extends JPanel implements ActionListener {
    /** */
	private static final long serialVersionUID = 1L;
	/** */
	private JButton pauseButton = new JButton("");
	/** */
	private GameAssets assets = new GameAssets();
	/** */
	private Window window;
	/** */
	private Color defaultColor = new Color(255, 255, 235, 100);

	/**
	 * Constructor for PauseButton
	 * @param mainWindow
	 * 		This is the instance of the main window (JFrame) class.
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
     * Action Listener for the pause button being clicked. This will display the pause menu.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }

    /**
     * Required override to fix issues with the transparency of certain JPanels. Prior to this
     * override, mousing over a transparent panel would re-render its transparent effect, over the top
     * of the previous effect. It was very visually unappealing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        //g.setColor(getBackground());
        //g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
