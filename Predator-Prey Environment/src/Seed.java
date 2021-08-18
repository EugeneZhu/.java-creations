import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import processing.core.PVector;

public class Seed {
	
	private PVector pos;						
	private float height, length;						
	private float scale;						
	private Ellipse2D.Double seedShape;			
	private Area outline; 	
	private Color seedColor;
	
	public Seed (float x, float y, float size) {

		
		this.pos = new PVector(x, y);
		this.scale = size;
		
		
		this.height = 10;
		this.length = 20;
		setShapeAttributes();
		
		outline = new Area(seedShape);
	}
	
	private void setShapeAttributes() {
		this.seedShape = new Ellipse2D.Double(-height/2, -height/2, length, height);
	}

	public void draw(Graphics2D g) {
		AffineTransform at = g.getTransform();
		
		g.translate(pos.x, pos.y);
		g.scale(scale, scale);
		
		
		g.setColor(new Color(71, 40, 11));
		g.fill(seedShape);
		
		g.setTransform(at);
	}

	public boolean checkMouseHit(MouseEvent e) {
		PVector mousePos = new PVector(e.getX(), e.getY());
		float distance = PVector.dist(mousePos, pos);
		return (distance <= height/2*scale);
	}
	
	public PVector getPos() {
		return pos;
	}

	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}

	public void setColor(Color color) {
		this.seedColor = color;
		
	}
	
	public void setPos(int x, int y) {
		pos.x = x;
		pos.y = y;
	}

}
