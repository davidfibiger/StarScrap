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
	ArrayList<Meteorite> meteorites;
	public ArrayList<Star> stars;
	public ArrayList<Spark> sparks;
	public ArrayList<Sound> shotSounds;
	public ArrayList<Sound> deathSounds;
	public ArrayList<Sound> laserSounds;
	static boolean skinPick = false;
	static boolean menu = true;
	//Player activeMenuPlayer;
	ArrayList<Player> activeSkinPickPlayers;
	Player player1;
	Player player2;
	//Player player3;
	public GameStatus() {
		shotSounds = new ArrayList<Sound>();
		deathSounds = new ArrayList<Sound>();
		laserSounds = new ArrayList<Sound>();
		meteorites = new ArrayList<Meteorite>();
		addSounds(shotSounds, StarScrapMain.shotSoundPath);
		addSounds(deathSounds, StarScrapMain.deathSoundPath);
		addSounds(laserSounds, StarScrapMain.laserSoundPath);
		drawables = new ArrayList();
		players = new ArrayList();
		junk = new ArrayList();
		bullets = new ArrayList();
		usedLasers = new ArrayList();
		activeSkinPickPlayers = new ArrayList();
		stars = new ArrayList();
		sparks = new ArrayList();
		
		//player3 = new Player(3, 101, 98, 97, 99, 96, 10,KeyEvent.VK_3);
		//activeMenuPlayer = player1;
		/*
		activeSkinPickPlayers.add(player1);
		activeSkinPickPlayers.add(player2);
		drawables.add(player1);
		drawables.add(player2);
		//drawables.add(player3);
		players.add(player1);
		players.add(player2);
		//players.add(player3);
		*/
		
	}
	public void addPlayer(Player player) {
		activeSkinPickPlayers.add(player);
		drawables.add(player);
		players.add(player);
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
