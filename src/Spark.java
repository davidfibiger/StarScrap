import java.awt.Color;
import java.awt.Graphics;

public class Spark extends Drawable{
	private double speed;
	private long lastTime;
	private int direction;
	private double size;
	private double dSpeedX;
	private double dSpeedY;
	Color color;
	public Spark(int x, int y, double size, Color color, double dSpeedX, double dSpeedY){
		this.size = size/100;
		width = Math.random() * ( StarScrapMain.gameStatus.getCanvas().getWidth()/300)*this.size;
		height = width;
		direction = (int) (Math.random()*360); 
		speed = convert(380 + (Math.random()*(StarScrapMain.gameStatus.getCanvas().getWidth()/3.48))*this.size);
		this.x = x;
		this.y = y;
		this.color = color;
		this.dSpeedX = dSpeedX;
		this.dSpeedY = dSpeedY;
	}
	public void update() {
		long time = System.currentTimeMillis();
		
		if(lastTime > 0) {
			x += (Math.sin(direction) * calculateMovement(time, lastTime, speed))+calculateMovement(time, lastTime, dSpeedX);
			y += (Math.cos(direction) * calculateMovement(time, lastTime, speed))+calculateMovement(time, lastTime, dSpeedY);
		}
		lastTime = time;
		
		if(x < -100 || x > StarScrapMain.gameStatus.getCanvas().getWidth() +100 || y < -100 || y > StarScrapMain.gameStatus.getCanvas().getHeight() +100) {
			StarScrapMain.gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {		
			g.setColor(color);
		
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
