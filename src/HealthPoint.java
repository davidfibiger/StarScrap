import java.awt.Color;
import java.awt.Graphics;

public class HealthPoint extends Drawable{
	long spawnTime;
	int despawnTime = 10000;
	long lastBeat;
	boolean beat = false;
	public HealthPoint() {
		lastBeat = System.currentTimeMillis();
		width = canvasWidth/80;
		height = width;
		x = Math.random()*(double)(canvasWidth-width);
		y = Math.random()*(double)(canvasHeight-height);
		spawnTime = System.currentTimeMillis();
	}
	public void update(GameStatus gameStatus) {
		if(lastBeat+250<System.currentTimeMillis()) {
			lastBeat = System.currentTimeMillis();
			if(!beat) {
				x -= ((canvasWidth / 70) - (canvasWidth/80))/2;
				y -= ((canvasWidth / 70) - (canvasWidth/80))/2;
				width = canvasWidth/70;
				height = width;
				beat = true;
			}else {
				beat = false;
				x += ((canvasWidth / 70) - (canvasWidth/80))/2;
				y += ((canvasWidth / 70) - (canvasWidth/80))/2;
				width = canvasWidth/80;
				height = width;
			}
		}
		if(spawnTime + despawnTime<System.currentTimeMillis()) {
			gameStatus.junk.add(this);
		}
	}
	public void paint(Graphics g, GameStatus gameStatus) {
		g.setColor(new Color(158,19,22));
		g.fillRect((int)x, (int)(y+height*((double)3/8)), (int)width, (int)(height/4));
		g.fillRect((int)(x+width*((double)3/8)), (int)y, (int)width/4, (int)height);
	}
}
