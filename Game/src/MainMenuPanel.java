
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenuPanel extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JButton newGameButton = new JButton("New Game");
    private JButton restartButton = new JButton("Restart Game");
    private JButton exitButton = new JButton("Quit");
    private Window window;

    // constructor
    public MainMenuPanel(Window mainWindow) {

        newGameButton.addActionListener(this);
        restartButton.addActionListener(this);
        exitButton.addActionListener(this);


        setLayout(new GridBagLayout());
        this.setSize(new Dimension(1024, 768));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(2, 2, 2, 2);

        add(newGameButton, gbc);
        gbc.gridy++;

        add(restartButton, gbc);
        gbc.gridy++;

        add(exitButton, gbc);

        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 2;

        setBounds(500,500,500,500);

        window = mainWindow;
    }

    public void actionPerformed(ActionEvent e) {

        JButton buttonPressed = (JButton) e.getSource();

        // Reset game
        if ( buttonPressed.equals(newGameButton)) {
            window.startNewGame();
        } else if (buttonPressed.equals(restartButton)){
            window.resetWindow();
        } else if (buttonPressed.equals(exitButton) ){
            System.exit(0);
        }
    }
}
