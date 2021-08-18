import java.awt.*;
import java.awt.geom.*;
import processing.core.*;

public class Ant {
	private Ellipse2D.Double body;
	private Ellipse2D.Double body2;
	private Ellipse2D.Double body3;

	private Ellipse2D.Double head;
	//private Ellipse2D.Double referencePoint;

	private Line2D.Double legs;
	private Line2D.Double legs2;
	private Line2D.Double legs3;
	private Line2D.Double legs4;
	private Line2D.Double legs5;
	private Line2D.Double legs6;

	private Line2D.Double rightAntenna;
	private Line2D.Double leftAntenna;

	public PVector pos;

	protected boolean alive;
	public boolean highlight = false;
	public boolean chased = false;

	private Color antColor;
	private Color highlightColor;
	
	private Area outline;

	double angle = Math.random() * Math.PI * 2; // rotates the ant randomly
	double scale;

	public Ant(float x, float y, float size) {
		
		this.pos = new PVector(x, y);
		this.scale = size;
		
		alive = true;

		pos = new PVector((float) x, (float) y);

		antColor = new Color(64, 27, 27);
		highlightColor = new Color(230, 193, 193);
		
		

		body = new Ellipse2D.Double(-9, 10, 15 / 1.35, 15);
		body2 = new Ellipse2D.Double(-9, 20, 15 / 1.5, 15 * 1.25);
		body3 = new Ellipse2D.Double(-12, 35, 15 * 1.15, 15 * 1.35);
		head = new Ellipse2D.Double(-10, 0, 15 / 1.05, 15 / 1.25);

		legs = new Line2D.Double(0, 18, 20, -10);
		legs2 = new Line2D.Double(-7, 18, -27, -10);

		legs3 = new Line2D.Double(-1, 25, 25, 35);
		legs4 = new Line2D.Double(-5, 25, -30, 35);

		legs5 = new Line2D.Double(-1, 30, 20, 60);
		legs6 = new Line2D.Double(-5, 30, -30, 60);

		rightAntenna = new Line2D.Double(-6, 0, -3, -3);
		leftAntenna = new Line2D.Double(1, 1, -2, -3);

		createOutline();
		//referencePoint = new Ellipse2D.Double(-8, 18, 10, 10);

	}
	
	private void createOutline() {
		outline = new Area(body);
		outline.add (new Area(body2));
		outline.add (new Area(body3));
		outline.add(new Area(head));
	}

	// draws the ant
	public void draw(Graphics2D g) {
		if (alive) {
			AffineTransform tr = g.getTransform();
//
			
			g.setColor(highlight? highlightColor : antColor);

			g.translate(pos.x, pos.y);
			g.rotate(angle);
			g.scale(scale, scale);

			// legs
			g.draw(legs);
			g.draw(legs2);
			g.draw(legs3);
			g.draw(legs4);
			g.draw(legs5);
			g.draw(legs6);

			// antenna
			g.draw(rightAntenna);
			g.draw(leftAntenna);
			// head
			g.fill(head);
			// middle body
			g.fill(body);
			g.fill(body2);
			// bottom
			g.fill(body3);

			g.setTransform(tr);
		}

	}
	
	public Shape getBoundary() {
		AffineTransform at = new AffineTransform();
		at.translate(pos.x, pos.y);
		at.scale(scale, scale);
		return at.createTransformedShape(outline);
	}
	
	public PVector getPos() {
		return pos;
	}

	// returns the size of the ant (used in the algorithm)
	public double sizeOfAnt() {
		return scale;
	}

	// sets the color for the ant
	public void changeColor(Color color) {
		antColor = color;
	}

}
