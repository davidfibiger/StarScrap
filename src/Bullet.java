import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Bullet extends Drawable {
	private long lastTime;
	//public double height;
	//public double width;
	private int direction;
	public int order;
	private Image starShip;
	public boolean laser;
	private long bornTime;
	public int color;
	
	public Bullet(double x, double y, int direction, int order, Image starShip, boolean laser) {		
		speed = convert((double)((double)StarScrapMain.canvas.getWidth() / (double)1.536));
		this.direction = direction;
		if(direction == 3 || direction == 7) {
			height = (double)((double)StarScrapMain.canvas.getWidth() / (double)700);
			width = (double)((double)StarScrapMain.canvas.getWidth() / (double)150);
		}else {
			height = (double)((double)StarScrapMain.canvas.getWidth() / (double)150);
			width = (double)((double)StarScrapMain.canvas.getWidth() / (double)700);
		}
		this.x = x;
		this.y = y;
		this.order = order;
		this.starShip = starShip;
		this.laser = laser;
		if(laser) {
			bornTime = System.currentTimeMillis();
			if(direction == 1) {
				this.height = y;
				this.y = 0;
			}
			if(direction == 5)
				this.height = 10000;
			if(direction == 3) 				
				this.width  = 10000;
			if(direction == 7) {				
				this.width = x;
				this.x = 0;
			}
			
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
		
		g.fillRect((int)x, (int)y, (int)width, (int)height);		
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
			for(double dx = drawable.x; dx <= drawable.x + drawable.width; dx++) {
				for(double dy = drawable.y; dy <= drawable.y + drawable.width; dy++) {
					if(dx >= x && dx <= x + width && dy >= y && dy <= y + height)
						return true;
				}
			}
		}	
		return false;
	}
	
	public void move() {
		long time = System.currentTimeMillis();
		if (direction == 1)
			y -= calculateMovement(time, lastTime, speed);
		if(direction == 3)
			x += calculateMovement(time, lastTime, speed);
		if(direction == 5)
			y += calculateMovement(time, lastTime, speed);
		if(direction == 7)
			x -= calculateMovement(time, lastTime, speed);
		lastTime = time;		
	}

}
