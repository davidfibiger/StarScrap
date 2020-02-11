import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStatus {
	
	DrawingCanvas canvas;
	ArrayList<Drawable> drawables;
	ArrayList<Player> players;
	ArrayList<Drawable> junk;
	ArrayList<Bullet> bullets;
	ArrayList<Bullet> usedLasers;
	public ArrayList<Star> stars;
	public ArrayList<Spark> sparks;
	public ArrayList<Sound> shotSounds;
	public ArrayList<Sound> deathSounds;
	public ArrayList<Sound> laserSounds;
	static boolean menu = false;
	//Player activeMenuPlayer;
	ArrayList<Player> activeMenuPlayers;
	Player player1;
	Player player2;
	//Player player3;
	public GameStatus() {
		shotSounds = new ArrayList<Sound>();
		deathSounds = new ArrayList<Sound>();
		laserSounds = new ArrayList<Sound>();
		addSounds(shotSounds, StarScrapMain.shotSoundPath);
		addSounds(deathSounds, StarScrapMain.deathSoundPath);
		addSounds(laserSounds, StarScrapMain.laserSoundPath);
		drawables = new ArrayList();
		players = new ArrayList();
		junk = new ArrayList();
		bullets = new ArrayList();
		usedLasers = new ArrayList();
		activeMenuPlayers = new ArrayList();
		stars = new ArrayList();
		sparks = new ArrayList();
		player1 = new Player(1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE, KeyEvent.VK_1);
		player2 = new Player(2, KeyEvent.VK_I, KeyEvent.VK_K, KeyEvent.VK_J, KeyEvent.VK_L, KeyEvent.VK_B,KeyEvent.VK_ENTER,KeyEvent.VK_2);
		//player3 = new Player(3, 101, 98, 97, 99, 96, 10,KeyEvent.VK_3);
		//activeMenuPlayer = player1;
		activeMenuPlayers.add(player1);
		activeMenuPlayers.add(player2);
		drawables.add(player1);
		drawables.add(player2);
		//drawables.add(player3);
		players.add(player1);
		players.add(player2);
		//players.add(player3);
		
		
	}
	public DrawingCanvas getCanvas() {
		return canvas;
	}
	public void setCanvas(DrawingCanvas canvas) {
		this.canvas = canvas;
	}
	public void addSounds(ArrayList sounds, String path) {
		for(int s = 20; s >= 0; s--) {
			sounds.add(new Sound(path));
		}
	}
	Sound getSoundFrom(ArrayList<Sound> sounds) {
		Sound soundMax = null;
		int max = 0;
		for(Sound sound : sounds) {	
			
			
			if(max < sound.clip.getFramePosition()) { 
				max = sound.clip.getFramePosition();
				soundMax = sound;
			}
			if(sound.clip.getFramePosition() == 0) {
				return sound;			
			}
		}
		
		for(Sound s : sounds) {
			
		}
		return soundMax;
		
	}


}
