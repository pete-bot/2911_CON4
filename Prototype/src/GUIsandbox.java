import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class GUIsandbox {
	   private JFrame mainFrame;
	   private JLabel headerLabel;
	   
	   public GUIsandbox (){
	      prepareGUI();
	   }

	   public static void main(String[] args){
	      GUIsandbox  swingContainerDemo = new GUIsandbox();  
	      swingContainerDemo.showJFrameDemo();

	      JFrame frame = new JFrame("GridLayout Test");
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      
	   }

	   private void prepareGUI(){
	      mainFrame = new JFrame("DEFCONNECT 41");
	      mainFrame.setSize(800,500);
	      mainFrame.setLocationRelativeTo(mainFrame);

	      mainFrame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            System.exit(0);
	         }        
	      });
	      
	      headerLabel = new JLabel("", JLabel.CENTER);         

	      
	      int rows = 6;
	      int cols = 7;
	      
	      JPanel gridBoard = new JPanel();
	      gridBoard.setLayout(new GridLayout(rows, cols));
	      
	      for (int y = 0; y < cols; y++) {
	    	  for (int x = 0; x < rows; x++) {
	    		  JButton b1 = new JButton();
	    	      try {
	    	    	  Image img = ImageIO.read(getClass().getResource("circle50.png"));
	    	    	  Color c = new Color(255, 0, 0);
	    	    	  b1.setBackground(c);
	    	    	  b1.setIcon(new ImageIcon(img));
	    	      } catch (IOException e) {
	    	    		  e.printStackTrace();
	    	    		  
	    	      }
	    		  gridBoard.add(b1);
	    	  }
	      }
	      
	      mainFrame.add(gridBoard);
	      gridBoard.setVisible(true);
	      
	      mainFrame.pack();
	      mainFrame.setVisible(true);  
	   }


	   
	   private void showJFrameDemo(){
	      headerLabel.setText("example of a label");   

	      /*final JFrame frame = new JFrame();
	      frame.setSize(300, 300);
	      frame.setLayout(new FlowLayout());       
	      frame.add(msglabel);
	      frame.addWindowListener(new WindowAdapter() {
	         public void windowClosing(WindowEvent windowEvent){
	            frame.dispose();
	         }        
	      });    
	      */

	      
	      JButton okButton = new JButton("");
	      okButton.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	        	 System.out.println("LMAO");
	         }
	      });
	      

	      mainFrame.setVisible(true);  
	   }
	   

	
}
