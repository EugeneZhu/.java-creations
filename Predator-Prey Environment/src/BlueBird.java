
import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;
import processing.core.*;

import java.awt.event.MouseEvent;

public class BlueBird extends Bird{

	private Ellipse2D.Double body;

	private Ellipse2D.Double wing;
	private Ellipse2D.Double wing2;
	private Ellipse2D.Double wing3;
	private Ellipse2D.Double wing4;
	private Ellipse2D.Double wing5;
	private Ellipse2D.Double wing6;
	private Ellipse2D.Double wing7;
	private Ellipse2D.Double wing8;
	// private Ellipse2D.Double referencePoint;

	private Ellipse2D.Double head;
	private Ellipse2D.Double lefteyes;
	private Ellipse2D.Double righteyes;

	private Ellipse2D.Double tail;
	private Ellipse2D.Double tail2;
	private Ellipse2D.Double tail3;

	private Ellipse2D.Double rightAccent;
	private Ellipse2D.Double leftAccent;
	private Ellipse2D.Double beak;

	private Rectangle2D.Double tail4;
	private Line2D.Double wingAccent;
	private Line2D.Double wingAccent2;
	private Line2D.Double wingAccent3;
	private Line2D.Double wingAccent4;
	private Line2D.Double tailAccent;
	private Line2D.Double tailAccent2;
	private Line2D.Double tailAccent3;
	
	private float angle, maxSpeed;

	private PVector pos, vel, accel; 
	
	private Area outline; 

	private float scale;

	private Color dot;

	public boolean chasing;
	int eatingTime = 90; // timer that determines how long the bird stays in the 'eating' animation
	// int timeAlive = 45; // timer that determines how long the ant stays on screen
	// after collision

	// constructor
	public BlueBird(float x, float y, float size) {
		super(x,y,size);
		
		this.pos = new PVector(x, y);
		this.scale = size;
		
		while (maxSpeed == 0)
			this.maxSpeed = Util.random(3, 5);
		this.vel = Util.randomPVector(maxSpeed);
		this.accel = new PVector(0, 0);

		//boundingBox = new Rectangle2D.Double(-53, -25, 70, 70);
		beak = new Ellipse2D.Double();
		body = new Ellipse2D.Double(0, 0, 20, 20 * 3);

		wing = new Ellipse2D.Double(15, 5, 20, 20 / 1.25);
		wing2 = new Ellipse2D.Double(-15, 5, 20, 20 / 1.25);
		wing3 = new Ellipse2D.Double(28, 5, 2.25 * 20, 20 / 2);
		wing4 = new Ellipse2D.Double(25, 13, 2 * 20, 20 / 2);
		wing5 = new Ellipse2D.Double(10, 19, 2 * 20, 20 / 2);
		wing6 = new Ellipse2D.Double(-50, 5, 2.25 * 20, 20 / 2);
		wing7 = new Ellipse2D.Double(-45, 13, 2 * 20, 20 / 2);
		wing8 = new Ellipse2D.Double(-28, 19, 2 * 20, 20 / 2);

		head = new Ellipse2D.Double();

		lefteyes = new Ellipse2D.Double(3, -7, 20 / 5, 20 / 5);
		righteyes = new Ellipse2D.Double(13, -7, 20 / 5, 20 / 5);

		tail = new Ellipse2D.Double(0, 57, 20, 20 / 1.3);
		tail2 = new Ellipse2D.Double(0, 65, 20 / 3, 20 / 1.5);
		tail3 = new Ellipse2D.Double(14, 65, 20 / 3, 20 / 1.5);
		tail4 = new Rectangle2D.Double(0, 67, 20, 20 / 2);

		rightAccent = new Ellipse2D.Double(13, 0, 20 / 3, 20 / 2);
		leftAccent = new Ellipse2D.Double(0, 0, 20 / 3, 20 / 2);

		wingAccent = new Line2D.Double(-10, 15, -40, 15);
		wingAccent2 = new Line2D.Double(-10, 20, -25, 23);
		wingAccent3 = new Line2D.Double(20, 15, 60, 15);
		wingAccent4 = new Line2D.Double(30, 20, 50, 23);

		tailAccent = new Line2D.Double(10, 70, 10, 75);
		tailAccent2 = new Line2D.Double(5, 70, 5, 77);
		tailAccent3 = new Line2D.Double(15, 70, 15, 77);
		
		createOutline();
		setBoundary();

		// referencePoint = new Ellipse2D.Double(5, 20, 10, 10);
		// middleDot = new Ellipse2D.Double(2,10, size/1.15,size/1.15);
	}
	
	public void setBoundary() {
		beak.setFrame(8, -15, 20 / 5, 20 / 2);
		head.setFrame(2, -10, 20 / 1.25, 20);
	}
	
	private void createOutline() {
		outline = new Area();
		outline.add(new Area(beak));
		outline.add(new Area(head));
		outline.add(new Area(body));
		outline.add(new Area(lefteyes));
		outline.add(new Area(righteyes));
		outline.add(new Area(leftAccent));
		outline.add(new Area(rightAccent));
		outline.add(new Area(wing));
		outline.add(new Area(wing2));
		outline.add(new Area(wing3));
		outline.add(new Area(wing4));
		outline.add(new Area(wing5));
		outline.add(new Area(wing6));
		outline.add(new Area(wing7));
		outline.add(new Area(wing8));
		
	}

	public void draw(Graphics2D g2) {
		super.draw(g2);
		AffineTransform tr = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.rotate(-11);
		g2.rotate(vel.heading());
		g2.scale(scale, scale);
		if (vel.x < 0)
			g2.scale(-1, 1);
		// wings

			// right wing
			g2.setColor(new Color(108, 177, 255));
			g2.fill(wing3);
			g2.fill(wing4);
			g2.fill(wing5);

			// left wing
			g2.fill(wing6);
			g2.fill(wing7);
			g2.fill(wing8);

		// blue dot things
		g2.setColor(new Color(85, 139, 219));
		g2.fill(wing);
		g2.fill(wing2);

		g2.setColor(new Color(108, 177, 255));
		g2.fill(tail);
		g2.fill(tail2);
		g2.fill(tail3);
		g2.fill(tail4);

		g2.fill(body);

		// beak
		g2.setColor(new Color(0));
		g2.fill(beak);

		// head
		g2.setColor(new Color(108, 177, 255));
		g2.fill(head);

//
//		// eyes
		g2.setColor(new Color(0));
		g2.fill(lefteyes);
		g2.fill(righteyes);
//
//		draws a dot with a random color passed through constructor
		g2.setColor(dot);
		g2.fill(rightAccent);
		g2.fill(leftAccent);

		
		g2.setTransform(tr);
		
//		g.getTransform();
//		g.translate(pos.x, pos.y);
//		g.rotate(angle);
//		g.draw(boundingBox);
//		g.setTransform(tr);
		
	}
	
	public void detectBoundaries(Dimension panelSize) {
		
		Rectangle2D.Double top = new Rectangle2D.Double(10, 10, panelSize.width, 10);
		Rectangle2D.Double bottom = new Rectangle2D.Double(10, panelSize.height - 10, panelSize.width - 10, 10);
		Rectangle2D.Double left = new Rectangle2D.Double(10, 10, 10, panelSize.height - 100);
		Rectangle2D.Double right = new Rectangle2D.Double(panelSize.width - 10, 10, 10, panelSize.height - 10);
		float coef = 0.5f;

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
        vel.add(accel.mult(coef));
	}
	
	protected Shape getBoundary() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.rotate(vel.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}

	// detects when the bird has collided with the ant
	public boolean detectCollision(Ant ant) {
		boolean hit = false;

		if (getBoundary().intersects(ant.getBoundary().getBounds2D())
				&& ant.getBoundary().intersects(getBoundary().getBounds2D()))
			hit = true;

		return hit;
	}
	
	public void attractedBy(Ant target) {
		float coef = .2f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), pos).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed * coef);
		vel.add(acceleration);
	}

	public Ant selectTarget(ArrayList<Ant> ants) {
			if (ants.size() == 0)
				return null;

			Ant closestAnt = ants.get(0);
			float closestDist = PVector.dist(this.getPos(), closestAnt.getPos());

			for (Ant a : ants)
				if (PVector.dist(this.getPos(), a.getPos()) < closestDist) {
					closestAnt = a;
					closestDist = PVector.dist(this.getPos(), closestAnt.getPos());
					
				}
			this.attractedBy(closestAnt);
			return closestAnt;
		}

	public float getScale() {
		return scale;
	}
	
	// for the mouse dragging interaction portion
	public void setPos(int x, int y) {
		pos.x = x;
		pos.y = y;
	}
	
	public PVector getPos() {
		return pos;
	}
	
	public void move() {
		vel.normalize().mult(maxSpeed);
		pos.add(vel);
	}
	
	public void chase(Ant target1) {
		PVector path = PVector.sub(target1.getPos(), pos);
		vel = path.limit(maxSpeed);
	}

	// a boolean that detects when the body/head is dragged around by the mouse
	public boolean clickedOn(MouseEvent e) {
		AffineTransform tr = new AffineTransform();
		angle = vel.heading();

		tr.translate(pos.x, pos.y);
		tr.rotate(-11);
		tr.rotate(angle);
		tr.scale(scale, scale);

		Shape transformBody = tr.createTransformedShape(body);

		Shape transformHead = tr.createTransformedShape(head);

		boolean onBody = transformBody.contains(e.getX(), e.getY());
		boolean onHead = transformHead.contains(e.getX(), e.getY());

		return onBody | onHead;

	}
	
	public boolean checkMouseHit(MouseEvent e) {
		return getBoundary().contains(e.getX(), e.getY());
	}


}
