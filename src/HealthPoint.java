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
				x -= canvasWidth / 26331.5;
				y -= canvasWidth / 26331.5;
				width = canvasWidth/70;//54,85714285714286
				beat = true;
			}else {
				beat = false;
				x += canvasWidth / 26331.5;
				y += canvasWidth / 26331.5;
				width = canvasWidth/80;//48
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
