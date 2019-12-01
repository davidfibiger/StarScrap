import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

public class GameController extends MouseAdapter implements KeyListener{
	
	private boolean firstMenu = true;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	GameStatus gameStatus;
	DrawingCanvas canvas;
	private ArrayList<Integer> keysDown;
	
	GameController(GameStatus gameStatus){
		this.gameStatus = gameStatus;
		keysDown = new ArrayList<Integer>();
		
	}
	
	boolean running = true;
	
	public void run(DrawingCanvas canvas) {
		this.canvas = canvas;

		while(running) {
			canvas.repaint();
			for(Player player : gameStatus.players) {
				player.movementListener(gameStatus, keysDown);
				player.actions(keysDown, gameStatus);
			}
			for(Bullet bullet : gameStatus.bullets) {
				bullet.checkOnScreen(gameStatus);
				bullet.move();
				for(Player player : gameStatus.players) { 
					if(bullet.checkColision(bullet, player) && bullet.order != player.order) {						
						gameStatus.junk.add(bullet);
						player.lives--;
						printLives(player);
						if(player.lives <= 0) {
							respawn(player);
						}
						
					}
						
				}
				
			}				
			clear();
			
			
			
		}
		  
		
	}
	public void printLives(Player player) {
		if(player == gameStatus.player1) {
			System.out.println("player1 lives: "+player.lives);
		}else {
			System.out.println("player2 lives: "+player.lives);						
		}
	}
	public void respawn(Player player) {
		gameStatus.activeMenuPlayer = player;
		player.starShip = null;
		gameStatus.menu = true;
		player.lives = 1;
	}
	public void clear() {
		/*
		System.out.print("junk: "+gameStatus.junk.size());
		System.out.print("bullets: "+gameStatus.bullets.size());
		System.out.println("drawables: "+gameStatus.drawables.size());
		*/
		for(Drawable drawable : gameStatus.junk) {
			
			gameStatus.bullets.remove(drawable);
			gameStatus.drawables.remove(drawable);
		}
		gameStatus.junk.clear();
	}
	public void mousePressed(MouseEvent e) {
		//System.out.println(e.getX()+", "+e.getY());
		if(gameStatus.menu) {
			chooseStarShip(e);
		}
	}
	private void chooseStarShip(MouseEvent e) {
		int x = (int)canvas.getWidth()/7;
		int y = ((int)canvas.getHeight()/7)*3;
		if(e.getY() > y && e.getY() < (y/3)*4) {
			if(e.getX() > x && e.getX() < x*2) {				
				activeMenuPlayerToCenter();				
				gameStatus.activeMenuPlayer.starShip = StarScrapMain.starShip1;
				endOrContinueMenu();				
			}
			
			if (e.getX() > x*3 && e.getX() < x*4) {				
				activeMenuPlayerToCenter();
				gameStatus.activeMenuPlayer.starShip = StarScrapMain.starShip2;
				endOrContinueMenu();
				
			}
			if(e.getX() > x*5 && e.getX() < x*6) {	
				activeMenuPlayerToCenter();
				gameStatus.activeMenuPlayer.starShip = StarScrapMain.starShip3;
				endOrContinueMenu();
			}
		}
	}
	public void endOrContinueMenu() {
		if(firstMenu) {
			firstMenu = false;
			boolean last = isPlayerLast();	
			if(last) {
				GameStatus.menu = false;
				return;
			}
			boolean setNow = false;
			for(Player player : gameStatus.players) {
				if(setNow) {
					gameStatus.activeMenuPlayer = player;
				}
				if(player == gameStatus.activeMenuPlayer) {
					setNow = true;
				}				
			}
		}else {
			GameStatus.menu = false;
			return;
		}
	}
	public boolean isPlayerLast() {
		boolean last = false;
		for(Player player : gameStatus.players) {
			last = false;
			if(player == gameStatus.activeMenuPlayer) {
				last = true;						
			}
		}
		return last;
	}
	private void activeMenuPlayerToCenter() {
		if(gameStatus.activeMenuPlayer.starShip == null) {
			gameStatus.activeMenuPlayer.x = gameStatus.getCanvas().getWidth()/2;
			gameStatus.activeMenuPlayer.y = gameStatus.getCanvas().getHeight()/2;
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("TYPED"+e.getKeyCode());
	}
	@Override
	public void keyPressed(KeyEvent e) {
		//System.out.println("pressed"+e.getKeyCode());
		if(!keysDown.contains(e.getKeyCode())) {
			keysDown.add(e.getKeyCode());
			System.out.println(e.getKeyCode());
			//System.out.println(e.getKeyCode());
		}
		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		keysDown.remove((Integer)e.getKeyCode());
		//System.out.println("released"+e.getKeyCode());
		
	}
		
		
	
}
