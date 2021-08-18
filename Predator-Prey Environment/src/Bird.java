import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import processing.core.PVector;

public abstract class Bird {

	private PVector pos, vel, accel; 
	private float maxSpeed; 
	private Dimension dimension; 
	private double scale; 
	private Color color; 

	private Area outline; 

	public Bird(float x, float y, double size) {

		
		this.pos = new PVector(x, y);
		this.scale = size;

		
		while (maxSpeed == 0)
			this.maxSpeed = Util.random(3, 5);
		this.vel = Util.randomPVector(maxSpeed); 
		this.accel = new PVector(0, 0);
	}

	public void draw(Graphics2D g) {
		Graphics2D g2 = (Graphics2D) g;
		
		AffineTransform transform = g2.getTransform();
		g2.translate(pos.x, pos.y);
		g2.rotate(vel.heading());
		g2.scale(scale, scale);
		if (vel.x < 0)
			g.scale(1, -1);
		g2.setTransform(transform);

	}

	public void move() {
		vel.normalize().mult(maxSpeed);
		pos.add(vel);
	}

	public PVector getPos() {
		return pos;
	}

	protected abstract Shape getBoundary();
}
