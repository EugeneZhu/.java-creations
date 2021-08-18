//This class generates the trees in the background with recursion.

package Media;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

public class GeneratedTree {
	private Branch tree;
	private int locx, locy;

	public static final int TYPE1 = 1;
	public static final int TYPE2 = 2;
	public static final int TYPE3 = 3;
	public static final int TYPE4 = 4;
	public static final int TYPE5 = 5;

	public GeneratedTree(int x, int y, double len, double wid, double ang, int depth) {
		locx = x;
		locy = y;
		tree = new Branch(len, wid, ang, depth);
		tree.addLeaves(0);
		setColor();
	}

	public void drawTree(Graphics2D g2) {
		AffineTransform tr = g2.getTransform();
		g2.translate(locx, locy);
		tree.drawBranch(g2);
		g2.setTransform(tr);
	}
	
	public void setColor() {
		Iterator <Branch> it = Branch.getAllBranchIterator(tree);
		while (it.hasNext()) {
			Branch br = it.next();
		}
	}

}
