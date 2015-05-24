import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class PauseButton extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    // KEY BINDING
    // MAY NOT BE THE BEST PLACE FOR THIS
    // for key binding
    private static final int InFocusWindow = JComponent.WHEN_IN_FOCUSED_WINDOW;
    private static final KeyStroke escapeStroke = KeyStroke.getKeyStroke(
            KeyEvent.VK_ESCAPE, 0);
    private JButton pauseButton = new JButton("");

    private GameAssets assets = new GameAssets();
    private Window window;

    // for escape sequence (esc loads menu)
    // public AbstractAction escapeAction = new AbstractAction() {
    // private static final long serialVersionUID = 1L;
    //
    // @Override
    // public void actionPerformed(ActionEvent event) {
    //
    // System.out.println("paused-esc");
    // if (window.paused == false) {
    // System.out.println("Pausing.");
    // window.pauseGame();
    // } else if (window.paused == true) {
    // System.out.println("un-Pausing.");
    // window.resumeGame();
    // }
    // }
    // };

    // constructor
    public PauseButton(Window mainWindow) {

        // init buttons w/ listeners
        pauseButton.addActionListener(this);
        // init key bindings
        // getInputMap(InFocusWindow).put(escapeStroke, "escapeSequence");
        // getActionMap().put("escapeSequence", escapeAction);

        // board transparency settings - may need to change depending on colour
        // prefs
        setOpaque(false);
        setBackground(new Color(127, 127, 127, 127));

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

    // fix tranparent panel issue
    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
