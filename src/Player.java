import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.sound.sampled.Clip;

public class Player extends Drawable {
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Image starShip;	
	private long lastEnergyUpdate = System.currentTimeMillis();
	private int shotCost = 15;
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
	public final int maxLives = 6;
	public int lives = maxLives;
	public final int maxEnergy = 100;
	public int energy = maxEnergy;
	public int direction;
	public int color;
	
	public Player(int order, int forwardKey, int backwardsKey, int leftwardsKey, int rightwardsKey, int boostKey, int shootingKey, int skinKey) {		
		GameStatus.skinPick = true;
		defaultSpeed = convert((double)((double)StarScrapMain.canvas.getWidth() / (double)3.84));
		//width = 80;
		width = StarScrapMain.canvas.getWidth()/40;
		height = width;

		speed = defaultSpeed;
		boost = defaultSpeed;
		this.forwardKey = forwardKey;
		this.backwardsKey = backwardsKey;
		this.leftwardsKey = leftwardsKey;
		this.rightwardsKey = rightwardsKey;
		this.boostKey = boostKey;
		this.shootingKey = shootingKey;
		this.skinKey = skinKey;
		this.order = order;
		toRandomPosition(this);
	}



	

	
	
	public void paint(Graphics g, GameStatus gameStatus) {
		g.drawImage(starShip, (int)x, (int)y, (int)width, (int)height, null);
		if(gameStatus.skinPick) {
	        	int x = (int)gameStatus.getCanvas().getWidth()/7;	    		
	    		final int width = x;
	    		
	    		final int height = ((int)gameStatus.getCanvas().getHeight()/7);
	    		int y = height * 3;
	    		
	    		g.drawImage(StarScrapMain.starShip1, x, y, width, height, null);
	    		
	    		g.drawImage(StarScrapMain.starShip2, x * 3, y, width, height, null);
	    		
	    		g.drawImage(StarScrapMain.starShip3, x * 5, y, width, height, null);
	    		 	
	    }
		
		drawHpBar(g, gameStatus);
		drawEnergyBar(g, gameStatus);
		
		
	}
	
	private void drawEnergyBar(Graphics g, GameStatus gameStatus) {
		//g.setColor(Color.WHITE);
		color = setColor(g, starShip);
		int x = gameStatus.getCanvas().getWidth()/32;	
		int y = gameStatus.getCanvas().getHeight()/27;
		int width = x * 8;
		int height = y / 3;
		if(order == 1) {
			g.drawRect(x, y, width, height);
			drawEnergy(g, gameStatus, x, y, width, height);
		}else {
			x = gameStatus.getCanvas().getWidth() - x * 9;
			g.drawRect(x, y, width, height);
			drawEnergy(g, gameStatus, x, y, width, height);
		}
	}
	
	private void drawEnergy(Graphics g, GameStatus gameStatus, int x, int y, int width, int height) {
		color = setColor(g, starShip);
		if(energy>0) {
			if(order == 1) {				
				g.fillRect(x + 2, y + 2, (int)(((double)width / (double)maxEnergy) * energy) - 4, height - 4);
			}else {
				g.fillRect(x + 2 + width - (int)(((double)width / (double)maxEnergy) * energy), y + 2, (int)(((double)width / (double)maxEnergy) * energy) - 4, height - 4);
			}
		}
	}



	private void drawHpBar(Graphics g, GameStatus gameStatus) {
		//g.setColor(Color.WHITE);
		color = setColor(g, starShip);
		int x = gameStatus.getCanvas().getWidth()/32;	
		int y = gameStatus.getCanvas().getHeight()/18;
		int width = x * 4;
		int height = y / 2;
		if(order == 1) {
			x = gameStatus.getCanvas().getWidth()/32;			
			g.drawRect(x,y,width, height);		
			drawHp(g, gameStatus, x, y, width, height);
		}else {
			x = gameStatus.getCanvas().getWidth() - x * 5;
			g.drawRect(x,y,width , height);
			drawHp(g, gameStatus, x, y, width, height);
		}
		
	
		
	}

	public void drawHp(Graphics g, GameStatus gameStatus, int x, int y, int width, int height) {
		setColor(g, starShip);
		if(lives>0) {
			if(order == 1) {
				for(int helthBar = 0; helthBar < lives; helthBar++) {
					g.fillRect(x + width/maxLives * helthBar + 3, y + 3, width/maxLives - 6, height - 6);
				}
			}else {
				for(int helthBar = 1; helthBar <= lives; helthBar++) {
					g.fillRect(x + width - width/maxLives * helthBar + 3, y + 3, width/maxLives - 6, height - 6);
				}
			}
		}
	}
	

	void movementListener(GameStatus gameStatus, ArrayList<Integer> keysDown) {
		long time = System.currentTimeMillis();
		
		//nahoru
		if(lastTimeForward >0) {
			if(keysDown.contains(leftwardsKey) && keysDown.contains(rightwardsKey)) {
				y -= calculateMovement(time, lastTimeForward, speed);
				direction = 1;
			}else if(keysDown.contains(leftwardsKey) || keysDown.contains(rightwardsKey)) {
				y -= calculateMovement(time, lastTimeForward, speed) / Math.sqrt(2);
				//sikmo nahoru
				if(keysDown.contains(leftwardsKey)){
					direction = 8;
				}else {
					direction = 2;
				}
			}else {
				y -= calculateMovement(time, lastTimeForward, speed);
				direction = 1;
			}					
		}
		lastTimeForward = getLastTime(time,lastTimeForward,forwardKey,y, keysDown);
		
		//dolu
		if(lastTimeBackwards >0) {
			if(keysDown.contains(leftwardsKey) && keysDown.contains(rightwardsKey)) {
				y += calculateMovement(time, lastTimeBackwards, speed);
				direction = 5;
			}else if(keysDown.contains(leftwardsKey) || keysDown.contains(rightwardsKey)) {
				y += calculateMovement(time, lastTimeBackwards, speed) / Math.sqrt(2);
				//sikmo dolu
				if(keysDown.contains(leftwardsKey)) {
					direction = 6;
				}else {
					direction = 4;
				}
			}else {
				y += calculateMovement(time, lastTimeBackwards, speed);
				direction = 5;
			}						
		}
		lastTimeBackwards = getLastTime(time,lastTimeBackwards, backwardsKey,(gameStatus.getCanvas().getHeight() - height) - y, keysDown);
		
		//doleva
		if(lastTimeLeftwards >0) {
			if(keysDown.contains(forwardKey) && keysDown.contains(backwardsKey)) {
				x -= calculateMovement(time, lastTimeLeftwards, speed);
				direction = 7;
			}else if(keysDown.contains(forwardKey) || keysDown.contains(backwardsKey)) {
				x -= calculateMovement(time, lastTimeLeftwards, speed) / Math.sqrt(2);
				//sikmo doleva
				if(keysDown.contains(forwardKey)) {
					direction = 8;
				}else {
					direction = 6;
				}
			}else {
				x -= calculateMovement(time, lastTimeLeftwards, speed);
				direction = 7;
			}						
		}
		lastTimeLeftwards = getLastTime(time,lastTimeLeftwards, leftwardsKey, x, keysDown);
		
		//doprava
		if(lastTimeRightwards >0) {
			if(keysDown.contains(forwardKey) && keysDown.contains(backwardsKey)) {
				x += calculateMovement(time, lastTimeRightwards, speed);	
				direction = 3;
			}else if(keysDown.contains(forwardKey) || keysDown.contains(backwardsKey)) {
				x += calculateMovement(time, lastTimeRightwards, speed) / Math.sqrt(2);	
				//sikmo doprava
				if(keysDown.contains(forwardKey)) {
					direction = 2;
				}else {
					direction = 4;
				}
			}else{
				x += calculateMovement(time, lastTimeRightwards, speed);
				direction = 3;
			}					
		}
		lastTimeRightwards = getLastTime(time,lastTimeRightwards, rightwardsKey,(gameStatus.getCanvas().getWidth() - width) - x, keysDown);
		
		if(keysDown.contains(boostKey) && energy >= 1) {
			if(speed == defaultSpeed) {
				speed += boost;				
			}			
		}else {
			if(speed == defaultSpeed + boost){
				speed = defaultSpeed;
			}
			
		}
		
	}
	
	
	void actions(ArrayList<Integer>  keysDown, GameStatus gameStatus) {
		if(keysDown.contains(skinKey) && !gameStatus.skinPick) {
			gameStatus.activeSkinPickPlayers.add(this);
			gameStatus.skinPick = true;
			
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
		
		if(lastLaser < time - 300  && shooted && lastLaser != 0 && energy >= shotCost) {
			Bullet laser = new Bullet(x, y, direction, order, starShip, true && starShip!=null);
			gameStatus.bullets.add(laser);
			gameStatus.drawables.add(laser);
			gameStatus.getSoundFrom(gameStatus.laserSounds).play();
			lastLaser = time;
			energy = energy - shotCost;	
		}
	}

	

	private void shoot(GameStatus gameStatus,double x , double y){
		
		long time = System.currentTimeMillis();
		
		if(lastShot < time - 1  && !shooted && starShip!=null && energy >= shotCost) {
			Bullet bullet = new Bullet(x,y, direction, order, starShip, false);
			gameStatus.bullets.add(bullet);
			gameStatus.getSoundFrom(gameStatus.shotSounds).play();
			gameStatus.drawables.add(bullet);
			//System.out.println("boom");
			lastShot = time;
			shooted = true;
			lastLaser = time;
			energy = energy - shotCost;
		}
		
	}
	public void energyUpdate() {
		if(System.currentTimeMillis() >= lastEnergyUpdate + 25) {
			if(energy < maxEnergy && speed != defaultSpeed + boost)
				energy++;
			
			if(speed == defaultSpeed + boost) {
				energy = energy - 1;				
			}
			lastEnergyUpdate = System.currentTimeMillis();			
		}
	}

}
