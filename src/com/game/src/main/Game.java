/*
 * 
 */


package com.game.src.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.awt.event.MouseListener;

import javax.print.DocFlavor.URL;
import javax.swing.JFrame;

import com.game.src.classes.EntityA;
import com.game.src.classes.EntityB;

public class Game extends Canvas implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	
	// dimension for the window
	public static final int WIDTH = 320;  
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	public final String TITLE = "2D Space Game";
	
	private boolean running = false;
	private Thread thread;
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB); 	//buffers whole window
	private BufferedImage spriteSheet = null;
	private BufferedImage background = null;
	
	private boolean is_shooting = false;
	
	private Textures tex;
	private BufferedImage player; 
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	
	private Player p;
	private Controller c;
	private Menu menu;
	private endGame endGame;
	
	private int enemyCount = 1;				// how many enemies to spawn
	private int enemyKill = 0;		
	private int totalEnemyKillCount = 0;
	public static int health = 200;
	
	public int gettotalEnemyKillCount() {
		return totalEnemyKillCount;
	}

	public void settotalEnemyKillCount(int totalEnemyKillCount) {
		this.totalEnemyKillCount = totalEnemyKillCount;
	}
	
	
	public int getEnemyCount() {
		return enemyCount;
	}

	
	public void setEnemyCount(int enemyCount) {
		this.enemyCount = enemyCount;
	}


	public int getEnemyKill() {
		return enemyKill;
	}


	public void setEnemyKill(int enemyKill) {
		this.enemyKill = enemyKill;
	}
	

	public static enum STATE{
		MENU,
		GAME,
		ENDGAME
	};
	
	public static STATE state = STATE.MENU;
	
	public void init(){
		requestFocus();
		bufferedImageLoader loader = new bufferedImageLoader();
		try{
			java.net.URL url1 = Game.class.getResource("/spriteSheet.png");
			java.net.URL url2 = Game.class.getResource("/background.png");
			String stringurl1 = url1.toString();
			String stringurl2 = url2.toString();
			System.out.println(stringurl1);
			System.out.println(stringurl2);
			spriteSheet = loader.loadImage("/spriteSheet.png");
			background = loader.loadImage("/background.png");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		

		addKeyListener(new KeyInput(this));
		tex = new Textures(this);
		c = new Controller(tex,this);
		p = new Player(310,350,tex,this,c);
		menu = new Menu();
		endGame = new endGame();
		
		c.createEnemy(enemyCount);
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		
		addKeyListener(new KeyInput(this));
		addMouseListener(new MouseInput());
		
	}

	
	private synchronized void start() {
	
		if(running) {
			return;
		}
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	private synchronized void stop() {
		if(!running) {
			return;
		}
		running = false;
		try {
			thread.join();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	private synchronized void restart() {
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	
	
	// this is the game loop
	// manages frames per second
	public void run() {
		init();
		long lastTime = System.nanoTime();    // Used for the refreshing
		final double amountOfTicks = 60.0;	// update 60x
		double ns = 1000000000 / amountOfTicks;
		double delta = 0; // used to find latency of time method call to while loop
		int update = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				update++;
				delta--;
			}
			render();
			frames++;
			
			//checks your frames per second
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				update = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	private void tick() {
		if(state == STATE.GAME){ 
			p.tick();
			c.tick();
		}
		
		if(enemyKill>= enemyCount) {
			enemyCount+=2;
			enemyKill = 0;
			c.createEnemy(enemyCount);
			}
		
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if(bs == null) {
			createBufferStrategy(3);     // 3 buffers
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		/////////////////////////////////////////////////  used for spritesheets
		g.drawImage(image,0,0,getWidth(),getHeight(),this);
		
		g.drawImage(background, 0, 0, null);
		
		if(state ==STATE.GAME) {
			p.render(g);
			c.render(g);
			
			g.setColor(Color.gray);
			g.fillRect(5, 5, 200, 50);
			
			g.setColor(Color.green);
			g.fillRect(5, 5, health, 50);
			
			g.setColor(Color.white);
			g.drawRect(5, 5, health, 50);
			
			
			g.setColor(Color.yellow);
			Font minionsDestroyed = new Font("arial",Font.BOLD,14);
			g.setFont(minionsDestroyed);
			
			g.drawString( totalEnemyKillCount + "  Minions Destroyed", 10, 460);
			
			
			
		}
		
		if(health <= 0){
			state = STATE.ENDGAME;
			endGame.render(g);
			//running = false;
			
			
		}
		
		if(state == STATE.MENU) {
			menu.render(g);
		}
		/////////////////////////////////////////////////
		g.dispose();
		bs.show();
		//}
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(state == STATE.GAME) {
		if (key == KeyEvent.VK_RIGHT) {
			p.setVelx(5);
			
		}else if (key == KeyEvent.VK_LEFT) {
			p.setVelx(-5);
			
		}else if (key == KeyEvent.VK_DOWN) {
			//p.setVely(5);
			
		}else if (key == KeyEvent.VK_UP) {
			//p.setVely(-5);
		} else if (key == KeyEvent.VK_SPACE && !is_shooting) {
			is_shooting = true;
			c.addEntity(new Bullet(p.getX(),p.getY(),tex,this));
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_RIGHT) {
			p.setVelx(0);
			
		}else if (key == KeyEvent.VK_LEFT) {
			p.setVelx(0);
			
		}else if (key == KeyEvent.VK_DOWN) {
			p.setVely(0);
			
		}else if (key == KeyEvent.VK_UP) {
			p.setVely(0);
			
		}else if(key == KeyEvent.VK_SPACE) {
			is_shooting = false;
		}
		
	}
	
	
	public static void main(String args[]){
		Game game = new Game();
		
		// creating new dimensions 
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		
		// Creates the window for the game 
		JFrame frame = new JFrame(game.TITLE);						// titles the window
		frame.add(game);
		frame.pack();												
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// allows x button to work
		frame.setResizable(false);         						 // do not resize
		frame.setLocationRelativeTo(null); 						 // do not set location
		frame.setVisible(true);									// window is visible 
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}
	
}
