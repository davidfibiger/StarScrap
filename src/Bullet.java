import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public class Bullet extends Drawable {
	private long lastTime;
	private int direction;
	public int order;
	private Image starShip;
	public boolean laser;
	private long bornTime;
	public Color color;
	
	public Bullet(double x, double y, int direction, int order, Image starShip, boolean laser) {		
		speed = convert((double)((double)StarScrapMain.canvas.getWidth() / (double)1.536));
		this.direction = direction;
		/*if(direction == 2 || direction == 6) {
			height = (double)((double)StarScrapMain.canvas.getWidth() / (double)700);
			width = (double)((double)StarScrapMain.canvas.getWidth() / (double)150);
		}else {*/
			height = (double)((double)StarScrapMain.canvas.getWidth() / (double)150);
			width = (double)((double)StarScrapMain.canvas.getWidth() / (double)700);
		/*}*/
		this.x = x;
		this.y = y;
		this.order = order;
		this.starShip = starShip;
		this.laser = laser;
		if(laser) {
			bornTime = System.currentTimeMillis();
			if(direction == 0) {
				//this.height = y;
				//this.y = 0;
			}
			/*if(direction == 4)*/
				this.height = 10000;
			/*if(direction == 2) 				
				this.width  = 10000;
			if(direction == 6) {				
				this.width = x;
				this.x = 0;
			}*/
			
			speed = 0;
			/*System.out.print("x: "+ x);
			System.out.print(" y: "+ y);
			System.out.print(" width: "+ width);
			System.out.println(" height: "+ height);
			*/
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {	
		color = setColor(g, starShip);
		
		/*rect = g.fillRect((int)x, (int)y, (int)width, (int)height);		*/
		if(laser) {
			drawRotatedRect(g, 45*direction + 180, (int)x, (int)y, (int)width, (int)height, color);
		}else {
			drawRotatedRect(g, 45*direction, (int)x, (int)y, (int)width, (int)height, color);
		}
		
	}
	
	public void checkJustification(GameStatus gameStatus) {
		if( x < 0 - width|| x > gameStatus.getCanvas().getWidth() || y < 0 - height || y > gameStatus.getCanvas().getHeight()) {
			gameStatus.junk.add(this);
		}
		long time = System.currentTimeMillis();
		if(laser) {
			if(bornTime < time - 50)
				gameStatus.junk.add(this);
		}
	}
	public boolean checkLaserHit(Drawable drawable) {
		if(laser) {
			/*for(double dx = drawable.x; dx <= drawable.x + drawable.height; dx++) {
				for(double dy = drawable.y; dy <= drawable.y + drawable.height; dy++) {
					if(dx >= x && dx <= x + height && dy >= y && dy <= y + height)
						return true;
				}
			}*/
			
			
				for(double hypotenuse  = 0; hypotenuse < StarScrapMain.canvas.getWidth() + StarScrapMain.canvas.getHeight();hypotenuse++) {
					double x = (hypotenuse * Math.cos((direction-2)*(Math.PI/4)))+this.x;
					double y = (hypotenuse * Math.sin((direction-2)*(Math.PI/4)))+this.y;
					
					if(x > drawable.x && x < drawable.x + drawable.width && y > drawable.y && y < drawable.y + drawable.height) {
						return true;
					}
				}
			
			
		}	
		return false;
	}
	
	public void move() {
		long time = System.currentTimeMillis();
		if(direction == 0)
			y -= calculateMovement(time, lastTime, speed);
		
		if(direction == 1) {
			y -= calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
			x += calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
		}
		
		if(direction == 2)
			x += calculateMovement(time, lastTime, speed);
		
		if(direction == 3) {
			y += calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
			x += calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
		}
		
		if(direction == 4)
			y += calculateMovement(time, lastTime, speed);
		
		if(direction == 5) {
			y += calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
			x -= calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
		}
		
		if(direction == 6)
			x -= calculateMovement(time, lastTime, speed);
		
		if(direction == 7) {
			y -= calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
			x -= calculateMovement(time, lastTime, speed)/ Math.sqrt(2);
		}
		
		lastTime = time;		
	}

}
