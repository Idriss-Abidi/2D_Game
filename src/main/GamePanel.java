package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize=16;
	 final int scale =3;
	 
	 public final int tileSize=originalTileSize * scale; // 48x48 tile
	 public final int maxScreenCol = 16;
	 public final int maxScreenRow =12;
	 public final int screenWidth = tileSize * maxScreenCol; //768 pixels
	 public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	 
	 public final int maxWorldCol=50;
	 public final int maxWorldRow=50;
	 public final int WorldWidth=tileSize*maxWorldCol;
	 public final int WorldHeigh=tileSize*maxWorldRow;	 
	 
	 int FPS= 60;
	 
	 TileManager tileM=new TileManager(this);
	 keyHandler keyH=new keyHandler();
	 Thread gameThread;
	 public CollisionChecker cChecker = new CollisionChecker(this);
	 public Player player = new Player(this, keyH);
//	 int playerX=100;
//	 int playerY=100;
//	 int playerSpreed =4;
	 public GamePanel() {
		 this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		 this.setBackground(Color.black);
		 this.setDoubleBuffered(true);
		 this.addKeyListener(keyH);
		 this.setFocusable(true);
	 }

	 public void startGameThread() {
		 gameThread = new Thread(this);
		 gameThread.start();
	 }
	@Override
//	public void run() {
//		
//		double drawInterval = 1000000000/FPS;
//		double nextDrawTime = System.nanoTime() + drawInterval;
//		while(gameThread!=null) {
////			System.out.println("The game is running.");
//			
////			long currentTime = System.nanoTime();
//			
//			// Update information such as character positions
//			update();
//			
//			
//			//draw the screen with the updated information
//			repaint();
//			
//			try {
//				double remainingTime = nextDrawTime - System.nanoTime();
//				remainingTime =remainingTime/1000000;
//				if(remainingTime <0) {
//					remainingTime=0;
//				}
//				Thread.sleep((long) remainingTime);
//				
//				nextDrawTime+=drawInterval;
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
	public void run() {
		double drawInterval =1000000000/FPS;
		double delta =0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(gameThread!=null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			lastTime=currentTime;
			long timer=0;
			int drawCount=0;
			if(delta>=1) {
				update();
				repaint();
				delta --;
				drawCount++;
			}
			if(timer>=1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount=0;
				timer=0;
				
			}
		}
	}
	public void update() {
		player.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2= (Graphics2D)g;
		
		
		tileM.draw(g2);
		
		player.draw(g2);
		g2.dispose();
		
	}
	 
	 
}
