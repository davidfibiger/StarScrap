import java.awt.Graphics;
import java.awt.Color;
public class Meteorite extends Drawable{
	//public double x;
	//public double y;
	private long time;
	private long lastTime;
	private int target;
	private double spawningField;
	//private double speed;
	private double speedX;
	private double speedY;
	double deltaX;
	double deltaY;
	private double img;
	//public double width;
	//public double height;
	private double direction;
	private int intTarget;
	public Color color = Color.orange;
	
	public Meteorite() {
		img = Math.random()*2;
		speed = convert(((double)StarScrapMain.canvas.getWidth() / (double)12) + (Math.random()* (double)((double)StarScrapMain.canvas.getWidth() / (double)8)));
		width = (StarScrapMain.canvas.getWidth()/80) + (Math.random() * StarScrapMain.canvas.getWidth()/80);
		height = width;
		setSpawnLocation();
		target = (int)(Math.random()*2);
		if(target < 1) {
			setDeltas(StarScrapMain.gameStatus.player1);
			intTarget = 1;
		}else {
			setDeltas(StarScrapMain.gameStatus.player2);
		}
		
		direction = Math.atan(deltaY/deltaX);
		speedX = Math.cos(direction) * speed;
		speedY = Math.sin(direction) * speed;
		StarScrapMain.gameStatus.drawables.add(this);
	}
	public void setDeltas(Player player) {
		deltaX = player.x - x;
		deltaY = player.y - y;
	}
	public void update() {
		time = System.currentTimeMillis();
		if(lastTime>0) {
			x += calculateMovement(time, lastTime, speedX);
			y += calculateMovement(time, lastTime, speedY);
		}
		lastTime = time;
		
		if(x < -1000 || x > 1000 + StarScrapMain.canvas.getWidth() || y < -1000 || y > 1000 + StarScrapMain.canvas.getHeight()) {
			StarScrapMain.gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		if(img<1) {
			g.drawImage(StarScrapMain.meteorite1, (int)x, (int)y, (int)width, (int)height, null);
		}else {
			g.drawImage(StarScrapMain.meteorite2, (int)x, (int)y, (int)width, (int)height, null);
		}
		/*if(intTarget == 1) {
			g.setColor(Color.red);
		}else {
			g.setColor(Color.blue);
		}
		g.drawRect((int)x, (int)y, (int)width, (int)height);*/
	}

	public void setSpawnLocation() {
		spawningField = Math.random()*4;
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
		}else{
			//right
			x = 250 + StarScrapMain.canvas.getWidth();
			y = -250 + ((Math.random() * StarScrapMain.canvas.getHeight()) + Math.random() * 500);
			System.out.println("right");
		}
	}
	
	
}
