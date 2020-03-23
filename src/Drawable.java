import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public  class Drawable {
	double defaultSpeed;
	double speed;
	double boost;
	double x;
	double y;	
	double width;
	double height;
	
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getSpeed() {
		return speed;
	}
	public void paint(Graphics g, GameStatus gameSystem) {
		
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public double getIntervalInMilis() {
		//System.out.println(speed);
		return 1000d/speed;
		
	}
	public double convert(double speed) {
		return speed / 1000d;
	}
	public double calculateMovement(long time, long lastTime, double speed) {
		if(lastTime!=0) {
			long deltaT = time - lastTime;
			return (double)deltaT * speed;
			
		}
		else return 0;
	}
	public long getLastTime(long time, long lastTime,int keyCode, double limit, ArrayList<Integer>  keysDown) {
		if(keysDown.contains(keyCode) && limit > 0) {		
			lastTime = time;
			
		}else {
			lastTime = 0;
		}
		return lastTime;
	}
	public boolean checkColisionInternal(Drawable drawable1, Drawable drawable2) {
		boolean colision = false;
		if(drawable1.x >= drawable2.x && drawable1.x <= drawable2.x + drawable2.width) {
			if((drawable1.y >= drawable2.y && drawable1.y <= drawable2.y + drawable2.height) || (drawable1.y + drawable1.height >= drawable2.y && drawable1.y + drawable1.height <= drawable2.y + drawable2.height)) {
				colision = true;
				//System.out.println("colizion");
			}
		}	
		if(drawable1.x + drawable1.width >= drawable2.x && drawable1.x + drawable1.width < drawable2.x +drawable2.width) {
			if((drawable1.y >= drawable2.y && drawable1.y <= drawable2.y + drawable2.height) || (drawable1.y + drawable1.height >= drawable2.y && drawable1.y + drawable1.height <= drawable2.y + drawable2.height)){
				colision = true;
				//System.out.println("colizion");
			}
		}
		
		return colision;
	}
	public boolean checkColision(Drawable drawable1, Drawable drawable2) {
		
		
		return checkColisionInternal(drawable1, drawable2) || checkColisionInternal(drawable2, drawable1);
		
	}
	public int setColor(Graphics g, Image starShip) {
		g.setColor(Color.WHITE);
		int color = 0;
		if(starShip == StarScrapMain.starShip1) { 
			g.setColor(Color.RED);
			color = 1;
		}	
		if(starShip == StarScrapMain.starShip2) {
			g.setColor(Color.BLUE);
			color = 2;
		}	
		if(starShip == StarScrapMain.starShip3) {
			g.setColor(Color.GREEN);
			color = 3;
		}
		return color;	
	}
	public void toRandomPosition(Player player) {
		player.x = Math.random()*StarScrapMain.gameStatus.getCanvas().getWidth()-player.width;
		player.y = Math.random()*StarScrapMain.gameStatus.getCanvas().getHeight()-player.height;
	}
}
