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
	private long lastStar = System.currentTimeMillis();
	private long lastMeteorite = System.currentTimeMillis();
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
			if(lastFrame + 3 < System.currentTimeMillis()) {
				if(!gameStatus.menu && gameStatus.players.size()==0)
					createPlayers();
				/*for(Integer k : keysDown) {
					System.out.println(k);
				}*/
				lastFrame = System.currentTimeMillis();
				if(keysDown.contains(KeyEvent.VK_ESCAPE)) {
					System.exit(0);
				}
				starsManagement();
				sparksManegement();
				playersManagement();
				bulletsManagement();	
				if(!gameStatus.menu) {
					meteoriteManagement();
				}else {
					for(Meteorite meteorite : gameStatus.meteorites) {
						gameStatus.junk.add(meteorite);
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
	private void meteoriteManagement() {		
		if(lastMeteorite + 200 < System.currentTimeMillis() && !gameStatus.skinPick) {
			createMeteorite();
			lastMeteorite = System.currentTimeMillis();
		}
		for(Meteorite meteorite : gameStatus.meteorites) {
			meteorite.update();
			for(Meteorite m : gameStatus.meteorites) {
				if(m.checkColision(m, meteorite) && !meteorite.equals(m)) {
					StarScrapMain.gameStatus.getSoundFrom(StarScrapMain.gameStatus.colisionSounds).play();
					gameStatus.junk.add(m);
					explosion(m, 30, 4);
					gameStatus.junk.add(meteorite);
					explosion(meteorite, 30, 4);
				}
			}
			for(Player player : gameStatus.players) {
				if(meteorite.checkColision(meteorite, player)){
					
					if(!gameStatus.activeSkinPickPlayers.contains(player)) {
						gameStatus.junk.add(meteorite);
						player.lives--;
						StarScrapMain.gameStatus.getSoundFrom(StarScrapMain.gameStatus.colisionSounds).play();
						explosion(meteorite, 30, 4);
					}
					if(player.lives <= 0) {
						respawn(player);
					}
				}
			}
			for(Bullet bullet : gameStatus.bullets) {
				if((meteorite.checkColision(meteorite, bullet)||bullet.checkLaserHit(meteorite))&& !gameStatus.usedLasers.contains(bullet)){
					gameStatus.junk.add(meteorite);
					explosion(meteorite, 30, 4);
					StarScrapMain.gameStatus.getSoundFrom(StarScrapMain.gameStatus.colisionSounds).play();
					if(!bullet.laser) {
						gameStatus.junk.add(bullet);
						explosion(bullet, 20, bullet.color);
					}else {
						gameStatus.usedLasers.add(bullet);
					}
				}
			}
		}
		
	}
	public void createMeteorite() {
		Meteorite m = new Meteorite();
		gameStatus.meteorites.add(m);
	}
	public void createPlayers() {
		gameStatus.player1 = new Player(1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE, KeyEvent.VK_1);
		gameStatus.addPlayer(gameStatus.player1);
		gameStatus.player2 = new Player(2, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_B,KeyEvent.VK_ENTER,KeyEvent.VK_2);
		gameStatus.addPlayer(gameStatus.player2);
	}
	public void bulletsManagement() {
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
					explosion(bullet,20, bullet.color);
					player.lives--;
					StarScrapMain.gameStatus.getSoundFrom(StarScrapMain.gameStatus.colisionSounds).play();
					//printLives(player);
					if(player.lives <= 0) {
						respawn(player);
					}
					
				}
					
			}
			
		}
	}
	public void playersManagement() {
		for(Player player : gameStatus.players) {
			player.movementListener(gameStatus, keysDown);
			player.actions(keysDown, gameStatus);
			player.energyUpdate();
		}
	}
	public void sparksManegement() {
		for(Spark spark : gameStatus.sparks) {
			spark.update();
		}
	}
	public void starsManagement() {
		if(lastStar + 80 < System.currentTimeMillis()) {
			lastStar = System.currentTimeMillis();
			Star star = new Star();
			gameStatus.stars.add(star);
			gameStatus.drawables.add(star);
		}
		for(Star star : gameStatus.stars) {
			star.update();
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
		if(!gameStatus.activeSkinPickPlayers.contains(player)) {
			explosion(player, 100, player.color);
			gameStatus.getSoundFrom(gameStatus.deathSounds).play();
			gameStatus.activeSkinPickPlayers.add(player);
			player.starShip = null;
			gameStatus.skinPick = true;
			player.lives = player.maxLives;
			player.toRandomPosition(player);
		}
	}
	public void explosion(Drawable drawable,int size, int color) {
		for(int a = 0; a < size; a++) {
			Spark spark = new Spark((int)(drawable.x + drawable.width/2), (int)(drawable.y + drawable.height/2), size, color);
			gameStatus.sparks.add(spark);
			gameStatus.drawables.add(spark);
		}
	}
	public void clear() {
		
		for(Drawable drawable : gameStatus.junk) {
			
			gameStatus.bullets.remove(drawable);
			gameStatus.drawables.remove(drawable);
			gameStatus.meteorites.remove(drawable);
		}
		gameStatus.junk.clear();
	}
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX()+", "+e.getY());
		if(gameStatus.skinPick) {
			chooseStarShip(e);
		}
		if(gameStatus.menu) {
			menu(e);
		}
	}
	private void menu(MouseEvent e) {
		int x = (int)canvas.getWidth()/5;
		int y = (int)canvas.getHeight()/5;
		if(e.getY() > y*3 && e.getY() < (y*4)){
			if(e.getX() > x*2 && e.getX() < x*3) {
				gameStatus.menu = false;
			}
		}
		
	}
	private void chooseStarShip(MouseEvent e) {
		
		int x = (int)canvas.getWidth()/7;
		int y = ((int)canvas.getHeight()/7)*3;
		if(e.getY() > y && e.getY() < (y/3)*4) {
			if(e.getX() > x && e.getX() < x*2) {				
							
				gameStatus.activeSkinPickPlayers.get(0).starShip = StarScrapMain.starShip1;
				endOrContinueMenu();				
			}
			
			if (e.getX() > x*3 && e.getX() < x*4) {				
				
				gameStatus.activeSkinPickPlayers.get(0).starShip = StarScrapMain.starShip2;
				endOrContinueMenu();
				
			}
			if(e.getX() > x*5 && e.getX() < x*6) {	
				
				gameStatus.activeSkinPickPlayers.get(0).starShip = StarScrapMain.starShip3;
				endOrContinueMenu();
			}
		}
		
	}
	public void endOrContinueMenu() {		
		gameStatus.activeSkinPickPlayers.remove(0);
		
		if(gameStatus.activeSkinPickPlayers.size() == 0) 
			gameStatus.skinPick = false;
		
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
