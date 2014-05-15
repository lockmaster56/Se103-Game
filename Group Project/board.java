import java.awt.*;
import javax.swing.*;

public class board extends JPanel
{
    // instance variables 
    
    public void paintComponent( Graphics g )
    {
        super.paintComponent( g ); //call paintComponent
        this.setBackground( Color.BLACK ); //set bg
        this.setPreferredSize(new Dimension(350,350));
        
        //6:32 5/12/14 -- black screen currently
        //JPanel gridContain = new JPanel();
        //gridContain.setBackground( Color.YELLOW );
        //gridContain.setPreferredSize(new Dimension(350,350));
        
        //creates grid of panels 
        JPanel[][] gridPanel = new JPanel[10][10];        
        for( int i = 0; i < gridPanel.length; i++ )
        {
            for( int k = 0; k < gridPanel.length; k++ )
            {
                gridPanel[i][k] = new JPanel();
                gridPanel[i][k].setBackground( Color.RED );
                gridPanel[i][k].setPreferredSize(new Dimension(30,30));
                add( gridPanel[i][k] );
            }
        }

        //add( gridContain ); 
    }
    /*

    public static void main( String args[] )
    {
        JFrame boardFrame = new JFrame( "Pacman Board" );
        boardFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        board boardPanel = new board();
        boardFrame.add( boardPanel );
        
        boardFrame.setSize( 400, 400 );
        boardFrame.setVisible( true );
    }
    */
}
