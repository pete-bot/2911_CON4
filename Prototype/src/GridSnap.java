import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;

public class GridSnap extends JFrame {
     int vpw = 640;//viewport width
     int vph = 480;//viewport height
     int gw = 100;//grid width
     int gh = 100;//grid height

     BufferedImage buf;//double buffer
     JPanel vp;//viewport for display
     JLabel status;//status bar
     int npx, npy;//current snap coords

     public GridSnap() {
          super("Grid Snapping");

          status = new JLabel("Status bar");

          vp = new JPanel();
          vp.setPreferredSize(new Dimension(vpw, vph));
          vp.addMouseMotionListener(new MouseMotionAdapter(){
               public void mouseMoved(MouseEvent e) {//get the nearest snap point
                    int x = e.getX(), y = e.getY();
                    int mx = x % gw, my = y % gh;
                    if (mx<gw/2) npx = x - mx;
                    else npx = x + (gw-mx);

                    if (my<gh/2) npy = y - my;
                    else npy = y + (gh-my);

                    status.setText(npx+", "+npy);
                    repaint();
               }
          });
          //buffer
          buf = new BufferedImage(vpw, vph, BufferedImage.TYPE_INT_RGB);

          add(vp);
          add(status, BorderLayout.SOUTH);
          setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          pack();
          setVisible(true);
     }
     
     public void paint (Graphics notused) {
          Graphics2D g = (Graphics2D)buf.createGraphics();
          g.clearRect(0, 0, vpw, vph);

          g.setColor(Color.DARK_GRAY);
          //grid vertical lines
          for (int i=gw;i<vpw;i+=gw) {
               g.drawLine(i, 0, i, vph);
          }
          //grid horizontal lines
          for (int j=gh;j<vph;j+=gh) {
               g.drawLine(0, j, vpw, j);
          }
          
          //show the snapped point
          g.setColor(Color.WHITE);
          if (npx>=0 && npy>=0 && npx<=vpw && npy<=vph) {
               g.drawOval(npx-4, npy-4, 8, 8);
          }

          vp.getGraphics().drawImage(buf, 0, 0, Color.BLACK, null);
     }
     
     public static void main(String args[]) {
          new GridSnap();
     }
}