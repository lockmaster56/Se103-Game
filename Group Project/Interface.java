import java.awt.*;
import javax.swing.*;

public class Interface extends JFrame
{
    //instance variables
    
    public Interface()
    {
        super( "Pacman" );
        setLayout( new FlowLayout() ); 
        
        //adds board JPanel to frame
        JPanel boardSpace = new JPanel();
        boardSpace.setPreferredSize(new Dimension(400,400));
        board bd = new board();
        boardSpace.add( bd );
        add( boardSpace );
        
        //add image JPanel to frame
        JPanel imgPanel = new JPanel();
        imgPanel.setPreferredSize(new Dimension(75,75));
        Mover mv = new Mover();
        imgPanel.add( mv );
        add( imgPanel);
        add( mv );
        
        //adds buttons to frame
        //JPanel buttonSpace = new JPanel();
        
    }

    public static void main(String args[])
    {
        Interface gui = new Interface();
        gui.setDefaultCloseOperation(EXIT_ON_CLOSE);
        gui.setSize( 640,800 );
        gui.setVisible( true );
    }
}
