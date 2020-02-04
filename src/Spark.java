import java.awt.Color;
import java.awt.Graphics;

public class Spark extends Drawable{
	private double speedX;
	private double speedY;
	private long lastTime;
	private double direction;
	private int angle;
	
	public Spark(int x, int y){
		width = Math.random() * 8;
		height = width;
		angle = (int) (Math.random()*360); 
		speedX = convert(380 + (Math.random()*1000));
		speedY = convert(380 + (Math.random()*1000));
		direction = Math.random()*4;
		this.x = x;
		this.y = y;
	}
	public void update() {
		long time = System.currentTimeMillis();
		
		if(lastTime > 0) {
			x += Math.sin(angle) * calculateMovement(time, lastTime, speedX);
			y += Math.cos(angle) * calculateMovement(time, lastTime, speedY);
		}
		lastTime = time;
		
		if(x < -100 || x > StarScrapMain.gameStatus.getCanvas().getWidth() +100 || y < -100 || y > StarScrapMain.gameStatus.getCanvas().getHeight() +100) {
			StarScrapMain.gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		g.setColor(Color.yellow);
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
