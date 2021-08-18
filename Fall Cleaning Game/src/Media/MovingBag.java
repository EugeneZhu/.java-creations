//This class creates the movable bag seen in state 6.

package Media;

import static util.ImageLoader.loadImage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovingBag {
	protected double xPos;
	protected double yPos;
	protected double scale;

	protected BufferedImage img;
	
	public MovingBag(double x, double y, double sca) {
		xPos = x;
		yPos = y;
		scale = sca;
		img = loadImage("assets/FullBag.png");

	}

	public boolean hit(BagSpot bagspot) {
		boolean hit = false;

		if (Math.abs(xPos - bagspot.getXPos()) < 50 && Math.abs(yPos- bagspot.getYPos()) < 30)
			hit = true;
		
		return hit;
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if(x > (xPos - ((double) img.getWidth())/2*scale) && x < (xPos + ((double) img.getWidth())/2*scale) && y > (yPos - ((double) img.getHeight())/2*scale) && y < (yPos + ((double) img.getHeight())/2*scale)) 
			clicked = true;
		
		return clicked;
	}
	
	public void setXPos(double x){
		xPos = x;
	}
	
	public void setYPos(double y){
		yPos = y;
	}

	public void drawButton(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(scale, scale);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);
		
		g2.setTransform(transform);
		
	}
}
