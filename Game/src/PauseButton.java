import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseButton extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton pauseButton = new JButton("");

    private Window window;

    // constructor
    public PauseButton(Window mainWindow) {

        // TODO: set pause button icon here

        // init buttons w/ listeners
        pauseButton.addActionListener(this);

        // create GridBagLayout et al
        setLayout(new GridBagLayout());
        // this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        javax.swing.border.Border empty = BorderFactory.createEmptyBorder();

        String runningDir = System.getProperty("user.dir");
        Path assetsLocation = Paths
                .get(runningDir.matches(".*src") ? runningDir.replaceFirst(
                        "src", "") + "assets/" : runningDir + "/assets");
        Path menuPath = Paths.get(assetsLocation + "/menu.png");
        ImageIcon menuIcon = new ImageIcon(menuPath.toString());

        pauseButton.setIcon(menuIcon);
        pauseButton.setOpaque(false);
        pauseButton.setContentAreaFilled(false);
        pauseButton.setBorderPainted(false);
        pauseButton.setBorder(empty);

        add(pauseButton, gbc);
        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if (buttonPressed.equals(pauseButton)) {
            window.pauseGame();
        }
    }
}
