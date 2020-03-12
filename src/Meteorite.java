import java.awt.Graphics;
import java.awt.Color;
public class Meteorite extends Drawable{
	//public double x;
	//public double y;
	private long time;
	private long lastTime;
	private int target;
	private int spawningField;
	//private double speed;
	private double speedX;
	private double speedY;
	double deltaX;
	double deltaY;
	//public double width;
	//public double height;
	private double direction;
	private int intTarget;
	
	public Meteorite() {
		speed = convert( /*Math.random()* (double)((double)StarScrapMain.canvas.getWidth() / (double)1.5)*/550);
		width = (StarScrapMain.canvas.getWidth()/80) + (Math.random() * StarScrapMain.canvas.getWidth()/80);
		height = width;
		setSpawnLocation();
		target = (int)(Math.random()*2);
		if(target < 1) {
			deltaX = StarScrapMain.gameStatus.player1.x - x;
			deltaY = StarScrapMain.gameStatus.player1.y - y;
			intTarget = 1;
		}else {
			deltaX = StarScrapMain.gameStatus.player2.x - x;
			deltaY = StarScrapMain.gameStatus.player2.y - y;
		}
		
		direction = 1/(Math.tan(deltaY/deltaX));
		speedX = Math.cos(direction) * speed;
		speedY = Math.sin(direction) * speed;
		StarScrapMain.gameStatus.drawables.add(this);
	}
	public void update() {
		time = System.currentTimeMillis();
		if(lastTime>0) {
			x += calculateMovement(time, lastTime, speedX);
			y += calculateMovement(time, lastTime, speedY);
		}
		lastTime = time;
		
		if(x < -300 || x > 300 + StarScrapMain.canvas.getWidth() || y < -300 || y > 300 + StarScrapMain.canvas.getHeight()) {
			StarScrapMain.gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		//g.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, observer)
		if(intTarget == 1) {
			g.setColor(Color.red);
		}else {
			g.setColor(Color.blue);
		}
		g.drawRect((int)x, (int)y, (int)width, (int)height);
	}

	public void setSpawnLocation() {
		spawningField = (int)(Math.random()*4);
		if(spawningField < 1) {
			//up
			x = -250 + ((Math.random() * StarScrapMain.canvas.getWidth()) + Math.random() * 500);
			y = -250;
		}else if(spawningField < 2) {
			//down
			x = -250 + ((Math.random() * StarScrapMain.canvas.getWidth()) + Math.random() * 500);
			y = 250 + StarScrapMain.canvas.getHeight();
		}else if(spawningField < 3) {
			//left
			x = -250;
			y = -250 + ((Math.random() * StarScrapMain.canvas.getHeight()) + Math.random() * 500);
		}else {
			//right
			x = 250 + StarScrapMain.canvas.getWidth();;
			y = -250 + ((Math.random() * StarScrapMain.canvas.getHeight()) + Math.random() * 500);
		}
	}
	
	
}
