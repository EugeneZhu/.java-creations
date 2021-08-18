//This class creates the movable leaves seen in the simulation states.

package Media;

import static util.ImageLoader.loadImage;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FallenLeaves {
	protected double xPos;
	protected double yPos;
	protected double scale;

	protected BufferedImage img;
	
	public FallenLeaves(double x, double y, double sca) {
		xPos = x;
		yPos = y;
		scale = sca;
		img = loadImage("assets/Leaves.png");

	}

	public boolean hit(Bag bag) {
		boolean hit = false;

		if (Math.abs(xPos - bag.getXPos()) < 50 && Math.abs(yPos- bag.getYPos()) < 30)
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
