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
	static boolean menu = false;
	//Player activeMenuPlayer;
	ArrayList<Player> activeMenuPlayers;
	Player player1;
	Player player2;
	//Player player3;
	public GameStatus() {
		
		drawables = new ArrayList();
		players = new ArrayList();
		junk = new ArrayList();
		bullets = new ArrayList();
		usedLasers = new ArrayList();
		activeMenuPlayers = new ArrayList();
		player1 = new Player(1, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_SHIFT, KeyEvent.VK_SPACE, KeyEvent.VK_1);
		player2 = new Player(2, 91, 222, 59, 92, 44, 17,KeyEvent.VK_2);
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
	


}
