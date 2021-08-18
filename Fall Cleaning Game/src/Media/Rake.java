//This class creates the rake seen in states 1 and 2.

package Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class Rake  {
	private double xPos;
	private double yPos;
	private double scale;
	private BufferedImage img;
	private boolean lightOn = false;

	public Rake(double x, double y,  double s) {
		xPos = x;
		yPos = y;
		scale = s;
		img = ImageLoader.loadImage("assets/Rake.png");
	}
 
	public void drawButton(Graphics2D g2) {

		AffineTransform transform = g2.getTransform(); 
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);
		
		if (!lightOn){
		g2.drawImage(img, -img.getWidth()/2, img.getHeight()/2, null);
		}
		g2.setTransform(transform);
	}
	
	public void setLightOn(boolean on){
		lightOn = on;
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (xPos - ((double) img.getWidth()) / 2 * scale) && x < (xPos + ((double) img.getWidth())*scale) && y > (yPos ) && y < (yPos + ((double) img.getHeight())*scale*2)) 
			clicked = true;
		
		return clicked;
	}
}
