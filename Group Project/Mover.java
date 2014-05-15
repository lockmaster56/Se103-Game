import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.Timer;
import java.io.*;

public class Mover extends JPanel implements ActionListener, KeyListener 
{
    Timer tm = new Timer(5, this);
    
    // instance variables 
    private BufferedImage img;
    private int x=0, y=0, velX=0, velY=0;
    
    public Mover()
    {                    
        tm.start();
        addKeyListener(this);
        setFocusable( true );
        setFocusTraversalKeysEnabled( false );
        
        try {
            img = ImageIO.read(new File("Pacman.png"));
        } catch(IOException e) {
             } //calls image   
    }
   
    public void paint( Graphics g )
    {
        g.drawImage( img, x, y, null ); //draws image
    }
    
    public void keyPressed( KeyEvent e )
    {
        int c = e.getKeyCode();
        
        if( c == KeyEvent.VK_LEFT )
        {
            velX = -1;
            velY = 0;
        }
        
        if( c == KeyEvent.VK_UP )
        {
            velX = 0;
            velY = -1;
        }
        
        if( c == KeyEvent.VK_DOWN )
        {
            velX = 0;
            velY = 1;
        }
        
        if( c == KeyEvent.VK_RIGHT )
        {
            velX = 1;
            velY = 0;
        }        
    }
    
    public void keyReleased( KeyEvent e )
    {
        // to stop movement on release
        velX = 0;
        velY = 0;
    }
    
    public void keyTyped( KeyEvent e )
    {
        //handle typing : n/a
    }
    
    public void actionPerformed( ActionEvent ae )
    {
        if( x < 0 )
        {
            velX = 0;
            x = 0;
        }//west boundary
        if( x > 400 )
        {
            velX = 0;
            x = 399;
        }//east boundary
        if( y < 0 )
        {
            velY = 0;
            y = 0;
        }//north boundary
        if( y > 400 )
        {
            velY = 0;
            y = 399;
        }//south boundary        
        
        x = x + velX;
        y = y + velY;
        repaint();
    }
    
   
    public static void main( String args[] )
    {
        JFrame imgFrame = new JFrame("Image Mover");
        imgFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //imgFrame.setLayout( new FlowLayout() );
        
        
        Mover obj = new Mover();
  
        imgFrame.add( obj );
        imgFrame.setSize(600,600);
        imgFrame.setVisible( true );
        
    } 
}
