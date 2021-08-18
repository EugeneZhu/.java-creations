import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PVector;

public class Cockatoo extends Bird{

	private PVector pos, vel, accel; 
	private float maxSpeed; 
	private Dimension dimension; 
	private float scale; 
	private Color color; 

	private Ellipse2D.Double body, eye1, eye2, lwing, rwing, head;
	private Rectangle2D.Double beak;

	private Area outline; 

	public Cockatoo(float x, float y, float size) {
		super(x,y,size);
		
		this.pos = new PVector(x, y);
		this.scale = size;

		
		while (maxSpeed == 0)
			this.maxSpeed = Util.random(3, 5);
		this.vel = Util.randomPVector(maxSpeed); 
		this.accel = new PVector(0, 0);

		body = new Ellipse2D.Double();
		eye1 = new Ellipse2D.Double();
		eye2 = new Ellipse2D.Double();
		lwing = new Ellipse2D.Double();
		rwing = new Ellipse2D.Double();
		head = new Ellipse2D.Double();
		beak = new Rectangle2D.Double();

		setBody();
		createOutline();
	}

	public void setBody() {
		body.setFrame(-60, -15, 60, 30);
		head.setFrame(-10,-10, 20, 20);
		eye1.setFrame(4, -5, 3, 3); 
		eye2.setFrame(4, 3, 3, 3); 
		lwing.setFrame(-60, -20, 50, 10);
		rwing.setFrame(-60, 10, 50, 10);
		beak.setFrame(-3, -3, 6, 6);

	}

	private void createOutline() {
		outline = new Area();
		outline.add(new Area(body));
		outline.add(new Area(head));
	}

	public void draw(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g;

		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.rotate(vel.heading());
		g2.scale(scale, scale);
		if (vel.x < 0)
			g.scale(1, -1);
		
		AffineTransform transform1 = g2.getTransform();
		g2.setColor(new Color(200, 200, 200));
		g2.translate(10, 0);
		g2.rotate(Math.toRadians(45));
		g2.fill(beak);
		g2.setTransform(transform1);
		
		g2.setColor(new Color(255,255,255));
		g2.fill(body);
		g2.fill(head);
		g2.fill(lwing);
		g2.fill(rwing);
		
		g2.setColor(new Color(0, 0, 0));
		g2.fill(eye1);
		g2.fill(eye2);
		g2.setTransform(transform);

	}

	public void move() {
		vel.normalize().mult(maxSpeed);
		pos.add(vel);
	}

	public void edgeCollision(Dimension panelSize) {

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

	public boolean checkMouseHit(MouseEvent e) {
		return getBoundary().contains(e.getX(), e.getY());
	}
	
	public void attractedBy(Seed target) {
		float coef = .2f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(target.getPos(), pos).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed * coef);
		vel.add(acceleration);
	}

	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.rotate(vel.heading());
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}

	public PVector getPos() {
		return pos;
	}

	public void chase(Seed target) {
		PVector path = PVector.sub(target.getPos(), pos);
		vel = path.limit(maxSpeed);
	}

	public boolean detectCollision(Seed seed) {
		boolean hit = false;

		if (getBoundary().intersects(seed.getBoundary().getBounds2D())
				&& seed.getBoundary().intersects(getBoundary().getBounds2D()))
			hit = true;

		return hit;
	}

	public float getScale() {
		return scale;
	}
	
	public void setPos(int x, int y) {
		pos.x = x;
		pos.y = y;
	}
	
	public boolean collides(Cockatoo c) {
		//Detecting the collision between the cockatoos
		return (c.getBoundary().intersects(getBoundary().getBounds2D()) && 
				getBoundary().intersects(c.getBoundary().getBounds2D()));
	}
	
	public void moveAwayFrom(Cockatoo c) {
		float coef = .2f; // coefficient of acceleration relative to maxSpeed
		PVector direction = PVector.sub(pos, c.getPos()).normalize();
		PVector acceleration = PVector.mult(direction, maxSpeed * coef);
		vel.add(acceleration);
	}
	
	public void changeColor() {
		this.color = Util.setColor();
	}
	
	public Seed searchClosestSeed(ArrayList<Seed> seed) {
		if (seed.size() == 0)
			return null;

		Seed closestSeed = seed.get(0);
		float closestDist = PVector.dist(this.getPos(), closestSeed.getPos());

		for (Seed s : seed)
			if (PVector.dist(this.getPos(), s.getPos()) < closestDist) {
				closestSeed = s;
				closestDist = PVector.dist(this.getPos(), closestSeed.getPos());
				
			}
		this.attractedBy(closestSeed);
		return closestSeed;
	}
}
