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
    private static final long serialVersionUID = 1L;
    private JButton pauseButton = new JButton("");
    private GameAssets assets = new GameAssets();
    private Window window;
    private Color defaultColor = new Color(255, 255, 235, 100);

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

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonPressed = (JButton) e.getSource();

        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }

    // fix tranparent panel issue
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
