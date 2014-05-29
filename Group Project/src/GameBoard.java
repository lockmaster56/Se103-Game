import java.awt.*;

import java.awt.event.*;
import javax.swing.*;

public class GameBoard extends JPanel implements ActionListener
{
	private Dimension d;
	
	private Image ii;
	private Color mazeColor;
	private final Color dotColor = new Color(230, 230, 250);
	
	private boolean ingame = false;
	private boolean die = false;
	
	private final int blockSize = 24;
	private final int blocks = 15;
	private final int screenSize = blocks * blockSize;
	private final int delay = 2;
	private final int count = 4;
	private final int maxghosts = 12;
	private final int pacSpeed = 6;
	
	private int paccount = delay;
	private int direction = 1;
	private int position = 0;
	private int ghosts = 6;
	private int pacsleft, score;
	private int[] dx, dy;
	private int[] ghostX, ghostY, ghostDx, ghostDy, ghostSpeed;
	
	private Image ghost;
	private Image pac1, pac2up, pac2left, pac2right, pac2down;
	private Image pac3up, pac3down, pac3left, pac3right;
	private Image pac4up, pac4down, pac4left, pac4right;
	
	private int pacmanX, pacmanY, pacmanDx, pacmanDy;
	private int reqdx, reqdy, viewdx, viewdy;
	
	private final short data[] = {
			19, 26, 26, 26, 18, 18, 18, 18, 18, 18, 18, 18, 18, 18, 22,
	        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	        21, 0, 0, 0, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20,
	        21, 0, 0, 0, 17, 16, 16, 24, 16, 16, 16, 16, 16, 16, 20,
	        17, 18, 18, 18, 16, 16, 20, 0, 17, 16, 16, 16, 16, 16, 20,
	        17, 16, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 16, 24, 20,
	        25, 16, 16, 16, 24, 24, 28, 0, 25, 24, 24, 16, 20, 0, 21,
	        1, 17, 16, 20, 0, 0, 0, 0, 0, 0, 0, 17, 20, 0, 21,
	        1, 17, 16, 16, 18, 18, 22, 0, 19, 18, 18, 16, 20, 0, 21,
	        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
	        1, 17, 16, 16, 16, 16, 20, 0, 17, 16, 16, 16, 20, 0, 21,
	        1, 17, 16, 16, 16, 16, 16, 18, 16, 16, 16, 16, 20, 0, 21,
	        1, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 20, 0, 21,
	        1, 25, 24, 24, 24, 24, 24, 24, 24, 24, 16, 16, 16, 18, 20,
	        9, 8, 8, 8, 8, 8, 8, 8, 8, 8, 25, 24, 24, 24, 28
	};
	
	private final int validspeeds[] = {1, 2, 3, 4, 6, 8};
	private final int maxspeed = 6;
	
	private int currentspeed = 3;
	private short[] screendata;
	private Timer timer;
	
	
	public GameBoard()
	{
		loadImages();
		initVariables();
		
		addKeyListener(new TAdapter());
		
		setFocusable(true);
		
		setBackground(Color.black);
		setDoubleBuffered(true);
	} //end constructor
	
	private void initVariables()
	{
		screendata = new short[blocks * blocks];
		mazeColor = new Color(216, 145, 239);
		d = new Dimension(400, 400);
		ghostX = new int[maxghosts];
		ghostDx = new int[maxghosts];
		ghostY = new int[maxghosts];
		ghostDy = new int[maxghosts];
		ghostSpeed = new int[maxghosts];
		dx = new int[4];
		dy = new int[4];
		
		timer = new Timer(40, this);
		timer.start();
	} //end method
	
	public void addNotify()
	{
		super.addNotify();
		
		initGame();
	} //end method
	
	private void doAnim()
	{
		paccount--;
		
		if(paccount <= 0)
		{
			paccount = delay;
			position = position + direction;
			
			if(position == (count - 1) || position == 0)
			{
				direction = -direction;
			} //end if
		} //end if
	} //end method 
	
	private void playGame(Graphics2D g2d)
	{
		if(die)
		{
			death();
		} //end if
		else
		{
			moveMan();
			drawMan(g2d);
			moveGhosts(g2d);
			checkMaze();
		} //end else
	} //end method
	
	private void introScreen(Graphics2D g2d)
	{
		g2d.setColor( new Color(178, 102, 255) );
		g2d.fillRect(50, screenSize / 2 - 30, screenSize - 100, 50);
		g2d.setColor( Color.white );
		g2d.drawRect(50, screenSize / 2 - 30, screenSize - 100, 50 );
		
		String s = "Press s to Start.";
		Font small = new Font( "Helvetica", Font.BOLD, 14 );
		FontMetrics metr = this.getFontMetrics(small);
		
		g2d.setColor(Color.white);
		g2d.setFont(small);
		g2d.drawString(s, (screenSize - metr.stringWidth(s)) /2, screenSize / 2 );
	} //end method introScreen
	
	private void drawScore(Graphics2D g)
	{
		int i;
		String s;
		
		Font smallfont = new Font( "Helvetica", Font.BOLD, 14 );
		g.setFont(smallfont);
		g.setColor( new Color(96, 128, 255) );
		s = "Score: " + score;
		g.drawString(s, screenSize / 2 + 96, screenSize + 16);
		
		for (i = 0; i < pacsleft; i++ )
		{
			g.drawImage(pac3left, i * 28 + 8, screenSize + 1, this);
		} //end for
	} //end method drawScore
	
	private void checkMaze()
	{
		short i = 0; 
		boolean finished = true;
		
		while (i < blocks * blocks && finished)
		{
			if ( (screendata[i] & 48) != 0 )
			{
				finished = false;
			} //end if
			
			i++;
		} //end while
		if (finished)
		{
			score += 50;
			
			if(ghosts < maxghosts) 
			{
				ghosts++;
			} //end if
			if(currentspeed < maxspeed)
			{
				currentspeed++;
			} //end if
			
			initLevel();
		} //end if block
	} //end method checkMaze
	
	private void death()
	{
		pacsleft--;
		
		if(pacsleft == 0)
		{
			ingame = false;
		} //end if
		
		continueLevel();
	} //end method death
	
	private void moveGhosts(Graphics2D g2d) 
	{
	       short i;
	       int pos;
	       int count;
	       
	       for (i = 0; i < ghosts; i++) 
	       {
	           if (ghostX[i] % blockSize == 0 && ghostY[i] % blockSize == 0) 
	           {
	               pos = ghostX[i] / blockSize + blocks * (int) (ghostY[i] / blockSize);

	               count = 0;

	               if ((screendata[pos] & 1) == 0 && ghostDx[i] != 1) 
	               {
	                   dx[count] = -1;
	                   dy[count] = 0;
	                   count++;
	               } //end if

	               if ((screendata[pos] & 2) == 0 && ghostDy[i] != 1) 
	               {
	            	   dx[count] = 0;
	            	   dy[count] = -1;
	                   count++;
	               } //end if

	               if ((screendata[pos] & 4) == 0 && ghostDx[i] != -1) 
	               {
	                   dx[count] = 1;
	                   dy[count] = 0;
	                   count++;
	               } //end if

	               if ((screendata[pos] & 8) == 0 && ghostDy[i] != -1) 
	               {
	                   dx[count] = 0;
	                   dy[count] = 1;
	                   count++;
	               } //end if

	               if (count == 0) 
	               {

	                   if ((screendata[pos] & 15) == 15) 
	                   {
	                       ghostDx[i] = 0;
	                       ghostDy[i] = 0;
	                   } //end if
	                   else 
	                   {
	                       ghostDx[i] = -ghostDx[i];
	                       ghostDy[i] = -ghostDy[i];
	                   } //end else

	               } //end if
	               else 
	               {

	                   count = (int) (Math.random() * count);

	                   if (count > 3) 
	                   {
	                       count = 3;
	                   } //end if

	                   ghostDx[i] = dx[count];
	                   ghostDy[i] = dy[count];
	               } //end else

	           } //end if
	            
	           ghostX[i] = ghostX[i] + (ghostDx[i] * ghostSpeed[i]);
	           ghostY[i] = ghostY[i] + (ghostDy[i] * ghostSpeed[i]);
	           drawGhost(g2d, ghostX[i] + 1, ghostY[i] + 1);

	           if (pacmanX > (ghostX[i] - 12) && pacmanX < (ghostX[i] + 12)
	                    && pacmanY > (ghostY[i] - 12) && pacmanY < (ghostY[i] + 12)
	                    && ingame) 
	           {

	               die = true;
	           } //end if
	       } //end for
	   } //end method moveGhosts
	            
	
	// drawGhosts
	private void drawGhost(Graphics2D g2d, int x, int y) 
	{
		g2d.drawImage(ghost, x, y, this);
	} //end method drawGhost
	
	
	private void moveMan() {

        int pos;
        short ch;

        if (reqdx == -pacmanDx && reqdy == -pacmanDy) {
            pacmanDx = reqdx;
            pacmanDy = reqdy;
            viewdx = pacmanDx;
            viewdy = pacmanDy;
        }

        if (pacmanX % blockSize == 0 && pacmanY % blockSize == 0) {
            pos = pacmanX / blockSize + blocks * (int) (pacmanY / blockSize);
            ch = screendata[pos];

            if ((ch & 16) != 0) {
                screendata[pos] = (short) (ch & 15);
                score++;
            }

            if (reqdx != 0 || reqdy != 0) {
                if (!((reqdx == -1 && reqdy == 0 && (ch & 1) != 0)
                        || (reqdx == 1 && reqdy == 0 && (ch & 4) != 0)
                        || (reqdx == 0 && reqdy == -1 && (ch & 2) != 0)
                        || (reqdx == 0 && reqdy == 1 && (ch & 8) != 0))) {
                    pacmanDx = reqdx;
                    pacmanDy = reqdy;
                    viewdx = pacmanDx;
                    viewdy = pacmanDy;
                }
            }

            // Check for standstill
            if ((pacmanDx == -1 && pacmanDy == 0 && (ch & 1) != 0)
                    || (pacmanDx == 1 && pacmanDy == 0 && (ch & 4) != 0)
                    || (pacmanDx == 0 && pacmanDy == -1 && (ch & 2) != 0)
                    || (pacmanDx == 0 && pacmanDy == 1 && (ch & 8) != 0)) {
                pacmanDx = 0;
                pacmanDy = 0;
            }
        }
        pacmanX = pacmanX + pacSpeed * pacmanDx;
        pacmanY = pacmanY + pacSpeed * pacmanDy;
    }
	    
	
	// drawman
	 private void drawMan(Graphics2D g2d) 
	 {
		 if (viewdx == -1)
		 {  
			 drawManLeft(g2d);
		 } //end if
		 else if (viewdx == 1) 
		 {
			 drawManRight(g2d);
			 
		 } //end else if
		 else if (viewdy == -1) 
		 {
			 drawManUp(g2d);
		 } //end else if
		 else 
		 {
			 drawManDown(g2d);
		 } //end else
	 } //end method drawMan
	 
	 private void drawManUp(Graphics2D g2d) {

	        switch (position) {
	            case 1:
	                g2d.drawImage(pac2up, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 2:
	                g2d.drawImage(pac3up, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 3:
	                g2d.drawImage(pac4up, pacmanX + 1, pacmanY + 1, this);
	                break;
	            default:
	                g2d.drawImage(pac1, pacmanX + 1, pacmanY + 1, this);
	                break;
	        }
	    }

	    private void drawManDown(Graphics2D g2d) {

	        switch (position) {
	            case 1:
	                g2d.drawImage(pac2down, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 2:
	                g2d.drawImage(pac3down, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 3:
	                g2d.drawImage(pac4down, pacmanX + 1, pacmanY + 1, this);
	                break;
	            default:
	                g2d.drawImage(pac1, pacmanX + 1, pacmanY + 1, this);
	                break;
	        }
	    }

	    private void drawManLeft(Graphics2D g2d) {

	        switch (position) {
	            case 1:
	                g2d.drawImage(pac2left, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 2:
	                g2d.drawImage(pac3left, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 3:
	                g2d.drawImage(pac4left, pacmanX + 1, pacmanY + 1, this);
	                break;
	            default:
	                g2d.drawImage(pac1, pacmanX + 1, pacmanY + 1, this);
	                break;
	        }
	    }

	    private void drawManRight(Graphics2D g2d) {

	        switch (position) {
	            case 1:
	                g2d.drawImage(pac2right, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 2:
	                g2d.drawImage(pac3right, pacmanX + 1, pacmanY + 1, this);
	                break;
	            case 3:
	                g2d.drawImage(pac4right, pacmanX + 1, pacmanY + 1, this);
	                break;
	            default:
	                g2d.drawImage(pac1, pacmanX + 1, pacmanY + 1, this);
	                break;
	        }
	    }
	
	 // drawMaze
	 private void drawMaze(Graphics2D g2d) 
	 {
	        short i = 0;
	        int x, y;

	        for (y = 0; y < screenSize; y += blockSize) 
	        {
	            for (x = 0; x < screenSize; x += blockSize) 
	            {

	                g2d.setColor(mazeColor);
	                g2d.setStroke(new BasicStroke(2));

	                if ((screendata[i] & 1) != 0) 
	                { 
	                    g2d.drawLine(x, y, x, y + blockSize - 1);
	                }

	                if ((screendata[i] & 2) != 0) 
	                { 
	                    g2d.drawLine(x, y, x + blockSize - 1, y);
	                }

	                if ((screendata[i] & 4) != 0) 
	                { 
	                    g2d.drawLine(x + blockSize - 1, y, x + blockSize - 1,
	                            y + blockSize - 1);
	                }

	                if ((screendata[i] & 8) != 0) 
	                { 
	                    g2d.drawLine(x, y + blockSize - 1, x + blockSize - 1,
	                            y + blockSize - 1);
	                }

	                if ((screendata[i] & 16) != 0) 
	                { 
	                    g2d.setColor(dotColor);
	                    g2d.fillRect(x + 11, y + 11, 2, 2);
	                }

	                i++;
	            }
	        }
	    }

	
	
	private void initGame()
	{
		pacsleft = 3;
		score = 0;
		initLevel();
		ghosts = 6;
		currentspeed = 3;
	} //end method initGame
	
	private void initLevel()
	{
		int i;
		for(i = 0; i < blocks * blocks; i++)
		{
			screendata[i] = data[i];
		} //end for
		
		continueLevel();
	} //end method initLevel
	
	private void continueLevel()
	{
		short i;
		int dx = 1;
		int random;
		
		for(i = 0; i < ghosts; i++)
		{
			ghostY[i] = 4 * blockSize;
			ghostX[i] = 4 * blockSize;
			ghostDy[i] = 0;
			ghostDx[i] = dx;
			dx = -dx;
			random = (int) (Math.random() * (currentspeed + 1) );
			
			if(random > currentspeed)
			{
				random = currentspeed;
			} //end if
			
			ghostSpeed[i] = validspeeds[random];
		} //end for
		
		pacmanX = 7 * blockSize;
		pacmanY = 11 * blockSize;
		pacmanDx = 0;
		pacmanDy = 0;
		reqdx = 0;
		reqdy = 0;
		viewdx = -1;
		viewdy = 0;
		die = false;
	} //end method continueLevel
	
	
	//method loadImages
	private void loadImages()
	{
		ghost = new ImageIcon("ghost.jpg").getImage();
		pac1 = new ImageIcon("pac.png").getImage();
		pac2up = new ImageIcon("pacup.png").getImage();
		pac3up = new ImageIcon("pacup.png").getImage();
		pac4up = new ImageIcon("pacup.png").getImage();
		pac2down = new ImageIcon("pacdown.png").getImage();
		pac3down = new ImageIcon("pacdown.png").getImage();
		pac4down = new ImageIcon("pacdown.png").getImage();
		pac2left = new ImageIcon("pacleft.png").getImage();
		pac3left = new ImageIcon("pacleft.png").getImage();
		pac4left = new ImageIcon("pacleft.png").getImage();
		pac2right = new ImageIcon("pac.png").getImage();
		pac3right = new ImageIcon("pac.png").getImage();
		pac4right = new ImageIcon("pac.png").getImage();
	}
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		doDrawing(g);
	} //end method paintComponent
	
	private void doDrawing(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, d.width, d.height);
		
		drawMaze(g2d);
		drawScore(g2d);
		doAnim();
		
		if(ingame)
		{
			playGame(g2d);
		} //end if
		else
		{
			introScreen(g2d);
		} //end else
		
		g2d.drawImage(ii, 5, 5, this);
		Toolkit.getDefaultToolkit().sync();
		g2d.dispose();
	} //end method doDrawing
	
	class TAdapter extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			int key = e.getKeyCode();
			
			if(ingame)
			{
				if (key == KeyEvent.VK_LEFT) 
				{
                    reqdx = -1;
                    reqdy = 0;
                } //end if
				else if (key == KeyEvent.VK_RIGHT) 
				{
                    reqdx = 1;
                    reqdy = 0;
                } //end else if
				else if (key == KeyEvent.VK_UP) 
				{
                    reqdx = 0;
                    reqdy = -1;
                } //end else if
				else if (key == KeyEvent.VK_DOWN) 
				{
                    reqdx = 0;
                    reqdy = 1;
                } //end else if
				else if (key == KeyEvent.VK_ESCAPE && timer.isRunning()) 
				{
                    ingame = false;
                } //end else if
				else if (key == KeyEvent.VK_PAUSE) 
				{
                    if (timer.isRunning()) 
                    {
                        timer.stop();
                    } //end if
                    else 
                    {
                        timer.start();
                    } //end else
				} //end else if
			} //end if
			else
			{
				if(key == 's' || key == 'S')
				{
					ingame = true;
					initGame();
				} //end if
			} //end else
		} //end method keyPressed
		
		public void keyReleased(KeyEvent e)
		{
			int key = e.getKeyCode();
			
			if(key == Event.LEFT || key == Event.RIGHT
					|| key == Event.UP || key == Event.DOWN)
			{
				reqdx = 0;
				reqdy = 0;
			} //end if
		} //end method keyReleased
	} //end class TAdapter
	
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	} //end method actionPerformed
	
	
	
} // end class
