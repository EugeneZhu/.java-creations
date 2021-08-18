//Keys: 
	//"P" Key will pause the game
	//Mouse dragging the objects will move them 


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

import processing.core.PVector;

public class GamePanel extends JPanel implements ActionListener {

	private ArrayList<Eagle> eagle;
	private ArrayList<Cockatoo> cockatoo;
	private ArrayList<BlueBird> bbird;
	private ArrayList<Seed> seed;
	private ArrayList<Ant> ant;
	private Timer t;
	private Dimension paneSize;
	private Cockatoo selection;

	public GamePanel() {
		paneSize = new Dimension(1200, 800);
		this.setPreferredSize(paneSize);
		
		this.cockatoo = new ArrayList<>();
			for (int i = 0; i < 4; i++)
		cockatoo.add (new Cockatoo(Util.random(100, paneSize.width - 100),
				Util.random(100, paneSize.height - 100),
				1)); 
		
		this.bbird = new ArrayList<>();
			for (int i = 0; i < 2; i++)
		bbird.add (new BlueBird(Util.random(100, paneSize.width - 100),
				Util.random(100, paneSize.height - 100),
				(float) 1.25));

		this.seed = new ArrayList<Seed>();
		this.ant = new ArrayList<Ant>();
		for (int i = 0; i < 10; i++)
			seed.add(new Seed(Util.random(100, paneSize.width - 100),
					Util.random(100, paneSize.height - 100), 
					Util.random(1, 2))); 
		for (int i = 0; i < 10; i++)
			ant.add(new Ant(Util.random(100, paneSize.width - 100),
					Util.random(100, paneSize.height - 100), 
					Util.random(0.1, 1))); 
		
		this.eagle = new ArrayList<>();
			for (int i = 0; i < 2; i++)
		eagle.add (new Eagle(Util.random(100, paneSize.width - 100),
				Util.random(100, paneSize.height - 100),
				1.5));
		addMouseListener(new MyMouseAdapter());
		addMouseMotionListener(new MyMouseMotionAdapter());
		addKeyListener(new MyKeyAdapter());
		setFocusable(true);

		t = new Timer(33, this);
		t.start();
		
		selection =null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		this.setPreferredSize(new Dimension(paneSize.width, paneSize.height));
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0, 120, 0));
		g2.fillRect(10, 10, paneSize.width - 20, paneSize.height - 20);
		for (int i = 0; i < seed.size(); i++)
			seed.get(i).draw(g2);
		for (int i = 0; i < ant.size(); i++)
			ant.get(i).draw(g2);
		for (Cockatoo c : cockatoo)
		c.draw(g2);
		for (BlueBird bb : bbird)
		bb.draw(g2);
		for (Eagle e : eagle)
		e.draw(g2);
	}
	
	private class MyKeyAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_P) {
				if (t.isRunning())
					t.stop();
				else
					t.start();
			}
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		for (Cockatoo c : cockatoo) {
		Seed target = c.searchClosestSeed(seed);
		if (target != null) {
			target.setColor(Util.setColor());
			c.chase(target);
		}
		for (Cockatoo c2 : cockatoo) {
			if (c2 != c && c2.collides(c)) {
				
					c2.moveAwayFrom(c);
				
			}
		}
		}
		for (Eagle ea : eagle) {
		Cockatoo target = ea.flyToTarget(cockatoo);
		if (target != null) {
			ea.chase(target);
		}
		}
		
		for (BlueBird bb : bbird) {
		Ant target1 = bb.selectTarget(ant);
		if (target1 != null) {
			bb.chase(target1);
		}
		}
		
		for (BlueBird bb : bbird)
		bb.move();
		for (Cockatoo c : cockatoo)
		c.move();
		for (Eagle ea : eagle)
		ea.move();
		
		for (int i = 0; i < seed.size(); i++) {
			for (Cockatoo c : cockatoo)
			if (c.detectCollision(seed.get(i))) {
				seed.remove(i);
				seed.add(new Seed(Util.random(100, paneSize.width - 100),
						Util.random(100, paneSize.height - 100), 
						Util.random(1, 2)));
			}
		}
			
			for (int i = 0; i < cockatoo.size(); i++) {
			for (Eagle ea : eagle) {
			if (ea.detectCollision(cockatoo.get(i))) {
				cockatoo.remove(i);
				cockatoo.add(new Cockatoo(Util.random(100, paneSize.width - 100),
						Util.random(100, paneSize.height - 100),
						1));
			}
		}
			}
		
		
		
		for (int i = 0; i < ant.size(); i++) {
			for (BlueBird bb : bbird)
			if (bb.detectCollision(ant.get(i))) {
				ant.remove(i);
				ant.add(new Ant(Util.random(100, paneSize.width - 100),
						Util.random(100, paneSize.height - 100), 
						Util.random(0.1, 1)));
			}
		}
		for (Cockatoo c : cockatoo)
		c.edgeCollision(paneSize);
		for (BlueBird bb : bbird)
		bb.detectBoundaries(paneSize);
		repaint();
	}
	
	private class MyMouseAdapter extends MouseAdapter {
		//Click the bird to change color
		public void mousePressed(MouseEvent e) {
			for (Cockatoo c : cockatoo) {
				if (c.checkMouseHit(e)) {
					selection = c;
					if (e.isShiftDown()) {
						for (Cockatoo c2 : cockatoo) 
							if(c2!=c) c2.changeColor();
					} 
					else 
						c.changeColor();
				}
			}
		}
	}
	
	private class MyMouseMotionAdapter extends MouseMotionAdapter {
		//Drag the mouse to move the objects
		public void mouseDragged(MouseEvent e) {
			for (BlueBird bb : bbird) {
				if (bb.checkMouseHit(e)) {
					bb.setPos(e.getX(),e.getY());
				}
			}
			for(Seed s: seed) {
				if (s.checkMouseHit(e)) {
					s.setPos(e.getX(),e.getY());
				}
			}
			for(Eagle ea: eagle) {
				if (ea.checkMouseHit(e)) {
					ea.setPos(e.getX(),e.getY());
				}
			}
			for (Cockatoo c : cockatoo) {
				if (c.checkMouseHit(e)) {
					c.setPos(e.getX(),e.getY());
				}
			}
		}
	}
}

