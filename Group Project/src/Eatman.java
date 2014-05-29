import java.awt.*;
import javax.swing.*;

public class Eatman extends JFrame
{
	public Eatman()
	{
		initUI();
	}
	
	private void initUI()
	{
		add(new GameBoard() );
		setTitle("Man");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(380, 420);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run()
			{
				Eatman ex = new Eatman();
				ex.setVisible(true);
			}
		});
	}
	
}