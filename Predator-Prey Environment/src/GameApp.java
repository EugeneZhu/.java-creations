import javax.swing.JFrame;

public class GameApp extends JFrame {

	public GameApp(String title) {
		super(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GamePanel panel = new GamePanel();
		
		
		this.add(panel);
		this.pack();
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new GameApp("Bird Game");
	}

}
