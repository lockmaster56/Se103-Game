import java.awt.*;
import javax.swing.*;
import java.util.*;

public class GameFrame extends JFrame
{
	public GameFrame()
	{
	super("Mad Man");
	setLayout(new BorderLayout());
	
	
	JPanel jp = new JPanel();
	jp.setBackground( new Color(178, 102, 255) );
	
	add( jp );
	
	JPanel buttonPanel = new JPanel();
	JButton start = new JButton("Start");
	buttonPanel.add( start );
	
	
	add( buttonPanel, BorderLayout.AFTER_LAST_LINE  );
	
	java.net.URL imageURL = GameFrame.class.getResource("PacmanFamily.jpg");
	if (imageURL != null) {
		ImageIcon icon = new ImageIcon(imageURL);
	}
	//ImageIcon icon = createImageIcon("PacmanFamily.jpg");
	//jp.add(icon, BorderLayout.CENTER);
	
	} //end constructor
	
	public static void main(String args[])
	{
		GameFrame app = new GameFrame();
		app.setSize(500,300);
		app.setVisible(true);
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

}
