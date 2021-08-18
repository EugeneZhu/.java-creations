//This class creates the ending scene in state 5.

package Backgrounds;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.SimPanel;
import util.ImageLoader;

public class Ending {
	private BufferedImage img;
	private double xPos;
	private double yPos;
	private double scale;

	public Ending(String file, double x, double y,  double s) {
		img = ImageLoader.loadImage(file);
		xPos = x;
		yPos = y;
		scale = s;
	}

	public void drawEnding(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(1, 1);
		g2.drawImage(img, 0, 0, SimPanel.W_WIDTH, SimPanel.W_HEIGHT, null);
		g2.setTransform(at);
	}
	
	public boolean clicked(double x, double y){
		boolean clicked = false;
		
		if (x > (xPos - ((double) img.getWidth())) && x < (xPos + ((double) img.getWidth())*scale) && y > (yPos) && y < (yPos + ((double) img.getHeight()))) 
			clicked = true;
		
		return clicked;
	}
}
