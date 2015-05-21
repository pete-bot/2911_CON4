import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class PlayArea extends JLabel {

    private static final long serialVersionUID = 1L;
    private Dimension minSize;
    private final int rows = 6;
    private final int cols = 7;

    public PlayArea(Color color, Dimension dimension) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setOpaque(false);
        setLayout(new GridLayout(rows,cols));
        minSize = dimension;
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }

}
