import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import java.io.*;
import javax.imageio.ImageIO;

public class Pacman extends JPanel
{
	public Pacman( int width, int length )
	{
		
		
		}//end constructor
	}
	//load an image 
	
	//move the guy
	
	//show up places
	
	//create grid
	public void paintComponent(Graphics g)
	{
	    int width = 50;
	    int height = 50;
	    super.paintComponent(g);
	    
	    Pacman arr[][] = new Pacman[10][10];
	    for( int i = 0; i < arr.length; i ++ )
	    {
	    	for( int p = 0; p < 10; p++)
	    	{
	    		arr[i][p]= add.( g.fillRect(0, 0, width, height) );
	    	}
	    }

}
