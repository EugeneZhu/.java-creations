//This class generates the tree branches on the tree.

package Media;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import java.util.Collection;

import util.Util;

public class Branch{
	public static final double angleLeft = -1.0;
	public static final double angleRight = 1.0;
	public static final double centerCoef = 0.8;
	public static final double sideCoef = 0.6;
	
		
	private double length;
	private double width;
	private double angle;
	private int depth;
	
	
	private Branch left = null;
	private Branch center = null;
	private Branch right = null;
	private Leaves leaves = null;
	
	private Rectangle2D.Double shape;
	
	public Branch(double len, double wid, double ang, int dep) {
		length = len;
		width = wid;
		angle = ang;
		depth = dep;
		shape = new Rectangle2D.Double(-width/2,-length,width,length);
		
		if (depth > 0) {
			if (Util.random(0,1) > 0.03) //if limb is not broken
				//then randomly adjust sub-branch angle
				left = new Branch(length*sideCoef, width*sideCoef, angleLeft+Util.random(-angleLeft*0.2, angleLeft*0.2), depth-1);
			if (Util.random(0,1) > 0.03)
				right = new Branch(length*sideCoef, width*sideCoef, angleRight+Util.random(-angleRight*0.2, angleRight*0.2), depth-1);
			if (Util.random(0,1) > 0.03)
				center = new Branch(length*centerCoef, width*sideCoef, Util.random(-0.1, 0.1), depth-1);
		}	
	}
	
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_G) {
				depth = depth-1;
			}
		}
	
	
	public void drawBranch(Graphics2D g2) {
		AffineTransform tr = g2.getTransform();
		g2.rotate(angle);

		g2.setColor(new Color(160,82,45));
		g2.fill(shape);

		g2.translate(0, -length);
		if (left != null) left.drawBranch(g2);
		if (right != null) right.drawBranch(g2);
		if (center != null) center.drawBranch(g2);		
		g2.setTransform(tr);
		drawLeaves(g2, 10);
	}
	
	public void addLeaves(double absoluteAngle) {
		
		absoluteAngle += angle;
		if (depth < 3) {
			if (Math.abs(absoluteAngle) > 0.5) {
				leaves = new Leaves();
			}
		}
		if (left != null) left.addLeaves(absoluteAngle);
		if (right != null) right.addLeaves(absoluteAngle);
		if (center != null) center.addLeaves(absoluteAngle);
		
	}
	
	public void drawLeaves(Graphics2D g, double absoluteAngle) {
		AffineTransform tr = g.getTransform();
		g.rotate(angle);
		if (leaves != null) {
			int offset = (int)(width*6);
			if (absoluteAngle > 0)
				offset = - offset;
			leaves.draw(g, offset, length);
		}
		g.setTransform(tr);
	}
	
	public int getDepth() {
		return depth;
	}
	
	public List <Branch> children() {
		List <Branch> children = new ArrayList <Branch>();
		if (left != null) children.add(left);
		if (center != null) children.add(center);
		if (right != null) children.add(right);
		return children;
	}
	
	public static Iterator <Branch> getAllBranchIterator(Branch root) {
		return new AllBranchIterator(root);
	}
	
	private static class AllBranchIterator implements Iterator <Branch> {
		Stack <Branch> stack;
		
		private AllBranchIterator(Branch root) {
			stack = new Stack <Branch>();
			stack.push(root);
		}
		
		public boolean hasNext() {
			return !stack.isEmpty();
		}
		
		public Branch next() {
			if (!stack.isEmpty()) {
				Branch curr = stack.pop();
				List <Branch> children = curr.children();
				Collections.reverse(children);
				for (Branch b : children) {
					stack.push(b);
				}
				if (curr.getDepth() == 0)
					return curr;
				else
					return next();
			}
			return null;
		}
	}

}
