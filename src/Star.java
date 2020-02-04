import java.awt.Color;
import java.awt.Graphics;

public class Star extends Drawable {
	long lastTime;
	public Star() {
		speed = convert((double)(Math.random()*2000));
		x = Math.random() * StarScrapMain.gameStatus.getCanvas().getWidth();
		y = -100;
		width = Math.random() * 8;
		height = width;
		//System.out.println("pada hvezda neco si prej");
	}

	public void update() {
		long time = System.currentTimeMillis();
		if(lastTime > 0) {
			y += calculateMovement(time, lastTime, speed);
		}
		lastTime = time;
		if(y > StarScrapMain.gameStatus.getCanvas().getHeight() + 100) {
			StarScrapMain.gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		g.setColor(Color.LIGHT_GRAY);
		//g.setColor(new Color(255, 255, 179));
		g.fillRect((int)x, (int)y, (int)width, (int)height);
	}

}
