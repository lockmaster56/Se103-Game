import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.Timer;
import java.io.*;

public class Baddies extends JPanel implements ActionListener 
{  
    // instance variables 
    private BufferedImage pic[]; 
    private int xVal = 0, yVal = 0;
    
    public Baddies()
    {                            
        setLayout( new FlowLayout() );
        
        pic = new BufferedImage[4];
        try {
            //instatiate pic array for bad guys
            pic[ 0 ] = ImageIO.read(new File("Pacman 1.png"));
            pic[ 1 ] = ImageIO.read(new File("Pacman 2.png"));
            pic[ 2 ] = ImageIO.read(new File("Pacman 3.png"));
            pic[ 3 ] = ImageIO.read(new File("Pacman 4.png"));
        } catch(IOException e) {
             } //calls image
    }
   
    public void paint( Graphics g )
    {
        for( int x = 0; x < pic.length; x++)
        {
            g.drawImage( pic[x], xVal, yVal, null );
            xVal+=25;
        }//draws image
    }
        
    public void actionPerformed( ActionEvent ae )
    {

    }
   
    /*public static void main( String args[] )
    {
        JFrame bFrame = new JFrame("Free Moving Baddies");
        bFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Baddies obj1 = new Baddies();
        
        bFrame.add( obj1 );
        
        bFrame.setSize(300,300);
        bFrame.setVisible( true );
    }
     */
}