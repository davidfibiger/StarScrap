import java.awt.Color;
import java.awt.Graphics;

public class Spark extends Drawable{
	private double speedX;
	private double speedY;
	private long lastTime;
	private int direction;
	private double size;
	int color;
	public Spark(int x, int y, double size, int color){
		this.size = size/100;
		width = Math.random() * ( StarScrapMain.gameStatus.getCanvas().getWidth()/300)*this.size;
		height = width;
		direction = (int) (Math.random()*360); 
		speedX = convert(380 + (Math.random()*(StarScrapMain.gameStatus.getCanvas().getWidth()/3.48))*this.size);
		speedY = speedX;
		this.x = x;
		this.y = y;
		this.color = color;
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
		if(color == 1)
			g.setColor(Color.red);
		if(color == 2)
			g.setColor(Color.blue);
		if(color == 3)
			g.setColor(Color.green);
		if(color == 4) {
			g.setColor(Color.yellow);
		}
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}
}
