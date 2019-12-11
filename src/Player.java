import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player extends Drawable {
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Image starShip;
	private int forwardKey;
	private int backwardsKey;
	private int leftwardsKey;
	private int rightwardsKey;
	private int boostKey;
	private int shootingKey;
	private int skinKey;
	private long lastTimeForward;
	private long lastTimeBackwards;
	private long lastTimeLeftwards;
	private long lastTimeRightwards;
	private long lastShot = 0;
	private long lastLaser = 0;
	private boolean shooted = false;
	public int order;	
	public int maxLives = 4;
	public int lives = maxLives;
	public int direction;
	public Player(int order, int forwardKey, int backwardsKey, int leftwardsKey, int rightwardsKey, int boostKey, int shootingKey, int skinKey) {
		GameStatus.menu = true;
		defaultSpeed = convert(1200d);
		width = 80;
		height = 80;
		speed = defaultSpeed;
		boost = convert(1000d);
		this.forwardKey = forwardKey;
		this.backwardsKey = backwardsKey;
		this.leftwardsKey = leftwardsKey;
		this.rightwardsKey = rightwardsKey;
		this.boostKey = boostKey;
		this.shootingKey = shootingKey;
		this.skinKey = skinKey;
		this.order = order;
		
	}
	
	public void paint(Graphics g, GameStatus gameStatus) {
		
		 if(gameStatus.menu) {
	        	int x = (int)gameStatus.getCanvas().getWidth()/7;	    		
	    		final int width = x;
	    		
	    		final int height = ((int)gameStatus.getCanvas().getHeight()/7);
	    		int y = height * 3;
	    		
	    		g.drawImage(StarScrapMain.starShip1, x, y, width, height, null);
	    		
	    		g.drawImage(StarScrapMain.starShip2, x * 3, y, width, height, null);
	    		
	    		g.drawImage(StarScrapMain.starShip3, x * 5, y, width, height, null);
	    		 	
	    }
		g.drawImage(starShip, (int)x, (int)y, (int)width, (int)height, null);
		drawHpBar(g, gameStatus);
		
		
	}
	
	private void drawHpBar(Graphics g, GameStatus gameStatus) {
		setColor(g, starShip);	
		int x = gameStatus.getCanvas().getWidth()/32;	
		int y = gameStatus.getCanvas().getHeight()/18;
		int width = x * 2;
		int height = y / 2;
		if(order == 1) {
			x = gameStatus.getCanvas().getWidth()/32;			
			g.drawRect(x,y,width*2, height);		
			printHP(g, gameStatus, x, y, width, height);
		}else {
			x = gameStatus.getCanvas().getWidth() - x * (maxLives + 1);
			g.drawRect(x,y,width * 2, height);
			printHP(g, gameStatus, x, y, width, height);
		}
		
	
		
	}

	public void printHP(Graphics g, GameStatus gameStatus, int x, int y, int width, int height) {
		if(lives>0 && !gameStatus.activeMenuPlayers.contains(this)) {
			for(int helthBar = 0; helthBar < lives; helthBar++) {
				g.fillRect(x + width/2 * helthBar + 3, y + 3, width/2 - 6, height - 6);
			}
		}
	}

	void movementListener(GameStatus gameStatus, ArrayList<Integer> keysDown) {
		long time = System.currentTimeMillis();
		
		if(lastTimeForward >0) {
			if(keysDown.contains(leftwardsKey) && keysDown.contains(rightwardsKey)) {
				y -= calculateMovement(time, lastTimeForward, speed);
				direction = 1;
			}else if(keysDown.contains(leftwardsKey) || keysDown.contains(rightwardsKey)) {
				y -= Math.sqrt(0.5 * calculateMovement(time, lastTimeForward, speed));	
			}else {
				y -= calculateMovement(time, lastTimeForward, speed);
				direction = 1;
			}					
		}

		lastTimeForward = getLastTime(time,lastTimeForward,forwardKey,y, keysDown);
		
		if(lastTimeBackwards >0) {
			if(keysDown.contains(leftwardsKey) && keysDown.contains(rightwardsKey)) {
				y += calculateMovement(time, lastTimeBackwards, speed);
				direction = 5;
			}else if(keysDown.contains(leftwardsKey) || keysDown.contains(rightwardsKey)) {
				y += Math.sqrt(0.5 * calculateMovement(time, lastTimeBackwards, speed));
			}else {
				y += calculateMovement(time, lastTimeBackwards, speed);
				direction = 5;
			}						
		}
		lastTimeBackwards = getLastTime(time,lastTimeBackwards, backwardsKey,(gameStatus.getCanvas().getHeight() - height) - y, keysDown);
		
		if(lastTimeLeftwards >0) {
			if(keysDown.contains(forwardKey) && keysDown.contains(backwardsKey)) {
				x -= calculateMovement(time, lastTimeLeftwards, speed);
				direction = 7;
			}else if(keysDown.contains(forwardKey) || keysDown.contains(backwardsKey)) {
				x -= Math.sqrt(0.5 * calculateMovement(time, lastTimeLeftwards, speed));
			}else {
				x -= calculateMovement(time, lastTimeLeftwards, speed);
				direction = 7;
			}						
		}
		lastTimeLeftwards = getLastTime(time,lastTimeLeftwards, leftwardsKey, x, keysDown);
		
		if(lastTimeRightwards >0) {
			if(keysDown.contains(forwardKey) && keysDown.contains(backwardsKey)) {
				x += calculateMovement(time, lastTimeRightwards, speed);	
				direction = 3;
			}else if(keysDown.contains(forwardKey) || keysDown.contains(backwardsKey)) {
				x += Math.sqrt(0.5 * calculateMovement(time, lastTimeRightwards, speed));	
			}else{
				x += calculateMovement(time, lastTimeRightwards, speed);
				direction = 3;
			}					
		}
		lastTimeRightwards = getLastTime(time,lastTimeRightwards, rightwardsKey,(gameStatus.getCanvas().getWidth() - width) - x, keysDown);
		
		if(keysDown.contains(boostKey)) {
			if(speed == defaultSpeed) {
				speed += boost;
				
			}			
		}else if(speed == defaultSpeed + boost){
			speed = defaultSpeed;
			
		}
		
	}
	
	
	void actions(ArrayList<Integer>  keysDown, GameStatus gameStatus) {
		if(keysDown.contains(skinKey) && !gameStatus.menu) {
			gameStatus.activeMenuPlayers.add(this);
			gameStatus.menu = true;
			
		}
		if(keysDown.contains(shootingKey) && starShip!=null) {
			if(direction == 1) {
				double bX = x + width/2 - 5/2;
				double bY = y + height / 2;
				shoot(gameStatus, bX, bY);
				laser(gameStatus, bX, bY);
			}	
			if(direction == 3) {
				double bX = x + width / 2;
				double bY = y + height / 2 - 5/2;
				shoot(gameStatus, bX, bY);
				laser(gameStatus, bX, bY);
			}	
			if(direction == 5) {
				double bX = x + width / 2 - 5/2;
				double bY = y + height / 2;
				shoot(gameStatus,bX, bY);
				laser(gameStatus, bX, bY);
			}	
			if(direction == 7) {
				double bX = x + width / 2;
				double bY = y + height / 2 - 5/2;
				shoot(gameStatus, bX, bY);
				laser(gameStatus, bX, bY);
			}
			
		}else {
			shooted = false;
			lastLaser = 0;
		}
		
	}

	private void laser(GameStatus gameStatus, double x , double y) {
		long time = System.currentTimeMillis();
		
		if(lastLaser < time - 500  && shooted && lastLaser != 0) {
			Bullet laser = new Bullet(x, y, direction, order, starShip, true && starShip!=null);
			gameStatus.bullets.add(laser);
			gameStatus.drawables.add(laser);
			lastLaser = time;
				
		}
	}

	private void shoot(GameStatus gameStatus,double x , double y){
		long time = System.currentTimeMillis();
		
		if(lastShot < time - 1  && !shooted && starShip!=null) {
			Bullet bullet = new Bullet(x,y, direction, order, starShip, false);
			gameStatus.bullets.add(bullet);
			
			gameStatus.drawables.add(bullet);
			//System.out.println("boom");
			lastShot = time;
			shooted = true;
			lastLaser = time;
		}
		
	}

}
