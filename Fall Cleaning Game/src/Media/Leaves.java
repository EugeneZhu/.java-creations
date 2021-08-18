//Generate the leaves on the tree

package Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

public class Leaves {
	
	Color leavesColor;
	
	
	public Leaves() {
		leavesColor = new Color(255,69,0);
	}
	
	public void draw(Graphics2D g, int offset, double length) {
		g.setColor(leavesColor);
		int leavesSize = 12;
		g.fill(new Ellipse2D.Double(offset-leavesSize/2, (int)(-length-leavesSize/2), leavesSize*2, leavesSize));
	}
	
	public void setColor (int r, int g, int b) {
		leavesColor = new Color(r,g,b);
	}
}
