import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * Class that stores and displays the game window background image.
 * @author Ryan S, Ryan C, Oliver, Sanjay, Bruce, Peter
 *
 */
public class BackgroundImagePanel extends JPanel {

	/**ID for serialisation */
    private static final long serialVersionUID = 1L;
    /**The image used as background */
    private Image image;

    /**
     * Method to set the background image panel.
     * @param image
     * 		Image for background.
     */
    public BackgroundImagePanel(Image image) {
        setImage(image);
        setLayout(new BorderLayout());
    }

    /**
     * Method to return the preferred dimension of the background image
     */
    @Override
    public Dimension getPreferredSize() {
        if (image == null)
            return super.getPreferredSize();
        else
            return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    /**
     * Method used to paint the background image
     * Takes in Generic graphics object, g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null)
            return;

        Dimension d = getSize();
        g.drawImage(image, 0, 0, d.width, d.height, null);
    }

    /**
     * Method to set the game background image.
     * @param image
     * 		The image to be set.
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

}