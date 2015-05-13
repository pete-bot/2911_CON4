import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class PlayArea extends JLabel {
 
	Dimension minSize;

    public PlayArea(Color color, Dimension dimension) {
        setBackground(color);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setOpaque(true);
		setVerticalAlignment(SwingConstants.CENTER);
		setHorizontalAlignment(SwingConstants.CENTER);
        minSize = dimension;
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    public Dimension getPreferredSize() {
        return minSize;
    }
    
}
