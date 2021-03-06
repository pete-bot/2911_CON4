import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
public class PlayArea extends JLabel {

    private static final long serialVersionUID = 1L;
    Dimension minSize;

    public PlayArea(Color color, Dimension dimension) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setOpaque(false);
        //setVerticalAlignment(SwingConstants.CENTER);
        //setHorizontalAlignment(SwingConstants.CENTER);
        minSize = dimension;
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }

}
