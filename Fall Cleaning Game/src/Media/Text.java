//This class creates the text seen in state 1.

package Media;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Text extends JPanel{
	
    public void paint(Graphics2D g2){
    	g2.setColor(Color.WHITE);
    	g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
        g2.drawString("Oh no!!!", 350, 600);
        g2.drawString("The storm has blown the leaves all over the floor!!!", 350, 650);
        g2.drawString("Click on the rake, move the leaves into the bag, and put the filled bag in the front!", 350, 700);
    }
    
}