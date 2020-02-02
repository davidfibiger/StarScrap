import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class GameController extends MouseAdapter implements KeyListener{
	private long lastFps = System.currentTimeMillis();
	private long lastFrame = System.currentTimeMillis();
	private boolean firstMenu = true;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	GameStatus gameStatus;
	DrawingCanvas canvas;
	int frames = 0;
	int fps = 0;
	private ArrayList<Integer> keysDown;
	
	GameController(GameStatus gameStatus){
		this.gameStatus = gameStatus;
		keysDown = new ArrayList<Integer>();
		
		
	}
	
	boolean running = true;
	
	public void run(DrawingCanvas canvas) {
		this.canvas = canvas;

		while(running) {
			if(lastFrame + 3<System.currentTimeMillis()) {
				lastFrame = System.currentTimeMillis();
				for(Player player : gameStatus.players) {
					player.movementListener(gameStatus, keysDown);
					player.actions(keysDown, gameStatus);
				}
				for(Bullet bullet : gameStatus.bullets) {
					bullet.checkJustification(gameStatus);
					bullet.move();
					for(Player player : gameStatus.players) { 
						if((bullet.checkColision(bullet, player) || bullet.checkLaserHit(player)) && bullet.order != player.order && !gameStatus.usedLasers.contains(bullet) && !(player.starShip == null)) {						
							if(!bullet.laser) {
								gameStatus.junk.add(bullet);
							}else {
								gameStatus.usedLasers.add(bullet);
							}
							player.lives--;
							//printLives(player);
							if(player.lives <= 0) {
								respawn(player);
							}
							
						}
							
					}
					
				}				
				clear();
			
			
				
				frames++;
				if(lastFps+ 1000 < System.currentTimeMillis()) {
					lastFps = System.currentTimeMillis();
					fps = frames;
					frames = 0;					
				}
				canvas.newFrame(fps);
			}
			
			
		}
		  
		
	}
	public void printLives(Player player) {
		if(player == gameStatus.player1) {
			System.out.println("player1 lives: "+player.lives);
		}else {
			System.out.println("player2 lives: "+player.lives);						
		}
	}
	public void respawn(Player player) {
		if(!gameStatus.activeMenuPlayers.contains(player)) {
			player.getSoundFrom(player.deathSounds).play();
			gameStatus.activeMenuPlayers.add(player);
			player.starShip = null;
			gameStatus.menu = true;
			player.lives = player.maxLives;
		}
	}
	public void clear() {
		/*
		System.out.print("junk: "+gameStatus.junk.size());
		System.out.print("bullets: "+gameStatus.bullets.size());
		System.out.println("drawables: "+gameStatus.drawables.size());
		*/
		for(Drawable drawable : gameStatus.junk) {
			
			gameStatus.bullets.remove(drawable);
			gameStatus.drawables.remove(drawable);
		}
		gameStatus.junk.clear();
	}
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX()+", "+e.getY());
		if(gameStatus.menu) {
			chooseStarShip(e);
		}
	}
	private void chooseStarShip(MouseEvent e) {
		/*for(Player player : gameStatus.activeMenuPlayers) {
			if(player.starShip != null) {				
				gameStatus.menu = false;
				return;
			}
		}*/
		int x = (int)canvas.getWidth()/7;
		int y = ((int)canvas.getHeight()/7)*3;
		if(e.getY() > y && e.getY() < (y/3)*4) {
			if(e.getX() > x && e.getX() < x*2) {				
				activeMenuPlayerToCenter(gameStatus.activeMenuPlayers.get(0));				
				gameStatus.activeMenuPlayers.get(0).starShip = StarScrapMain.starShip1;
				endOrContinueMenu();				
			}
			
			if (e.getX() > x*3 && e.getX() < x*4) {				
				activeMenuPlayerToCenter(gameStatus.activeMenuPlayers.get(0));
				gameStatus.activeMenuPlayers.get(0).starShip = StarScrapMain.starShip2;
				endOrContinueMenu();
				
			}
			if(e.getX() > x*5 && e.getX() < x*6) {	
				activeMenuPlayerToCenter(gameStatus.activeMenuPlayers.get(0));
				gameStatus.activeMenuPlayers.get(0).starShip = StarScrapMain.starShip3;
				endOrContinueMenu();
			}
		}
		
	}
	public void endOrContinueMenu() {		
		gameStatus.activeMenuPlayers.remove(0);
		
		if(gameStatus.activeMenuPlayers.size() == 0) 
			gameStatus.menu = false;
		
	}
	
	private void activeMenuPlayerToCenter(Player activeMenuPlayer) {
		if(activeMenuPlayer.starShip == null) {
			activeMenuPlayer.x = gameStatus.getCanvas().getWidth()/2;
			activeMenuPlayer.y = gameStatus.getCanvas().getHeight()/2;
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("TYPED"+e.getKeyCode());
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("pressed"+e.getKeyCode());
		if(!keysDown.contains(e.getKeyCode())) {
			keysDown.add(e.getKeyCode());
			//System.out.println(e.getKeyCode());
			//System.out.println(e.getKeyCode());
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove((Integer)e.getKeyCode());
		//System.out.println("released"+e.getKeyCode());
		
	}
	
		
		
	
}
