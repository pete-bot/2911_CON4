import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class TextPanel extends JPanel {

    private JTextArea textArea;
    private JScrollPane scrollPane;
	
	public TextPanel() {
		setLayout(new BorderLayout());
		textArea = new JTextArea();
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
	}

	public void addTextToPanel(String string) {
		textArea.append(string);
	}
}
