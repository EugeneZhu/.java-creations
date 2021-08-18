package main;

import static util.ImageLoader.loadImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.Timer;

import Backgrounds.Backyard;
import Backgrounds.Driveway;
import Backgrounds.Ending;
import Backgrounds.Intro;
import Media.Bag;
import Media.BagSpot;
import Media.Branch;
import Media.FallenLeaves;
import Media.GeneratedTree;
import Media.Leaves;
import Media.Man;
import Media.MovingBag;
import Media.Rake;
import Media.Text;
import Media.Wind;
import ddf.minim.AudioPlayer;
import ddf.minim.Minim;
import main.SimPanel.MyMouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import util.MinimHelper;

public class SimPanel extends JPanel implements ActionListener {
	public static int W_WIDTH = 1050;
	public static int W_HEIGHT = 750;

	private GeneratedTree tree1;
	private GeneratedTree tree2;
	
	// variables for holding mouse position
	private double mouseX;
	private double mouseY;

	// Fields for state and transitions
	private int state = -1;
	private Backyard backyard;
	private Driveway driveway;
	private Intro intro;
	private Ending ending;
	private Man man;
	private Rake rake;
	private Bag bag;
	private BagSpot bagspot;
	private FallenLeaves fallenLeaves1, fallenLeaves2, fallenLeaves3;
	private MovingBag movingbag;
	private Wind wind;
	private Text text;


	private Timer timer, windTimer;
	
	private Minim minim;
	private AudioPlayer bkmusic, windsound, click;

	SimPanel(JFrame frame) {
		this.setBackground(Color.white);
		setPreferredSize(new Dimension(W_WIDTH, W_HEIGHT));
		Random rand = new Random();

		//Set values for images/objects
		backyard = new Backyard("assets/Backyard.jpg");
		driveway = new Driveway("assets/Driveway.jpg");
		intro = new Intro("assets/Intro.jpg", 0, 0, 1);
		ending = new Ending("assets/Ending.jpg", 0, 0, 1);
		man = new Man("assets/Man.png");
		rake = new Rake(W_WIDTH / 2, 20, 0.5);
		bag = new Bag(W_WIDTH / 2 - 230, W_HEIGHT / 2 + 200, 1);
		bagspot = new BagSpot(W_WIDTH / 2 + 100, W_HEIGHT / 2 - 50, 0.5);
		fallenLeaves1 = new FallenLeaves(rand.nextInt(W_WIDTH), W_HEIGHT*2/3 + 200, 0.5);
		fallenLeaves2 = new FallenLeaves(rand.nextInt(W_WIDTH), W_HEIGHT*2/3 + 200, 0.5);
		fallenLeaves3 = new FallenLeaves(rand.nextInt(W_WIDTH), W_HEIGHT*2/3 + 200, 0.5);
		movingbag = new MovingBag(W_WIDTH / 2 - 180, W_HEIGHT - 200, 1);
		wind = new Wind(0, 0, W_WIDTH, W_HEIGHT);
		text = new Text();
		
		minim = new Minim(new MinimHelper());

		bkmusic = minim.loadFile("Autumn.mp3");
		click = minim.loadFile("tap.mp3");
		windsound = minim.loadFile("Wind.mp3");
		
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		
		MyMouseMotionListener mml = new MyMouseMotionListener();
		addMouseMotionListener(mml);

		timer = new Timer(30, this);
		timer.start();
		windTimer = new Timer(60, this);
		
		tree1 = new GeneratedTree(W_WIDTH/3, W_HEIGHT*4/5, 200,20,0,4);
		tree2 = new GeneratedTree(W_WIDTH*2/3, W_HEIGHT*4/5, 200,20,0,4);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		System.out.println(state);

		//Game States
		if (state == -1) {
			intro.drawIntro(g2);
		}
		
		else if (state == 0) {
			intro.drawIntro(g2);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fill(new Rectangle2D.Double(0, 0, W_WIDTH, W_HEIGHT));
			wind.drawWind(g2);
			windsound.play();
			
			
		}  else if (state == 1) {
			windsound.pause();
			windsound.rewind();
			rake.setLightOn(false);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			//fallenLeaves.drawButton(g2);
			bag.setBagImg(0);
			bag.drawButton(g2);
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fill(new Rectangle2D.Double(0, 0, W_WIDTH, W_HEIGHT));
			rake.drawButton(g2);
			man.drawMan(g2);
			text.paint(g2);
			

		}  else if (state == 2) {
			windsound.pause();
			windsound.rewind();
			rake.setLightOn(false);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			//fallenLeaves.drawButton(g2);
			bag.setBagImg(0);
			bag.drawButton(g2);
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fill(new Rectangle2D.Double(0, 0, W_WIDTH, W_HEIGHT));
			rake.drawButton(g2);
			
			
		}  else if (state == 3) {
			rake.setLightOn(true);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			rake.drawButton(g2);
			bag.drawButton(g2);
			fallenLeaves1.drawButton(g2);
			
			
		}  else if (state == 4) {
			rake.setLightOn(true);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			rake.drawButton(g2);
			bag.drawButton(g2);
			fallenLeaves2.drawButton(g2);
			
			
		}  else if (state == 5) {
			rake.setLightOn(true);
			backyard.drawBackyard(g2);
			tree1.drawTree(g2);
			tree2.drawTree(g2);
			rake.drawButton(g2);
			bag.drawButton(g2);
			fallenLeaves3.drawButton(g2);
			
			
		}  else if (state == 6) {
			driveway.drawDriveway(g2);
			bagspot.drawButton(g2);
			movingbag.drawButton(g2);
			
		}  else if (state == 7) {
			ending.drawEnding(g2);
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		repaint();
	}

	public class MyMouseListener extends MouseAdapter {
		
		//Enable mouse/touch pad clicking on screen
		public void mouseClicked(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			
			if (state == -1 && intro.clicked(mouseX, mouseY)) {
				state = 0;
			}
			
			
			else if (state == 0 && intro.clicked(mouseX, mouseY)) {
				state = 1;
			} 
			
			else if (state == 1 && intro.clicked(mouseX, mouseY)) {
				state = 2;
			}
			
			else if (state == 2 && rake.clicked(mouseX, mouseY)) {
				click.play(0);
				bkmusic.play();
				state = 3;
			} 
			
			else if (state == 7 && ending.clicked(mouseX, mouseY)) {
				state = -1;
				bkmusic.pause();
				bkmusic.rewind();
			}
			
		} 
	}
	
	public class MyMouseMotionListener extends MouseMotionAdapter {

		public void mouseDragged(MouseEvent e) {

			//Allow mouse dragging the leaves and bag
			mouseX = e.getX();
			mouseY = e.getY();

			if (state == 3) {
				fallenLeaves1.setXPos(mouseX);
				fallenLeaves1.setYPos(mouseY);
				if (fallenLeaves1.hit(bag)) {
					bag.setBagImg(1);
					state = 4;
				}
				
			}
			
			if (state == 4) {
				fallenLeaves2.setXPos(mouseX);
				fallenLeaves2.setYPos(mouseY);
				if (fallenLeaves2.hit(bag)) {
					bag.setBagImg(1);
					state = 5;
				}
				
			}
			
			if (state == 5) {
				fallenLeaves3.setXPos(mouseX);
				fallenLeaves3.setYPos(mouseY);
				if (fallenLeaves3.hit(bag)) {
					bag.setBagImg(1);
					state = 6;
				}
				
			}
			
			if (state == 6) {
				movingbag.setXPos(mouseX);
				movingbag.setYPos(mouseY);
				if (movingbag.hit(bagspot)) {
					bagspot.setBagImg(1);
					state = 7;
				}
			}
			repaint();

		}
	}
	
}
