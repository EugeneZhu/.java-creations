//This class creates the driveway scene in state 4.

package Backgrounds;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.SimPanel;
import util.ImageLoader;

public class Driveway {
	private BufferedImage img;

	public Driveway(String file) {
		img = ImageLoader.loadImage(file);
	}

	public void drawDriveway(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(0, 0);
		g2.scale(1, 1);
		g2.drawImage(img, 0, 0, SimPanel.W_WIDTH, SimPanel.W_HEIGHT, null);
		g2.setTransform(at);
	}

}
