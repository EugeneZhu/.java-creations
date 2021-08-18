//This class creates the man seen in state 1.

package Media;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import main.SimPanel;
import util.ImageLoader;

public class Man {
	private BufferedImage img;

	public Man(String file) {
		img = ImageLoader.loadImage(file);
	}

	public void drawMan(Graphics2D g2) {
		AffineTransform at = g2.getTransform();
		g2.translate(25, 400);
		g2.scale(0.3, 0.6);
		g2.drawImage(img, 0, 0, SimPanel.W_WIDTH, SimPanel.W_HEIGHT, null);
		g2.setTransform(at);
	}

}
