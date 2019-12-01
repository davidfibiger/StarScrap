import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class Bullet extends Drawable {
	private long lastTime;
	public double height;
	public double width;
	private int direction;
	public int order;
	private Image starShip;
	
	public Bullet(double x, double y, int direction, int order, Image starShip, boolean laser) {		
		speed = convert(2500d);
		this.direction = direction;
		if(direction == 3 || direction == 7) {
			height = 5;
			width = 20;
		}else {
			height = 20;
			width = 5;
		}
		this.x = x;
		this.y = y;
		this.order = order;
		this.starShip = starShip;
		/*if(laser) {
			if(direction == 1) {
				height = 10000;
				y -= 10000;
			}
			if(direction == 5)
				height = 10000;
			if(direction == 3) 				
				width  = 10000;
			if(direction == 7) {
				x -= 10000;
				width  = 10000;
			}
			
			speed = 0;
		}*/
	}
	public void paint(Graphics g, GameStatus gameStatus) {	
		g.setColor(Color.WHITE);
		if(starShip == StarScrapMain.starShip1) 
			g.setColor(Color.RED);
		if(starShip == StarScrapMain.starShip2)
			g.setColor(Color.BLUE);
		if(starShip == StarScrapMain.starShip3)
			g.setColor(Color.GREEN);
		
		g.fillRect((int)x, (int)y, (int)width, (int)height);		
	}
	public void checkOnScreen(GameStatus gameStatus) {
		if( x < 0 - width|| x > gameStatus.getCanvas().getWidth() || y < 0 - height || y > gameStatus.getCanvas().getHeight()) {
			gameStatus.junk.add(this);
		}
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
