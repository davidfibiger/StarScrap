import java.awt.Color;
import java.awt.Graphics;

public class Spark extends Drawable{
	private double speedX;
	private double speedY;
	private long lastTime;
	private int direction;
	
	public Spark(int x, int y){
		width = Math.random() * ( StarScrapMain.gameStatus.getCanvas().getWidth()/300);
		height = width;
		direction = (int) (Math.random()*360); 
		speedX = convert(380 + (Math.random()*(StarScrapMain.gameStatus.getCanvas().getWidth()/3.48)));
		speedY = convert(380 + (Math.random()*(StarScrapMain.gameStatus.getCanvas().getWidth()/3.48)));
		this.x = x;
		this.y = y;
	}
	public void update() {
		long time = System.currentTimeMillis();
		
		if(lastTime > 0) {
			x += Math.sin(direction) * calculateMovement(time, lastTime, speedX);
			y += Math.cos(direction) * calculateMovement(time, lastTime, speedY);
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
