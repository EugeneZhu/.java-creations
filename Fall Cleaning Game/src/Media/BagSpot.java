//This class creates the transparent bag image seen in state 6.

package Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import util.ImageLoader;

public class BagSpot  {
	private double xPos;
	private double yPos;
	private double sca;
	private BufferedImage img;
	// constructor
	public BagSpot(double x, double y, double s) {
		xPos = x;
		yPos = y;
		sca = s;
		img = ImageLoader.loadImage("assets/BagSpot.png");
	}

	public void drawButton(Graphics2D g2) {
		AffineTransform transform = g2.getTransform();
		g2.translate(xPos, yPos);
		g2.scale(sca, sca);

		g2.drawImage(img, -img.getWidth() / 2, -img.getHeight() / 2, null);

		g2.setTransform(transform);
	}

	public void setBagImg(int bagState) {
		if (bagState == 0)
			img = ImageLoader.loadImage("assets/BagSpot.png");
		else if (bagState == 1)
			img = ImageLoader.loadImage("assets/FullBag.png"); 

	}
	
	public double getXPos(){
		return xPos;
	}
	
	public double getYPos(){
		return yPos;
	}
}
