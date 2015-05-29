import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class BackgroundImagePanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Image image;

    public BackgroundImagePanel(Image image) {
        setImage(image);
        setLayout(new BorderLayout());
    }

    @Override
    public Dimension getPreferredSize() {
        if (image == null)
            return super.getPreferredSize();
        else
            return new Dimension(image.getWidth(null), image.getHeight(null));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null)
            return;

        Dimension d = getSize();
        g.drawImage(image, 0, 0, d.width, d.height, null);
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

}