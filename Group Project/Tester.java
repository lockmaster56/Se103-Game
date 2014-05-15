import javax.swing.*;
import java.awt.*;

public class Tester
{

    public Tester()
    {
        // initialise instance variables
    }

    public static void main( String args[] )
    {
        JFrame app =  new JFrame("Tester Class");
        app.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        app.setVisible( true );
        app.setSize(600,600);
        
        Mover pac = new Mover();
        //Baddies bad = new Baddies();
        //board brd = new board();
        
        app.setLayout( new GridLayout(3,1) );
        app.add( pac );
        //app.add( bad );
        //app.add( brd );
    }
}
