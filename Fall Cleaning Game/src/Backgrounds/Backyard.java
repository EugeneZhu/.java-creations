//This class creates the backyard scene in states 0-3.

package Backgrounds;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.SimPanel;
import util.ImageLoader;

public class Backyard {
	private BufferedImage img;

	public Backyard(String file) {
		img = ImageLoader.loadImage(file);
	}

	public void drawBackyard(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(-90, 0);
		g2.scale(1.25, 1.25);
		g2.drawImage(img, 0, 0, SimPanel.W_WIDTH, SimPanel.W_HEIGHT, null);
		g2.setTransform(at);
	}

}
