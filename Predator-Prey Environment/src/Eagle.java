import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.ArrayList;

import javax.swing.JPanel;

import processing.core.PVector;

public class Eagle {
	private float Max_acc = 0.05f;
	private float Max_vel = 10f;
	
	private int eagleWidth;
	private int eagleHeight;
	private PVector pos, vel, accel; 
	private float maxSpeed; 
	private Dimension dimension; 
	private double scale; 
	
	private Area outline;
	private Double body;
	private java.awt.geom.Arc2D.Double wing1;
	private java.awt.geom.Arc2D.Double wing2;
	
	
	
	public Eagle(float x, float y, double size) {
		
		this.pos = new PVector(x, y);
		this.scale = size;

		
		while (maxSpeed == 0)
			this.maxSpeed = Util.random(3, 5);
		this.vel = Util.randomPVector(maxSpeed); 
		this.accel = new PVector(0, 0);
		
		body = new Rectangle2D.Double();
		wing1 = new Arc2D.Double();
		wing2 = new Arc2D.Double();
		
		setBody();
		createOutline();
	}
	
	private void createOutline() {
		outline = new Area();
		outline.add(new Area(body));
	}
	
	public void setBody() {
		body.setFrame((int)(-100*0.3), (int)(-100*0.1), (int)(100*0.5), (int)(100*0.2));

	}
	
	public void draw(Graphics2D g2) {
		AffineTransform af = g2.getTransform();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			    RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.translate(pos.x, pos.y);
		g2.rotate(vel.heading());
		g2.scale(scale, scale);
		if (vel.x < 0)
			g2.scale(1, -1);
		
		
		
		g2.setColor(Color.BLACK); //wings
		g2.fill(body);
		g2.fillArc((int)(-100*0.2), (int)(-100*0.5), (int)(100*0.8), (int)(100*0.8),-180, -90);
		g2.fillArc((int)(-100*0.2), (int)(-100*0.3), (int)(100*0.8), (int)(100*0.8),180,90);
		
		g2.setColor(Color.WHITE); //tail
		int tailX[] = {(int)(-100*0.3),(int)(-100*0.3),(int)(-100*0.5),(int)(-100*0.5)};
		int tailY[] = {(int)(-100*0.1),(int)(100*0.1),(int)(100*0.2),(int)(-100*0.2)};
		g2.fillPolygon(tailX, tailY, 4);
		
		g2.setColor(Color.WHITE); //head
		int headX[] = {(int)(100*0.2), (int)(100*0.2), (int)(100*0.5) ,(int)(100*0.5)};
		int headY[] = {(int)(-100*0.1), (int)(100*0.1), (int)(100*0.05), (int)(-100*0.05)};
		g2.fillPolygon(headX, headY, 4);
		
		g2.setColor(Color.YELLOW); //mouth
		int mouthX[] = {(int)(100*0.5) ,(int)(100*0.5), (int)(100*0.7)};
		int mouthY[] = {(int)(100*0.05), (int)(-100*0.05), 0};
		g2.fillPolygon(mouthX, mouthY, 3);
		
		g2.setColor(Color.BLACK); //eyes
		g2.fillOval((int)(100*0.35), (int)(-100*0.06), 5,5);
		g2.fillOval((int)(100*0.35), (int)(100*0.06-5), 5,5);
		g2.setTransform(af);
		
	}
	
	
	public Cockatoo flyToTarget(ArrayList<Cockatoo> cockatoo) {
		if (cockatoo.size() == 0)
			return null;

		Cockatoo closestBird = cockatoo.get(0);
		float closestDist = PVector.dist(this.getPos(), closestBird.getPos());

		for (Cockatoo c : cockatoo)
			if (PVector.dist(this.getPos(), c.getPos()) < closestDist) {
				closestBird = c;
				closestDist = PVector.dist(this.getPos(), closestBird.getPos());
				
			}
		this.attractedBy(closestBird);
		return closestBird;
		}	
	

	public void attractedBy(Bird target) {
		float coef = .2f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), pos).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed * coef);
		vel.add(acceleration);
	}
	
	public boolean detectCollision(Bird bird) {
		boolean hit = false;

		if (getBoundary().intersects(bird.getBoundary().getBounds2D())
				&& bird.getBoundary().intersects(getBoundary().getBounds2D()))
			hit = true;

		return hit;
	}
	
	public void move() {
		vel.normalize().mult(maxSpeed);
		pos.add(vel);
	}
	
	private Shape getBoundary() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.rotate(vel.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}
	
	private void detectBound(Dimension panelSize) {
		// Collision against edge
		
		Rectangle2D.Double top = new Rectangle2D.Double(10, 10, panelSize.width, 10);
		Rectangle2D.Double bottom = new Rectangle2D.Double(10, panelSize.height - 10, panelSize.width - 10, 10);
		Rectangle2D.Double left = new Rectangle2D.Double(10, 10, 10, panelSize.height - 100);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width - 10, 10, 10, panelSize.height - 10);

		if(getBoundary().intersects(left) && left.intersects(getBoundary().getBounds2D())) {
            accel.add(1, 0);
        }
        else if(getBoundary().intersects(right) && right.intersects(getBoundary().getBounds2D())) {
            accel.add(-1, 0);
        }
        else if(getBoundary().intersects(top) && top.intersects(getBoundary().getBounds2D())) {
            accel.add(0, 1);
        }
        else if(getBoundary().intersects(bottom) && bottom.intersects(getBoundary().getBounds2D())) {
            accel.add(0, -1);
        }
        else {
            accel.mult(0.8f);
        }
        vel.add(accel);
	}
	
	public PVector getPos() {
		return pos;
	}
	
	public void setPos(int x, int y) {
		pos.x = x;
		pos.y = y;
	}
	
	public boolean checkMouseHit(MouseEvent e) {
		return getBoundary().contains(e.getX(), e.getY());
	}
	
	public void chase(Cockatoo target) {
		PVector path = PVector.sub(target.getPos(), pos);
		vel = path.limit(maxSpeed);
	}
	
}