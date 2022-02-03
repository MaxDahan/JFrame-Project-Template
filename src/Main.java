import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

public class Main extends Canvas implements Runnable, KeyListener {
	private final static int GWIDTH = 1920, GHEIGHT = 1080;
	protected boolean running;
	protected Thread thread;
	
	public Main() {
		new Frame(this, GWIDTH, GHEIGHT);
		
		start();
	}
	
	// methods ran every frame
	public void update() {
		
	}
	public void paint() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		////////////////////////////////////////////////
		
		g.setColor(new Color(255, 255, 255));
		g.fillRect(0, 0, GWIDTH, GHEIGHT);
		
		g.setColor(Color.BLACK);
		g.fillRect(100, 100, 400, 300);
		
		////////////////////////////////////////////////
		g.dispose();
		bs.show();
	}

	// interpreting user input
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_ESCAPE) {
			stop();
		}
	}
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		
	}

	// methods for set up
	public void start() {
		addKeyListener(this);
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	public void stop() {
		running = false;
		thread.stop();
		System.exit(0);
	}
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			paint();
			frames++;
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
		}
		stop();
	}
	public static void main(String[] args) {
		new Main();
	}
	public void keyTyped(KeyEvent e) {}
}
